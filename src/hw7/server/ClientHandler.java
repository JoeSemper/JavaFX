package hw7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Server server;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;

    private String nick;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split("\\s");
                            if (token.length < 3) {
                                continue;
                            }
                            String newNick = server
                                    .getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                sendMsg("/authok " + newNick);
                                nick = newNick;
                                login = token[1];
                                server.subscribe(this);
                                System.out.printf("Клиент %s подключился \n", nick);
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }

                        server.broadcastMsg(str, nick);
                    }
                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/w ")){
                            String[] arr = str.split("\\s");
                            str="";
                            for (int i = 2; i <arr.length ; i++) {
                                str+=arr[i];
                            }
                            sendMsg(nick + ": " + str);
                            server.sendPrivateMsg(arr[1], str, nick);
                            continue;
                        }

                        if (str.equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        }

                        server.broadcastMsg(str, nick);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Клиент отключился");
                    server.unsubscribe(this);
                    try {
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void sendMsg(String str) {
        try {
            out.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}

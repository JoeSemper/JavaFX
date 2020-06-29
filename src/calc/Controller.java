package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField textField;

    private static int memory = 0;
    private static boolean flag;
    private static String prevAction;

    public void btnPressed(ActionEvent actionEvent) {
        if (flag){
            textField.setText("");
            flag = false;
        }
        Button btn = (Button) actionEvent.getSource();
        textField.setText(textField.getText() + btn.getText());
    }

    public void clear() {
        textField.clear();
        memory = 0;
    }


    public void actionMul() {
        int a = Integer.parseInt(textField.getText());

        if (flag) {
            return;
        }
        if (memory == 0) {
            memory = Integer.parseInt(textField.getText());
            flag = true;
            prevAction = "*";
            return;
        }

        memory *= a;
        textField.setText("" + memory);
        prevAction = "*";
        flag = true;


    }

    public void actionDiv() {
        int a = Integer.parseInt(textField.getText());

        if (flag) {
            return;
        }
        if (memory == 0) {
            memory = Integer.parseInt(textField.getText());
            flag = true;
            prevAction = "/";
            return;
        }
        memory /= a;
        textField.setText("" + memory);
        prevAction = "/";
        flag = true;
    }

    public void actionPlus() {
        int a = Integer.parseInt(textField.getText());

        if (flag) {
            return;
        }

        memory += a;
        textField.setText("" + memory);
        prevAction = "+";
        flag = true;
    }

    public void actionMinus() {
        int a = Integer.parseInt(textField.getText());

        if (flag) {
            return;
        }
        if (memory == 0) {
            memory = Integer.parseInt(textField.getText());
            flag = true;
            prevAction = "-";
            return;
        }

        memory -= a;
        textField.setText("" + memory);
        prevAction = "-";
        flag = true;
    }


    public void equal() {
        if (prevAction == "+"){
            actionPlus();
        } else if (prevAction == "-") {
            actionMinus();
        } else if (prevAction == "*") {
            actionMul();
        } else {
            actionDiv();
        }
        textField.setText("" + memory);
        flag = true;
    }
}

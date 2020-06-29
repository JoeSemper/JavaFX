package chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField textField;
    @FXML
    public Button send;
    @FXML
    public TextArea field;


    public void EnterPressed(ActionEvent actionEvent) {

        field.setText(field.getText() + textField.getText() +"\n");
        textField.clear();
    }


    public void Send(ActionEvent actionEvent) {

        field.setText(field.getText() + textField.getText() +"\n");
        textField.clear();
    }
}

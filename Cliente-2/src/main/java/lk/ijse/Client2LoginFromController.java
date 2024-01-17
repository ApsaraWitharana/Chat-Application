package lk.ijse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client2LoginFromController {

        @FXML
        private Button btnJoin;

        @FXML
        private Circle circle;

        @FXML
        private PasswordField txtPassword;

        @FXML
        private TextField txtUsername;

    public static List<String> users = new ArrayList<>();

    public static Image image;

    public static List list= new ArrayList();
    public static HashMap<String, Image> userLIst = new HashMap<>();
    public   static String userName;
        @FXML
        void btnJoinOnAction(ActionEvent event) {

            if(!txtUsername.getText().equals("")) {

                userName = txtUsername.getText();

                if (users.contains(txtUsername.getText())) {
                    new Alert(Alert.AlertType.ERROR,"already added").show();
                } else {
                    users.add(userName);
                    userLIst.put(txtUsername.getText(), image);
                    try {
                        Stage stage = new Stage();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Client2ChatForm.fxml"))));
                        stage.setTitle("Chat Room");
                        stage.show();
                        txtUsername.setText("");
                        circle.setFill(null);
                        image = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else
                new Alert(Alert.AlertType.ERROR, "Please enter your name!").show();
        }



        @FXML
        void txtPasswordOnAction(ActionEvent event) {

        }

        @FXML
        void txtUsernameOnAction(ActionEvent event) {

        }

    }



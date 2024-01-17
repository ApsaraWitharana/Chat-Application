package lk.ijse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lk.ijse.Model.LoginModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client1LoginFromController {

    public static List<String> users = new ArrayList<>();

    public static Image image;

    public static List list= new ArrayList();
    public static HashMap<String, Image> userLIst = new HashMap<>();

        @FXML
        private Circle circle;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

  public   static String userName;



    @FXML
    void btnJoinOnAction(ActionEvent event) throws IOException, SQLException {

// database eken data arn user log wima

//        var LoginModel = new LoginModel();
//       boolean isCorrect = LoginModel.isCurrect(txtUsername.getText(), txtPassword.getText());
//
//        if (isCorrect) {
//            Parent root = FXMLLoader.load(getClass().getResource("/view/Client1ChatForm.fxml"));
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.centerOnScreen();
//            stage.show();

      //  }

        if(!txtUsername.getText().equals("")) {

            userName = txtUsername.getText();

            if (users.contains(txtUsername.getText())) {
                new Alert(Alert.AlertType.ERROR,"already added").show();
            } else {
                users.add(userName);
                userLIst.put(txtUsername.getText(), image);
                try {
                    Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Client1ChatForm.fxml"))));
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



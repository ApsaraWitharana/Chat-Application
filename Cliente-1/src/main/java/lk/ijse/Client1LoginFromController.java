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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.Model.LoginModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client1LoginFromController {

    public static List<String> users = new ArrayList<>();

    public static Image image;

    public static List list= new ArrayList();
    public static HashMap<String, Image> userLIst = new HashMap<>();
     //String user_name;

    @FXML
        private Circle circle;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

  public static   String user_name;

    @FXML
    private AnchorPane loginPane;

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
//            user_name = txtUsername.getText();
//            txtUsername.clear();
//            String user_name = txtUsername.getText();
//            Client1chatFromController client1chatFromController = new Client1chatFromController();
//             client1chatFromController.getName(user_name);
//        }


// nomal log
                 user_name = txtUsername.getText();
                 txtUsername.clear();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Client1ChatForm.fxml"))));
                    stage.setTitle("Chat Room");
                    stage.show();

                   //btnJoin .getScene().getWindow().hide();
   }



    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        btnJoinOnAction(actionEvent);
    }

    @FXML
    void btnImageOnAction(MouseEvent event) {

    }


    public void btnImageOnAction(javafx.scene.input.MouseEvent actionEvent) {
        Window window = ((Node) (actionEvent.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);
        actionEvent.consume();
        try {
            InputStream in = new FileInputStream(file);
            image = new Image(in);
            circle.setFill(new ImagePattern(image));

        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}



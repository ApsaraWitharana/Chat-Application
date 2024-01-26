package lk.ijse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client3LoginFormController {

        @FXML
        private Button btnJoin;

        @FXML
        private Circle circle;

        @FXML
        private TextField txtUsername;

        @FXML
        public static List<String> users = new ArrayList<>();

    public static Image image;

    public static List list= new ArrayList();
    public static HashMap<String, Image> userLIst = new HashMap<>();
    static String user_name;
    private DataInputStream dtin;
    private DataOutputStream dtout;
    @FXML
    void btnJoinOnAction(ActionEvent event) throws IOException {



//            if(!txtUsername.getText().equals("")) {
//
//                user_name = txtUsername.getText();
//
//                if (users.contains(txtUsername.getText())) {
//                    new Alert(Alert.AlertType.ERROR,"already added").show();
//                } else {
//                    users.add(user_name);
//                    userLIst.put(txtUsername.getText(), image);
//                    try {
//                        Stage stage = new Stage();
//                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Client2ChatForm.fxml"))));
//                        stage.setTitle("Chat Room");
//                        stage.show();
//                        txtUsername.setText("");
//                        circle.setFill(null);
//                        image = null;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }else
//                new Alert(Alert.AlertType.ERROR, "Please enter your name!").show();


        user_name = txtUsername.getText();
        txtUsername.clear();

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Client3ChatForm.fxml"))));
        stage.setTitle("Chat Room");
        stage.show();
        //stage.close();

    }

    @FXML
    void btnImageOnAction(MouseEvent actionEvent) {
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


    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent actionEvent) {
        //  btnJoinOnAction(actionEvent);
    }
    }



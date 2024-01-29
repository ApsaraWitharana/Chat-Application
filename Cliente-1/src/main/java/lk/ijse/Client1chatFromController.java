package lk.ijse;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;

import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class Client1chatFromController  implements Initializable {

    @FXML
    private ImageView iconFileClick;

    @FXML
    private VBox chatBox;

    @FXML
    private FlowPane emojiCategoryPane;

    @FXML
    private FlowPane emojiContainer;

    @FXML
    private Group grpEnterName;

    @FXML
    private Group grpMessageArea;

    @FXML
    private Label lblName;

    @FXML
    private ScrollPane spaneForFlowPane;

    @FXML
    private TextField txtSendmsg;

    Socket socket;
    private Socket clientSocket;
    private DataInputStream dtin;
    private DataOutputStream dtout;
    @FXML
    private static TextField txtUsername ;
    @FXML
    private Circle circle;

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private AnchorPane imgPane;

    @FXML
    private AnchorPane particatePane;

    public static List<String> users = new ArrayList<>();

    public static Image image;
   static String user_name;

      //  public static String user_name =String.valueOf(txtUsername);
   //public   String user_name = String.valueOf(txtUsername);
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
       // lblName.setText(String.valueOf(txtUsername));

       //Client1chatFromController client1chatFromController = new Client1chatFromController();
      // client1chatFromController.lblName.setText(user_name);
      //  lblName.setText(user_name);

        lblName.setText(Client1LoginFromController.user_name);

//        try {
//            setName();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        try {
            clientSocket = new Socket("localhost",3003);
            dtin = new DataInputStream(clientSocket.getInputStream());
            dtout = new DataOutputStream(clientSocket.getOutputStream());


            chatBox.setPadding(new Insets(20));
            chatBox.setSpacing(10);
            emojiContainer.setPadding(new Insets(10));
            emojiContainer.setHgap(20);
            emojiContainer.setVgap(20);
            emojiCategoryPane.setPadding(new Insets(0,10,0,10));
            emojiCategoryPane.setHgap(20);
            emojiContainer.setVisible(false);
            emojiCategoryPane.setVisible(false);
            spaneForFlowPane.setVisible(false);
            displaySmileyEmojis();
            dispayEmojiCategories();

            new Thread(()-> {
                try {
                    while (true){
                        String massage = dtin.readUTF();

                        if (massage.startsWith("image")){
                            String sender = dtin.readUTF();
                            Label senderLabel = new Label(sender+ ": ");
                            String path = dtin.readUTF();

                            ImageView imageView = new ImageView(new Image("file:" + path));
                            imageView.setFitWidth(192);
                            imageView.setPreserveRatio(true);

                            imageView.setOnMouseClicked(even ->{

                                try {
                                    File file = new File(path);
                                    Desktop.getDesktop().open(file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            Platform.runLater(() ->{
                                chatBox.getChildren().add(senderLabel);
                            });

                            Platform.runLater(()->{
                                chatBox.getChildren().add(imageView);
                            });
                        }else {
                            if (massage.startsWith("System")){
                                Label label = new Label(massage);
                                Platform.runLater(()->{
                                    chatBox.getChildren().add(label);
                                });
                            }else {
                                Platform.runLater(()->{
                                    HBox hBox = new HBox();
                                    hBox.setPadding(new Insets(5,15,5,15));
                                    hBox.setStyle("-fx-background-color: #6a1387; -fx-text-fill: #ffff;-fx-background-radius: 14");
                                    hBox.setAlignment(Pos.BASELINE_LEFT);
                                    Label label = new Label(massage);
                                    label.setTextFill(Color.WHITE);
                                    label.setMaxWidth(300);
                                    label.setWrapText(true);
                                    hBox.getChildren().add(label);
                                    hBox.setMaxWidth(Region.USE_PREF_SIZE);
                                    hBox.setMaxHeight(Region.USE_PREF_SIZE);
                                    hBox.setMinHeight(Region.USE_PREF_SIZE);
                                    hBox.setMinWidth(Region.USE_PREF_SIZE);
                                    StackPane stackPane = new StackPane(hBox);
                                    stackPane.setAlignment(Pos.BASELINE_LEFT);
                                    chatBox.getChildren().add(stackPane);
                                });
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }).start();


        } catch (IOException e) {
            System.out.println(e);

        }

        Platform.runLater(()->{
                Image backgroundImage = new Image("/image/wpp-wallpaper.jpg");
                BackgroundImage background = new BackgroundImage(backgroundImage,
                        BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
                chatBox.setBackground(new Background(background));

            Stage stage = (Stage) chatBox.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                event.consume();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(user_name);
                alert.setHeaderText("Are you sure you want to leave the chat?");
                alert.setContentText("Your data will be lost if you leave the chat application now");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    try {
                        dtout.writeUTF("pass-qpactk3i5710-xkdwisq@ee358fyndvndla98r478t35-jvvhjfv94r82@");
                        dtout.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    stage.close();
                }
            });

        }); //end of the initialize method

        if(image==null) {
            image = new Image("/image/whatsapp-dp-images-for-girl-wp-p.jpg");
        }
        circle.setFill(new ImagePattern(image));

    }



    private void setName() throws SQLException {
//        String user_name = String.valueOf(txtUsername);
//        lblName.setText(user_name);
//        System.out.println(user_name);

//        LoginModel model = new LoginModel();
//        ObservableList<String> obList = FXCollections.observableArrayList();
//        try {
//            List<LoginDTO> allUsers = model.AllUsers();
//            for (LoginDTO dto : allUsers) {
//                obList.add(dto.getUser_name());
//                System.out.println(dto.getUser_name());
//            }
//            lblName.setText(user_name);
//            System.out.println(txtUsername);
//        } catch (SQLException e) {
//            System.out.printf(e.getMessage());
//        }



//        LoginModel model = new LoginModel();
//
//        List<LoginDTO> allUsers = model.AllUsers();
//        lblName.setText(user_name);
//        System.out.println(user_name);
//
//        for (LoginDTO dto : allUsers) {
//            lblName.setText(user_name);
//        }


    }


    private void dispayEmojiCategories() {
        String[] emojiCategories = {"\uD83D\uDE06", "\uD83D\uDC2C", "\uD83C\uDF30"};

        for (String emoji : emojiCategories) {
            Label emojiLabel = new Label();
            emojiLabel.setText(emoji);
            emojiLabel.setStyle("-fx-font-size: 30");
            if (emoji.equals("\uD83D\uDE06")) {
                emojiLabel.setStyle("-fx-text-fill: green; -fx-font-size: 30;  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 5, 0, 0, 2)");
            }
            emojiLabel.setOnMouseClicked(event -> {
                String unicode = emoji;

                if (unicode.equals("\uD83D\uDE06")) {
                    displaySmileyEmojis();
                    changeColorOfEmojiCategories();
                    emojiLabel.setStyle("-fx-text-fill: green; -fx-font-size: 30;  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 5, 0, 0, 2)");
                } else if (unicode.equals("\uD83D\uDC2C")) {
                    displayAnimalEmojis();
                    changeColorOfEmojiCategories();
                    emojiLabel.setStyle("-fx-text-fill: green; -fx-font-size: 30;  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 5, 0, 0, 2)");
                } else if (unicode.equals("\uD83C\uDF30")) {
                    displayFoodEmojis();
                    changeColorOfEmojiCategories();
                    emojiLabel.setStyle("-fx-text-fill: green; -fx-font-size: 30;  -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 5, 0, 0, 2)");
                }
            });
            emojiCategoryPane.getChildren().add(emojiLabel);
        }
    }

    private void displayFoodEmojis() {


    }

    private void displayAnimalEmojis() {

        emojiContainer.getChildren().clear();

        String[] emojis = {
                "\uD83D\uDC2C", "\uD83D\uDC2D", "\uD83D\uDC2E", "\uD83D\uDC2F", "\uD83D\uDC30",
                "\uD83D\uDC31", "\uD83D\uDC32", "\uD83D\uDC33", "\uD83D\uDC34", "\uD83D\uDC35",
                "\uD83D\uDC36", "\uD83D\uDC37", "\uD83D\uDC38", "\uD83D\uDC39", "\uD83D\uDC3A",
                "\uD83D\uDC3B", "\uD83D\uDC3C", "\uD83D\uDC3D", "\uD83D\uDC3E", "\uD83D\uDC3F",
                "\uD83D\uDC40", "\uD83D\uDC41", "\uD83D\uDC42", "\uD83D\uDC43", "\uD83D\uDC44",
                "\uD83D\uDC45", "\uD83D\uDC46", "\uD83D\uDC47", "\uD83D\uDC48", "\uD83D\uDC49",
                "\uD83D\uDC4A", "\uD83D\uDC4B", "\uD83D\uDC4C", "\uD83D\uDC4D", "\uD83D\uDC4E",
                "\uD83D\uDC4F", "\uD83D\uDC50", "\uD83D\uDC51", "\uD83D\uDC52", "\uD83D\uDC53",
                "\uD83D\uDC54", "\uD83D\uDC55", "\uD83D\uDC56", "\uD83D\uDC57", "\uD83D\uDC58",
                "\uD83D\uDC59", "\uD83D\uDC5A", "\uD83D\uDC5B", "\uD83D\uDC5C", "\uD83D\uDC5D",
                "\uD83D\uDC5E", "\uD83D\uDC5F", "\uD83D\uDC60", "\uD83D\uDC61", "\uD83D\uDC62",
                "\uD83D\uDC63", "\uD83D\uDC64", "\uD83D\uDC65", "\uD83D\uDC66", "\uD83D\uDC67",
                "\uD83D\uDC68", "\uD83D\uDC69", "\uD83D\uDC6A", "\uD83D\uDC6B", "\uD83D\uDC6C",
                "\uD83D\uDC6D", "\uD83D\uDC6E", "\uD83D\uDC6F", "\uD83D\uDC70", "\uD83D\uDC71",
                "\uD83D\uDC72", "\uD83D\uDC73", "\uD83D\uDC74", "\uD83D\uDC75", "\uD83D\uDC76",
                "\uD83D\uDC77", "\uD83D\uDC78", "\uD83D\uDC79", "\uD83D\uDC7A", "\uD83D\uDC7B",
                "\uD83D\uDC7C", "\uD83D\uDC7D", "\uD83D\uDC7E", "\uD83D\uDC7F"
        };

        for (String emoji : emojis) {
            Label emojiLabel = new Label();
            emojiLabel.setText(emoji);
            emojiLabel.setStyle("-fx-font-size: 30");
            emojiLabel.setOnMouseClicked(event -> {
                String unicode = emoji;
                txtSendmsg.appendText(emoji);
                txtSendmsg.requestFocus();
                txtSendmsg.positionCaret(txtSendmsg.getText().length());
            });
            emojiContainer.getChildren().add(emojiLabel);
        }
    }

    private void displaySmileyEmojis() {

        emojiContainer.getChildren().clear();

        String[] emojis = {
                "\u263A", "\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03",
                "\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "\uD83D\uDE07", "\uD83D\uDE08",
                "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE0B", "\uD83D\uDE0C", "\uD83D\uDE0D",
                "\uD83D\uDE0E", "\uD83D\uDE0F", "\uD83D\uDE10", "\uD83D\uDE11", "\uD83D\uDE12",
                "\uD83D\uDE13", "\uD83D\uDE14", "\uD83D\uDE15", "\uD83D\uDE16", "\uD83D\uDE17",
                "\uD83D\uDE18", "\uD83D\uDE19", "\uD83D\uDE1A", "\uD83D\uDE1B", "\uD83D\uDE1C",
                "\uD83D\uDE1D", "\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE20", "\uD83D\uDE21",
                "\uD83D\uDE22", "\uD83D\uDE23", "\uD83D\uDE24", "\uD83D\uDE25", "\uD83D\uDE26",
                "\uD83D\uDE27", "\uD83D\uDE28", "\uD83D\uDE29", "\uD83D\uDE2A", "\uD83D\uDE2B",
                "\uD83D\uDE2C", "\uD83D\uDE2D", "\uD83D\uDE2E", "\uD83D\uDE2F", "\uD83D\uDE30",
                "\uD83D\uDE31", "\uD83D\uDE32", "\uD83D\uDE33", "\uD83D\uDE34", "\uD83D\uDE35",
                "\uD83D\uDE36", "\uD83D\uDE37", "\uD83D\uDE38", "\uD83D\uDE39", "\uD83D\uDE3A",
                "\uD83D\uDE3B", "\uD83D\uDE3C", "\uD83D\uDE3D", "\uD83D\uDE3E", "\uD83D\uDE3F",
                "\uD83D\uDE40", "\uD83D\uDE41", "\uD83D\uDE42", "\uD83D\uDE43", "\uD83D\uDE44",
                "\uD83D\uDE45", "\uD83D\uDE46", "\uD83D\uDE47", "\uD83D\uDE48", "\uD83D\uDE49",
                "\uD83D\uDE4A", "\uD83D\uDE4B", "\uD83D\uDE4C", "\uD83D\uDE4D", "\uD83D\uDE4E",
                "\uD83D\uDE4F",
        };

        for (String emoji : emojis) {
            Label emojiLabel = new Label();
            emojiLabel.setText(emoji);
            emojiLabel.setStyle("-fx-font-size: 30");
            emojiLabel.setOnMouseClicked(event -> {
                String unicode = emoji;
                txtSendmsg.appendText(emoji);
                txtSendmsg.requestFocus();
                txtSendmsg.positionCaret(txtSendmsg.getText().length());
            });
            emojiContainer.getChildren().add(emojiLabel);
        }
    }


    @FXML
    void btnEmojiOnAction(MouseEvent event) {

        if (emojiContainer.isVisible()){
            emojiContainer.setVisible(false);
            spaneForFlowPane.setVisible(false);
            emojiCategoryPane.setVisible(false);
        }else {
            emojiContainer.setVisible(true);
            spaneForFlowPane.setVisible(true);
            emojiCategoryPane.setVisible(true);
        }
    }

    private void changeColorOfEmojiCategories() {
        ObservableList<Node> children = emojiCategoryPane.getChildren();

        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.setStyle("-fx-text-fill: black; -fx-font-size: 30");
        }
    }


    @FXML
    void btnFileOnAction(MouseEvent mouseEvent) throws IOException {
        FileChooser fileChooser = new FileChooser(); //create the object
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) iconFileClick.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();//set the image to you select
            System.out.println("Selected image path: " + imagePath);
            dtout.writeUTF("image");     //sending the image to other clients
            dtout.writeUTF(lblName.getText() );//set name
            dtout.writeUTF(imagePath); //set image
            dtout.flush();

            ImageView imageView = new ImageView(new Image("file:" + imagePath));
            imageView.setFitWidth(192);
            imageView.setPreserveRatio(true);

            imageView.setOnMouseClicked(event -> {
                try {
                    File file = new File(imagePath);
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Platform.runLater(() -> {         //display image in the own clients display
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.BASELINE_RIGHT);
                hbox.getChildren().add(imageView);
                chatBox.getChildren().add(hbox);
            });
        }

        try {
            EventObject actionEvent = null;
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            File filePath = fileChooser.showOpenDialog(stage);
            dtout.writeUTF(lblName.getText()+ "::" + "img" + filePath.getPath());//set image to the path
            dtout.flush();
        }catch (NullPointerException e){
            System.out.println(e);
            System.out.println("Image not Selected!");
        }
        
    }

    @FXML
    void btnSendOnAction(MouseEvent event) {
        String massage = txtSendmsg.getText();

        if (!massage.isEmpty()){
            try {
                Platform.runLater(()->{
                    HBox hbox = new HBox();
                    hbox.setPadding(new Insets(5, 15, 5, 15));
                    hbox.setStyle("-fx-background-color: #ffff; -fx-text-fill: black;-fx-background-radius: 10");
                    hbox.setAlignment(Pos.BASELINE_RIGHT);
                    Label label = new Label(massage+"\n");
                    label.setMaxWidth(300);
                    label.setWrapText(true);
                    hbox.getChildren().add(label);
                    hbox.setMaxWidth(Region.USE_PREF_SIZE);
                    hbox.setMaxHeight(Region.USE_PREF_SIZE);
                    hbox.setMinWidth(Region.USE_PREF_SIZE);
                    hbox.setMinHeight(Region.USE_PREF_SIZE);
                    StackPane stackPane = new StackPane(hbox);
                    stackPane.setAlignment(Pos.BASELINE_RIGHT);
                    chatBox.getChildren().add(stackPane);
                });
                dtout.writeUTF(lblName.getText() + ":" + txtSendmsg.getText());
                dtout.flush();
                txtSendmsg.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        emojiContainer.setVisible(false);
        spaneForFlowPane.setVisible(false);
        emojiCategoryPane.setVisible(false);

    }

    @FXML
    void txtMassageOnAction(ActionEvent event) {

    }

    public  void getName(String user_name){
          this.user_name = user_name;

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

    @FXML
    void camaraOnAcction(MouseEvent actionEvent) {
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
    void settingOnAction(MouseEvent actionEvent) {

//        if(!backgroundPane.isVisible() && !imgPane.isVisible())
//            backgroundPane.setVisible(true);
//        else if (backgroundPane.isVisible() && !imgPane.isVisible())
//            backgroundPane.setVisible(false);
//        else if (imgPane.isVisible())
//            imgPane.setVisible(false);

    }


//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("JavaFX Input Name Label Example");
//
//        // Create TextField for input
//        TextField nameInput = new TextField();
//        nameInput.setPromptText("Enter your name");
//
//        // Create Label for displaying the input name
//        Label nameLabel = new Label();
//
//        // Event handling: update label text when the user types in the TextField
//        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
//            nameLabel.setText("Your name is: " + newValue);
//        });
//
//        // Layout setup using VBox
//        VBox vbox = new VBox(10);
//        vbox.getChildren().addAll(nameInput, nameLabel);
//
//        // Create the scene
//        Scene scene = new Scene(vbox, 300, 150);
//
//        // Set the scene
//        primaryStage.setScene(scene);
//
//        // Show the stage
//        primaryStage.show();
//    }


}


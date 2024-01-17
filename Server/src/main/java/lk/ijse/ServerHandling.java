package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandling extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/sever_form.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 608, 497));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    static final int port = 3003;
//    static ServerSocket serverSocket;
//    static Socket socket;
//    static DataInputStream dataInputStream;
//    static List<ClientHandler> clientList = new ArrayList<>();
//
//    public static void main(String[] args) {
//        try {
//            serverSocket = new ServerSocket(port);
//            System.out.println("Server Accepted!\nWaiting for client...");
//
//            while (true){
//                socket = serverSocket.accept();
//                dataInputStream = new DataInputStream(socket.getInputStream());
//                System.out.println(dataInputStream.readUTF()+"Accepted!");
//
//                ServerController.ClientHandler clientHandling = new ClientHandling(socket,clientList);
//                clientList.add(clientHandling);
//                clientHandling.start();
//            }
//        } catch (IOException e) {
//           throw new RuntimeException(e);
//        }
//    }



}

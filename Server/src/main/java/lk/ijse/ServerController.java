package lk.ijse;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



public class ServerController implements Initializable {

    @FXML
    private TextArea txtMassageArea;

    @FXML
    private TextField txtTypeMsg;


    private ServerSocket serverSocket;
    private List<ClientHandler> clients;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clients = new ArrayList<>();

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3003);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandling = new ClientHandler(clientSocket);
                    clients.add(clientHandling);
                    System.out.println(clients);
                    clientHandling.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public void btnSendOnAction(ActionEvent event) {

        String message = txtTypeMsg.getText();

        if (!message.isEmpty()) {
            broadcastMessagebyAdmin("System", message);
            txtTypeMsg.clear();
        }
    }

    private void broadcastMessagebyAdmin(String sender, String message) {

        txtTypeMsg.appendText(sender + ": " + message + "\n");
        for (ClientHandler client : clients) {
            client.sendMessage(sender, message);
        }

    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader dtin;
        private DataOutputStream dtout;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
            try {
                this.dtin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                dtout = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String name = dtin.readLine();
                broadcastMessagebyClients("System", name + " has joined the chat.", clientSocket);

                while (true) {
                    String message = dtin.readLine();
                    if (message.equals("pass-qpactk3i5710-xkdwisq@ee358fyndvndla98r478t35-jvvhjfv94r82@")) {
                        break;
                    } else if (message.startsWith("image")) {
                        String username = dtin.readLine();
                        String path = dtin.readLine();
                        broadcastImagesbyClients(username, path, clientSocket);
                    } else {
                        broadcastMessagebyClients(name, message, clientSocket);
                    }
                }

                clients.remove(this);
                System.out.println(clients);
                clientSocket.close();
                broadcastMessagebyClients("System", name + " has left the chat.", clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcastImagesbyClients(String username, String path, Socket clientSocket) {
        }

        public void sendMessage(String sender, String message) {
            try {
                if (sender.equals("System")) {
                    dtout.writeUTF(sender + ": " + message);
                    dtout.flush();
                } else {
                    dtout.writeUTF(sender + ":\n" + message);
                    dtout.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void broadcastMessagebyClients(String sender, String  message, Socket socket) {
            txtTypeMsg.appendText(sender + ": " + message + "\n");
            for (ClientHandler client : clients) {
                if (client.clientSocket != socket) {
                    client.sendMessage(sender, message);
                }
            }

        }
    }
}

//package lk.ijse;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.List;
//
//public class ClientHandling extends Thread{
//
//    Socket socket;
//
//    List<ClientHandling> clientList;
//
//    BufferedReader dtin;
//
//    DataOutputStream dtout;
//
//    public ClientHandling(Socket socket ,List<ClientHandling> clientList) throws IOException {
//        this.clientList= clientList;
//        this.socket = socket;
//        this.dtin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        this.dtout = new DataOutputStream (socket.getOutputStream());
//    }
//
//    public ClientHandling(Socket clientSocket) {
//    }
//
//    @Override
//    public void run(){
//        try {
//            String massage;
//            while ((massage = dtin.readLine()) != null){
//
//                if (massage.equalsIgnoreCase("exit")){
//                    break;
//                }
//
//                for (ClientHandling clientHandling : clientList){
//                    clientHandling.dtout.writeUTF(massage);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e);
//        }finally {
//            try {
//                dtin.close();
//                dtout.close();
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println(e);
//            }
//        }
//    }
//}

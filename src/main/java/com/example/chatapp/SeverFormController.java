package com.example.chatapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SeverFormController implements Initializable {

    @FXML
    private TextArea txtAreaSever;

    @FXML
    private TextField txtSever;

    ServerSocket serverSocket ;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String msg = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(()->{
            try {
                serverSocket = new ServerSocket(4005);
                txtAreaSever.appendText("Sever Started!!!");
                socket =serverSocket.accept();
                txtAreaSever.appendText("client Accepted");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());

                while (!msg.equals("finish")){
                    msg = dataInputStream.readUTF();
                    txtAreaSever.appendText("\nclient : "+msg);
                }

//                socket.close();
//                serverSocket.close();
//                dataOutputStream.close();
//                dataInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void btnSeverOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtSever.getText().trim());
        dataOutputStream.flush();
    }
}

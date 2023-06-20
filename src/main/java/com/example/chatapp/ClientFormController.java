package com.example.chatapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {

    @FXML
    private TextArea txtAreaClient;

    @FXML
    private TextField txtClient;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String msg = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(()->{
            try {
                socket=new Socket("localhost",4005);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                while (!msg.equals("finish")){
                    msg = dataInputStream.readUTF();
                    txtAreaClient.appendText("\nclient : "+msg);
                }
//                socket.close();
//                dataOutputStream.close();
//                dataInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void btnClientOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtClient.getText().trim());
        dataOutputStream.flush();
    }
}

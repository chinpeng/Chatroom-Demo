/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apple
 */
public class DeliverClient implements Runnable{
    
    private final String path = "src/userProfile/";
    ServerSocket deliverServer;
    Socket deliverClient;
    private DataInputStream in;
    private DataOutputStream out;
    private String fileName;
    private int fileSize;
    
    
    public DeliverClient(Socket s, ServerSocket ds) throws IOException{
        deliverClient = s;
        deliverServer = ds;
        in = new DataInputStream( deliverClient.getInputStream() );
        out = new DataOutputStream( deliverClient.getOutputStream() );
    }
    
    @Override
    public void run() {
        try {
            fileName = in.readUTF();
            
            File file = new File(path + fileName + ".jpg");
            fileSize=(int)file.length();
            
            out.writeInt(fileSize);
            
            String input;
            input = in.readUTF();
            if(input.equals("/a")){
                FileInputStream fs = new FileInputStream( file );
                BufferedInputStream bis = new BufferedInputStream(fs);
                byte [] bufferArray = new byte [ fileSize ];
                bis.read( bufferArray, 0, bufferArray.length );
                out.write( bufferArray, 0, bufferArray.length );
                out.flush();
                out.close();
                deliverClient.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DeliverClient.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}

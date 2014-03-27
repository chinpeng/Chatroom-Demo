/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apple
 */
public class DeliverServer implements Runnable{
    
    private ServerSocket ds;
    private final int port = 6002;
    
    

    public DeliverServer() throws IOException{
        ds = new ServerSocket(port);     
        System.out.println("deliver!");
    }
    
    public String getAddr() throws UnknownHostException{
        return InetAddress.getLocalHost().toString().split("/", 2)[1];
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Socket s = ds.accept();
                DeliverClient dc = new DeliverClient( s , ds );
                Thread thd = new Thread( dc );
                thd.start();         
            } catch (IOException ex) {
                Logger.getLogger(DeliverServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileexchange;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nmlab-PC
 */
public class myRecvPro implements Runnable {
    
    private String srcAddr;
    private String fileName;
    private Socket s;
    
    public myRecvPro( String addr ,String name ){
        srcAddr = addr;
        fileName = name;
    }

    @Override
    public void run() {
        try {
            s= new Socket(srcAddr,6002);
            DataInputStream in =new DataInputStream(s.getInputStream());
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            out.writeUTF(fileName);
            int fileSize=in.readInt();
             File file = new File("src/main/java/userProfile/" + fileName + ".jpg");
             if(file.canRead()){
                 file.delete();
                 file.createNewFile();
             }
             if(file == null)
                 file.createNewFile();
             out.writeUTF("/a");
             FileOutputStream fos=new FileOutputStream(file);
             BufferedOutputStream bos=new BufferedOutputStream(fos);
             int byteRead;
             int current=0;
             byte [] bufferArray=new byte[fileSize];
             byteRead=in.read(bufferArray,0,fileSize);
             current=byteRead;
                    
             do{
                byteRead=in.read(bufferArray,current,(bufferArray.length-current));
                if(byteRead>=0){
                    current +=byteRead;
                }               
             }while(current<fileSize);
             bos.write(bufferArray,0,current);
             bos.flush();
             bos.close();
             s.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(myRecvPro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(myRecvPro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

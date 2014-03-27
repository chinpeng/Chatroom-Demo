/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileexchange;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import MyClient.*;
/**
 *
 * @author nmlab-PC
 */
public class myChangePro extends Thread{
    
    private File file;
    private String fileName;
    int fileSize;
    
    public myChangePro(String name){
        fileName = name ;
    }
    
    public boolean getFile() {
        JFileChooser fc =new JFileChooser();
        int result =fc.showOpenDialog(new JFrame());

        if(result==JFileChooser.APPROVE_OPTION){
            file=fc.getSelectedFile();
            fileSize=(int)file.length();
            return true;
        }
        else return false;
    }
    
    public void sendFile(Socket s, ServerSocket ss, File file) throws IOException{
        DataInputStream in = new DataInputStream( s.getInputStream() );
	DataOutputStream out = new DataOutputStream( s.getOutputStream() );
	FileInputStream fs = new FileInputStream( file );
	BufferedInputStream bis = new BufferedInputStream(fs);
        
        out.writeUTF(fileName);
	out.writeInt(fileSize);
        if( in.readUTF().equals("/a") ) {
            byte [] bufferArray = new byte [ fileSize ];
            bis.read( bufferArray, 0, bufferArray.length );
            out.write( bufferArray, 0, bufferArray.length );
            out.flush();
            out.close();
            s.close();
            ss.close();
        }
        
    }
    
    @Override
    public void run(){
        try {
            ServerSocket ss =new ServerSocket(6002);
            Socket s=ss.accept();  
            sendFile(s,ss,file);
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(myChangePro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



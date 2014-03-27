/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileexchange;
import java.net.*;
import java.io.*;

/**
 *
 * @author nmlab
 */
public class myFileSend implements Runnable {
    
    private mySendGui gui;
     
  //  private String ip;
  //  private int port;
    private  File file;
    
    public myFileSend(mySendGui g){
        gui=g;
    }
    public void run(){
        
        file=gui.getChosenFile();
             
        if(file!=null ){
            
            try{
             //How to accept a socket ??
                
                  ServerSocket ss =new ServerSocket(9988);
                  //get a socket(destination) from server                   
                  Socket s=ss.accept();  
                 
                  sendFile(s,ss,file);
                  
                }
            catch(IOException e){
            
            }
        }    
        
    }
    
  
    public void sendFile(Socket s,ServerSocket ss,File file){
         
        try{
            
  			DataInputStream in = new DataInputStream( s.getInputStream() );
			DataOutputStream out = new DataOutputStream( s.getOutputStream() );
			FileInputStream fs = new FileInputStream( file );
			BufferedInputStream bis = new BufferedInputStream(fs);
			int filesize = (int)file.length();

			out.writeUTF(file.getName());
			//System.out.println("size = "+filesize);
			out.writeInt(filesize);

			if( in.readUTF().equals("/a") ) {
				byte [] bufferArray = new byte [ filesize ];
				bis.read( bufferArray, 0, bufferArray.length );
				gui.send();
				out.write(bufferArray, 0, bufferArray.length);
				out.flush();
				out.close();
				//System.out.println( in.readUTF() );
				gui.done();
				s.close();
				ss.close();
          
                        }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        
 }
    
    
    
}

    
            
    
    


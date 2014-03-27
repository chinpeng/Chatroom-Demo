/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileexchange;
import java.io.*;
import java.net.*;

/**
 *
 * @author nmlab
 */
public class myFileRecv  implements Runnable{
   
    private myRecvGui gui;
    private String srcAddr;
    private String srcName;
    private String fileName;
    
    private Socket s;
    
    public myFileRecv(String addr ,String name){
      
       srcAddr=addr;
       srcName=name;      
   
    }
    
    public void run() {
      gui=new myRecvGui();  
      gui.setVisible(true);
      try{
       s= new Socket(srcAddr,9988);
       fileRecv(s,srcName,gui);     
      }
      catch(IOException e){
          System.out.println("send GG");
      }
    }
    
    public void fileRecv(Socket s,String fileName,myRecvGui gui){
        try{
            DataInputStream in =new DataInputStream(s.getInputStream());
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            fileName=in.readUTF();
            int fileSize=in.readInt();
            gui.setSrcName(srcName);
            gui.setFileInformation(fileName,fileSize);
            
            if(gui.confirm()){
                File f= gui.getFile(fileName);
                if(f!=null){
                    out.writeUTF("/a");
                    FileOutputStream fos=new FileOutputStream(f);
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
                    //gui.done();
                    s.close();
                             
                }
            }     
        }
        catch(IOException e){
             System.out.println(e.toString());
             e.printStackTrace();
            
        }
       
    }
  
}

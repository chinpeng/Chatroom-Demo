/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileexchange;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author nmlab
 */
public class myRecvGui extends JFrame {
    public myRecvGui(){
        
    }
   
    public File getFile(String fName){
        JFileChooser fc= new JFileChooser();
        fc.setSelectedFile(new File(fName));
        int result=fc.showSaveDialog(new JFrame());
        File recvFile;
        if(result==JFileChooser.APPROVE_OPTION){
            recvFile=fc.getSelectedFile();
            fileName=recvFile.getAbsolutePath();
            return recvFile;
        }
        else 
            return null;
    }
    
    public void setFileInformation(String name,int size){
        fileName=name;
        fileSize=size;
        
    }
    
    public void setSrcName(String name){
        srcName=name;
    }
   
    public boolean confirm(){
        int n=JOptionPane.showConfirmDialog(this,
                srcName+"Want to send"+fileName+"recieve?","Comfirmed",JOptionPane.YES_NO_OPTION);
        if(n== JOptionPane.YES_OPTION) return true;
               else return false;
        
    }
   
    private String fileName;
    private int fileSize;
    private String srcName;
       

}

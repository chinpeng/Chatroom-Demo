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
public class readFile {
    
    String dest;
    String src;
    
    public void read(String src,String dest) throws IOException{
        
        this.dest=dest;
        this.src=src;
        
        FileReader fr=new FileReader("src");
        BufferedReader br=new BufferedReader(fr);
        while (br.ready()){
            System.out.println(br.readLine());       
        }
        
        fr.close();
        
    }
    
    
    
}

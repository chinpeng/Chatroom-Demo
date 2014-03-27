/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.*;

/**
 *
 * @author user
 */
    public class ChatRoom extends JTextPane{
       public ChatRoom(){
          super();
          init();
          setEditable(false);
       }
       
       private void init(){
           doc = getStyledDocument();
       }
       
       public void addChatRoomNewLine(String msg){
           System.out.println("addChatRoomNewLine");
           if((msg.contains("@stks"))&&msg.contains("http://")){
               System.out.println("has both");
               splitHyperStks(msg);
               if(msg.lastIndexOf("@stks") > msg.lastIndexOf("http://")){  //剩下一串有stks的還沒印, 前面最後一定是一個超連結的
                   String[] msgArray = msg.split("http://");
                   String[] twoParts = msgArray[msgArray.length - 1].split(" ",2);
                   splitStks(twoParts[1]);
               }
               else{                                                       //剩下一串有http://的還沒印, 前面最後一定是個stks的
                   String[] msgArray = msg.split("@");
                   splitHyperLinks(msgArray[msgArray.length - 1]);
               }
           }
           else if(msg.contains("@stks")){
               System.out.println("has stks");
               splitStks(msg);
           }
           else if(msg.contains("http://")){
               System.out.println("has hyper");
               splitHyperLinks(msg);
           }
           else{
              append(msg);
              append("\n");
           }
       }
       
       private void splitHyperStks(String msg){   //每次把後面那段等於msg
           while((msg.indexOf("@stks") != -1)&&(msg.indexOf("http://") != -1)){  //兩個都有
               if(msg.indexOf("@stks") < msg.indexOf("http://")){   //兩個都有但貼圖在前
                   String[] msgArray = msg.split("@stks",2);  //分成兩份
                   append(msgArray[0]);
                   String[] twoParts = msgArray[1].split("@",2);  //前面數字後面是剩下的msg
                   addStickers(Integer.valueOf(twoParts[0]));
                   msg = twoParts[1];
               }
               else{
                   String[] msgArray = msg.split("http://",2);  //分成兩份
                   append(msgArray[0]);
                   String[] twoParts = msgArray[1].split(" ",2);  //前面網址,後面是剩下的msg
                   System.out.println(twoParts[0]);  //印出往只看一下
                   HyperLink link = new HyperLink("http://"+twoParts[0]);
                   setCaretPosition(doc.getLength());
                   insertComponent(link);
                   msg = twoParts[1];
               }
           }
       }
       
       private void splitStks(String msg){
           String[] msgArray = msg.split("@stks");  //先找出有貼圖的 後面會剩下  123@hello 之類
           append(msgArray[0]);  //第一個是好ㄉ
           for(int i = 1; i < msgArray.length; i++){
              String[] twoParts = msgArray[i].split("@",2);
              System.out.println(twoParts[0]);
              addStickers(Integer.valueOf(twoParts[0]));
              append(twoParts[1]);
           }
           append("\n");
       }
       
       private void splitHyperLinks(String msg){
            String[] msgArray = msg.split("http://");     //後面會剩下: www.asdasd 就是這個
            append(msgArray[0]);
            for(int i = 1;i < msgArray.length;i++){
                String[] twoParts = msgArray[i].split(" ",2);  //先用空格分開吧
                System.out.println(twoParts[0]);  //印出往只看一下
                HyperLink link = new HyperLink("http://"+twoParts[0]);
                setCaretPosition(doc.getLength());
                insertComponent(link);
                if(twoParts.length == 2){
                    append(twoParts[1]);
                }
            }
            append("\n");
       }
       
       public void append(String str){
           try{
              doc.insertString(doc.getLength(),str,simpleAttribute);         
           }
           catch(Exception e){
              System.out.println(e);
           }
       }
       
       public void addStickers(int i){   //加入貼圖, protocol   @stks1@  之類
    	   if(i == 201){    ///////////////////////////////////////////////
               String full = pathHead + i + pathTailG;
               this.setCaretPosition(doc.getLength());
               stkIcon = new ImageIcon(full);
               this.insertIcon(stkIcon);	   
    	   }
    	   else{
           String full = pathHead + i + pathTail;
           this.setCaretPosition(doc.getLength());
           stkIcon = new ImageIcon(full);
           Image img = stkIcon.getImage();
           int h = (int)Math.round(img.getHeight(null) * 0.4);
           int w = (int)Math.round(img.getWidth(null) * 0.4);        
           BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);  //開始縮小
           Graphics g = bi.createGraphics();
           g.drawImage(img,0,0,w,h,null);
           ImageIcon newStkIcon = new ImageIcon(bi);
           this.insertIcon(newStkIcon);    //新的縮小的圖片
    	   }
       }
       
       private StyledDocument doc;
       private SimpleAttributeSet simpleAttribute;
       private ImageIcon stkIcon;
       public String pathHead = "src/main/java/myGUI/stickers/stks (";
       public String pathTail = ").png";
       private String pathTailG = ").gif";
       
    }

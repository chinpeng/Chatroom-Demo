/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myserver;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apple
 */
public class MyClient extends Thread {
    private Socket sock;
    private MyServer mainserver;
    private DataInputStream in;
    private DataOutputStream out;
    public String msg, username;
    private int clientID;
    public int userColor;
    public File profileFile; 
    
    public MyClient( MyServer ms, Socket s, int id ) {
	try {
            sock = s;
            mainserver = ms;
            in = new DataInputStream( s.getInputStream() );
            out = new DataOutputStream( s.getOutputStream() );
            clientID = id;
	} catch (IOException e) {
            System.out.println(e.toString());
        }
        userColor = java.awt.Color.BLACK.getRGB();
    }
    
    public String getUserName(){
        return username;
    }
    
    
    public void setUserName() throws IOException {
        String input;
	out.writeUTF("/u");
        while( true ) {
            input = in.readUTF();
            //  /u [useraccount] [password] [isnew]
            System.out.println(input);
            String acc = input.split(" ",4)[1];
            String pwd = input.split(" ",4)[2];
            int isnew = Integer.parseInt( input.split(" ",4)[3] );
            if(input.startsWith("/u")){
                if( mainserver.loginData.containsKey(acc) && isnew == 1) {
                    out.writeUTF("Account already taken! Please use another name.");
                    continue;
                }   
                else if( mainserver.loginData.containsKey(acc) && isnew == 0 ){
                    if( pwd.equals(mainserver.loginData.get(acc)) ){
                        username = acc;
                        System.out.println(username);
                        send( "/ua" ); 
                        send( "/s Welcome back to the chatroom");
                        for( MyClient c: (mainserver.cli) ) { 
                            if (c!=this) send("/q+ "+ c.username+" " + c.userColor);
                        }
                        mainserver.sendAll( "/q+ " + username+ " " + userColor); 
                        mainserver.addUser(username, clientID, this);
                        break;
                    }
                    else{
                        out.writeUTF("Wrong password! Please enter again.");
                        continue;
                    }
                }
                else if( !(mainserver.loginData.containsKey(acc)) && isnew == 0 ){
                    out.writeUTF("There is no this account registered! Please try again or register now.");
                    continue;
                }
                else {
                    username = acc;
                    send( "/ua" ); 
                    mainserver.loginData.put(acc,pwd);
                    send( "/s Server Welcome to the chatroom");
                    for( MyClient c: (mainserver.cli) ) { 
                        if (c!=this) send("/q+ "+ c.username+" " + c.userColor);
                    }
                    mainserver.sendAll( "/q+ " + username+" " + userColor); 
                    mainserver.addUser(username, clientID, this);
                    break;
                }
            }
	}
        
    }


    public void run() {
        try{
            setUserName();
            while(true){
                msg = in.readUTF();
                parseMsg(msg);
            }
        }catch( IOException e ){
            if(e instanceof SocketException){
                try {
                    mainserver.removeClient(this, clientID);
                } catch (IOException ex) {
                    Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                System.out.println(e.toString());
                e.printStackTrace();
            }
        }
    }
    
    public void send(String s) throws IOException{
        try{
            out.writeUTF(s);
        }catch(IOException e){
            if(e instanceof SocketException){
                mainserver.removeClient(this, clientID);
            }
            else{
                System.out.println(e.toString());
            }
        }
        System.out.println("SystemOUT : "+s);
    }
    
    public void parseMsg (String s) throws IOException {
        System.out.println("SystemIN : "+s);
        if(s.startsWith("/s")){
            //  /s [srcname] message
            //  廣播
            mainserver.sendPublic(s);
        }
        else if(s.startsWith("/w")){
            //  /w [srcname] [destname] message
            //  悄悄話
            String [] sdivid = s.split(" ", 4);
            if(sdivid[2].equals("Server")){
                mainserver.whisperToServer (sdivid[1]+"敲敲跟你說:"+sdivid[3]);
            }
            else{
                mainserver.sendPrivate( sdivid[2], s);
            }
        }
        else if(s.startsWith("/n")){
            //   /n
            //   開房間
            int room = mainserver.makeRoom();
            mainserver.roomadd(room, this);
        }
        else if(s.startsWith("/rs")){
            // /rs [roomid] [msg]
            // 房間聊天
            int room = Integer.parseInt(s.split(" ", 4)[1]);
            if( mainserver.sendRoom(room, s) != true ) {
		send("/e You're not in this room!");
            }
        }
        else if(s.startsWith("/a")){
            // /a [roomid] [username]
            // 加入新使用者至房間
            String room = s.split(" ", 3)[1];
            String username = s.split(" ", 3)[2];
            mainserver.roomAdd(Integer.valueOf(room), username);
        }
        else if(s.startsWith("/a-")){
            // /a- [roomid] [username]
            // 踢除 指定使用者
            String room = s.split(" ", 3)[1];
            String username = s.split(" ", 3)[2];
            mainserver.roomDelete(Integer.valueOf(room), username);
        }
        else if(s.startsWith("/l")){
            // /l [roomid]
            // 離開房間
            int room = Integer.parseInt(s.split(" ", 2)[1]);
            mainserver.roomDelete(room, this);
        }
        else if(s.startsWith("/f")){
            // /f [src name] [dest name]
            // 傳檔（對象位置）
            String src = s.split(" ", 3)[1];
            String dest = s.split(" ", 3)[2];
            String temp = (s + " " + (sock.getInetAddress().getHostAddress()));
            mainserver.sendPrivate( dest, temp);
        }
        else if(s.startsWith("/@")){
             Thread recvThd = new Thread( new myRecvPro( sock.getInetAddress().getHostAddress() ,mainserver) );
             System.out.println(sock.getInetAddress().getHostAddress());
             recvThd.start();
        }
        //streaming
        else if(s.startsWith("/m")){
        	// /f [dest name] 
        	String dest=s.split(" ",2)[1];
        	String temp=(s+" "+sock.getInetAddress().getHostAddress());
        	mainserver.sendPrivate(dest,temp);
        }
        
        else{
        }
    }
    
    
    
    public void socketTerminate() throws IOException{
        sock.close();
    }

   
}

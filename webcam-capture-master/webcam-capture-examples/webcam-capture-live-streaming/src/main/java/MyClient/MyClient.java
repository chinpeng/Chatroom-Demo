/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClient;

import us.sosia.video.stream.agent.*;


/**
 *
 * @author nmlab-PC
 */
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import fileexchange.*;


import myGUI.*;

public class MyClient implements Runnable{
	
        Lobby lob;
	private String userName;
	private String ipAddress;
	private int port;
        private String password;
	private Socket socket;
        private int oldOrNew;
        private DataOutputStream os;
        private DataInputStream is;
	private Thread thread; 
        private ConnectWindow connectWindow;
        
        private Vector<String> userList;
        private Vector<Integer> roomList;
        
             

	public MyClient(){
            
             lob = new Lobby (this);
             lob.setVisible(true);
             
             createConnectWindow();   
             connectToServer();
     
             
	}
        
    public void run() {
        try {
            while (true) {
		String TransferLine = is.readUTF();
                System.out.println("Recv: " + TransferLine);        
                parseMsg(TransferLine);
            }
	}
        catch (Exception e) {
            System.out.println(e.toString());
            try {
                interrupt();
            } catch (IOException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
    }        
	
    public void connectToServer(){
		try{
		    socket = new Socket(InetAddress.getByName(ipAddress),port);
                    System.out.println("socket");
                    sendName();
                    os = new DataOutputStream(socket.getOutputStream());
                    is = new DataInputStream(socket.getInputStream());
                    this.os = new DataOutputStream(os);
                    this.is = new DataInputStream(is);
                    thread = new Thread(this);
                    thread.start(); //start run()
                    System.out.println("connected");
		}
		catch(Exception e){
                    System.out.println("cannot connect......");
                    reconnect();
		}
	}
	
    private void interrupt() throws IOException {
        lob.addLobbyNewLine("Some problem occured.......Reconnect!" , "Server");
        socket.close();
        reconnect();
    }
    
    public void reconnect(){   //pop up connect window, set IP and Port, wait for userName
        createConnectWindow();
        connectToServer(); 
        lob.dispose();
        lob = new Lobby (this);
        lob.setVisible(true);
    }
    
    private void createConnectWindow(){
        connectWindow = new ConnectWindow(lob ,this);
        connectWindow.setLocationRelativeTo(lob);
        connectWindow.setVisible(true);
    }
    
    public synchronized void sendName() throws IOException {
	DataOutputStream o = new DataOutputStream(socket.getOutputStream());
	DataInputStream i = new DataInputStream(socket.getInputStream());
	String msg = i.readUTF();
    	System.out.println(msg);
        if( msg.startsWith("/u") ) {
    		System.out.println("recv: /u Send username "+userName);
                String output = "/u "+ userName + " " + password + " " + Integer.toString(oldOrNew);
    		o.writeUTF(output);
        };
        while( true ) {
	    	msg = i.readUTF();
                System.out.println(msg);
		if( msg.equals("/ua") ) break;
		else {
                    lob.addLobbyNewLine(msg , "Server");
                    createConnectWindow();
                    String output = "/u "+ userName + " " + password + " " + Integer.toString(oldOrNew);
		    o.writeUTF(output);
	    	};
        };
    }
    
    public void getClientInformation(String IP, int portNum, String acc, String pwd, int isNew){
	ipAddress = IP;
	port = portNum;
        userName = acc;
        password = pwd;
        oldOrNew = isNew;
    }
 	
	
    public void send ( String msg ) throws IOException {
        try{
            os.writeUTF(msg);
            System.out.println("Send: " + msg);
	    }
	    catch (Exception e) {
            interrupt();
	    }
    }

    //open new room
    public void sendNewRoom () throws IOException {
        //new room: "/n"
        send("/n");
    }

    //leave room
    public void sendLeaveRoom ( int roomID ) throws IOException {
        //leave room: "/l <roomID>"
        send("/l "+roomID);
    }
    
    public void sendAddRoomUser ( String user, int roomID ) throws IOException {
        //leave room: "/a <roomID> <user>"
        send("/a " +roomID+" " +user);
    }

    //send public msg
    public void sendSMsg ( String msg ) throws IOException {
        //send msg: "/s <user> <msg>"
        send("/s " + userName+" " + msg);
    }

    //send room msg
    public void sendRMsg ( String msg , int roomID ) throws IOException {
        //send room msg: "/rs <roomID> [src name] <msg>"
        send("/rs " + roomID+" "+ userName+" " + msg);
    }

    //send whisper msg
    public void sendWMsg ( String msg , String target ) throws IOException {
       //whisper msg: "/w <user> <target>  <msg>"
       send("/w " + userName+" " + target+" "  + msg);
	
    }

    //color change
    public void sendColorChange ( int c ) throws IOException {
        //color change: "/c <user> <color>"
        send("/c " + userName+" " + c);
    }
    
    public void sendProfileChange(){
    }
   
   public void sendFile( String dest ,String src, mySendGui g) throws IOException {
       // send file request: /f [src name] [dest name]      
        myFileSend fs= new myFileSend(g);
        Thread fsthd = new Thread(fs);
        send( "/f "+src+" "+ dest);
        fsthd.start(); 
    }
   
   public void sendProfile() throws IOException{
       myChangePro  mp = new myChangePro(this.getClientName());
       if (mp.getFile()){
            send( "/@" );
            mp.start();
       }
   }
  
  //streaming
   
   public void sendVideo(String dest ) throws IOException{
	    
	    StreamServer vcs = new StreamServer();
	    Thread vcsthd=new Thread(vcs);
	    vcsthd.start();
	    send("/m "+dest);
	  	
	   
   } 
   
   
        
    public void parseMsg ( String msg ) {
        //add user:  /q+ <user> <texture>
        if (msg.startsWith("/q+")) {
            String[] splitedLine = msg.split(" ", 3);
            System.out.println("User joined:" + splitedLine[1]);
            lob.addUserList(splitedLine[1], Integer.parseInt(splitedLine[2]));               
        }
        //delete user:  /q- <user>
        else if (msg.startsWith("/q-")) {
            String[] splitedLine = msg.split(" ", 3);
            System.out.println("User left:" + splitedLine[1]);
            lob.rmUserList(splitedLine[1]);
            lob.addLobbyNewLine(splitedLine[1] + "is left" ,splitedLine[1] );
        }
        //normal msg:  /s <user> <msg>
        else if (msg.startsWith("/s")) {
            String[] splitedLine = msg.split(" ", 3);
            System.out.println("recv /s");
            lob.addLobbyNewLine(splitedLine[1] + " says: " + splitedLine[2], splitedLine[1]);
        }
        //whisper msg:  /w <user> <target> <msg>
        else if (msg.startsWith("/w")) {
            String[] splitedLine = msg.split(" ", 4);
            lob.addLobbyNewLine(splitedLine[1] +" whisper to you" + " : " + splitedLine[3], splitedLine[1]);
        }
        //color change:  /c <user> <texture>
        else if (msg.startsWith("/c")) {
            String[] splitedLine = msg.split(" ", 3);
            lob.userChangeColor(splitedLine[1], Integer.parseInt(splitedLine[2]));
        }
        //add client to room:  /a <roomID>
        else if (msg.startsWith("/a")) {
            String[] splitedLine = msg.split(" ", 2);
            lob.addTab(Integer.parseInt(splitedLine[1]));
        }
        //add user into room:  /r+ <roomID> [username] [texture]
        else if (msg.startsWith("/r+")) {
            String[] splitedLine = msg.split(" ", 4);
            lob.addRoomUserList(splitedLine[2],  Integer.parseInt(splitedLine[1]));
        }
        //remove user from room:  /r- <roomID> <user>
        else if (msg.startsWith("/r-")) {
            String[] splitedLine = msg.split(" ", 3);
            if(splitedLine[2].equals(getClientName())){
                for(int i = 0 ; i < roomList.size() ; i++){
                    if(roomList.get(i).equals(splitedLine[1])){
                        roomList.remove(i);
                        lob.deleteTab(Integer.parseInt(splitedLine[1]));
                    }
                }
            }
            lob.delRoomUser(splitedLine[2], Integer.parseInt(splitedLine[1]));
        }
        //room msg:  /rs <roomID> <user> msg
        else if (msg.startsWith("/rs")) {
            String[] splitedLine = msg.split(" ", 4);
            lob.addRoomNewLine(splitedLine[2] + " says: " + splitedLine[3] , Integer.parseInt(splitedLine[1]));
        }
        // file transfer request: /f [src name] [dest name] [src IP]
        else if( msg.startsWith("/f") ) {
                String srcName = msg.split(" ", 4)[1];
                String srcAddr = msg.split(" ", 4)[3];
                Thread recvThd = new Thread( new myFileRecv(srcAddr,srcName ) );
                recvThd.start();
        }
        //room closed
        else if( msg.startsWith("/!l") ){
            String[] splitedLine = msg.split(" ", 2);
            int roomID = Integer.parseInt(splitedLine[1]);
            lob.addLobbyNewLine("Room#" + roomID + "is dismissed. ","Server");
            lob.deleteTab(roomID);
        }
        //change profile /@ [srcName] [src IP]
        else if( msg.startsWith("/@")){
            String srcName = msg.split(" ", 3)[1];
            String srcAddr = msg.split(" ", 3)[2];
            Thread recvThd = new Thread( new myRecvPro( srcAddr , srcName ) );
            recvThd.start();
        }
        else if(msg.startsWith("/m")){
            String srcName =msg.split(" ",3)[1];
            String srcAddr=msg.split(" ",3)[2];
            
            StreamClient vcc=new StreamClient(srcAddr);
            
            Thread vccThd =new Thread(vcc );
            vccThd.start();
            
        }		
        else {
            //
        }
    } 
    public String getClientName(){
        return userName;
    }
    
    
    

    
    public static void main(String[] args) {
            // TODO Auto-generated method stub
           MyClient me = new MyClient();
    }

        
}

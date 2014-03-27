/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myserver;

import java.awt.Color;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import myGui.*;


/**
 *
 * @author apple
 */
public class MyServer {

    /**
     * @param args the command line arguments
     */
    private ServerSocket ss;
    public DeliverServer ds;
    private final int port = 5566;
    public Vector<MyClient> cli;
    public HashMap <String, MyClient> nowOn;
    public Vector<Chatroom> roomlist;
    private int id; // cumulate client ids
    private int roomid; // cumulate room ids
    private Lobby lob;
    HashMap <String, String> loginData;
    
    public MyServer() throws FileNotFoundException, IOException{
        loginData = new HashMap<String, String>();
 
                FileInputStream fis = new FileInputStream( "logdata.txt" );
                System.out.println( fis.available() );
                BufferedReader br = new BufferedReader( new InputStreamReader(fis) );
                System.out.println("br is " + br.ready() );
                String inputString;
                while((inputString = br.readLine()) != null){
                    String acc = inputString.split(" ",2)[0];
                    String pwd = inputString.split(" ",2)[1];
                    loginData.put(acc, pwd);
                }
                br.close();
                fis.close();
        
        lob = new Lobby(this);
        cli = new Vector<MyClient>();
        nowOn = new HashMap <String, MyClient> ();
        roomlist = new Vector<Chatroom>();
        id = 0;
        roomid = 0;
        Timer timer = new Timer();
        timer.schedule(new DataTask(), 5000,  2000);
        
        try{
            ss = new ServerSocket(port);
            ds = new DeliverServer();
            Thread dsthd = new Thread(ds);
            dsthd.start();
            lob.addText( "Server created. InetAddress:"+ ss.getInetAddress().getLocalHost()+" port:" +port );
            lob.addText( "Waiting for client." );
            while(true){
                synchronized(this){
                    Socket s = ss.accept();
                    lob.addText( "Client:"+s.getInetAddress()+"is connected.");
                    MyClient mc = new MyClient( this, s, id++ );
                    cli.add( mc );
                }
                Thread thd = new Thread( cli.lastElement() );
                thd.start();
            }
        }catch(IOException e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public void sendAll(String s) throws IOException {
        if(cli.isEmpty())
            return;
        for(MyClient c: cli){
            c.send(s);
        }
    }
    
    public void sendPublic( String msg ) throws IOException{
	sendAll( msg );
        String [] msgSplit = msg.split(" ",3);
	lob.addText( msgSplit[1]+ "說：" + msgSplit[2] );
    }
    
    
    public void removeClient(MyClient mc, int clientID) throws IOException{
        String name = mc.getUserName();
        nowOn.remove(name);
        lob.rmFriendList(name);
        cli.remove(mc);
        if( name != null ){
            for(MyClient c: cli ){
                c.send("/q- " + name + " " + mc.userColor);
            }
            lob.addText("Client #"+ clientID + " " + name +" is disconnected.");
        }
    }
    
    public void kickClient(String name) throws IOException{
        MyClient c = nowOn.get(name);
        c.socketTerminate();
    }
    
    public void addUser(String username, int clientID, MyClient mc){
        nowOn.put(username, mc);
        lob.addFriendList(username);
        lob.addText("Client #"+ clientID + " " + username +" is added");
    }
    
    public void sendPrivate( String desname, String msg) throws IOException{
        System.out.println("!!!"+ desname );
        MyClient descli = nowOn.get(desname);
        descli.send(msg);
    }
    
    public void whisperToServer(String s){
        lob.addText(s);
    }
    
    public int makeRoom(){
        roomid++;
        Chatroom cr = new Chatroom(roomid);
        roomlist.add(cr);
        lob.addRoomList("Room#"+roomid);
        lob.addText(cr.getName()+"is created.");
        return roomid; 
    }

    public void roomadd(int room ,MyClient c) throws IOException{
	c.send("/a "+room);
        roomlist.get(room-1).addRoomUser(c);
    }
    
    public void roomAdd(int room, String name) throws IOException{
        MyClient c = nowOn.get(name);
        roomlist.get(room-1).addRoomUser(c);
	c.send("/a "+room);
        for( MyClient cli: (roomlist.get(room-1).roomuser) ) { 
            if (cli!=c) c.send("/r+ "+ room+" " + cli.username+" " + cli.userColor);
        }
        sendRoom( room, "/r+ "+room+" "+c.username + " "+c.userColor);
    }
    
    public void roomDelete(int room, MyClient c) throws IOException{
        roomlist.get(room-1).removeRoomUser(c);
        sendRoom( room, "/r- "+ room +" "+c.getName() + " "+c.userColor );
    }
    
    public void roomDelete(int room, String name) throws IOException{
        MyClient c =  nowOn.get(name);
        roomlist.get(room-1).removeRoomUser(c);
        sendRoom( room, "/r- "+ room +" "+c.getName() + " "+c.userColor );
    }
    
    public boolean sendRoom(int room, String msg) throws IOException{
        if(room > roomlist.size())
            return false;
        roomlist.get(room-1).sendRoomAll(msg);
        return true;
    }
    
    public void quitRoom(int room) throws IOException{
        roomlist.get(room-1).quitRoom();
        lob.rmRoomList(roomlist.get(room-1).getName());
    }
    
    public void quitRoom(String roomname) throws IOException{
        Chatroom cr = null;
        System.out.println(roomname);
        for(Chatroom rl:roomlist){
            if(rl.getName().equals(roomname)){
                cr = rl;
            }    
        }
        cr.quitRoom();
        lob.rmRoomList(roomname);
        lob.addText(roomname + " is dismissed!");
    }
    
    public void saveUserAcc() throws IOException{
        if(!loginData.isEmpty()){
            File accountfile = new File ("logdata.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(accountfile));
            for(Object key : loginData.keySet()){
                String s = ((String)key) + " " + loginData.get(key);
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
        }
    }
    
    public class DataTask extends TimerTask{

        @Override
        public void run() {
            try {
                saveUserAcc();
            } catch (IOException ex) {
                Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

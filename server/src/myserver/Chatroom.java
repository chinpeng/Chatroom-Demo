/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myserver;


import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author apple
 */
public class Chatroom {
    private int roomid;
    public Vector<MyClient> roomuser;
    private String roomname;
    
    
    public Chatroom(int id){
        roomname = "Room#" + String.valueOf(id);
        roomid = id;
        roomuser = new Vector<MyClient> ();
    }
    
    public boolean isEmpty(){
        return roomuser.isEmpty();
    }
    
    public void addRoomUser(MyClient c){
        roomuser.add(c);
    }
    
    public void removeRoomUser(MyClient c){
        roomuser.remove(c);
    }
    
    public String getName(){
        return roomname;
    }
    
    public int getId(){
        return roomid;
    }
    
    public void sendRoomAll(String msg) throws IOException{
        for(MyClient c: roomuser){
            c.send(msg);
        }
    }
       
    // quitRoom
    public void quitRoom() throws IOException{
        for(MyClient c: roomuser){
            c.send("/!l " + roomid); ///
        }
    }
}

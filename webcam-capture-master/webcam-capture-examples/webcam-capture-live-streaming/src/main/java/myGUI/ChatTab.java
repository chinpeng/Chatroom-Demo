/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGUI;

/**
 *
 * @author nmlab-PC
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;


public class ChatTab extends JPanel{
    
    private Lobby myLobby;
    private int roomId;
    private String paneName;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private ChatRoom chatRoom;
    private SimpleAttributeSet simpleAttribute;
    private StyledDocument doc;
    private JList jList;
    private DefaultListModel vRoom;
    private JButton jButton;
    
    public ChatTab(int id, Lobby lob){
        myLobby = lob;
        roomId = id;
        paneName = "Room#" + Integer.toString(id) ;
        initComponents();
    }
    
    protected void initComponents(){
        setLayout(new BorderLayout());
        chatRoom = new ChatRoom();
        jScrollPane1 = new JScrollPane(chatRoom);
        jPanel1 = new JPanel();
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new Dimension(80,300));
        jPanel1.setLayout(new BoxLayout(jPanel1,BoxLayout.Y_AXIS));
        vRoom = new DefaultListModel();
        jList = new JList();
        jList.setModel(vRoom);
        jScrollPane2 = new JScrollPane(jList);
        jPanel1.add(jScrollPane2);
        jButton = new JButton("添加");
        jPanel1.add(jButton);
        add(jPanel1,BorderLayout.EAST);
        add(jScrollPane1);
        
        jButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if( !myLobby.getFriendChosen().equals("Server") && myLobby.getFriendChosen() != null ){
                    for(int i = 0 ; i < vRoom.size() ; i++){
                        if( ((String)vRoom.get(i)).equals( myLobby.getFriendChosen() ) )
                            return;
                    }
                    try {
                        myLobby.clientObject.sendAddRoomUser (myLobby.getFriendChosen(), roomId);
                    } catch (IOException ex) {
                        Logger.getLogger(ChatTab.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    public String getName(){
        return paneName;
    }
   
    public int getRoomId(){
        return roomId;
    }
    public void addRoomUser(String name){
        vRoom.addElement( name );
    }
    public void rmRoomUser(String name){
        vRoom.removeElement( name );
        
    }
    
    public void appendText(String msg){
        chatRoom.addChatRoomNewLine(msg);
    }
    public boolean isExist(String name){
        for(int i = 0 ; i < vRoom.size() ; i++){
            if(vRoom.elementAt(i).equals(name))
                return true;
        }
        return false;
    }
}

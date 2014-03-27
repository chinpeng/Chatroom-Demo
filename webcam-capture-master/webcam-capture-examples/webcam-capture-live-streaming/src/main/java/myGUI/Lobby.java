/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGUI;

import MyClient.*;
import fileexchange.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.JList.*;
import javax.swing.event.ListSelectionEvent;
import java.util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;   //加
import java.awt.event.KeyListener;    //加
import java.awt.event.KeyAdapter;   //加了


/**s
 *
 * @author apple
 */
public class Lobby extends JFrame implements KeyListener{   //加了keylistener
    
        public Lobby(MyClient myClient){
            super("Lobby");
            clientObject = myClient;
            setPreferredSize(new Dimension(1024,768));
            roomChatTab = new Vector<ChatTab>();
        
            initComponents();      
            setVisible(true);
            pack();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        }
    
    protected void initComponents(){

        jPanel2 = new JPanel();
        vFriend = new DefaultListModel();
        vFriend.addElement("Server");
        jList1 = new JList();
        
        jList1.setModel(vFriend);
        jList1.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                Point p = new Point(e.getX(),e.getY());
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                int Idx = (jList1.locationToIndex(p));
                System.out.println(Idx);
                if(Idx != preProfileIdx){
                    preProfileIdx = Idx;
                    if(profile != null){
                        profile.setVisible(false);
                        profile.dispose();
                    }
                    File f = new File("src/main/java/userProfile/"+ (String)(vFriend.getElementAt(Idx)) +".jpg");
                    if(f.canRead()){
                        profile = new profilePane( (String)(vFriend.getElementAt(Idx)) );
                        profile.repaint();
                    }
                    else
                        profile = new profilePane();
                    profile.setLocation(new Point(b.x+5,b.y+5));
                    profile.setVisible(true);
                }
            }
        });
        jList1.setSelectedIndex(0);
        jList1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e){
                if(profile != null){
                        profile.setVisible(false);
                        profile.dispose();
                }
            }
        });
        jScrollPane1 = new JScrollPane(jList1);
        jScrollPane1.setLayout(new ScrollPaneLayout());
        
        jButton2 = new JButton("新房間");
        jProfileButton = new JButton("大頭貼");
        jTButton1_2 = new JToggleButton("悄悄話");
        jTButton1_3 = new JToggleButton("說");
        jTButton1_3.setSelected(true);
        buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(jTButton1_2);
        buttonGroup1.add(jTButton1_3);
        jPanel2.setPreferredSize(new Dimension(100,400));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        jScrollPane1.setPreferredSize(new Dimension (60,350));
        jScrollPane1.setBorder(BorderFactory.createTitledBorder("Online"));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 1;
        jPanel2.add(jScrollPane1, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        jPanel2.add(jTButton1_2, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        jPanel2.add(jTButton1_3, c);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        jPanel2.add(jButton2, c);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        jPanel2.add(jProfileButton, c);
        
        
        jPanel3 = new JPanel();
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS));
        jPanel3.setOpaque(false);  
        
        chatRoom = new ChatRoom();
        chatRoom.append("Connecting to server......\n"/*,doc,simpleAttribute*/);
        jScrollPane2 = new JScrollPane(chatRoom);
        
        jPanel3.add(Box.createVerticalStrut(20));
        jTabbedPane1 = new JTabbedPane();
        jTabbedPane1.setTabPlacement(JTabbedPane.TOP);
        jTabbedPane1.setOpaque(false);
        SubPane = new JPanel();
        SubPane.setLayout(new BorderLayout());
        SubPane.add(jScrollPane2, BorderLayout.CENTER);
        jTabbedPane1.add("Main" ,SubPane);
        jPanel3.add(jTabbedPane1);
        
        jPanel4 = new JPanel();
        jPanel4.setPreferredSize(new Dimension(80,400));
        jPanel4.setOpaque(false);
        
        jPanel5 = new JPanel();
        jPanel5.setLayout(new FlowLayout());
        jPanel5.setOpaque(false);
        jPanel5l = new JPanel();
        jPanel5l.setLayout(new FlowLayout());
        jPanel5l.setOpaque(false);
        jPanel5r = new JPanel();
        jPanel5r.setLayout(new FlowLayout());
        jPanel5r.setOpaque(false);
        jPanel5.setPreferredSize(new Dimension(400,120));
        jPanel5l.setPreferredSize(new Dimension(250,100));
        jPanel5r.setPreferredSize(new Dimension(150,100));
        jTextArea2 = new JTextArea(1000,100);
        jTextArea2.addKeyListener(this);//讓按enter就可送出
        jScrollPane4 = new JScrollPane(jTextArea2);
        jScrollPane4.setPreferredSize(new Dimension(250,60));
        jButton3 = new JButton("貼圖");
        jButton4 = new JButton("字體");
        jButton5 = new JButton("塗鴉版");
        jPanel5l.add(jScrollPane4);
        jPanel5l.add(jButton3);
        jPanel5l.add(jButton4);
        jPanel5l.add(jButton5);
        
        jButton6 = new JButton("送出"); //send msgs
        jButton7 = new JButton("視訊");
        jButton8 = new JButton("傳送檔案");
        jPanel5r.add(jButton6);
        jPanel5r.add(jButton7);
        jPanel5r.add(jButton8);
        
        jPanel5.add(jPanel5l);
        jPanel5.add(jPanel5r);
        
        jPanel1 = new Lobby.picturePane();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jPanel2, BorderLayout.WEST);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(jPanel4,BorderLayout.EAST);
        jPanel1.add(jPanel5, BorderLayout.SOUTH);
        Container cp = getContentPane();
        cp.add(jPanel1);
        

        
        
        jProfileButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientObject.sendProfile();
                } catch (IOException ex) {
                    Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
        
        jButton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientObject.sendNewRoom();
                } catch (IOException ex) {
                    Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    

        
        jTButton1_2.addActionListener(
            new ActionListener() {   
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("talkIn = 2, whisper.");
                    talkIn = 2;
                }
            }
        );
                
        jTButton1_3.addActionListener(
            new ActionListener() {   
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("talkIn = 3, speak.");
                    talkIn = 3;
                }
            }
        );
        
        jButton6.addActionListener(
            new ActionListener() {   
                public void actionPerformed(ActionEvent evt) {
                    SendActionPerformed();
                }
            }
        );
        
        jButton3.addActionListener(   //表情
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                FaceActionPerformed(evt);
                }
            }
        );
        
        jButton4.addActionListener(    //字體
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                FontActionPerformed(evt);
                }
            }
        );
        
        jButton5.addActionListener(   //塗鴉版
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                ScribbleActionPerformed(evt);
                }
            }
        );
        
        jButton7.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                  try {
					VideoTalkActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                }
            }
        );
 
        jButton8.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        SendFileActionPerformed();
                    } catch (IOException ex) {
                        Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        ); 

        
       jList1.addListSelectionListener(new ListSelectionListener(){ 
          public void valueChanged( ListSelectionEvent e){      
          }
       }) ; 
        
    }


    
    private void SendActionPerformed() {                                           
        String msg = getInpuText();
        String selectedname = getFriendChosen();
        System.out.println("send action performed");
        try{
            if(talkIn == 3){  //說
                if( jTabbedPane1.getSelectedIndex() == 0)
                    clientObject.sendSMsg(msg);
                else{
                    ChatTab ct = (ChatTab) jTabbedPane1.getSelectedComponent();
                    clientObject.sendRMsg(msg , ct.getRoomId());
                }
            }
            else{   //悄悄話
                clientObject.sendWMsg(msg, selectedname);
                chatRoom.addChatRoomNewLine("對" +selectedname+"敲敲說： " + msg);
            }
        }
        catch(IOException exc){
            System.err.println(exc);
        }
        jTextArea2.setText(null);
    }    
    
    private void FaceActionPerformed(ActionEvent evt){
        System.out.println("Stickers chooser opened.");
        stickerChooser = new MyStickerChooser(this);
    }
    
    private void FontActionPerformed(ActionEvent evt){
    
    }
    
    private void ScribbleActionPerformed(ActionEvent evt){
       
      
    }
    
    private void SendFileActionPerformed() throws IOException{
          sendFile( getFriendChosen() );
    }
    
    private void VideoTalkActionPerformed(ActionEvent evt) throws IOException{
          videoTalk( getFriendChosen());
    }
    
    
    
    public void addLobbyNewLine( String msg ,String user){
        chatRoom.addChatRoomNewLine(msg);
    }
    
    public void addStickersLine(int i){
       jTextArea2.insert("@stks" + i + "@",jTextArea2.getCaretPosition());
    }
    
    public void addRoomNewLine( String msg , int roomId){
        for( int i = 1; i < jTabbedPane1.getTabCount() ;i++ ){
            ChatTab ct = (ChatTab)jTabbedPane1.getComponentAt(i);
            if(ct.getRoomId() == roomId){
                ct.appendText(msg);
                break;
            }
        } 
    }
 
    public void userChangeColor ( String name, int c ) {
    }

    
     public void addTab ( int roomID ) {
         ChatTab temp = new ChatTab (roomID, this );
         roomChatTab.addElement( temp );
         jTabbedPane1.add(temp.getName() ,temp);
         temp.addRoomUser("Yourself");
    }
     
    public void deleteTab(int roomID){
         for( int i = 1; i < jTabbedPane1.getTabCount() ;i++ ){
                ChatTab ct = (ChatTab)jTabbedPane1.getComponentAt(i);
                if(ct.getRoomId() == roomID){
                    jTabbedPane1.remove(ct);
                    break;
                }
            } 
    } 
   
    public void rmUserList(String s){
        
            vFriend.removeElement(s);
            if(getFriendChosen() .equals(s))
                jList1.setSelectedIndex(0);
            for( int i = 1; i < jTabbedPane1.getTabCount() ;i++ ){
                ChatTab ct = (ChatTab)jTabbedPane1.getComponentAt(i);
                if(ct.isExist(s)){
                    ct.rmRoomUser(s);
                }
            } 
        
    }
    
    
    public boolean addUserList(String s ,int option){
        if( vFriend.contains(s) )
            return false;
        else{
           vFriend.addElement(s);
           return true;
        }
    }
    
    public void addRoomUserList( String name , int roomId ){
        for( int i = 1; i < jTabbedPane1.getTabCount() ;i++ ){
            ChatTab ct = (ChatTab)jTabbedPane1.getComponentAt(i);
            if(ct.getRoomId() == roomId){
                ct.addRoomUser(name);
                break;
            }
        } 
    }
    
    public void delRoomUser( String name , int roomId){
        for( int i = 1; i < jTabbedPane1.getTabCount() ;i++ ){
            ChatTab ct = (ChatTab)jTabbedPane1.getComponentAt(i);
            if(ct.getRoomId() == roomId){
                ct.rmRoomUser(name);
                break;
            }
        } 
    }
    

    public void setLastWhisper ( String name , int roomID ) {

    }
    
    public void delRoomTab ( int roomID ) {

    }
    
    public void sendFile( String dest ) throws IOException {
 
        gui = new mySendGui( );
        gui.setVisible(true);
        gui.pack();
        
        if(gui.getFileName()!=null && gui.getWantToSend()!=false){
             System.out.println("sendfile called!");
             clientObject.sendFile(dest , gui.getFileName() , gui);      
        }     
        else{
             System.out.println("No file choosen");
             return;             
        }  
    }
  
    public void videoTalk(String dest) throws IOException{
    	
    	System.out.println("videochat with "+dest );
    	clientObject.sendVideo(dest);
    	
    }  
   
    public String getFriendChosen(){
        return (String)(jList1.getSelectedValue());
    }
    

    private String getInpuText(){
        return jTextArea2.getText();
    }
   //按enter送出
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == jTextArea2){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                System.out.println("ENTER pressed.");
                SendActionPerformed();
                jTextArea2.setText(null);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void keyReleased(KeyEvent e) {
                if(e.getSource() == jTextArea2){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                System.out.println("ENTER released.");
                jTextArea2.setText(null);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //新家按enter送出結束

    
    
    class picturePane extends javax.swing.JPanel {
        protected ImageIcon icon;
        protected ImageIcon stkIcon;
        public picturePane(){
            super();
            icon = new ImageIcon("src/main/java/myGUI/img/scar.jpg");
        }
        public picturePane(String src, int x, int y){
            super();
            stkIcon = new ImageIcon(src);
            Image img = stkIcon.getImage();
            BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);  //開始縮小
            Graphics g = bi.createGraphics();
            g.drawImage(img,0,0,x,y,null);
            icon = new ImageIcon(bi);  //新的縮小的圖片
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, getParent());
        }
    }
    
    
    class profilePane extends JDialog{
        picturePane pane;
        public profilePane(){
            super();
            pane = new picturePane("src/main/java/userProfile/default.jpg", 300, 300);
            Container c = this.getContentPane();
            c.add(pane);
            setSize(300,300);
        }
        public profilePane(String s){
            super();
            pane = new picturePane("src/main/java/userProfile/"+s+".jpg",300,300);
            Container c = this.getContentPane();
            c.add(pane);
            setSize(300,300);
        }
    }
    
    
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToggleButton jTButton1_2;
    private javax.swing.JToggleButton jTButton1_3;
    private javax.swing.JButton jProfileButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JList jList1;
    private Lobby.picturePane jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel SubPane;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel5r;
    private javax.swing.JPanel jPanel5l;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea2;
    private ChatRoom chatRoom;
    private SimpleAttributeSet simpleAttribute;
    private StyledDocument doc;
    //
    private mySendGui gui;
    private MyStickerChooser stickerChooser;
    
    private int talkIn = 3;  //  2悄悄話  3說        
    public MyClient clientObject;
    private DefaultListModel vFriend;
    private Vector<ChatTab> roomChatTab;
    HashMap <String ,Integer> userOption;
    int preProfileIdx = 0;
    private profilePane profile;
    private File profileFile;
    private final String pfrp = "src/main/java/userProfile/";
}

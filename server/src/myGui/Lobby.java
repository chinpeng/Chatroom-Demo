/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myGui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import myserver.*;
/**
 *
 * @author apple
 */
public class Lobby extends JFrame implements WindowListener{
    
    public Lobby(MyServer ss){
        super("聊天室");
        this.ms = ss;
        setPreferredSize(new Dimension(800,600));
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
        jList1.setSelectedIndex(0);
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
                    if(profile != null)
                        profile.setVisible(false);
                    File f = new File("src/userProfile/"+ (String)(vFriend.getElementAt(Idx)) +".jpg");
                    if(f.canRead())
                        profile = new profilePane( (String)(vFriend.getElementAt(Idx)) );
                    else
                        profile = new profilePane();
                    
                    profile.setLocation(new Point(b.x+5,b.y+5));
                    profile.setVisible(true);
                }
            }
        });
        jList1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseExited(MouseEvent e){
                preProfileIdx = -1;
                if(profile != null)
                     profile.setVisible(false);
            }
        });
        jScrollPane1 = new JScrollPane(jList1);
        jScrollPane1.setLayout(new ScrollPaneLayout());
        jButton1 = new JButton("踢除");
        jPanel2.setPreferredSize(new Dimension(100,400));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        jScrollPane1.setPreferredSize(new Dimension (60,350));
        jScrollPane1.setBorder(BorderFactory.createTitledBorder("使用者"));
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 1;
        jPanel2.add(jScrollPane1, c);
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 0;
        c.gridheight = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 0;
        jPanel2.add(jButton1, c);
        
        jPanel3 = new JPanel();
        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS));
        jPanel3.setOpaque(false);  
        jTextArea1 = new JTextArea("Loading...",1000,100);
        jTextArea1.setEditable(false);
        jScrollPane2 = new JScrollPane(jTextArea1);
        jPanel3.add(Box.createVerticalStrut(20));
        jTabbedPane1 = new JTabbedPane();
        jTabbedPane1.setTabPlacement(JTabbedPane.TOP);
        vSubPane = new Vector<JPanel>();
        vSubPane.add(new JPanel());
        vSubPane.get(0).setLayout(new BorderLayout());
        vSubPane.get(0).add(jScrollPane2, BorderLayout.CENTER);
        jTabbedPane1.add("Main" ,vSubPane.get(0));
        jPanel3.add(jTabbedPane1);
        
        jPanel4 = new JPanel();
        vRoom = new DefaultListModel();
        jList2 = new JList();
        jList2.setModel(vRoom);
        jScrollPane3 = new JScrollPane(jList2);
        jScrollPane3.setLayout(new ScrollPaneLayout());
        jScrollPane3.setBorder(BorderFactory.createTitledBorder("Room"));
        jButton2 = new JButton("解散");
        jPanel4.setPreferredSize(new Dimension(100,400));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new GridBagLayout());
        jScrollPane3.setPreferredSize(new Dimension (60,350));
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 1;
        jPanel4.add(jScrollPane3, c);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 0;
        c.gridheight = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 0;
        jPanel4.add(jButton2, c);

        
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
        jScrollPane4 = new JScrollPane(jTextArea2);
        jScrollPane4.setPreferredSize(new Dimension(250,60));
        jButton3 = new JButton("表情");
        jButton4 = new JButton("字體");
        jButton5 = new JButton("塗鴉版");
        jPanel5l.add(jScrollPane4);
        jPanel5l.add(jButton3);
        jPanel5l.add(jButton4);
        jPanel5l.add(jButton5);
        
        jButton6 = new JButton("送出");
        jButton7 = new JButton("視訊");
        jButton8 = new JButton("傳檔");
        jPanel5r.add(jButton6);
        jPanel5r.add(jButton7);
        jPanel5r.add(jButton8);
        
        jPanel5.add(jPanel5l);
        jPanel5.add(jPanel5r);
        
        
        
        jPanel1 = new picturePane();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jPanel2, BorderLayout.WEST);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(jPanel4, BorderLayout.EAST);
        jPanel1.add(jPanel5, BorderLayout.SOUTH);
        Container cp = getContentPane();
        cp.add(jPanel1);
        
        jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String clientname = (String)(jList1.getSelectedValue());
                    if(clientname.equals("Server")){
                        addText("Server cannot be removed");
                        return;
                    }   
                    ms.kickClient (clientname);
                } catch (IOException ex) {
                    Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String roomname = ((String)(jList2.getSelectedValue()));
                    ms.quitRoom(roomname);
                } catch (IOException ex) {
                    Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        // 送出
        jButton6.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                    String output = jTextArea2.getText();
                    jTextArea2.setText("");
                    addText("Server廣播:"+output);
                    if ( ((String)(jList1.getSelectedValue())).equals("Server") ){
                        try {
                            ms.sendAll("/s Server "+ output);
                        } catch (IOException ex) {
                            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        String desname = (String)jList1.getSelectedValue();
                        try {
                            ms.sendPrivate( desname, "/w Server "+ desname +" "+ output);
                        } catch (IOException ex) {
                            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }
        });
    }
    
    
    public void addText(String s){
        jTextArea1.append("\n"+s);
    }
    
    public boolean rmFriendList(String s){
        if( vFriend.contains(s) ){
            return vFriend.removeElement(s);
        }
        else
            return false;
    }
    
    
    public boolean addFriendList(String s){
        if( vFriend.contains(s) )
            return false;
        else{
           vFriend.addElement(s);
           return true;
        }
    }
    
    public void rmRoomList(String s){
        vRoom.removeElement(s);
    }
    


    public void addRoomList(String name) {
        System.out.println("YA");
        vRoom.addElement(name);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            ms.saveUserAcc();
        } catch (IOException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    class picturePane extends javax.swing.JPanel {
        protected ImageIcon icon;
        protected ImageIcon stkIcon;
        public picturePane(){
            super();
            icon = new ImageIcon("src/background.jpg");
        }
        public picturePane(String src , int x, int y){
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
            pane = new picturePane("src/userProfile/default.jpg", 300, 300);
            Container c = this.getContentPane();
            c.add(pane);
            setSize(300,300);
        }
        public profilePane(String s){
            super();
            pane = new picturePane("src/userProfile/"+ s +".jpg",300,300);
            Container c = this.getContentPane();
            c.add(pane);
            setSize(300,300);
        }
    }
    
    
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private picturePane jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private Vector<JPanel> vSubPane;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel5r;
    private javax.swing.JPanel jPanel5l;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    
    private MyServer ms;
    private DefaultListModel vFriend;
    private DefaultListModel vRoom;
    profilePane profile;
    int preProfileIdx = 0;
}

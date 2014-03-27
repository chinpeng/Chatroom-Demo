/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGUI;

/**
 *
 * @author nmlab-PC
 */
import MyClient.*;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.*;

/**
 *
 * @author Eddie
 */
public class ConnectWindow extends javax.swing.JDialog {

    /** Creates new form ConnectWindow */
    public ConnectWindow( Lobby f, MyClient mc) {
        super(f);
        myClient = mc;
        setTitle("Login");
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        backgroundPane = new picturePane("src/main/java/myGUI/img/login.jpg");
        buttonGroup1 = new ButtonGroup();
        jTButtonOld = new JToggleButton("舊使用者");  
        jTButtonNew = new JToggleButton("新使用者");
        
        buttonGroup1.add(jTButtonOld);
        buttonGroup1.add(jTButtonNew);
        buttonGroup1.setSelected( jTButtonNew.getModel(), true);

        jLabelPassword = new JLabel();
        jLabelPassword.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        jLabelPassword.setForeground(Color.WHITE);
        jLabelPassword.setText("Password");
        
        PasswordField = new JPasswordField(20);
        PasswordField.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        PasswordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setText("IP");
        
        NameField = new javax.swing.JTextField(20);
        NameField.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        NameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        jLabel3.setForeground(Color.WHITE);
        jLabel3.setText("Name");
        
        jButton1 = new javax.swing.JButton();
        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        jButton1.setText("GO!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        IPField = new javax.swing.JTextField(20);
        IPField.setFont(new java.awt.Font("微軟正黑體", 1, 12)); // NOI18N
        IPField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IPField.setText("140.112.18.192");
        
        PortField = new javax.swing.JTextField(20);
        PortField.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        PortField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PortField.setText("5566");
        
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setText("port");
        
        jButton2 = new javax.swing.JButton();
        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("微軟正黑體", 1, 12));
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //不能關
        setModal(true);
        
        
        backgroundPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5;
        c.ipady = 5;
        
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jLabel1, c);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 4;
        c.gridheight = 1;
        backgroundPane.add(IPField, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jLabel2, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 4;
        c.gridheight = 1;
        backgroundPane.add(PortField, c);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jLabel3, c);
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 4;
        c.gridheight = 1;
        backgroundPane.add(NameField, c);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jLabelPassword, c);
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 4;
        c.gridheight = 1;
        backgroundPane.add(PasswordField, c);
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jTButtonNew, c);
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jTButtonOld, c);
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jButton1, c);
        c.gridx = 3;
        c.gridy = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        backgroundPane.add(jButton2, c);
        
        
        getContentPane().add(backgroundPane);
        setSize(600,500);
       
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String ip = IPField.getText();
        int port = new java.lang.Integer( PortField.getText() ).intValue();
        int isNew = ( buttonGroup1.getSelection() == jTButtonOld.getModel()) ? 0 : 1;

        if( NameField.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "請輸入名稱", "", JOptionPane.ERROR_MESSAGE);
        } else if( NameField.getText().contains(" ") ) {
            JOptionPane.showMessageDialog(this, "名稱請不要有空白", "", JOptionPane.ERROR_MESSAGE);
            NameField.setText("");
        }
        else if( PasswordField.getPassword().equals(" ") ) {
            JOptionPane.showMessageDialog(this, "請輸入密碼", "", JOptionPane.ERROR_MESSAGE);
            NameField.setText("");
        }
        else {
            String name = NameField.getText();
            String password = new String( PasswordField.getPassword() );
            myClient.getClientInformation(ip, port, name, password, isNew);
            setVisible(false);
        }
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }                                        

    public void rename() {
		IPField.setBackground(new java.awt.Color(204, 204, 255));
		IPField.setEditable(false);
		PortField.setBackground(new java.awt.Color(204, 204, 255));
		PortField.setEditable(false);
		NameField.setText("");
		setVisible(true);
    }

    public void reconnect() {
		IPField.setBackground(java.awt.Color.WHITE);
		IPField.setEditable(true);
		PortField.setBackground(java.awt.Color.WHITE);
		PortField.setEditable(true);
		NameField.setText("");
		setVisible(true);
    }
    
    class picturePane extends javax.swing.JPanel {
        protected ImageIcon icon;
        public picturePane(String s){
            super();
            icon = new ImageIcon(s);
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, getParent());
        }
    }


    // Variables declaration - do not modify  
    private picturePane backgroundPane;
    private JToggleButton jTButtonOld;
    private JToggleButton jTButtonNew;
    private ButtonGroup buttonGroup1;
    private javax.swing.JTextField IPField;
    private javax.swing.JTextField NameField;
    private javax.swing.JTextField PortField;

    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelPassword;
    // End of variables declaration                   
    
    private MyClient myClient;
}


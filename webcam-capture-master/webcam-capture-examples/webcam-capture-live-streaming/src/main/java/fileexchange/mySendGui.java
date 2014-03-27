/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileexchange;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import MyClient.*;
/**
 *
 * @author nmlab
 */
public class mySendGui extends JDialog {
    
    public mySendGui(){
              
        initComponent();
    }
    
    private void initComponent(){
        
        fileChooseBtn=new JButton();
        fileSendBtn=new JButton();
        fileNameLabel=new JLabel();
        fileSizeLabel=new JLabel();
        cancelBtn=new JButton();
        mainPane=new picturePane ();
        wantToSend=false;
        
 
        
        setTitle("Send File");
        setMinimumSize(new java.awt.Dimension(227, 117));

        fileSendBtn.setText("Send");
        fileSendBtn.setEnabled(false);
        fileSendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSendBtnActionPerformed(evt);
            }
        });
 
        fileChooseBtn.setText("Choose");
        fileChooseBtn.setEnabled(true);
        fileChooseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooseBtnActionPerformed(evt);
            }
        });
     
        cancelBtn.setText("Cancel");
        cancelBtn.setEnabled(true);
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        fileNameLabel.setFont(new java.awt.Font("", 0, 12));
        fileNameLabel.setText("FileName:");

        fileSizeLabel.setFont(new java.awt.Font("", 0, 12));
        fileSizeLabel.setText("File Size:");
        
        this.setLayout(new BorderLayout());
        this.add(mainPane,BorderLayout.CENTER);
        
        mainPane.setLayout(new GridLayout(8,1,0,0));
        mainPane.add(fileNameLabel);
        mainPane.add(fileSizeLabel);
        mainPane.add(fileChooseBtn);
        mainPane.add(fileSendBtn);
        mainPane.add(cancelBtn);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
      
        
    }
   
    
    class picturePane extends javax.swing.JPanel {
        protected ImageIcon icon;
        public picturePane(){
            super();
            icon = new ImageIcon("src/myGUI/img/scar.jpg");
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, getParent());
        }
    }
    
    private void fileSendBtnActionPerformed(ActionEvent e){
        fileSendBtn.setEnabled(false);
        wantToSend=true;  
        dispose();
        
    }
    

    
    private void fileChooseBtnActionPerformed(ActionEvent e){
        sendFile=getFile();   
        
    }
    
    private void cancelBtnActionPerformed(ActionEvent e){
        
        dispose();
        
    }
    public File getFile() {
        JFileChooser fc =new JFileChooser();
        int result =fc.showOpenDialog(new JFrame());

        if(result==JFileChooser.APPROVE_OPTION){
            sendFile=fc.getSelectedFile();
            fileName=sendFile.getAbsolutePath();
            fileSize=(int)sendFile.length();
	    fileNameLabel.setText("傳送檔案" + fileName);
	    fileSizeLabel.setText("大小: "+fileSize+" bytes");   
            fileSendBtn.setEnabled(true);
            
            return sendFile;
        }
        else 
            return null;
        
    }
    
    public void send() {
        
      		fileNameLabel.setText("傳送檔案" + fileName +" ING"+"/大小: "+fileSize+" bytes");  
                wantToSend=true;    
                fileChooseBtn.setEnabled(false);
                
  }
    
   public boolean getWantToSend(){
       
       return wantToSend;
       
   } 
    public void done() {
        
		fileNameLabel.setText("傳送檔案" + fileName +"傳送完成！");
		fileSendBtn.setEnabled(true);
                this.setVisible(false);
                
   }
    public void denyTransfer() {
		JOptionPane.showMessageDialog(this, "對方拒絕接收檔案", "", JOptionPane.INFORMATION_MESSAGE);
   }
   public String getFileName(){
       
       return fileName;
   }
   
    public File getChosenFile(){
        
          return sendFile;
          
    }
    
    private File sendFile;
    private String fileName;
    private int fileSize;
    private boolean wantToSend;
    private JButton fileChooseBtn;
    private JButton fileSendBtn;
    private JButton cancelBtn;
    private JLabel fileNameLabel;
    private JLabel fileSizeLabel;
    private picturePane mainPane; 
   
    
     
}

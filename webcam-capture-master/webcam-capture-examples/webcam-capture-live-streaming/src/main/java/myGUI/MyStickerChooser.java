/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGUI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author user
 */
public class MyStickerChooser extends JDialog implements ActionListener{
    public MyStickerChooser(Lobby lobby){
        setTitle("貼圖選擇器");
        lob = lobby;
        init();
        setVisible(true);
        setSize(300,300);
        pack();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);     
        setModal(true);
    }
    
    private void init(){
        modFlowLayout = new MyStickerChooser.ModifiedFlowLayout();
        jButton = new JButton[201];    ////////////////////////////////
        for(int i = 0;i < 200;i++){
            int newi = i + 1;
            String path = pathHead + newi + pathTail;
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage();     //縮小點
            int h = (int)Math.round(img.getHeight(null) * 0.5);
            int w = (int)Math.round(img.getWidth(null) * 0.5);        
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);  //開始縮小
            Graphics g = bi.createGraphics();
            g.drawImage(img,0,0,w,h,null);
            ImageIcon newIcon = new ImageIcon(bi);
            jButton[i] = new JButton(null, newIcon);
            jButton[i].addActionListener(this);
        }
        //////////////
        String path = pathHead + 202 + pathTail;
        ImageIcon icon = new ImageIcon(path);
        jButton[200] = new JButton(null, icon);
        jButton[200].addActionListener(this);
        ///////////////////////
        FlowLayout flow = new FlowLayout();
        jPane1 = new JPanel(modFlowLayout);
        jPane1.setSize(500,600);
        jScrollPane1 = new JScrollPane();
        Container cp = getContentPane();
        for(int i = 0;i < 201;i++){
            jPane1.add(jButton[i]);
        }
        jScrollPane1 = new JScrollPane(jPane1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        cp.add(jScrollPane1);
        cp.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        for(int i = 0;i<201;i++){
           if(e.getSource() == jButton[i]){
               lob.addStickersLine(i+1);
           }
        }
    }
    
     public class ModifiedFlowLayout extends FlowLayout {
       public ModifiedFlowLayout() {
              super();
           }

           public ModifiedFlowLayout(int align) {
              super(align);
           }
       public ModifiedFlowLayout(int align, int hgap, int vgap) {
          super(align, hgap, vgap);
       }

       public Dimension minimumLayoutSize(Container target) {
          // Size of largest component, so we can resize it in
          // either direction with something like a split-pane.
          return computeMinSize(target);
       }

       public Dimension preferredLayoutSize(Container target) {
          return computeSize(target);
       }

       private Dimension computeSize(Container target) {
          synchronized (target.getTreeLock()) {
             int hgap = getHgap();
             int vgap = getVgap();
             int w = target.getWidth();

             // Let this behave like a regular FlowLayout (single row)
             // if the container hasn't been assigned any size yet
             if (w == 0) {
                w = Integer.MAX_VALUE;
             }

             Insets insets = target.getInsets();
             if (insets == null){
                insets = new Insets(0, 0, 0, 0);
             }
             int reqdWidth = 0;

             int maxwidth = w - (insets.left + insets.right + hgap * 2);
             int n = target.getComponentCount();
             int x = 0;
             int y = insets.top + vgap; // FlowLayout starts by adding vgap, so do that here too.
             int rowHeight = 0;

             for (int i = 0; i < n; i++) {
                Component c = target.getComponent(i);
                if (c.isVisible()) {
                   Dimension d = c.getPreferredSize();
                   if ((x == 0) || ((x + d.width) <= maxwidth)) {
                      // fits in current row.
                      if (x > 0) {
                         x += hgap;
                      }
                      x += d.width;
                      rowHeight = Math.max(rowHeight, d.height);
                   }
                   else {
                      // Start of new row
                      x = d.width;
                      y += vgap + rowHeight;
                      rowHeight = d.height;
                   }
                   reqdWidth = Math.max(reqdWidth, x);
                }
             }
             y += rowHeight;
             y += insets.bottom;
             return new Dimension(reqdWidth+insets.left+insets.right, y);
          }
       }

       private Dimension computeMinSize(Container target) {
          synchronized (target.getTreeLock()) {
             int minx = Integer.MAX_VALUE;
             int miny = Integer.MIN_VALUE;
             boolean found_one = false;
             int n = target.getComponentCount();

             for (int i = 0; i < n; i++) {
                Component c = target.getComponent(i);
                if (c.isVisible()) {
                   found_one = true;
                   Dimension d = c.getPreferredSize();
                   minx = Math.min(minx, d.width);
                   miny = Math.min(miny, d.height);
                }
             }
             if (found_one) {
                return new Dimension(minx, miny);
             }
             return new Dimension(0, 0);
          }
       }

    }
    
    private MyStickerChooser.ModifiedFlowLayout modFlowLayout;
    private JButton[] jButton;
    private JPanel jPane1;
    private JScrollPane jScrollPane1;
    private Lobby lob;    
    private String pathHead = "src/main/java/myGUI/stickers/stks (";
    private String pathTail = ").png";
    private String pathTailG = ").gif";
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Nick
 */
public class HyperLink extends JLabel{
    
    public HyperLink(String add){
        url = add;
        setText(url);
        setForeground(Color.blue);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(
                new MouseAdapter() 
                {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                    Desktop.getDesktop().browse(new URI(url));
                    }
                    catch (URISyntaxException | IOException ex) {
                            System.out.println("It looks like there's a problem");
                    }
                }
            }
        );
    }
    
    private String url;        
}

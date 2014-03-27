/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myserver;

import javax.swing.UIManager;

/**
 *
 * @author apple
 */
public class Main {
    	private static MyServer ms;
    /**
     * @param args the command line arguments
     * @throws java.lang.Throwable
     */
    public static void main(String[] args) throws Throwable {
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		ms = new MyServer();
    }
    
}

package us.sosia.video.stream.agent;

import java.awt.Dimension;
import java.net.*;
//import java.io.*;

import com.github.sarxos.webcam.Webcam;

public class StreamServer implements Runnable {
   
	public Webcam webcam; 
	public Dimension dimension = new Dimension(320,240);
	public StreamServerAgent serverAgent;
	
	
	public StreamServer(){
		
		Webcam.setAutoOpenMode(true);
		Webcam webcam = Webcam.getDefault();	
		webcam.setViewSize(dimension);

		serverAgent = new StreamServerAgent(webcam, dimension);
		/*
		try {
			serverAgent.start(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 20000));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		*/	
	
	}
	
	public void run() {
		/*			
		StreamServerAgent serverAgent = new StreamServerAgent(webcam, dimension);
		*/
		try {
			serverAgent.start(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 20000));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

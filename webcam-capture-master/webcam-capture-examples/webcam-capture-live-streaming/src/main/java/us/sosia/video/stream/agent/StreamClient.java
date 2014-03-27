package us.sosia.video.stream.agent;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.sosia.video.stream.agent.ui.SingleVideoDisplayWindow;
import us.sosia.video.stream.handler.StreamFrameListener;

public class StreamClient implements Runnable{
	/**
	 * @author kerr
	 * */
	private final static Dimension dimension = new Dimension(320,240);
	private final static SingleVideoDisplayWindow displayWindow = new SingleVideoDisplayWindow("Streaming Webcam ",dimension);
	protected final static Logger logger = LoggerFactory.getLogger(StreamClient.class);
	public StreamClientAgent clientAgent;
	
	private String srcAddr;
	public StreamClient(String srcAddr){
	  
		this.srcAddr=srcAddr;
		displayWindow.setVisible(true);
		logger.info("setup dimension :{}",dimension);
		clientAgent = new StreamClientAgent(new StreamFrameListenerIMPL(),dimension);
		clientAgent.connect(new InetSocketAddress(srcAddr, 20000));

	}
	
	public void run (){
	
		  

	}
	
	
	protected static class StreamFrameListenerIMPL implements StreamFrameListener{
		private volatile long count = 0;
		@Override
		public void onFrameReceived(BufferedImage image) {
			logger.info("frame received :{}",count++);
			displayWindow.updateImage(image);			
		}
		
	}
	

}

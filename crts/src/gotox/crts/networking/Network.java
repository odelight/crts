package gotox.crts.networking;

import gotox.crts.controller.Action;
import gotox.networking.FrameTimer;
import gotox.networking.NetworkedQueue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Network {
	private final NetworkedQueue<NetworkFrame> frameQueue;
	private final FrameTimer frameTimer = new FrameTimer();
	
	private final ReentrantLock NextFrameAcionsLock = new ReentrantLock();
	private List<Action> NextFrameActions = new ArrayList<>();
	
	public Network(int portNumber) throws IOException{
		
		try ( 
			    ServerSocket serverSocket = new ServerSocket(portNumber);
			    Socket clientSocket = serverSocket.accept();
			    OutputStream out = clientSocket.getOutputStream();
			    InputStream in = clientSocket.getInputStream();
			) {
			frameQueue = new NetworkedQueue<NetworkFrame>(out, in);
		}
		
	}
	public Network (String hostName, int portNumber) throws IOException{
		try (
			    Socket clientSocket = new Socket(hostName, portNumber);
			    OutputStream out = clientSocket.getOutputStream();
			    InputStream in = clientSocket.getInputStream();
			)	{
			frameQueue = new NetworkedQueue<NetworkFrame>(out, in);
		}
	}
	public void queueAction(Action a){
		NextFrameAcionsLock.lock();
		NextFrameActions.add(a);
		NextFrameAcionsLock.unlock();
	}
	private void sendFrameAndScheduleNext(){
		NextFrameAcionsLock.lock();
		List<Action> sendActions = NextFrameActions;
		NextFrameActions = new ArrayList<>();
		NextFrameAcionsLock.unlock();
		NetworkFrame frame = new NetworkFrame(sendActions);
		frameQueue.pushFrame(frame);
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	sendFrameAndScheduleNext();
		            }
		        }, 
		        frameTimer.nextFrameEnd()
		);
	}
	
}

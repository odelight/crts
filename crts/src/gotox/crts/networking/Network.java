package gotox.crts.networking;

import gotox.crts.controller.Action;
import gotox.networking.FrameTimer;
import gotox.networking.NetworkedQueue;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Network {
	private final NetworkedQueue<NetworkFrame> frameQueue;
	private final FrameTimer frameTimer = new FrameTimer();
	private final List<NetworkFrame> receivedFrames = new ArrayList<>();
	private AtomicInteger frameNumber = new AtomicInteger(0);
	
	private final ReentrantLock NextFrameAcionsLock = new ReentrantLock();
	private List<Action> NextFrameActions = new ArrayList<>();
	
	public Network(int portNumber) throws IOException{
		
		try ( 
			    ServerSocket serverSocket = new ServerSocket(portNumber);
			    Socket clientSocket = serverSocket.accept();
			    OutputStream out = clientSocket.getOutputStream();
			    InputStream in = clientSocket.getInputStream();
			) {
			frameQueue = new NetworkedQueue<NetworkFrame>(new ObjectOutputStream(out), new ObjectInputStream(in));
		}
		
	}
	public Network (String hostName, int portNumber) throws IOException{
		try (
			    Socket clientSocket = new Socket(hostName, portNumber);
			    OutputStream out = clientSocket.getOutputStream();
			    InputStream in = clientSocket.getInputStream();
			)	{
			frameQueue = new NetworkedQueue<NetworkFrame>(new ObjectOutputStream(out), new ObjectInputStream(in));
		}
	}
	//Starts a vestigial network for single player.
	public Network(){
		frameQueue = new NetworkedQueue<NetworkFrame>(Collections.<ObjectOutputStream> emptyList(), Collections.<ObjectInputStream> emptyList());
	}
	public void start(){
		sendFrameAndScheduleNext();
	}
	public void queueActions(Collection<Action> actions){
		for(Action a : actions){
			queueAction(a);
		}
	}
	public void queueAction(Action a){
		NextFrameAcionsLock.lock();
		NextFrameActions.add(a);
		NextFrameAcionsLock.unlock();
	}
	public List<Action> getActions(){
		List<NetworkFrame> frames = frameQueue.pollFrames();
		receivedFrames.addAll(frames);
//		Collections.
//		for(Frame f : )
		return Collections.emptyList();
	}
	private void sendFrameAndScheduleNext() {
		NextFrameAcionsLock.lock();
		List<Action> sendActions = NextFrameActions;
		NextFrameActions = new ArrayList<>();
		NextFrameAcionsLock.unlock();
		NetworkFrame frame = new NetworkFrame(sendActions, frameNumber.getAndIncrement());
		try {
			frameQueue.pushFrame(frame);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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

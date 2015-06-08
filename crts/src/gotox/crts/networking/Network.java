package gotox.crts.networking;

import gotox.crts.controller.Action;
import gotox.networking.NetworkedQueue;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Network {
	private  NetworkedQueue<NetworkFrame> frameQueue;
	private final List<NetworkFrame> receivedFrames = new ArrayList<>();
	private AtomicInteger frameNumber = new AtomicInteger(0);

	private final ReentrantLock NextFrameAcionsLock = new ReentrantLock();
	private List<Action> NextFrameActions = new ArrayList<>();
	
	private List<Closeable> cleanupList = new ArrayList<>();

	public Network(int portNumber) throws IOException {
		ServerSocket serverSocket = new ServerSocket(portNumber);
		cleanupList.add(serverSocket);
		Socket s = serverSocket.accept();
		cleanupList.add(s);
		init(s);
	}

	public Network(String hostName, int portNumber) throws IOException {
		Socket s = new Socket(hostName, portNumber);
		cleanupList.add(s);
		init(s);
	}
	

	// Starts a vestigial network for single player.
	public Network() {
		frameQueue = new NetworkedQueue<NetworkFrame>(
				Collections.<ObjectOutputStream> emptyList(),
				Collections.<ObjectInputStream> emptyList());
	}

	private void init (Socket s) throws IOException{
		OutputStream out = s.getOutputStream();
		InputStream in = s.getInputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.flush();
		ObjectInputStream objIn = new ObjectInputStream(in);
		frameQueue = new NetworkedQueue<NetworkFrame>(objOut, objIn);
		cleanupList.addAll(Arrays.asList(out, in, objOut, objIn));
	}

	public void cleanUp() throws IOException{
		Collections.reverse(cleanupList);
		for(Closeable c : cleanupList){
			c.close();
		}
	}
	
	public void start() {
		new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask() {
			@Override
			public void run() {
				NextFrameAcionsLock.lock();
				List<Action> sendActions = NextFrameActions;
				NextFrameActions = new ArrayList<>();
				NextFrameAcionsLock.unlock();
				NetworkFrame frame = new NetworkFrame(sendActions, frameNumber
						.getAndIncrement());
				try {
					frameQueue.pushFrame(frame);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}, 0, 200);
	}

	public void queueActions(Collection<Action> actions) {
		for (Action a : actions) {
			queueAction(a);
		}
	}

	public void queueAction(Action a) {
		NextFrameAcionsLock.lock();
		NextFrameActions.add(a);
		NextFrameAcionsLock.unlock();
	}

	public List<Action> getActions() {
		List<NetworkFrame> frames = frameQueue.pollFrames();
		receivedFrames.addAll(frames);
		List<Action> ret = new ArrayList<>();
		Iterator<NetworkFrame> iter = receivedFrames.iterator();
		while (iter.hasNext()) {
			NetworkFrame f = iter.next();
			if (f.getFrameNumber() <= (frameNumber.get() - 2)) {
				ret.addAll(f.getActionList());
				iter.remove();
			}
		}
		return ret;
	}

}

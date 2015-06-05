package gotox.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Queue of objects of type T('frames') which is synchronized over a network.
 * frames can be added freely, but will only be visible in the queue once all
 * other frames with the same timestamp have been received.
 * 
 * @author olof
 * 
 * @param <T>
 */
public class NetworkedQueue<T extends Serializable> {
	private final List<ObjectOutputStream> remoteOut;
//	private final List<ObjectInputStream> remoteIn;
	private final List<ReentrantLock> receivedFramesLocks;
	private final List<List<FrameWrapper<T>>> receivedFrames;
	private final List<Poller> remoteInPollers;
	private int lastReceivedFrame = 0;
	private int lastSentFrame = 0;

	public NetworkedQueue(List<ObjectOutputStream> remoteOut,
			List<ObjectInputStream> remoteIn) {
		this.remoteOut = remoteOut;
//		this.remoteIn = remoteIn;
		receivedFrames = new ArrayList<>();
		receivedFramesLocks = new ArrayList<>();
		remoteInPollers = new ArrayList<>();
		for (int i = 0; i < remoteIn.size(); i++) {
			ArrayList<FrameWrapper<T>> receivedFrameList = new ArrayList<>();
			receivedFrames.add(receivedFrameList);
			ReentrantLock l = new ReentrantLock();
			receivedFramesLocks.add(l);
			Poller<T> poller = new Poller<T>(remoteIn.get(i), receivedFrameList,
					l);
			remoteInPollers.add(poller);
			Thread t = new Thread(poller);
			t.start();
		}
	}
	
	public void stop(){
		for(Poller p : remoteInPollers){
			p.kill();
		}
	}

	public NetworkedQueue(ObjectOutputStream out, ObjectInputStream in) {
		this(Arrays.asList(out), Arrays.asList(in));
	}

	public void pushFrame(T frame) throws IOException {
		if (frame == null)
			throw new NullPointerException();
		FrameWrapper<T> wrapped = new FrameWrapper<T>(frame, lastSentFrame++,
				System.currentTimeMillis());
		for (ObjectOutputStream out : remoteOut) {
			out.writeObject(wrapped);
			out.flush();
		}
	}

	public List<T> pollFrames() throws IOException {
		List<T> ret = new ArrayList<T>();

		int highestAvailableFrame = Integer.MAX_VALUE;
		for (int i = 0; i < receivedFrames.size(); i++) {
			List<FrameWrapper<T>> frameList = receivedFrames.get(i);
			if (frameList.size() > 0) {
				FrameWrapper<T> f = frameList.get(frameList.size() - 1);
				highestAvailableFrame = Math.min(highestAvailableFrame,
						f.frameNumber);
			} else {
				return ret;
			}
		}
		for (int i = 0; i < receivedFrames.size(); i++) {
			List<FrameWrapper<T>> frameList = receivedFrames.get(i);
			receivedFramesLocks.get(i).lock();
			while(frameList.size() > 0){
				FrameWrapper<T> wrapped = frameList.get(0);
				if(wrapped.frameNumber <= highestAvailableFrame){
					frameList.remove(0);
					ret.add(wrapped.innerFrame);
				} else {
					break;
				}
			}
			receivedFramesLocks.get(i).unlock();
		}
		return ret;
	}

	/**
	 * TODO: Put some more thought into the serialization here before release.
	 * 
	 * @author olof
	 * 
	 */
	static class FrameWrapper<S> implements Serializable {
		private static final long serialVersionUID = 1L;
		private final long pushedTimestamp;
		private final int frameNumber;
		private S innerFrame;

		private FrameWrapper(S frame, int frameNumber, long pushedTimestamp) {
			innerFrame = frame;
			this.frameNumber = frameNumber;
			this.pushedTimestamp = pushedTimestamp;
		}

		S getInnerFrame() {
			return innerFrame;
		}

		int getFrameNumber() {
			return frameNumber;
		}

		long getPushedTimestamp() {
			return pushedTimestamp;
		}
	}

	static class Poller<S> implements Runnable {
		private final ObjectInputStream in;
		private final List<FrameWrapper<S>> receivedFrames;
		private final ReentrantLock lock;
		private boolean running = true;

		public Poller(ObjectInputStream in,
				List<FrameWrapper<S>> receivedFrames, ReentrantLock lock) {
			this.in = in;
			this.receivedFrames = receivedFrames;
			this.lock = lock;
		}

		public void kill() {
			running = false;
		}

		@Override
		public void run() {
			try {
				while (running) {
					Object o = in.readObject();
					lock.lock();
					receivedFrames.add((FrameWrapper<S>) o);
					lock.unlock();
				}
			} catch (ClassNotFoundException | IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

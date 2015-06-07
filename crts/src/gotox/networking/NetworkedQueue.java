package gotox.networking;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private final List<List<FrameWrapper<T>>> receivedFramesBySource;
	private final List<FrameWrapper<T>> pushedFrames;
	private final List<Poller<T>> remoteInPollers;
	private int lastSentFrame = 0;

	public NetworkedQueue(List<ObjectOutputStream> remoteOut,
			List<ObjectInputStream> remoteIn) {
		this.remoteOut = remoteOut;
		receivedFramesBySource = new ArrayList<>();
		pushedFrames = new ArrayList<>();
		receivedFramesBySource.add(pushedFrames);
		remoteInPollers = new ArrayList<>();
		for (int i = 0; i < remoteIn.size(); i++) {
			ArrayList<FrameWrapper<T>> receivedFrameList = new ArrayList<>();
			receivedFramesBySource.add(receivedFrameList);
			Poller<T> poller = new Poller<T>(remoteIn.get(i), receivedFrameList);
			remoteInPollers.add(poller);
			Thread t = new Thread(poller);
			t.start();
		}
	}

	public void stop() {
		for (Poller<T> p : remoteInPollers) {
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
		synchronized (pushedFrames) {
			pushedFrames.add(wrapped);
		}
	}

	public List<T> pollFrames() {
		List<T> ret = new ArrayList<T>();

		int highestAvailableFrame = Integer.MAX_VALUE;
		for (int i = 0; i < receivedFramesBySource.size(); i++) {
			List<FrameWrapper<T>> frameList = receivedFramesBySource.get(i);
			if (frameList.size() > 0) {
				FrameWrapper<T> f = frameList.get(frameList.size() - 1);
				highestAvailableFrame = Math.min(highestAvailableFrame,
						f.getFrameNumber());
			} else {
				return ret;
			}
		}
		for (int i = 0; i < receivedFramesBySource.size(); i++) {
			List<FrameWrapper<T>> frameList = receivedFramesBySource.get(i);
			synchronized (frameList) {
				while (frameList.size() > 0) {
					FrameWrapper<T> wrapped = frameList.get(0);
					if (wrapped.getFrameNumber() <= highestAvailableFrame) {
						frameList.remove(0);
						ret.add(wrapped.innerFrame);
					} else {
						break;
					}
				}
			}
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
		private volatile Thread pollerThread;
		private final ObjectInputStream in;
		private final List<FrameWrapper<S>> receivedFrames;
		private boolean running = true;

		public Poller(ObjectInputStream in, List<FrameWrapper<S>> receivedFrames) {
			this.in = in;
			this.receivedFrames = receivedFrames;
		}

		public void kill() {
			running = false;
			pollerThread.interrupt();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			pollerThread = Thread.currentThread();
			try {
				while (running) {
					Object o = in.readObject();
					synchronized (receivedFrames) {
						receivedFrames.add((FrameWrapper<S>) o);
					}
				}
			} catch (InterruptedIOException e) {
				//Will get interrupted exception when shutting down.
				if (running)
					throw new RuntimeException(e);
			} catch (ClassNotFoundException | IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

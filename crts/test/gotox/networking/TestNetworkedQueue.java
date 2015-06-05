package gotox.networking;

import static org.junit.Assert.*;

import gotox.networking.NetworkedQueue.FrameWrapper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNetworkedQueue {
	private NetworkedQueue<String> netQueue;
	private NetworkedQueue<String> netQueueRemote;

	private ObjectOutputStream mockRemoteOut;
	private ObjectInputStream mockLocalIn;

	private ObjectInputStream mockRemoteIn;
	private ObjectOutputStream mockLocalOut;

	@Before
	public void init() throws IOException {
		PipedInputStream pipeRemote = new PipedInputStream();
		mockRemoteOut = new ObjectOutputStream(
				new PipedOutputStream(pipeRemote));
		mockRemoteIn = new ObjectInputStream(pipeRemote);

		PipedInputStream pipeLocal = new PipedInputStream();
		mockLocalOut = new ObjectOutputStream(new PipedOutputStream(pipeLocal));
		mockLocalIn = new ObjectInputStream(pipeLocal);
		
		netQueue = new NetworkedQueue<String>(mockLocalOut, mockRemoteIn);
		netQueueRemote  = new NetworkedQueue<String>(mockRemoteOut, mockLocalIn);

	}

	@After
	public void cleanup(){
		netQueue.stop();
		netQueueRemote.stop();
	}
	
	@Test(timeout = 500)
	public void testSendMsg() throws IOException, ClassNotFoundException {
		final String test = "yo dawg";
		netQueue.pushFrame(test);
		assertEquals(test,
				((FrameWrapper) mockLocalIn.readObject()).getInnerFrame());
	}
	@Test
	public void testExchangeMsg() throws IOException{
		final String msg1 = "yo dawg";
		final String msg2 = "sup homey";
		netQueue.pushFrame(msg1);
		netQueueRemote.pushFrame(msg2);
		
		List<String> list = netQueue.pollFrames();
		assertTrue(list.contains(msg1));
		assertTrue(list.contains(msg2));
		assertEquals(list.size(), 2);		
		list = netQueueRemote.pollFrames();
		assertTrue(list.contains(msg1));
		assertTrue(list.contains(msg2));
		assertEquals(list.size(), 2);
	}

}

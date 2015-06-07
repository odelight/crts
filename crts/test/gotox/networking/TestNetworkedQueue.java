package gotox.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	
	PipedInputStream pipeRemote;
	PipedInputStream pipeLocal;

	@Before
	public void init() throws IOException {
		pipeRemote = new PipedInputStream();
		mockRemoteOut = new ObjectOutputStream(
				new PipedOutputStream(pipeRemote));
		mockRemoteIn = new ObjectInputStream(pipeRemote);

		pipeLocal = new PipedInputStream();
		mockLocalOut = new ObjectOutputStream(new PipedOutputStream(pipeLocal));
		mockLocalIn = new ObjectInputStream(pipeLocal);
		
		netQueue = new NetworkedQueue<String>(mockLocalOut, mockRemoteIn);
		netQueueRemote  = new NetworkedQueue<String>(mockRemoteOut, mockLocalIn);

	}

	@After
	public void cleanup() throws IOException{
		stopQueues();

		mockLocalOut.close();
		mockRemoteOut.close();
		mockLocalIn.close();
		mockRemoteIn.close();
		
		pipeRemote.close();
		pipeLocal.close();
		
	}
	
	private void stopQueues(){
		netQueue.stop();
		netQueueRemote.stop();
	}
	
	@Test
	public void testExchangeMsg() throws IOException, InterruptedException{
		final String msg1 = "yo dawg";
		final String msg2 = "sup homey";
		netQueue.pushFrame(msg1);
		netQueueRemote.pushFrame(msg2);
		Thread.sleep(100);
		List<String> list = netQueue.pollFrames();
		assertTrue(list.contains(msg2));
		assertTrue(list.contains(msg1));
		assertEquals(list.size(), 2);		
		list = netQueueRemote.pollFrames();
		assertTrue(list.contains(msg1));
		assertTrue(list.contains(msg2));
		assertEquals(list.size(), 2);
	}
}

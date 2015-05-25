package gotox.networking;

import java.io.Serializable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Queue of objects of type T('frames') which is synchronized over a network.
 * frames can be added freely, but will only be visible in the queue once their 
 * position relative to frames pushed on other machines can be resolved. Null 
 * frames are not valid.
 * 
 * @author olof
 *
 * @param <T>
 */
public class NetworkedQueue<T extends Serializable> {
	
	public NetworkedQueue(List<OutputStream> remoteOut, List<InputStream> remoteIn){
		
	}
	
	public void pushFrame(T frame2){
		
	}

	public T pollFrame(){
		return null;
	}
	
	
}

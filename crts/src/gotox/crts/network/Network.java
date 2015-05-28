package gotox.crts.network;

import gotox.networking.NetworkedQueue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Network {
	public static NetworkedQueue<NetworkFrame> host(int portNumber) throws IOException{
		
		try ( 
			    ServerSocket serverSocket = new ServerSocket(portNumber);
			    Socket clientSocket = serverSocket.accept();
			    OutputStream out = clientSocket.getOutputStream();
			    InputStream in = clientSocket.getInputStream();
			) {
			return new NetworkedQueue<NetworkFrame>(out, in);
		}
		
	}
	public static NetworkedQueue<NetworkFrame> join(String hostName, int portNumber) throws IOException{
		try (
			    Socket clientSocket = new Socket(hostName, portNumber);
			    OutputStream out = clientSocket.getOutputStream();
			    InputStream in = clientSocket.getInputStream();
			)	{
			return new NetworkedQueue<NetworkFrame>(out, in);
		}
	}
}

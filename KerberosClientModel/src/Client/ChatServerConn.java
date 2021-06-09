package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatServerConn extends SocketConn {
	private static Socket chatConn = null;
	
	private static ChatServerConn conn = null;
	
	private ChatServerConn(){
		socket = null;
		try {
			chatConn = new Socket("192.168.43.226", 20000);
			//chatConn = new Socket("192.168.43.226", 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ChatServerConn getConn(){
		if(conn == null)
			conn = new ChatServerConn();
		return conn;
	}
	
	public void send(byte[] content){
		try {
			OutputStream socketOut = chatConn.getOutputStream();
			socketOut.write(content);
			socketOut.flush();
			log.info("(ChatServer) Messeag has been sent!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.warning("Fail to send Messeag due to IOException!");
			e.printStackTrace();
		}
	}
	
	public int receive(byte[] result){
		int len = -1;
		try {
			InputStream socketIn = chatConn.getInputStream();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			len = socketIn.read(result, 0, MAX_SIZE);
			log.info("(ChatServer) Messeag has been received!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.warning("Fail to receive Messeag due to IOException!");
			e.printStackTrace();
		}
		System.out.println("(ChatServer) len: "+len);
		return len;
	}
	
	public void close() throws IOException{
		chatConn.close();
		chatConn = null;
	}
}

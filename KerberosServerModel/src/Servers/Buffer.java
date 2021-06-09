package Servers;
/**
 * �̵߳���Ϣ���棬���ڶ��߳�֮����Ϣ�Ľ���
 * @author gaoshao
 *
 */
public class Buffer {
	private byte[] message = new byte[8216];
	private boolean available = false;
	
	/**
	 * ����Ϣ
	 * @return
	 */
	public synchronized byte[] get(){
		while(available == false){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		available = false;
		notify();
		return message;
	}
	
	/**
	 * ����Ϣд������
	 * @param value
	 */
	public synchronized void put(byte[] value){
		while(available){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int i=0;
		for(byte b : value){
			message[i++] = b;
		}
		
		available = true;
		notify();
	}
}

package Servers;
//AS���ڲ��������Ա��Ľ��з�����
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import DBManger.DataConn;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import DBManger.DBExcute;
import Message.Message;
import Security.DES.Des;
import Security.MD5.MD5;
import Security.RSA.RSA;

//���Ƿ�������һ���߳�
public class AS implements Runnable{
	 private Socket socket;
	 byte [] message=new byte[8216];
	 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	 private final long MAXTIME=100000; 
	 
	 //Դ
	 private final int AS = 1;//AS������
	 private final int TGS = 2;//TGS������
	 private final int PSERVERCHAT = 3;//Ӧ�÷���������Ϣ��
//	 private final int PSERVERFILE = 4;//Ӧ�÷��������ļ���
	 
	 //����respond
	 private final byte NEGATIVE = 0;//���ֶ�δ������
	 private final byte SUCCESS = 1;//�����ɹ�
	 private final byte DENY = 2;//�ܾ�����
	 private final byte FAILTOUPLOAD = 3;//�ļ��ϴ�ʧ��
	 private final byte FAILTODOWNLOAD = 4;//�ļ�����ʧ��
	 private final byte USEROFFLINE = 5;//��ϢͶ�ݳɹ������û���������״̬
	 private final byte OVERTIME = 6;//��Ϣ��ʱ
	 
	 private Logger log = Logger.getLogger("Connect-Status-Log"); //��־
	 
	 final static int MAX_SIZE = 8216;
	 
	 public AS(Socket socket){
		 this.socket=socket;
		 DataConn.OpenConn();
	 }
	 
	 public void send(byte[] content){
			try {
				OutputStream socketOut = socket.getOutputStream();
				socketOut.write(content);
				socketOut.flush();
				log.info("Messeag has been sent!");
			} catch (IOException e) {
				log.warning("Fail to send Messeag due to IOException!");
				e.printStackTrace();
			}
		}
	 
	 public int receive(byte[] result){
			int len = -1;
			try {
				InputStream socketIn = socket.getInputStream();
				len = socketIn.read(result, 0, MAX_SIZE);
				log.info("Messeag has been received!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.warning("Fail to receive Messeag due to IOException!");
				e.printStackTrace();
			}
			return len;
	}
	 
	 
	@Override
	/**
	 * �������߼�
	 */
	public void run(){
		System.out.println("New connection accepted "+socket.getInetAddress()+":"+socket.getPort());
		try {
			byte[] bytes = new byte[MAX_SIZE];//���յ��ı���
			int len = receive(bytes);//����	
			
			long src=Message.getSourceID(bytes);
			byte type =Message.getType(bytes);
			byte method = Message.getMethod(bytes);// ������method
			Date receiveTime=new Date();
			byte[] content = Message.getContent(bytes);

			// ����content�е�json
			String json = new String(content);
			JSONTokener jsonTokener = new JSONTokener(json);
			JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();// ��json����ת��ΪjsonObject����
			long IDC = jsonObject.getLong("IDC");
			long IDTgs = jsonObject.getLong("IDTgs");
			String timeStamp = jsonObject.getString("timeStamp");

			//�ж�IDTgs�Ƿ�Ϸ�������
			if (IDTgs!=TGS) {
				Message.setRespond(bytes, DENY);
				Message.setTargetID(bytes, src);
				Message.setSourceID(bytes, AS);
				send(bytes);
				return;	
			}
			
			
			//�ж��Ƿ�ʱ
			try {
				Date timeStampDate = format.parse(timeStamp);
				long s = receiveTime.getTime() - timeStampDate.getTime();
				if (s>MAXTIME) {
					Message.setRespond(bytes, OVERTIME);
					Message.setTargetID(bytes, src);
					Message.setSourceID(bytes, AS);
					send(bytes);
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//����IDC�����ݿ��ȡC��paasswd
			String passwdOfClient=DBExcute.getPasswd((int)IDC);
			//����MD5�㷨����keyClient,������ܺ���client��passwd�⿪
			String keyClient = MD5.getStringMD5(passwdOfClient);;//��keyClient��ASToClient����
			
			//����client��tgs�ĻỰԿ
			String string =  "ijksdahtuirwehy";
			//���ɶ�����keyCAndTgs
			String keyCAndTgs=MD5.getStringMD5(string);
			
			Date date=new Date();
			String timeStampInTicketTgs=format.format(date);//ʱ���
			long lifeTime = MAXTIME;// ������
			String ADC="MACC";//C��mac��ַ

			// ��װticketTgs��һ��json��
			JSONObject ticketTgs = new JSONObject();
			ticketTgs.put("keyCAndTgs", keyCAndTgs);
			ticketTgs.put("IDC", IDC);
			ticketTgs.put("ADC", ADC);
			ticketTgs.put("IDTgs", IDTgs);
			ticketTgs.put("timeStamp", timeStampInTicketTgs);
			ticketTgs.put("lifeTime", lifeTime);
			
			//��tgs�Ĺ�Կ��ticketTgs���зǶԳƼ���
			byte temp[]=RSA.encrypt(RSA.TGSPublicKey, ticketTgs.toString().getBytes());
			//String ticketTgsEncipher = new sun.misc.BASE64Encoder().encodeBuffer(temp);
			String ticketTgsEncipher =  Base64.getEncoder().encodeToString(temp);

			// ��װASToClient��һ��json��
			JSONObject ASToClient = new JSONObject();
			ASToClient.put("keyCAndTgs", keyCAndTgs);
			ASToClient.put("IDTgs", IDTgs);
			ASToClient.put("timeStamp", timeStampInTicketTgs);
			ASToClient.put("lifeTime", lifeTime);
			ASToClient.put("ticketTgsEncipher", ticketTgsEncipher);
			
			
			//��clientKey��ASToClient����
			Des.setKey(keyClient);
			byte ASToClientEncipher[]=Des.encrypt(ASToClient.toString().getBytes());

			// �����ݷ�װ�ڱ�����
			Message.setContent(message, ASToClientEncipher);
			//���÷���
			Message.setRespond(message, SUCCESS);
			Message.setTargetID(message, src);
			Message.setSourceID(message, AS);
			Message.setType(message, type);
			Message.setMethod(message, method);
			
			// ���ͱ���
			send(message);

			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package Servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import Message.Message;
import Security.DES.Des;
import Security.MD5.MD5;
import Security.RSA.RSA;
import sun.misc.BASE64Decoder;

//���Ƿ�������һ���߳�
public class TGS implements Runnable{
	 private Socket socket;
	 byte [] message=new byte[8216];
	 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	 private final long MAXTIME=100000; 
	 
	 //Դ
	 private final int AS = 1;//AS������
	 private final int TGS = 2;//TGS������
	 private final int PSERVERCHAT = 3;//Ӧ�÷���������Ϣ��
	 private final int PSERVERFILE = 4;//Ӧ�÷��������ļ���
	 
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
	 
	 public TGS(Socket socket){
		 this.socket=socket;
	 }
	 
	 public void send(byte[] content){
			try {
				OutputStream socketOut = socket.getOutputStream();
				socketOut.write(content);
				socketOut.flush();
				log.info("Messeag has been sent!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	public void run() {		
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
			long IDPServer = jsonObject.getLong("IDPServer");
			String ticketTgsEncipher= jsonObject.getString("ticketTgsEncipher");//json
			String AuthenticatorEncipher = jsonObject.getString("AuthenticatorEncipher");//json
			
			byte[] bt = new sun.misc.BASE64Decoder().decodeBuffer(ticketTgsEncipher);
			//��ticketTgsEncipher���ܳ�ticketTgs
			/**
			 * TGS��������˽Կ
			 */
	        String TGSPrivateKey = "8557101429532493902296069297432525966839984080979327210872956678683348055444964967001529143851526051674302144365958534595103475598437701907847447318576766643956753870338631318477695764725592831182069652413848583110556211008269306066443957530852580573158003931606862663354720091456520366718077516710925142743668247386421510669808588015590335943907223630577185479712024698807041111648656999415659482009438403276728722609217371949987026479788277879123846309104089082329707504277965397334028538418284539552314085362446506371015899741229715542022265559066873543511768304091636770969870525365939612341502515608477750495233&12212690687876111778631914058053907976672300451113723158078853698843000468416739308414181522225554504542219939793375968679427188159730219293000830899772769066746380191645968656774232302544015186785263475832891868277798832771100729783896725712096811259212894243591440731016513297774084827386817208464370668118510828455653669260208409066084619658228019359651857026389909006539430417151844000764455572849945097089283151421428135744852391646439525779749842257922838275014988726831619189559790172218242953019246646473673072924757195240042233223846998606331043164626716215112204416985916658702487045605969466575568761671927";
	        byte temp[] = RSA.decrypt(TGSPrivateKey, bt);
	        String ticketTgs=new String(temp);
	        
			//����ticketTgs
			JSONTokener jsonTokener2 = new JSONTokener(ticketTgs);
			JSONObject jsonTicketTgs = (JSONObject) jsonTokener2.nextValue();// ��json����ת��ΪjsonObject����
			long IDCInTicketTgs= jsonTicketTgs.getLong("IDC");
			String ADCInTicketTgs = jsonTicketTgs.getString("ADC");
			long IDTgs=jsonTicketTgs.getLong("IDTgs");
			String timeStampInTicketTgs=jsonTicketTgs.getString("timeStamp");
			long lifeTimeInTicketTgs=jsonTicketTgs.getLong("lifeTime");
			
			
			//1���жϽ���������IDTgs�ǲ����Լ�
			if (IDTgs!=TGS) {
				//���ؼ���������ȥ������respond,��������Client
				Message.setRespond(bytes, DENY);
				Message.setTargetID(bytes, src);
				Message.setSourceID(bytes, TGS);
				send(bytes);
				return;
			}
			
			//2��ticketTgs�Ƚ�ʱ�����������
			try {
				Date timeStampInTicketTgsDate = format.parse(timeStampInTicketTgs);
				long s = receiveTime.getTime() - timeStampInTicketTgsDate.getTime();
				if (s>lifeTimeInTicketTgs) {
					Message.setRespond(bytes, OVERTIME);
					Message.setTargetID(bytes, src);
					Message.setSourceID(bytes, TGS);
					send(bytes);
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//����client��tgs�ĻỰԿ
			String string =  "ijksdahtuirwehy";
			//���ɶ�����keyCAndTgs
			String keyCAndTgs=MD5.getStringMD5(string);
			
			byte[] bt1 = new sun.misc.BASE64Decoder().decodeBuffer(AuthenticatorEncipher);
			//��keyCAndTgs��AuthenticatorEncipher���ܣ����ܺ󷵻�String����AuthenticatorC
			Des.setKey(keyCAndTgs);
			byte temp2[]=Des.decrypt(bt1);
			String AuthenticatorC=new String(temp2);
			
			//��AuthenticatorC��������
			JSONTokener jsonTokener3 = new JSONTokener(AuthenticatorC);
			JSONObject jsonTicketAuthenticatorC = (JSONObject) jsonTokener3.nextValue();// ��json����ת��ΪjsonObject����
			long IDCInAuthenticatorC = jsonTicketAuthenticatorC.getLong("IDC");
			String ADCInAuthenticatorC=jsonTicketAuthenticatorC.getString("ADC");
			String timeStampInADCInAuthenticatorC=jsonTicketAuthenticatorC.getString("timeStamp");
			
			//3���Ƚ�IDCInTicketTgs��IDCInAuthenticatorC
			if (IDCInTicketTgs!=IDCInAuthenticatorC) {
				//���ؼ���������ȥ������respond,��������Client
				Message.setRespond(bytes, DENY);
				Message.setTargetID(bytes, src);
				Message.setSourceID(bytes, TGS);
				send(bytes);
				return;
			}
			
			//4���Ƚ�ADCInTicketTgs��ADCInAuthenticatorC
			if (!ADCInTicketTgs.equals(ADCInAuthenticatorC)) {
				//���ؼ���������ȥ������respond,��������Client
				Message.setRespond(bytes, DENY);
				Message.setTargetID(bytes, src);
				Message.setSourceID(bytes, TGS);
				send(bytes);
				return;
			}
			
			//5��AuthenticatorC�ж��Ƿ�ʱ
			try {
				Date timeStampInADCInAuthenticatorCDate = format.parse(timeStampInADCInAuthenticatorC);
				long s = receiveTime.getTime() - timeStampInADCInAuthenticatorCDate.getTime();
				if (s>MAXTIME) {
					Message.setRespond(bytes, OVERTIME);
					Message.setTargetID(bytes, src);
					Message.setSourceID(bytes, TGS);
					send(bytes);
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//����client��PServer�ĻỰԿ
			String str =  "asdhfafsfjhfaksfkooiij";
			//���ɶ�����keyCAndTgs
			String keyCAndPServer=MD5.getStringMD5(str);
			
			Date date=new Date();
			String timeStampInTicketPServer=format.format(date);//ʱ���
			long lifeTimeInTicketPServer=MAXTIME;
			
			// ��װticketPServer��һ��json��
			JSONObject ticketPServer = new JSONObject();
			ticketPServer.put("keyCAndPServer", keyCAndPServer);
			ticketPServer.put("IDC", IDCInTicketTgs);
			ticketPServer.put("ADC", ADCInTicketTgs);
			ticketPServer.put("IDPServer", IDPServer);
			ticketPServer.put("timeStamp", timeStampInTicketPServer);
			ticketPServer.put("lifeTime", lifeTimeInTicketPServer);
			
			//��PServer�Ĺ�Կ��ticketPServer���м���
			byte temp1[]=RSA.encrypt(RSA.ChatServerPublicKey, ticketPServer.toString().getBytes());
			String ticketPServerEncipher = new sun.misc.BASE64Encoder().encodeBuffer(temp1);
			
			//��װTgsToClient��һ��json��
			JSONObject TgsToClient = new JSONObject();
			TgsToClient.put("keyCAndPServer", keyCAndPServer);
			TgsToClient.put("IDPServer", IDPServer);
			TgsToClient.put("timeStamp", timeStampInTicketPServer);
			TgsToClient.put("ticketPServerEncipher", ticketPServerEncipher);
			
			//��Client��Tgs�ĻỰԿ��TgsToClient����
			Des.setKey(keyCAndTgs);
			byte TgsToClientEncipher[]=Des.encrypt(TgsToClient.toString().getBytes());
			
			// �����ݷ�װ�ڱ�����
			Message.setContent(message, TgsToClientEncipher);
			//���÷���
			Message.setRespond(message, SUCCESS);
			Message.setTargetID(message, src);
			Message.setSourceID(message, TGS);
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

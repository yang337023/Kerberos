package APP;

import wgl.Kerberos;
import wgl.Login;
import Client.ConnManger;
import Databean.User;

public class Application {
	
	public static User user = null; //ȫ��Ψһ���û����
	
	public static ConnManger cm = null;  //ȫ��Ψһ�����ӹ���
	
	public static final byte ON_LINE = 1; //����
	public static final byte ADD_FRIEND = 2; //��Ӻ���
	public static final byte GET_CHATHISTORY = 3; //��ȡ�����¼
	public static final byte ALTER_PWD = 4; //�޸�����
	public static final byte OFF_LINE = 5; //����
	public static final byte CHAT = 6; //����
	public static final byte GET_FRIEND = 7; //��ȡ�����б�
	public static final byte GET_OFFLINEINFO = 8; 
	 
	 //server2�ϵķ���
	public static final int CHECK_CHAT_HISTORY = 9;//�鿴�����¼
	public static final int UPLOAD_FILE = 10;//�ϴ��ļ�
	public static final int DOWNLOAD_FILE = 11;//�����ļ�
	public static final int CHANGE_FILE_STATUS = 12;//�����ļ�����״̬
	 
	 //Դ
	public static final int AS = 1;//AS������
	public static final int TGS = 2;//TGS������
	public static final int PSERVERCHAT = 3;//Ӧ�÷���������Ϣ��
	public static final int PSERVERFILE = 4;//Ӧ�÷��������ļ���
	 
	 //����respond
	public static final byte NEGATIVE = 0;//���ֶ�δ������
	public static final byte SUCCESS = 1;//�����ɹ�
	public static final byte DENY = 2;//�ܾ�����
	public static final byte FAILTOUPLOAD = 3;//�ļ��ϴ�ʧ��
	public static final byte FAILTODOWNLOAD = 4;//�ļ�����ʧ��
	public static final byte USEROFFLINE = 5;//��ϢͶ�ݳɹ������û���������״̬
	public static final byte OVERTIME = 6;//��Ϣ��ʱ
	public static final byte FAIL = 9;
	
	/**
	 * ��������Ӧ�����
	 * @param argv
	 */
	public static void main(String[] argv){
		new Login();
	}
}

package DBManger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import Databean.Information;
import Databean.User;


public class DBExcute {
	private static String sql;
	private static ResultSet rs;
	
	/**
	 * �û�ע��
	 * userName�û������ǳƣ�
	 * passwd����
	 * �ɹ�������userID
	 * ʧ�ܷ���0
	 */
	public static int register(String userName, String passwd) {
		sql = "insert into user(UserName,Passwd) values ('" + userName + "','"
				+ passwd + "')";
		DataConn.OpenConn();
		int re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("ע��ʧ�ܣ������ԣ�");
			return 0;
		}
		String sql2 = "select UserID from user where UserName='" + userName
				+ "' && Passwd='" + passwd + "'";
		int userID = 0;
		rs = DataConn.executeQuery(sql2);
		try {
			while (rs.next()) {
				userID = rs.getInt(1);
				System.out.println("userID��" + rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}
	
	/**
	 * �û���¼�������û�״̬
	 * userID�û�ID
	 * ��¼�ɹ������û���
	 * ʧ�ܷ���null
	 */
	public static String logIn(int userID) {
		// 
		String sql2 = "update user set Status=1 where UserID='" + userID
				+ "'";
		DataConn.OpenConn();
		int re = DataConn.executeUpdate(sql2);
		if (re == 0) {
			System.err.println("�û�״̬����ʧ�ܣ�");
			return null;
		}
		sql = "select UserName from user where UserID='" + userID + "'";
		rs = DataConn.executeQuery(sql);
		String usrname = null;
		try {
			while (rs.next()) {
				usrname = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usrname;
	}

	/**
	 * �����û�����
	 * newPasswd������
	 * userID�û�ID
	 * �ɹ�����1
	 * ʧ�ܷ���0
	 */
	public static int changePasswd(int userID, String newPasswd) {
		sql = "update user set Passwd='" + newPasswd + "' where UserID='"
				+ userID + "'";
		DataConn.OpenConn();
		int re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("�������ʧ�ܣ�");
			return 0;
		}
		System.out.println("������³ɹ���");
		return 1;
	}
	
	/**
	 * �û�����
	 * userID�û�ID
	 * �ɹ�����1
	 * ʧ�ܷ���0
	 */
	public static int offline(int userID) {
		sql = "update user set Status=0 where UserID='" + userID + "'";
		DataConn.OpenConn();
		int re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("�û�����ʧ�ܣ�");
			return 0;
		}
		System.out.println("�û����߳ɹ���");
		return 1;
	}
	
	/**
	 * ��Ӻ���
	 * userID�û�ID
	 * friendIDҪ��ӵĺ���ID
	 * �ɹ�����1
	 * ʧ�ܷ���0
	 */
	public static int addFriend(int userID, int friendID) {
		// �ڸ��û��ĺ����б��в鿴�Ƿ��Ѿ���Ӹú���
		sql = "select FriendID from User_user where UserID='" + userID + "'";
		DataConn.OpenConn();
		rs = DataConn.executeQuery(sql);
		int temp;
		try {
			while (rs.next()) {
				temp = rs.getInt(1);
				if (temp == friendID){
					return 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "insert into User_user (UserID, FriendID) values ('"+userID+"','"+friendID+"')";
		int re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("�������ʧ�ܣ�");
			return 0;
		}
		sql = "insert into User_user (UserID, FriendID) values ('"+friendID+"','"+userID+"')";
		re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("�������ʧ�ܣ�");
			return 0;
		}
		return 0;
	}
	

	/**
	 * �鿴����
	 * userID�û�ID
	 * �ɹ�����1����������ѵ�ID���û���
	 * ʧ�ܷ���0
	 */
	public static List<User> viewFriends(int myid) {
		sql = "select FriendID,UserName from User_user,user where User_user.FriendID=user.UserID AND User_user.UserID='"
				+ myid + "'";
		DataConn.OpenConn();
		rs = DataConn.executeQuery(sql);
		List<User> persons = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				User person = new User(id,name);
				persons.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persons;
	}
	
	/**
	 * ��¼��Ϣ,����Ϣ��¼�����ݿ�
	 * status ��Ϣ����״̬�����պ�δ����
	 * �ɹ�����1
	 * ʧ�ܷ���0
	 */
	public static int recordInformation(Information info, int status) {
		sql = "insert into info (InfoID,Type,Content) values ('"+ info.getID() +"','"+ info.TYPE + "','"
				+ info.getContent() + "')";
		DataConn.OpenConn();
		int re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("��Ϣ��¼ʧ�ܣ�");
			return 0;
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sdf.format(date);
		String sql3="insert into User_info (InfoId,Sender,Receiver,Status,Time) values " +
				"('"+info.getID()+"','"+info.getFrom()+"','"+info.getTo()+"','"+status+"','"+time+"')";
		re = DataConn.executeUpdate(sql3);
		if (re == 0) {
			System.err.println("��Ϣ��¼ʧ�ܣ�");
			return 0;
		}
		return 1;
	}

	/**
	 * AS��ȡ�û�����
	 * userID�û�ID
	 * �ɹ���������
	 * ʧ�ܷ���null
	 */
	public static String getPasswd(int userID) {
		sql = "select Passwd from user where UserID='" + userID + "'";
		DataConn.OpenConn();
		rs = DataConn.executeQuery(sql);
		String passwd = null;
		try {
			while (rs.next()) {
				passwd = rs.getString(1);
			}
			System.out.println("����Ϊ��" + passwd);
			return passwd;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �ӵ���Ϣ����Ϣ״̬�ı�
	 * to��Ϣ�Ľ��շ�
	 * �ɹ�����1
	 * ʧ�ܷ���0
	 */
	public static int infoStatusChange(int to) {
		sql = "update User_info set Status=1 where Receiver='" + to + "'";
		DataConn.OpenConn();
		int re = DataConn.executeUpdate(sql);
		if (re == 0) {
			System.err.println("��Ϣ״̬����ʧ�ܣ�");
			return 0;
		}
		return 1;
	}
	
	/**
	 * �鿴��ĳ�����ѵ������¼
	 * userID�û�ID
	 * friendID����ID
	 * �ɹ���������¼��������1
	 * ʧ�ܷ���0
	 */
	public static List<Information> checkChatHistory(int userID, int friendID) {
		sql = "select Content,info.InfoID,Sender,Receiver from User_info, info where (User_info.InfoId=info.InfoID) AND ((Sender='" + userID
				+ "' && Receiver='" + friendID + "') || (Sender='" + friendID
				+ "' && Receiver='" + userID + "')) AND Type='1'";
		DataConn.OpenConn();
		rs = DataConn.executeQuery(sql);
		List<Information> informations = new ArrayList<Information>();
		try {
			while (rs.next()) {
				String content = rs.getString(1);
				int id = rs.getInt(2);
				int from = rs.getInt(3);
				int to = rs.getInt(4);
				Information info = new Information(id,content,from,to);
				informations.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return informations;
	}
	
	/**
	 * ��ȡ�û���������Ϣ
	 * @param userId
	 * @return
	 */
	public static List<Information> getOfflineInfo(int userId){
		sql = "select Content,info.InfoID,Sender from User_info, info where User_info.InfoId=info.InfoID AND Status='0' AND Receiver='"+userId+"'";
		DataConn.OpenConn();
		rs = DataConn.executeQuery(sql);
		List<Information> informations = new ArrayList<Information>();
		try {
			while (rs.next()) {
				String content = rs.getString(1);
				int id = rs.getInt(2);
				int from = rs.getInt(3);
				Information info = new Information(id,content,from,userId);
				informations.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return informations;
	}
	
	public static int getMAXInfoID(){
		sql = "select max(InfoID) from info";
		DataConn.OpenConn();
		rs = DataConn.executeQuery(sql);
		int value = 0;
		try {
			while (rs.next()) {
				value = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
	
//	public static void main(String[] argv){
//		
//		System.out.println(getMAXInfoID());
//		
//	}
}

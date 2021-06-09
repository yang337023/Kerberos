package Message;

import java.util.List;

import net.sf.json.JSONObject;
import Databean.Information;
import Databean.User;

public class ContentTool {
	
	/**
	 * �����ĵ�contentתΪString
	 * @param content
	 * @return
	 */
	public static String getInfor(byte[] content){
		return new String(content);
	}
	
	/**
	 * �ӱ��ĵ�content�ֶ��н��һ��Infomation
	 * @param content
	 * @return
	 */
	public static Information getInfo(byte[] content){
		String con = new String(content);
		JSONObject jsonObj = JSONObject.fromObject(con);
		JSONObject jsonObject = jsonObj.getJSONObject("data");
		return new Information(jsonObject.getInt("ID"),jsonObject.getString("content"),jsonObject.getLong("from"),jsonObject.getLong("to"));
	}
	
	/**
	 * ��һ����Ϣת����json��ʽ���ַ������ֽ�����
	 * @param info
	 * @return
	 */
	public static byte[] getInfoBytes(Information info){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", info);
		return jsonObject.toString().getBytes();
	}
	
	/**
	 * ��һ����Ϣת����json��ʽ���ַ������ֽ�����
	 * @param infos
	 * @return
	 */
	public static byte[] getInfoBytes(List<Information> infos){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", infos);
		return jsonObject.toString().getBytes();
	}
	
	/**
	 * ��һ��userת����json��ʽ���ַ������ֽ�����
	 * @param u
	 * @return
	 */
	public static byte[] getUserBytes(User u){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", u);
		return jsonObject.toString().getBytes();
	}
	
	/**
	 * ��һ��user��Ϣת����json��ʽ���������ַ������ֽ�����
	 * @param us
	 * @return
	 */
	public static byte[] getUserBytes(List<User> us){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", us);
		return jsonObject.toString().getBytes();
	}
	
	
}

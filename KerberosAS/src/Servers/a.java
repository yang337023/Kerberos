package Servers;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class a {
//    static Statement stmt = null;
//    static ResultSet rs = null;
//    static Connection conn = null;
//    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            //conn = DriverManager.getConnection("jdbc:mysql://192.168.43.226:3306?user=root&password=yjc199846");
//            conn = DriverManager.getConnection("jdbc:mysql://192.168.43.146:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","root","root");
//            System.out.println("���ݿ����ӳɹ�");
//            String sqlSentence = "select Passwd from user where UserID=\'11\'";
//
//        } catch (Exception e) {
//            System.err.println("���ݿ�����ʧ�� " + e.getMessage());
//        }
//    }
//}


/**
 * Created by Administrator on 2017/12/24.
 */

import DBManger.DataConn;
import sun.security.util.Password;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class a {
    public static void main(String[] args) {
//        Connection con;
//        String driver="com.mysql.jdbc.Driver";
//        //�����ҵ����ݿ���cgjr
//        String url="jdbc:mysql://192.168.43.146:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//        String user="root";
//        String password="root";
//        try {
//            Class.forName(driver);
//            con = DriverManager.getConnection(url, user, password);
//            if (!con.isClosed()) {
//                System.out.println("���ݿ����ӳɹ�");
//            }
//            Statement statement = con.createStatement();
//
////            ִ�в�ѯ���
//            String sql = "select * from user ;";//�ҵı���persons
//            ResultSet resultSet = statement.executeQuery(sql);
//
//
////            ��ӡ��ѯ�����Ķ���
//            String ID;
//            String password1;
//            while (resultSet.next()) {
//                ID = resultSet.getString("UserID");
//                password1 = resultSet.getString("Passwd");
//                System.out.println(ID+'\t'+ password1);
//            }
//
//            String sql5="call add_student(3)";
//            statement.executeUpdate(sql5);
//
//
////            �ر�����
//            resultSet.close();
//            con.close();
//            System.out.println("���ݿ��ѹر�����");
//        } catch (ClassNotFoundException e) {
//            System.out.println("���ݿ�����û�а�װ");
//
//        } catch (SQLException e) {
//            System.out.println("���ݿ�����ʧ��");
//        }
        DataConn.OpenConn();
    }
}

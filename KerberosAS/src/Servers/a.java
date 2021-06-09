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
//            System.out.println("数据库连接成功");
//            String sqlSentence = "select Passwd from user where UserID=\'11\'";
//
//        } catch (Exception e) {
//            System.err.println("数据库连接失败 " + e.getMessage());
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
//        //这里我的数据库是cgjr
//        String url="jdbc:mysql://192.168.43.146:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//        String user="root";
//        String password="root";
//        try {
//            Class.forName(driver);
//            con = DriverManager.getConnection(url, user, password);
//            if (!con.isClosed()) {
//                System.out.println("数据库连接成功");
//            }
//            Statement statement = con.createStatement();
//
////            执行查询语句
//            String sql = "select * from user ;";//我的表格叫persons
//            ResultSet resultSet = statement.executeQuery(sql);
//
//
////            打印查询出来的东西
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
////            关闭连接
//            resultSet.close();
//            con.close();
//            System.out.println("数据库已关闭连接");
//        } catch (ClassNotFoundException e) {
//            System.out.println("数据库驱动没有安装");
//
//        } catch (SQLException e) {
//            System.out.println("数据库连接失败");
//        }
        DataConn.OpenConn();
    }
}

package DBManger;
//连接数据库操作
import java.sql.*;

public class DataConn {

	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection conn = null;
	//static String ip = "192.168.43.33:3306/test";

	static public void OpenConn() {
		stmt = null;
		rs = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection("jdbc:mysql://172.27.238.199:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","root","root");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.43.146:3306/test?user=root&password=root");
			System.out.println("Database connection successful !");
		} catch (Exception e) {
			System.err.println("Database connection failed !" + e.getMessage());
		}
	}

	public static ResultSet executeQuery(String sql) {
		stmt = null;
		rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("RS 运行");
			//rs = stmt.executeQuery("select Passwd from user where UserID=11");
			//System.out.println("111111"+stmt.executeQuery(sql));
		} catch (SQLException e) {
			System.err.println("Data query" + e.getMessage());
		}
		return rs;
	}

	public static int executeUpdate(String sql) {
		stmt = null;
		rs = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.executeUpdate(sql);
			// conn.commit();
		} catch (SQLException e) {
			System.err.println("Update the data" + e.getMessage());
			return 0;
		}
		return 1;
	}

	static void CloseConn() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Connection release");
		}
	}
}

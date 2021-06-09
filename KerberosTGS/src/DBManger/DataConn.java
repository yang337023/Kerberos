package DBManger;

import java.sql.*;

public class DataConn {

	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection conn = null;
	static String ip = "172.27.238.199:3306/test";

	static void OpenConn() {
		stmt = null;
		rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.43.146:3306/test?user=root&password=root";
			conn = DriverManager.getConnection(url);
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			System.err.println("数据库连接失败 " + e.getMessage());
		}
	}

	public static ResultSet executeQuery(String sql) {
		stmt = null;
		rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("查询数据" + e.getMessage());
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
			System.err.println("更新数据" + e.getMessage());
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
			System.err.println("连接释放");
		}
	}
}

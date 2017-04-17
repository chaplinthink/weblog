package com.moon.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

/**
 * Created by wei on 2016/5/4
 * 基础DAO
 * */

public class BaseDao {
	//驱动程序的名称和数据库一一对应,强记
	private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
	private static String user = "root";
	private static String password = "admin";
	private static String url = "jdbc:mysql://192.168.56.100:3306/weblog";

	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet rs = null;

	//安装驱动程序,静态代码块，自动调用一次且仅一次时机，类的任何方法调用 以前
	static {
		try {
			Class.forName(DRIVERNAME);
			System.err.println("加载驱动成功");
		} catch (ClassNotFoundException e) {

			System.err.println("加载失败" + e.getMessage());
		}

	}

	public Connection buildConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {

			System.out.println("建立连接失败" + e.getMessage());
		}
		return connection;

	}

	public int update(String sql) {
		int i = -1;
		Connection connection = null;
		Statement statement = null;
		try {

			connection = buildConnection();
			statement = connection.createStatement();
			i = statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("执行失败" + e.getMessage());
			System.out.println("执行失败" + sql);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return i;
	}

	/**
	 * 执行sql语句,实现对数据的增删改，返回影响记录的行数,-1代表执行失败，0代表执行成功，但是未影响数据库；n代表影响数据库的行数
	 * 
	 * @param sql
	 *            被执行的一般sql
	 * @return i 返回影响记录的行数-1，0代表执行成功，但是未影响数据库；n代表影响数据库的行数
	 * */

	public int update(String sql, String[] args) {
		int i = -1;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = buildConnection();
			statement = connection.prepareStatement(sql);
			// 判断sql语句是否存在占位符
			if ((args == null) || (args.length == 0)) {
				// do nothing
			} else { // 为占位符传参
				for (int j = 0; j < args.length; j++) {
					// 为占位符传值
					// 数组from 0 占位符from 1
					statement.setString(j + 1, args[j]);
				}
			}
			i = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("执行失败" + e.getMessage());
			System.out.println("执行失败" + sql);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	/**
	 * 通用的查询方法，查询结果保存在ResultSet对象中
	 * 
	 * @param sql
	 * @param args
	 * @return Result
	 * 
	 * */
	public Result query(String sql, String[] args) {
		// 定义Result 对象用于封装结果集对象
		Result r = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		// 建立与数据库的链接
		connection = buildConnection();
		// 构建preparestatement 对象，执行带有占位符的sql语句
		try {
			preparedStatement = connection.prepareStatement(sql);
			// 为占位符赋值
			if (args == null || args.length == 0) {

			} else {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setString(i + 1, args[i]);
				}
			}
			// 执行sql，获得resultset对象
			resultSet = preparedStatement.executeQuery();
			// resultset-->result
			r = ResultSupport.toResult(resultSet);

		} catch (SQLException e) {
			System.out.println("查询失败" + e.getMessage());
			System.out.println("查询失败" + sql);
		} finally {
			// 回收资源
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 返回执行结果，如果执行失败，返回null
		return r;

	}

//	public ResultSet executeQuery(String sql_1) {
//		try {
//			conn = buildConnection();
//			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_READ_ONLY);
//			rs = stmt.executeQuery(sql_1);
//		} catch (SQLException ex) {
//			System.err.println(ex.getMessage());
//		}
//		return rs;
//	}
	
	public int executeUpdate(String sql) {
		int result = 0;
		try {
			conn = buildConnection(); // 调用buildConnection()方法构造Connection对象的一个实例conn
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql); // 执行更新操作
		} catch (SQLException ex) {
			result = 0;
		}
		return result;
	}

	public static void main(String[] args) {
		String sql = "SELECT  userId ,userName, passWord  FROM admin " +
				"WHERE userName=? and passWord= ?";
		String ss [] = {"admin","zhangwei"};
		BaseDao db = new BaseDao();
		Result r = db.query(sql, ss);
		SortedMap[] sms = r.getRows();
		for(int i=0;i<r.getRowCount();i++){ 
		       System.out.println(sms[i].get("admin"));
		}

	}

	// public static void main(String[] args) {
	// String sql =
	// "select deptno as dno,dname as dn,loc from dept where deptno< ? ";
	//
	// String [] ss = {"3"};
	// BaseDao db = new BaseDao();
	// Result r = db.query(sql, ss);
	// SortedMap [] sms = r.getRows();
	//
	// for(int i=0;i<r.getRowCount();i++){
	// SortedMap sm = sms[i];
	// System.out.println(sm.get("dn"));
	// }
	//
	// }

	// public static void main1(String[] args) {
	//
	// String sql = "select * from dept ";
	// Connection connection;
	// try {
	// connection = DriverManager.getConnection(url, user, password);
	// Statement statement = connection.createStatement();
	// //查询结果集 ，只提供单行，单向，单次访问结果的功能
	// ResultSet resultSet = statement.executeQuery(sql);
	// while(resultSet.next()){//读取下一行记录，blooean,标记是否读到下一行记录
	// System.out.print(resultSet.getInt(1) + "\t");
	// System.out.print(resultSet.getString("dname")+"\t");
	// System.out.print(resultSet.getString(3)+"\t");
	// System.out.print("\r\n");
	// }
	// resultSet.close();
	// connection.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	//
	//

	// 只有数值才能被占位符替代
	/*
	 * String sql = "insert into dept (deptno,dname,loc) values (?,?,?)";
	 * 
	 * 
	 * String ss[] = {"哈尔滨","江北区"}; BaseDao bd = new BaseDao(); //int i =
	 * bd.update(sql); int i = bd.update(sql,ss);
	 * 
	 * System.out.println(i);
	 */

	// TODO Auto-generated method stub
	// //建立连接
	// try {
	// Connection connection = DriverManager.getConnection(url, user, password);
	// //获得执行SQL 语句的statement对象
	// Statement statement = connection.createStatement();
	// //提供ＳＱＬ语句
	// //String sql =
	// "insert into dept (deptno,dname,loc) values ('1','哈尔滨银行','南岗区')";
	// //String sql = "update dept set loc=('呼兰区')";
	//
	//
	//

	// // 执行SQL，获得结果
	// int i = statement.executeUpdate(sql);//执行增删改，并返回影响记录的个数
	// //必须关闭连接
	// connection.close();
	//
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// System.out.println("执行失败，原因是:"+e.getMessage());
	// }

	// System.out.println("BaseDao main");
	// }
	// }
}

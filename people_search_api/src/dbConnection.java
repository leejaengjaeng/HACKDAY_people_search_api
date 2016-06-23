import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

public class dbConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/hackday";
	static final String USERNAME = "root";
	static final String PASSWORD = "4678";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private static int insertCnt;
	dbConnection()
	{
		insertCnt=0;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			System.out.println("\n- MySQL Connection");
			

		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void inSertJson(JSONObject input) throws Exception
	{
		String sql = "insert into user values(0,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,input.toJSONString());
		pstmt.executeUpdate();
				
		System.out.println("input Done.."+insertCnt++);
	}
	public void closeConn() throws Exception
	{
		pstmt.close();
		conn.close();
	}
}

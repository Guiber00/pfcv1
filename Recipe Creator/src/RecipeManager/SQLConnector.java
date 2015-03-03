package RecipeManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLConnector {
	private String dbPath = "jdbc:sqlite:D:/sqlite2009 pro/recipetest.db3";
	private String dbDriver = "org.sqlite.JDBC";
	private Connection dbConnection;
	private String msg;
	
	public SQLConnector() {
		try {
			Class.forName(dbDriver);
			dbConnection = DriverManager.getConnection(dbPath);
			System.out.println("Connection success!");
		}
		catch(SQLException | ClassNotFoundException e) {
			msg = e.getMessage();
			System.out.println(getException());
		}
	}
	
	public Connection getConnection() {return dbConnection;}
	
	public void closeConnector() throws SQLException {
		dbConnection.close();
	}
	
	public String getException() {
		return msg;
	}
}
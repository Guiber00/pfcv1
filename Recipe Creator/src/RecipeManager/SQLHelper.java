package RecipeManager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLHelper {

	private ResultSet queryOut;
	private ResultSetMetaData metaOut;
	private String[][] dataOut;
	private String[] columnNames;
	private String msg;
	
	//Manda una consulta de tipo SQL
	public SQLHelper(SQLConnector connection, String query) {
		try {
			Statement toDB = connection.getConnection().createStatement(); //ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
			System.out.println( "breakpoint get query:   " + query);
			queryOut = toDB.executeQuery(query);
			metaOut = queryOut.getMetaData();
		}
		
		catch(SQLException e) {
			msg = e.getMessage();
			System.out.println(getException());
		}
	}
	
	
	//Devuelve los datos en un array
	public String[][] getDataOut() {
		if (msg == null) {
			try {System.out.println("breakpoint RESULTSET1");
			int columns = metaOut.getColumnCount();				System.out.println("breakpoint RESULTSET2");
				dataOut = new String[columns][];					System.out.println("breakpoint RESULTSET3 " + columns);
				
				while(queryOut.next()) {System.out.println("breakpoint RESULTSET row number:   " + queryOut.getRow());
					for (int i = 0; i < columns; i++)
						dataOut[queryOut.getRow()][i] = queryOut.getString(i);
				}
			}
			catch(SQLException e) {
				msg = e.getMessage();
				System.out.println(getException());
			}
		}
		return dataOut;
	}
	
	
	//Devuelve el nombre de las columnas
	public String[] getColumnNames() {
		if(msg == null) {
			try {
				int columns = metaOut.getColumnCount();
				for (int i = 0; i < columns; i++)
					columnNames[i] = metaOut.getColumnLabel(i + 1);
			}
			catch (SQLException e) {
				msg = e.getMessage();
				System.out.println(getException());
			}
		}
		return columnNames;
	}
	
	//Cierra el flujo de consultas
	public void closeSQLHelper() {
		try {
			queryOut.close();
		} catch (SQLException e) {
			msg = e.getMessage();
			System.out.println(getException() + "hola!!!");
		}
	}
	
	//Maneja las excepciones
	public String getException() {
		return msg;
	}
}
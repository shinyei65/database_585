import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class dbaction {

	Connection mainConnection = null;
	Statement mainStatement = null;
	ResultSet mainResultSet = null;
	public void connection(){
		try
		{
			
			System.out.print ("Looking for Oracle's jdbc-odbc driver ... ");
			//DriverManager.registerDriver (new OracleDriver());
			Class.forName ("oracle.jdbc.driver.OracleDriver");
			//Class.forName ("oracle.jdbc.OracleDriver");
			
			

			
	    	System.out.println(", Loaded.");

			String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	    	String userName = "scott";
	    	String password = "tiger";

	    	System.out.println("Connecting to DB...");
	    	mainConnection = DriverManager.getConnection(URL, userName, password);
	    	System.out.println(", Connected!");

    		mainStatement = mainConnection.createStatement();
	    

   		}
   		catch (Exception e)
   		{
     		System.out.println( "Error while connecting to DB: "+ e.toString() );
     		e.printStackTrace();
     		System.exit(-1);
   		}
 	}
	public void delete(String table){
		String query_delete = "delete from " + table;
		try {
			mainStatement.executeUpdate( query_delete );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insert(String query){
		try {
			mainStatement.executeUpdate( query );
			//System.out.println(query + " inserted");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			mainStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultSet getResultSet(String query) {
		try {
			
			mainResultSet = mainStatement.executeQuery(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mainResultSet;
	}
}

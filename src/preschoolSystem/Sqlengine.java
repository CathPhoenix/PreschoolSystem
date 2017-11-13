package preschoolSystem;


import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Steps for mySQL/Java usage: <p>
*  <ol>
*  	<li> Download mySQL and install
*  	<li> Configure a mysql server instance as part of install process
* 		<li> Download and install the mySQL gui tools
* 		<li> In the mysql administrator GUI tool you can create a database (a catalogue)
* 		<li> Generate the appropriate tables using mySQL administrator or straight SQL queries
* 		<li> Download the JDBC driver for mySQL (Connector/J) and extract the archive
* 		<li> Inside that archive there is a jar file like mysql-connector-java-version-bin.jar.
* 			 Add this to the eclipse build path by right clicking on your project and going to
* 			 "build path" and then to "add external archive".
* 		<li> If you are using a standard text editor you will have to add its location to the classpath
* 		<li> Now you can use the JDBC API calls as illustrated below.
* </ol><p>
* 
*	The sqlengine class is a class that uses Connector/J classes to simplify database
*	access and manipulation by using only a subset of the functionality of the sql
*	classes for common tasks that are available through easy to use methods. <p>
*
*	@author Simon Mcloughlin
*	@version 1.0
*/
public class Sqlengine {
		
		private Connection conn;
		private String url;
		private String userName;
		private String password;
		
		/**
		 * Constructor for sqlengine which is responsible for initialising
		 * the <code>userName</code> and <code>password</code>. 
		 * No connection made at this point.
		 * 
		 * @param userName The <code>username</code> to be used to connection to the database
		 * @param password The <code>password</code> required to validate the connection
		 */
		public Sqlengine(String userName, String password) {
			conn = null;
			this.userName = userName;
			this.password = password;
		}
		
		/**
		 * Method to establish a database connection, reads the database URL and
		 * port number from a properties file to prevent having to recompile code.
		 * Note: most of the J/Connector methods throw exceptions. 
		 */
		public void connect() {
	
			InputStream is = getClass().getResourceAsStream("mySQLEngine.properties");
		    Properties p = new Properties();
		    try {
		    	p.load(is);
		        url = p.getProperty("connectionURL");
		 
		    }
		    catch (IOException e) {
		        System.err.println("error loading properties...");
		    }
			try
		      {
				//register the mySQL driver with the DriverManager
				//by creating a new instance of the jdbc Driver class
				Class.forName ("com.mysql.jdbc.Driver").newInstance ();
				
				//try to connect to the database
			
				 conn = DriverManager.getConnection (url, userName, password);
			
				System.out.println ("Database connection established");
				
				
		      }
		      catch (Exception e1)
		      {
		          System.err.println ("Cannot connect to database server");
		      }
		}
		
			/**
			 * Method to close connection to a Database, always close connections when done.
			 */
			public void closeConnection() {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("SQLException: " + e.getMessage());

				}
			}
			
			/**
			 * Method to execute an SQL query and return results as a <code>ResultSet</code>object.
			 * An SQL result set is a set of rows from a database,as well as meta-information about 
			 * the query such as the column names, and the types and sizes of each column.
			 *
			 * @param sqlStatement The statement or query string to be executed.
			 * @return The <code>ResultSet</code> of the query, i.e. the rows of data and related metadata.
			 * @throws SQLException SQLExceptions maybe generated within by communicating with the DB, these
			 * are caught and thrown to the calling class.
			 */
			
			public ResultSet executeQuery(String sqlStatement) throws SQLException{
			ResultSet rs = null;
			
			//A Statement is the object used for executing an SQL statement 
			//and returning the results it produces.
			try {
				Statement stmt = conn.createStatement();
				//the execute method below executes the sql query
				//if the sql query is a select query it will will
				//return true, if it is update,insert,delete it will
				//return false.
				if(stmt.execute(sqlStatement))
					//we can get the result set if it was a select query.
					rs = stmt.getResultSet();
				
			}
			catch (SQLException e) {
				throw e;
			}
			return rs;
		}
			
			public Connection getConn(){
				return conn;
			}
		
	}
		
		
	


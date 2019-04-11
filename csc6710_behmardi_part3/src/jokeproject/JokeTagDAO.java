package jokeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/***************************************************
 * JokeTagDAO.java
 * This DAO class provides CRUD operation for JokeTag table
 * @author Gwen Hickey
 *
 ***************************************************/

public class JokeTagDAO 
{
		/* values to connect to the database */
		protected String databaseURL;
		protected String databaseUserName;
		protected String databasePassword;
		private Connection connection;
		
		/* constructors */
		public JokeTagDAO(String databaseURL, String databaseUserName, String databasePassword)
		{
			this.databaseURL = databaseURL;
			this.databaseUserName = databaseUserName;
			this.databasePassword = databasePassword;
		}
		
		/* establish connection to database */
		public void connect() throws SQLException
		{
			if (connection == null || connection.isClosed())
			{
	            try 
	            {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	            } 
	            catch (ClassNotFoundException e)
	            {
	                throw new SQLException(e);
	            }
	            connection = DriverManager.getConnection(databaseURL +  "?" + "user=" + databaseUserName + 
	            										 "&password=" + databasePassword + "&useSSL=false");
	        }
		}
		
		/* disconnect from the database */
		public void disconnect() throws SQLException
		{
			if (connection != null && !connection.isClosed())
			{
		        connection.close();
		    }
		}
		
		/* drop JokeTag table */
		public void dropJokeTagTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS JokeTag");
			statement.close();
			disconnect();
		}
		
		/* create JokeTag table */
		public void createJokeTagTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			String sqlStatement = "CREATE TABLE IF NOT EXISTS JokeTag" +
					              "(jokeId int(20) NOT NULL," +
					              " jokeTagWord varchar(25) NOT NULL," +
			                      " PRIMARY KEY (jokeId, jokeTagWord)," +
			         	          " FOREIGN KEY (jokeId) REFERENCES joke (jokeId) ON DELETE CASCADE)";
			statement.executeUpdate(sqlStatement);
			statement.close();
			disconnect();
				
		}
		
		/* initialize JokeTag table */
		public void initJokeTagTable() throws SQLException
		{			
			insertJokeTag(new JokeTag(1, "coin"));
			insertJokeTag(new JokeTag(1, "cents"));
			insertJokeTag(new JokeTag(1, "machine"));
			insertJokeTag(new JokeTag(2, "belt"));
			insertJokeTag(new JokeTag(2, "watches"));
			insertJokeTag(new JokeTag(2, "time"));
			insertJokeTag(new JokeTag(3, "letter"));
			insertJokeTag(new JokeTag(3, "alphabet"));
			insertJokeTag(new JokeTag(4, "whiteboards"));
			insertJokeTag(new JokeTag(5, "luggage"));
			insertJokeTag(new JokeTag(6, "atoms"));
			insertJokeTag(new JokeTag(7, "past"));
			insertJokeTag(new JokeTag(7, "future"));
			insertJokeTag(new JokeTag(7, "present"));
			insertJokeTag(new JokeTag(7, "bar"));
			insertJokeTag(new JokeTag(8, "atom"));
			insertJokeTag(new JokeTag(8, "electron"));
			insertJokeTag(new JokeTag(8, "ion"));
			insertJokeTag(new JokeTag(9, "clock"));
			insertJokeTag(new JokeTag(9, "time"));
			insertJokeTag(new JokeTag(10, "race"));
			insertJokeTag(new JokeTag(10, "tie"));
		}
		
		/* insert a jokeTag to JokeTag table */
		public boolean insertJokeTag(JokeTag jokeTag) throws SQLException
		{
			String sqlInsert = "INSERT INTO JokeTag (jokeId, jokeTagWord) " +
								"VALUES (?, ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setInt(1, jokeTag.getjokeId());
			preparedStatement.setString(2, jokeTag.getjokeTagWord());
			
			
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		/* get list of all jokeTags from jokeTag table */
		public List<JokeTag> getJokeTagList(String tag) throws SQLException
		{
			List<JokeTag> jokeTagList =  new ArrayList<JokeTag>();
			String sqlQuery = "SELECT * FROM JokeTag WHERE jokeTagWord = ?";
			
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, tag);
			
			
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{		
				int jokeId = result.getInt("jokeId");
				
				jokeTagList.add(new JokeTag(jokeId, tag));
			}
			
			result.close();
			preparedStatement.close();
			disconnect();
			
			return jokeTagList;
		}
		
		/* get list of all jokeTags from jokeTag table */
		public List<JokeTag> getJokeTagList() throws SQLException
		{
			List<JokeTag> jokeTagList =  new ArrayList<JokeTag>();
			String sqlQuery = "SELECT * FROM JokeTag";
			
			connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next())
			{
				int jokeId = result.getInt("jokeId");			
				String reviewTagWord = result.getString("reviewTagWord");
				
				jokeTagList.add(new JokeTag(jokeId, reviewTagWord));
			}
			result.close();
			statement.close();
			disconnect();
			
			return jokeTagList;
		}
		//***********************************************************// 
		// *          P  R  O  J  E  C  T   -   P A R T  2          *//
		// *     INSERT multiple TagWords to the TagWorld table     *//
		//***********************************************************//
		public boolean insertJokeTag(List<JokeTag> jokeTagList) throws SQLException
		{
			JokeTag joketag = new JokeTag();
			boolean status = false;
			
			String sqlInsert = "INSERT INTO JokeTag (jokeId, jokeTagWord) " +
					                "VALUES (?, ?)";
			connect();
			for (int i = 0; i < jokeTagList.size(); i++)
			{
				joketag = jokeTagList.get(i);
				
				PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
				preparedStatement.setInt(1, joketag.getjokeId());
				preparedStatement.setString(2, joketag.getjokeTagWord());
				
				status &= preparedStatement.executeUpdate() > 0;
				preparedStatement.close();
			}
			disconnect();
			
			return status;
		}
}

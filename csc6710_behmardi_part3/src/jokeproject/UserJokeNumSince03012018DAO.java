package jokeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/***************************************************
 * UserDAO.java
 * This DAO class provides CRUD operation for User table
 * @author Shahram Behmardi Kalantari
 *
 ***************************************************/
public class UserJokeNumSince03012018DAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserJokeNumSince03012018DAO(String databaseURL, String databaseUserName, String databasePassword)
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
	
	/* drop User table */
	public void dropUserJokeNumSince03012018View() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS UserJokeNumSince03012018");
		statement.close();
		disconnect();
	}
	
	/* create UserJokeNumSince03012018 view */
	public void createUserJokeNumSince03012018View() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW UserJokeNumSince03012018 (userId, userName, jokeCount) AS" +
							  " SELECT U.userId, U.userName, COUNT(*)" +
							  " FROM User U, Joke J" +
							  " WHERE U.userId = J.postUserId AND J.jokePostDate > '03-01-2018'" +
							  " GROUP BY U.userId, U.userName";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* run the query and return the result */
	public List<User> getQueryResult() throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT U1.userId, U1.userName" + 
						  " FROM UserJokeNumSince03012018 U1" + 
						  " WHERE U1.jokeCount >= (SELECT MAX(U2.jokeCount) FROM UserJokeNumSince03012018 U2)";
		connect();
		Statement statement = connection.createStatement();
		
		ResultSet result = statement.executeQuery(sqlQuery);
		
		while(result.next())
		{
			int userId = result.getInt("userId");
			String userName = result.getString("userName");
			
			User user = new User(userId, userName, null, null, null, null, null, 0);			
			userList.add(user);
		}
		
		result.close();
		statement.close();
		disconnect();
		
		return userList;
	}
}

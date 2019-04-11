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
 * UserDAO.java
 * This DAO class provides CRUD operation for User table
 * @author Shahram Behmardi Kalantari
 *
 ***************************************************/
public class UserJokeTagDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserJokeTagDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropUserJokeTagView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS UserJokeTag");
		statement.close();
		disconnect();
	}
	
	/* create UserJokeTag view */
	public void createUserJokeTagView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW UserJokeTag (userId, userName, jokeId, jokePostDate, jokeTagWord) AS" +
							  " SELECT U.userId, U.userName, J.jokeId, J.jokePostDate, T.jokeTagWord" +
							  " FROM User U, Joke J, JokeTag T" +
							  " WHERE U.userId = J.postUserId AND J.jokeId = T.jokeId";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* run the query and return the result */
	public List<User> getQueryResult(String tagX, String tagY) throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT DISTINCT U1.userId, U1.userName" + 
						  " FROM UserJokeTag U1, UserJokeTag U2" + 
						  " WHERE U1.userId = U2.userId AND U1.jokeId <> U2.jokeId AND U1.jokePostDate = U2.jokePostDate AND" +
						  " U1.jokeTagWord = ? AND U2.jokeTagWord = ?" +
						  " ORDER BY U1.userId";
		connect();
		PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery);
		prepareStatement.setString(1, tagX);
		prepareStatement.setString(2, tagY);
		
		ResultSet result = prepareStatement.executeQuery();
		
		while(result.next())
		{
			int userId = result.getInt("userId");
			String userName = result.getString("userName");
			
			User user = new User(userId, userName, null, null, null, null, null, 0);			
			userList.add(user);
		}
		
		result.close();
		prepareStatement.close();
		disconnect();
		
		return userList;
	}
}

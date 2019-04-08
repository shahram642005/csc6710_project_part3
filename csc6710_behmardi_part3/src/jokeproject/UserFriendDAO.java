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
public class UserFriendDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserFriendDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropUserFriendView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS UserFriend");
		statement.close();
		disconnect();
	}
	
	/* create UserFriend view */
	public void createUserFriendView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW UserFriend (userId, userName, friendId, friendName) AS" +
							  " SELECT U1.userId, U1.userName, F.friendUserId, U2.userName" +
							  " FROM User U1, Friend F, User U2" +
							  " WHERE U1.userId = F.userId AND F.friendUserId = U2.userId";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* run the query and return the result */
	public List<User> getQueryResult(int userId1, int userId2) throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT U1.userId, U1.userName" + 
						  " FROM UserFriend U1, UserFriend U2" + 
						  " WHERE U1.userId = U2.userId AND U1.friendId = ? AND U2.friendId=?";
		connect();
		PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery);
		prepareStatement.setInt(1, userId1);
		prepareStatement.setInt(2, userId2);
		
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

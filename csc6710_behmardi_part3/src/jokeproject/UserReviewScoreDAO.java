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
public class UserReviewScoreDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserReviewScoreDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropUserReviewScoreView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS UserReviewScore");
		statement.close();
		disconnect();
	}
	
	/* create UserReviewScore view */
	public void createUserReviewScoreView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW UserReviewScore (userId, userName, reviewScore) AS" +
							  " SELECT U.userId, U.userName, R.reviewScore" +
							  " FROM User U, JokeReview R" +
							  " WHERE U.userId = R.reviewUserId" +
							  " GROUP BY U.userId, U.userName, R.reviewScore";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* run the query and return the result */
	public List<User> getQueryResult() throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT U.userId, U.userName" + 
						  " FROM User U" + 
						  " WHERE NOT EXISTS (" + 
						  "					SELECT *\r\n" + 
						  "                    FROM UserReviewScore S2" + 
						  "                    WHERE S2.userId = U.userId AND S2.reviewScore = 'poor'" + 
						  "				 )" +
						  " ORDER BY U.userId";
		
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
	
	/* run the query and return the result */
	public List<User> getQuery2Result() throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT S1.userId, S1.userName" + 
						  " FROM UserReviewScore S1" + 
						  " WHERE NOT EXISTS (" + 
						  "					SELECT *" + 
						  "                    FROM UserReviewScore S2" + 
						  "                    WHERE S2.userId = S1.userId AND S2.reviewScore <> 'poor'" + 
						  "				 )" +
						  " ORDER BY S1.userId";
		
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

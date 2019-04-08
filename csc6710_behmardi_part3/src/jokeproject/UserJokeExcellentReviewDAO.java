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
public class UserJokeExcellentReviewDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserJokeExcellentReviewDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropUserJokeExcellentReviewView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS UserJokeExcellentReview");
		statement.close();
		disconnect();
	}
	
	/* create UserJokeExcellentReview view */
	public void createUserJokeExcellentReviewView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW UserJokeExcellentReview (userId, userName, jokeId, excellentCount) AS" +
							  " SELECT U.userId, U.userName, J.jokeId, COUNT(*)" +
							  " FROM User U, Joke J, JokeReview R" +
							  " WHERE U.userId = J.postUserId AND J.jokeId = R.reviewJokeId AND R.reviewScore = 'excellent'" +
							  " GROUP BY U.userId, U.userName, J.jokeId";
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
						  " WHERE EXISTS (" + 
						  "					SELECT *" + 
						  "                    FROM UserJokeExcellentReview E" + 
						  "                    WHERE E.userId = U.userId and E.excellentCount >= 3" + 
						  "				 )";
		
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

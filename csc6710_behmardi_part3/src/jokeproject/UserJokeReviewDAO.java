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
public class UserJokeReviewDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserJokeReviewDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropUserJokeReviewView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS UserJokeReview");
		statement.close();
		disconnect();
	}
	
	/* create UserJokeReview view */
	public void createUserJokeReviewView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW UserJokeReview (userId, userName, jokeId, jokeTitle, jokeText, reviewerId, reviewScore) AS" +
							  " SELECT U.userId, U.userName, J.jokeId, J.jokeTitle, J.jokeText, R.reviewUserId, R.reviewScore" +
							  " FROM User U, Joke J, JokeReview R" +
							  " WHERE U.userId = J.postUserId AND J.jokeId = R.reviewJokeId";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* run the query and return the result */
	public List<Joke> getQueryResult(int userId) throws SQLException
	{
		List<Joke> jokeList =  new ArrayList<Joke>();
		String sqlQuery = "SELECT DISTINCT R1.jokeId, R1.jokeTitle, R1.jokeText" + 
						  " FROM UserJokeReview R1" + 
						  " WHERE R1.userId = ? AND NOT EXISTS( SELECT *" + 
															  " FROM UserJokeReview R2" +
						  									  " WHERE R2.jokeId = R1.jokeId AND" + 
						  											" (R2.reviewScore <> 'excellent' AND R2.reviewScore <> 'good'))" +
						  " ORDER BY R1.jokeId";
		connect();
		PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery);
		prepareStatement.setInt(1, userId);
		
		ResultSet result = prepareStatement.executeQuery();
		
		while(result.next())
		{
			int jokeId = result.getInt("jokeId");
			String jokeTitle = result.getString("jokeTitle");
			String jokeText = result.getString("jokeText");
			
			Joke joke = new Joke(jokeId, jokeTitle, jokeText, null, userId);		
			jokeList.add(joke);
		}
		
		result.close();
		prepareStatement.close();
		disconnect();
		
		return jokeList;
	}
	
	/* run the query and return the result */
	public List<User> getQuery2Result() throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT DISTINCT R1.userId, R1.userName" + 
						  " FROM UserJokeReview R1" + 
						  " WHERE NOT EXISTS (" + 
						  "					SELECT *" + 
						  "                    FROM UserJokeReview R2" + 
						  "                    WHERE R2.userId = R1.userId AND R2.reviewScore = 'poor'" + 
						  "				 )" +
						  " ORDER BY R1.userId";
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

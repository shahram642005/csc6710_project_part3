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
public class JokeReviewerDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	private List<User> UserList1;
	private List<User> UserList2;
	
	public List<User> getUserList1()
	{
		return UserList1;
	}

	public List<User> getUserList2() 
	{
		return UserList2;
	}
	
	
	/* constructors */
	public JokeReviewerDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropJokeReviewerView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP VIEW IF EXISTS JokeReviewer");
		statement.close();
		disconnect();
	}
	
	/* create JokeReviewer view */
	public void createJokeReviewerView() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE VIEW JokeReviewer (jokeId, reviewerUserId, reviewerUserName, reviewScore) AS" +
							  " SELECT R.reviewJokeId, R.reviewUserId, U.userName, R.reviewScore" +
							  " FROM User U, JokeReview R" +
							  " WHERE U.userId = R.reviewUserId";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* run the query and return the result */
	public void getQueryResult() throws SQLException
	{
		UserList1 = new ArrayList<User>();
		UserList2 = new ArrayList<User>();
		String sqlQuery = "SELECT U1.userId AS userId1, U1.userName AS userName1, U2.userId AS userId2, U2.userName AS userName2" + 
						  " FROM User U1, User U2" + 
						  " WHERE U1.userId < U2.UserId AND " + 
						  "		  NOT EXISTS (" + 
						  "                    (SELECT J.jokeId" + 
						  "                     FROM Joke J" + 
						  "                     WHERE J.postUserId = U1.userId AND J.jokeId NOT IN (SELECT J.jokeId" + 
						  "																	  FROM JokeReviewer J" + 
						  "                                                                      WHERE J.reviewerUserId = U2.userId AND J.reviewScore = 'excellent')                    " + 
						  "                    )" + 
						  "                    UNION" + 
						  "                    (SELECT J.jokeId" + 
						  "                     FROM Joke J" + 
						  "                     WHERE J.postUserId = U2.userId AND J.jokeId NOT IN (SELECT J.jokeId" + 
						  "																	  FROM JokeReviewer J" + 
						  "                                                                      WHERE J.reviewerUserId = U1.userId AND J.reviewScore = 'excellent')                    " + 
						  "                    )" + 
						  "				     )";
		
		connect();
		Statement statement = connection.createStatement();
		
		ResultSet result = statement.executeQuery(sqlQuery);
		
		while(result.next())
		{
			int userId1 = result.getInt("userId1");
			String userName1 = result.getString("userName1");
			int userId2 = result.getInt("userId2");
			String userName2 = result.getString("userName2");
			
			User user1 = new User(userId1, userName1, null, null, null, null, null, 0);
			User user2 = new User(userId2, userName2, null, null, null, null, null, 0);
			UserList1.add(user1);
			UserList2.add(user2);
		}
		
		result.close();
		statement.close();
		disconnect();
	}
}

package jokeproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/***************************************************
 * JokeReviewDAO.java
 * This DAO class provides CRUD operation for JokeReview table
 * @author Gwen Hickey
 *
 ***************************************************/

public class JokeReviewDAO 
{
	/* values to connect to the database */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public JokeReviewDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	
	/* drop JokeReview table */
	public void dropJokeReviewTable() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE IF EXISTS JokeReview");
		statement.close();
		disconnect();
	}
		
	/* create JokeReview table */
	public void createJokeReviewTable() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE TABLE IF NOT EXISTS JokeReview" +
				              "(reviewJokeId int(20) NOT NULL," +
				              " reviewUserId int(20) NOT NULL," +
		                      " reviewScore varchar(25) DEFAULT NULL," + 
				              " CHECK (reviewScore = 'excellent' or 'good' or 'fair' or 'poor')," +
		                      " reviewRemark varchar(250) DEFAULT NULL ," + 
		                      " reviewDate date DEFAULT NULL," + 
		                      " PRIMARY KEY (reviewUserId, reviewJokeId)," +
				              " FOREIGN KEY (reviewUserId) REFERENCES User (userId)," +
		         	          " FOREIGN KEY (reviewJokeId) REFERENCES Joke (jokeId))";
		statement.executeUpdate(sqlStatement);
		sqlStatement = 	"  CREATE TRIGGER fivereviewlimit BEFORE INSERT ON JokeReview" + 
						"  FOR EACH ROW" + 
						"  BEGIN" + 
						"      IF (SELECT COUNT(*) FROM JokeReview" + 
						"                         WHERE (reviewDate = current_date()" + 
						"                           AND reviewUserId = NEW.reviewUserId)) > 4" + 
						"	  THEN SIGNAL SQLSTATE '45000'" + 
						"		   SET MESSAGE_TEXT = 'Cannot review more than 5 jokes per day';" + 
						"      END IF;" + 
						"  END";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
	
	/* initialize JokeReview table */
	public void initJokereviewTable() throws SQLException
	{
		Date date = Date.valueOf(LocalDate.now());
		insertJokeReview(new JokeReview(1, 2, "excellent", null, date));
		insertJokeReview(new JokeReview(2, 3, "good", null, date));
		insertJokeReview(new JokeReview(3, 4, "fair", null, date));
		insertJokeReview(new JokeReview(4, 5, "poor", null, date));
		insertJokeReview(new JokeReview(5, 6, "excellent", null, date));
		insertJokeReview(new JokeReview(6, 7, "good", null, date));
		insertJokeReview(new JokeReview(7, 8, "fair", null, date));
		insertJokeReview(new JokeReview(8, 9, "poor", null, date));
		insertJokeReview(new JokeReview(9, 10, "excellent", null, date));
		insertJokeReview(new JokeReview(10, 1, "good", null, date));
	}
	
	/* insert a joke to JokeReview table */
	public boolean insertJokeReview(JokeReview jokeReview) throws SQLException
	{
		String sqlInsert = "INSERT INTO JokeReview (reviewJokeId, reviewUserId, reviewScore, reviewRemark, reviewDate) " +
							"VALUES (?, ?, ?, ?, ?)";
		connect();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
		preparedStatement.setInt(1, jokeReview.getreviewJokeId());
		preparedStatement.setInt(2, jokeReview.getreviewUserId());
		preparedStatement.setString(3, jokeReview.getreviewScore());
		preparedStatement.setString(4, jokeReview.getreviewRemark());
		preparedStatement.setDate(5, jokeReview.getreviewDate());
		
		boolean status = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		
		return status;
	}
	
	/* update a joke information in Joke table */
	public boolean updateJokeReview(JokeReview jokeReview) throws SQLException
	{
		String sqlUpdate = "UPDATE JokeReview SET reviewScore = ?, reviewRemark = ?, reviewDate = ?" +
							" WHERE reviewUserId = ? AND reviewJokeId = ?";
		connect();
		PreparedStatement prepareStatement = connection.prepareStatement(sqlUpdate);
		prepareStatement.setString(1, jokeReview.getreviewScore());
		prepareStatement.setString(2, jokeReview.getreviewRemark());
		prepareStatement.setDate(3, jokeReview.getreviewDate());
		prepareStatement.setInt(4, jokeReview.getreviewUserId());
		prepareStatement.setInt(5, jokeReview.getreviewJokeId());
		
		boolean status = prepareStatement.executeUpdate() > 0;
		prepareStatement.close();
		disconnect();
		
		return status;
	}
	
	/* delete a joke review from table */
	public boolean deleteJokeReview(int userId, int jokeId) throws SQLException
	{
		String sqlDelete = "DELETE FROM JokeReview WHERE reviewUserId = ? AND reviewJokeId = ?";
		connect();
		
		PreparedStatement prepareStatement = connection.prepareStatement(sqlDelete);
		prepareStatement.setInt(1, userId);
		prepareStatement.setInt(2, jokeId);
		
		boolean status = prepareStatement.executeUpdate() > 0;
		prepareStatement.close();
		disconnect();
		
		return status;
	}
	
	/* get list of all jokeReviews from jokeReview table */
	public JokeReview getJokeReview(int userId, int jokeId) throws SQLException
	{
		JokeReview jokeReview = null;
		String sqlSelect = "SELECT * FROM JokeReview" +
							" WHERE reviewUserId = ? AND reviewJokeId = ?";
		
		connect();
		
		PreparedStatement prepareStatement = connection.prepareStatement(sqlSelect);
		prepareStatement.setInt(1, userId);
		prepareStatement.setInt(2, jokeId);
		ResultSet result = prepareStatement.executeQuery();
		
		while(result.next())
		{
			int reviewJokeId = result.getInt("reviewJokeId");
			int reviewUserId = result.getInt("reviewUserId");
			String reviewScore = result.getString("reviewScore");
			String reviewRemark = result.getString("reviewRemark");
			Date reviewDate = result.getDate("reviewDate");
			
			jokeReview = new JokeReview(reviewJokeId, reviewUserId, reviewScore, reviewRemark, reviewDate);
		}
		
		result.close();
		prepareStatement.close();
		disconnect();
		
		return jokeReview;
	}

	/* get list of all jokeReviews from jokeReview table */
	public List<JokeReview> getJokeReviewList() throws SQLException
	{
		List<JokeReview> jokeReviewList =  new ArrayList<JokeReview>();
		String sqlQuery = "SELECT * FROM JokeReview";
		
		connect();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sqlQuery);
		
		while(result.next())
		{
			int reviewJokeId = result.getInt("reviewJokeId");
			int reviewUserId = result.getInt("reviewUserId");
			String reviewScore = result.getString("reviewScore");
			String reviewRemark = result.getString("reviewRemark");
			Date reviewDate = result.getDate("reviewDate");
			
			jokeReviewList.add(new JokeReview(reviewJokeId, reviewUserId, reviewScore, reviewRemark, reviewDate));
		}
		result.close();
		statement.close();
		disconnect();
		
		return jokeReviewList;
	   }
}


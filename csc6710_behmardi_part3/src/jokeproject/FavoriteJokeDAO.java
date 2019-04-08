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
 * FavoriteJokeDAO.java
 * This DAO class provides CRUD operation for FavoriteJoke table
 * @author Gwen Hickey
 *
 *  P R O J E C T  -  P A R T  2
 ***************************************************/

public class FavoriteJokeDAO 
{
		/* values to connect to the database */
		protected String databaseURL;
		protected String databaseUserName;
		protected String databasePassword;
		private Connection connection;
		
		/* constructors */
		public FavoriteJokeDAO(String databaseURL, String databaseUserName, String databasePassword)
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
		
		/* drop FavoriteJoke table */
		public void dropFavoriteJokeTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS FavoriteJoke");
			statement.close();
			disconnect();
		}
		
		/* create FavoriteJoke table */
		public void createFavoriteJokeTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			String sqlStatement = "CREATE TABLE IF NOT EXISTS FavoriteJoke" +
					              "(jokeId int(20) NOT NULL," +
					              " userId int(20) NOT NULL," +
			                      " PRIMARY KEY (jokeId, userId)," +
			         	          " FOREIGN KEY (jokeId) REFERENCES Joke (jokeId) ON DELETE CASCADE," +
					              " FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE)"
					              ;
			statement.executeUpdate(sqlStatement);
			statement.close();
			disconnect();
			
		}
		
		/* initialize FavoriteJoke table */
		public void initFavoriteJokeTable() throws SQLException
		{			
			insertFavoriteJoke(new FavoriteJoke(1,2));
			insertFavoriteJoke(new FavoriteJoke(2,3));
			insertFavoriteJoke(new FavoriteJoke(3,4));
			insertFavoriteJoke(new FavoriteJoke(4,5));
			insertFavoriteJoke(new FavoriteJoke(5,6));
			insertFavoriteJoke(new FavoriteJoke(6,7));
			insertFavoriteJoke(new FavoriteJoke(7,8));
			insertFavoriteJoke(new FavoriteJoke(8,9));
			insertFavoriteJoke(new FavoriteJoke(9,10));
		}
		
		/* insert a favorite joke to FavoriteJoke table */
		public boolean insertFavoriteJoke(FavoriteJoke favoriteJoke) throws SQLException
		{
			String sqlInsert = "INSERT INTO FavoriteJoke (jokeId, userId) " +
								"VALUES (?, ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setInt(1, favoriteJoke.getjokeId());
			preparedStatement.setInt(2, favoriteJoke.getuserId());
			
			
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		//**********************************************************// 
	    //*          DELETE a favorite FROM Favorite table         *//
		//**********************************************************//
		public boolean deleteFavoriteJoke(FavoriteJoke favoriteJoke) throws SQLException
		{
			String sqlUpdate = "DELETE FROM FavoriteJoke WHERE (userId = ? AND jokeId = ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, favoriteJoke.getuserId());
			preparedStatement.setInt(2, favoriteJoke.getjokeId());
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		/* get list of all favorite jokes from FavoriteJoke table */
		public List<Joke> getFavoriteJokeList(int userId) throws SQLException
		{
			List<Joke> jokeList = new ArrayList<Joke>();
			String sqlQuery = "SELECT * FROM FavoriteJoke WHERE userId=?";
			
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userId);
			
			
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{		
				int jokeId = result.getInt("jokeId");
				
				jokeList.add(new Joke(jokeId));
			}
			
			result.close();
			preparedStatement.close();
			disconnect();
			
			return jokeList;
		}
		
		//*************************************************//
	    //*         P R O J E C T  -  P A R T  2          */
	    //*         get user's favorite jokes             */
		//*************************************************//
		public List<Joke> getFavJokes(int userId) throws SQLException
		{
			List<Joke> FavJokes =  new ArrayList<Joke>();
			String sqlQuery = "SELECT *" +
				    		  " FROM (Joke joke, FavoriteJoke fav)" +
				    		  " WHERE (joke.jokeId = fav.jokeId AND fav.UserId = ?)" +
				    		  " ORDER BY joke.jokeTitle ";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userId);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{
				int jokeId = result.getInt("jokeId");
				String jokeTitle = result.getString("jokeTitle");
				String jokeText = result.getString("jokeText");
				java.sql.Date jokePostDate = result.getDate("jokePostDate");
				int postUserId = result.getInt("postUserId");
				
				Joke joke = new Joke(jokeId, jokeTitle, jokeText, jokePostDate, postUserId);				
				FavJokes.add(joke);
			}
			result.close();
			preparedStatement.close();
			disconnect();
			
			return FavJokes;
	    }
		
		/* get list of all favorite jokes from FavoriteJoke table */
		public List<FavoriteJoke> getFavoriteJokeList() throws SQLException
		{
			List<FavoriteJoke> favoriteJokeList =  new ArrayList<FavoriteJoke>();
			String sqlQuery = "SELECT * FROM FavoriteJoke";
			
			connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next())
			{
				int jokeId = result.getInt("jokeId");			
				int userId = result.getInt("userId");
				
				favoriteJokeList.add(new FavoriteJoke(jokeId, userId));
			}
			result.close();
			statement.close();
			disconnect();
			
			return favoriteJokeList;
		}
}

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

public class FriendDAO 
{
		/* values to connect to the database */
		protected String databaseURL;
		protected String databaseUserName;
		protected String databasePassword;
		private Connection connection;
		
		/* constructors */
		public FriendDAO(String databaseURL, String databaseUserName, String databasePassword)
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
		
		/* drop Friend table */
		public void dropFriendTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS Friend");
			statement.close();
			disconnect();
		}
		
		/* create Friend table */
		public void createFriendTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			String sqlStatement = "CREATE TABLE IF NOT EXISTS Friend" +
					              "(userId int(20) NOT NULL," +
					              " friendUserId int(20) NOT NULL," +
			                      " PRIMARY KEY (userId, friendUserId)," +
			         	          " FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE," +
					              " FOREIGN KEY (friendUserId) REFERENCES User(userId) ON DELETE CASCADE)"
					              ;
			statement.executeUpdate(sqlStatement);
			statement.close();
			disconnect();
			
		}
		
		/* initialize Friend table */
		public void initFriendTable() throws SQLException
		{			
			/*insertFriend(new Friend(1,2));
			insertFriend(new Friend(2,3));
			insertFriend(new Friend(3,4));
			insertFriend(new Friend(4,5));
			insertFriend(new Friend(5,6));
			insertFriend(new Friend(6,7));
			insertFriend(new Friend(7,8));
			insertFriend(new Friend(8,9));
			insertFriend(new Friend(9,10));*/
			insertFriend(new Friend(1,4));
			insertFriend(new Friend(2,3));
			insertFriend(new Friend(3,1));
			insertFriend(new Friend(4,1));
			insertFriend(new Friend(5,2));
			
		}
		
		/* insert a friend to Friend table */
		public boolean insertFriend(Friend friend) throws SQLException
		{
			String sqlInsert = "INSERT INTO Friend (userId, friendUserId) " +
								"VALUES (?, ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setInt(1, friend.getuserId());
			preparedStatement.setInt(2, friend.getfriendUserId());
			
			
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		/* delete a friend from Friend table */
		public boolean deleteFriend(Friend friend) throws SQLException
		{
			String sqlUpdate = "DELETE FROM Friend WHERE (userId = ? AND friendUserId = ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, friend.getuserId());
			preparedStatement.setInt(2, friend.getfriendUserId());
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		/* get list of all friends from Friend table */
		public List<User> getFriendList(int userId) throws SQLException
		{
			List<User> friendList =  new ArrayList<User>();
			String sqlQuery = "SELECT *" +
								" FROM (Friend friend, User user)" +
								" WHERE friend.userId = ? AND user.userId = friend.friendUserId";
			
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userId);
			
			
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{
				int friendUserId = result.getInt("friendUserId");
				String userName = result.getString("userName");
				String firstName = result.getString("firstName");
				String password = "";
				String lastName = result.getString("lastName");
				String email = result.getString("email");
				String gender = result.getString("gender");
				int age = result.getInt("age");
				
				friendList.add(new User(friendUserId,userName, password, firstName, lastName, email, gender, age));
			}
			
			result.close();
			preparedStatement.close();
			disconnect();
			
			return friendList;
		}
		
		/* get list of all friends from Friend table */
		public List<Friend> getFriendList() throws SQLException
		{
			List<Friend> friendList =  new ArrayList<Friend>();
			String sqlQuery = "SELECT * FROM Friend";
			
			connect();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlQuery);
			
			while(result.next())
			{
				int userId = result.getInt("userId");			
				int friendUserId = result.getInt("friendUserId");
				
				friendList.add(new Friend(userId, friendUserId));
			}
			result.close();
			statement.close();
			disconnect();
			
			return friendList;
		}
		
		/* run the query and return the result */
		public List<User> getQueryResult(int userId1, int userId2) throws SQLException
		{
			List<User> userList =  new ArrayList<User>();
			String sqlQuery = "SELECT U.userId, U.userName" + 
							  " FROM User U, Friend F1, Friend F2" + 
							  " WHERE F1.userId = ? AND F2.userId = ?" +
							  "		  AND F1.friendUserId = F2.friendUserId AND F1.friendUserId = U.userId" +
							  " ORDER BY U.userId";
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

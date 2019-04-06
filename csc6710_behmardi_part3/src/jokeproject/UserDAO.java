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
public class UserDAO
{
	/* attributes of UserDAO class */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public UserDAO(String databaseURL, String databaseUserName, String databasePassword)
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
	public void dropUserTable() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE IF EXISTS User");
		statement.close();
		disconnect();
	}
	
	/* create User table */
	public void createUserTable() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE TABLE IF NOT EXISTS User(" +
							  " userId INTEGER not NULL AUTO_INCREMENT, " +
							  " userName VARCHAR(50) not NULL, " +
							  " password VARCHAR(50) not NULL, " +
							  " firstName VARCHAR(50), " +
							  " lastName VARCHAR(50), " +
							  " email VARCHAR(50), " +
							  " gender VARCHAR(20), " +
							  " age INTEGER, " +
							  " PRIMARY KEY ( userId ), " + 
							  " UNIQUE KEY (userName), " +
							  " UNIQUE KEY (email));";
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
	}
		
	/* initialize User table */
	public void initUserTable() throws SQLException
	{
		/* insert the default users */
		List<User> userList = new ArrayList<User>();
		userList.add(new User("root", "pass1234", "root", null, "root@wayne.edu", null, 0));
		userList.add(new User("john1234", "pass1234", "John", null, "John123@wayne.edu", "Male", 0));
		userList.add(new User("shahram1234", "pass1234", "Shahram", null, "Shahram123@wayne.edu", "Male", 0));
		userList.add(new User("gwen1234", "pass1234", "Gwen", null, "Gwen123@wayne.edu", "Female", 0));
		userList.add(new User("runni1234", "pass1234", "Runni", null, "Runni123@wayne.edu", "Female", 0));
		userList.add(new User("elvis1234", "pass1234", "Elvis", null, "Elvis123@wayne.edu", "Male", 0));
		userList.add(new User("shiyong1234", "pass1234", "Shiyong", null, "Shiyong123@wayne.edu", "Male", 0));
		userList.add(new User("luchia1234", "pass1234", "Luchia", null, "Luchia123@wayne.edu", "Female", 0));
		userList.add(new User("mike1234", "pass1234", "Mike", null, "Mike123@wayne.edu", "Male", 0));
		userList.add(new User("sarah1234", "pass1234", "Sarah", null, "Sarah123@wayne.edu", "Female", 0));
		userList.add(new User("jennifer1234", "pass1234", "Jennifer", null, "Jennifer123@wayne.edu", "Female", 0));
		insertUser(userList);
	}
	
	/* insert a userList to User table */
	public boolean insertUser(List<User> userList) throws SQLException
	{
		User user = new User();
		boolean status = false;
		
		String sqlInsert = "INSERT INTO User (userName, password, firstName, lastName, email, gender, age) " +
							"VALUES (?, ?, ?, ?, ?, ?, ?)";
		connect();
		for (int i = 0; i < userList.size(); i++)
		{
			user = userList.get(i);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getGender());
			preparedStatement.setInt(7, user.getAge());
			
			status &= preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
		}		
		disconnect();
		
		return status;
	}
	
	/* insert a user to User table */
	public boolean insertUser(User user) throws SQLException
	{
		String sqlInsert = "INSERT INTO User (userName, password, firstName, lastName, email, gender, age) " +
							"VALUES (?, ?, ?, ?, ?, ?, ?)";
		connect();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
		preparedStatement.setString(1, user.getUserName());
		preparedStatement.setString(2, user.getPassword());
		preparedStatement.setString(3, user.getFirstName());
		preparedStatement.setString(4, user.getLastName());
		preparedStatement.setString(5, user.getEmail());
		preparedStatement.setString(6, user.getGender());
		preparedStatement.setInt(7, user.getAge());
		
		boolean status = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		
		return status;
	}
	
	/* update a user information in User table */
	public boolean updateUser(User user) throws SQLException
	{
		String sqlUpdate = "UPDATE User SET userName = ?, password = ?, firstName = ?, " +
							"lastName = ?, email = ?, gender = ?, age = ?" +
							" WHERE userId = ?";
		connect();
		PreparedStatement prepareStatement = connection.prepareStatement(sqlUpdate);
		prepareStatement.setString(1, user.getUserName());
		prepareStatement.setString(2, user.getPassword());
		prepareStatement.setString(3, user.getFirstName());
		prepareStatement.setString(4, user.getLastName());
		prepareStatement.setString(5, user.getEmail());
		prepareStatement.setString(6, user.getGender());
		prepareStatement.setInt(7, user.getAge());
		prepareStatement.setInt(8, user.getUserId());
		
		boolean status = prepareStatement.executeUpdate() > 0;
		prepareStatement.close();
		disconnect();
		
		return status;
	}
	
	/* delete a user from User table */
	public boolean deleteUser(int userId) throws SQLException
	{
		String sqlDelete = "DELETE FROM User WHERE userId = ?";
		connect();
		
		PreparedStatement prepareStatement = connection.prepareStatement(sqlDelete);
		prepareStatement.setInt(1, userId);
		
		boolean status = prepareStatement.executeUpdate() > 0;
		prepareStatement.close();
		disconnect();
		
		return status;
	}
	
	/* get a user from User table based on userName */
	public User getUser(String userName) throws SQLException
	{
		User user = null;
		String sqlGet = "SELECT * FROM User WHERE userName = ?";
		connect();
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlGet);
		preparedStatement.setString(1, userName);
		ResultSet result = preparedStatement.executeQuery();
		
		while (result.next())
		{
			int userId = result.getInt("userId");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String email = result.getString("email");
			String gender = result.getString("gender");
			int age = result.getInt("age");
			
			user = new User(userId, userName, password, firstName, lastName, email, gender, age);
		}
		
		result.close();
		preparedStatement.close();
		disconnect();
		
		return user;
	}
	
	/* get a user from User table based on userId */
	public User getUser(int userId) throws SQLException
	{
		User user = null;
		String sqlGet = "SELECT * FROM User WHERE userId = ?";
		connect();
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlGet);
		preparedStatement.setInt(1, userId);
		ResultSet result = preparedStatement.executeQuery();
		
		while (result.next())
		{
			String userName = result.getString("userName");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String email = result.getString("email");
			String gender = result.getString("gender");
			int age = result.getInt("age");
			
			user = new User(userId, userName, password, firstName, lastName, email, gender, age);
		}
		
		result.close();
		preparedStatement.close();
		disconnect();
		
		return user;
	}
	
	/* get list of all users from user table */
	public List<User> getUserList() throws SQLException
	{
		List<User> userList =  new ArrayList<User>();
		String sqlQuery = "SELECT * FROM User";
		
		connect();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sqlQuery);
		
		while(result.next())
		{
			int userId = result.getInt("userId");
			String userName = result.getString("userName");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String email = result.getString("email");
			String gender = result.getString("gender");
			int age = result.getInt("age");
			
			User user = new User(userId, userName, password, firstName, lastName, email, gender, age);			
			userList.add(user);
		}
		
		result.close();
		statement.close();
		disconnect();
		
		return userList;
	}
}

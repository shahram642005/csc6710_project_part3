package jokeproject;

/***************************************************
 * User.java
 * This is a model class representing a user entity
 * @author Shahram Behmardi Kalantari
 *
 ***************************************************/
public class User
{	
	/* attributes of User class */
	protected int userId;
	protected String userName;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String gender;
	protected int age;
	protected boolean isBanned;

	/* constructors */
	public User() 
	{
	}
	
	public User(String userName, String password, String firstName, String lastName, String email, String gender, int age)
	{
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.age = age;
	}
	
	public User(int userId, String userName, String password, String firstName, String lastName, String email, String gender, int age)
	{
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.isBanned = false;
	}
	
	public User(int userId, String userName, String password, String firstName, String lastName, String email, String gender, int age, boolean isBanned)
	{
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.isBanned = isBanned;
	}
	
	/* define accessors and setters methods */
	public int getUserId()
	{
		return userId;
	}
	
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public boolean getIsBanned()
	{
		return isBanned;
	}
	
	public void setIsBanned(boolean isBanned)
	{
		this.isBanned = isBanned;
	}
}

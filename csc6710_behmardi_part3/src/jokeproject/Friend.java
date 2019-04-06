package jokeproject;

/***************************************************
 * Friend.java
 * This is a model class representing a friend entity
 * @author Gwen Hickey
 *
 ***************************************************/


public class Friend 
{
	/* attributes of Friend class */
	protected int userId;
	protected int friendUserId;
	
    /* constructors */
    public Friend() 
    {
    }

    public Friend(int userId, int friendUserId)
	{
		this.userId = userId;
		this.friendUserId = friendUserId;
	}
    
    /* define accessors and setters methods */
    
    //userId
	public int getuserId()
	{
		return userId;
	}
	public void setuserId(int userId)
	{
		this.userId = userId;
	}
	
	//friendUserId
	public int getfriendUserId()
	{
		return friendUserId;
	}
	public void setfriendUserId(int friendUserId)
	{
		this.friendUserId = friendUserId;
	}
}

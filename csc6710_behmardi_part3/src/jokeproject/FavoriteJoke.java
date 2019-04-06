package jokeproject;

/***************************************************
 * FavoriteJoke.java
 * This is a model class representing a favorite joke entity
 * @author Gwen Hickey
 *
 * P R O J E C T  -  P A R T  2
 ***************************************************/


public class FavoriteJoke 
{
	/* attributes of FavoriteJoke class */
	protected int jokeId;
	protected int userId;
	
    /* constructors */
    public FavoriteJoke() 
    {
    }

    public FavoriteJoke(int jokeId, int userId)
	{
		this.jokeId = jokeId;
		this.userId = userId;
	}
    
    /* define accessors and setters methods */
    
    //jokeId
	public int getjokeId()
	{
		return jokeId;
	}
	public void setjokeId(int jokeId)
	{
		this.jokeId = jokeId;
	}
	
	//userId
	public int getuserId()
	{
		return userId;
	}
	public void setuserId(int userId)
	{
		this.userId = userId;
	}
}

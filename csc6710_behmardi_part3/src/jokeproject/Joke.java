package jokeproject;

/***************************************************
 * Joke.java
 * This is a model class representing a joke entity
 * @author Gwen Hickey
 *
 ***************************************************/

public class Joke 
{
	/* attributes of Joke class */
	protected int jokeId;
	protected String jokeTitle;
	protected String jokeText;
	protected java.sql.Date jokePostDate;
	protected int postUserId;

    /* constructors */
    public Joke() 
    {
    }
    
    public Joke(int jokeId) 
    {
    	this.jokeId = jokeId;
    }
    
    public Joke(String jokeTitle, String jokeText, java.sql.Date jokePostDate, int postUserId)
	{
		this.jokeTitle = jokeTitle;
		this.jokeText = jokeText;
		this.jokePostDate = jokePostDate;
		this.postUserId = postUserId;
	}

    public Joke(int jokeId, String jokeTitle, String jokeText, java.sql.Date jokePostDate, int postUserId)
	{
		this.jokeId = jokeId;
		this.jokeTitle = jokeTitle;
		this.jokeText = jokeText;
		this.jokePostDate = jokePostDate;
		this.postUserId = postUserId;
	}
    
    //*******  P R O J E C T  -  P A R T  2    ***********//
    public Joke(String jokeTitle, String jokeText, int postUserId)
	{
		this.jokeTitle = jokeTitle;
		this.jokeText = jokeText;
		this.postUserId = postUserId;
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
	
	//jokeTitle
	public String getjokeTitle()
	{
		return jokeTitle;
	}
	public void setjokeTitle(String jokeTitle)
	{
		this.jokeTitle = jokeTitle;
	}
	
	//jokeText
	public String getjokeText()
	{
		return jokeText;
	}
	public void setjokeText(String jokeText)
	{
		this.jokeText = jokeText;
	}
	
	//jokePostDate
	public java.sql.Date getjokePostDate()
	{
		return jokePostDate;
	}
	public void setjokePostDate(java.sql.Date jokePostDate)
	{
		this.jokePostDate = jokePostDate;
	}
	
	//postUserId
	public int getpostUserId()
	{
		return postUserId;
	}
	public void setpostUserId(int postUserId)
	{
		this.postUserId = postUserId;
	}
}



	
	
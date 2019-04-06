package jokeproject;

/***************************************************
 * JokeReview.java
 * This is a model class representing a jokeReview entity
 * @author Gwen Hickey
 *
 ***************************************************/

public class JokeReview {
	/* attributes of JokeReview class */
	protected int reviewJokeId;
	protected int reviewUserId;
	protected String reviewScore;
	protected String reviewRemark;
	protected java.sql.Date reviewDate;

    /* constructors */
    public JokeReview() 
    {
    }
    public JokeReview(int reviewJokeId, int reviewUserId, String reviewScore, String reviewRemark, java.sql.Date reviewDate)
	{
		this.reviewJokeId = reviewJokeId;
		this.reviewUserId = reviewUserId;
		this.reviewScore = reviewScore;
		this.reviewRemark = reviewRemark;
		this.reviewDate = reviewDate;
	}

    /* define accessors and setters methods */
    //reviewJokeId
	public int getreviewJokeId()
	{
		return reviewJokeId;
	}
	public void setreviewJokeIdd(int reviewJokeId)
	{
		this.reviewJokeId = reviewJokeId;
	}
	
	//reviewUserId
	public int getreviewUserId()
	{
		return reviewUserId;
	}
	public void setreviewUserId(int reviewUserId)
	{
		this.reviewUserId = reviewUserId;
	}
	
	//reviewScore
	public String getreviewScore()
	{
		return reviewScore;
	}
	public void setreviewScore(String reviewScore)
	{
		this.reviewScore = reviewScore;
	}
	
	//reviewRemark
	public String getreviewRemark()
	{
		return reviewRemark;
	}
	public void setreviewRemark(String reviewRemark)
	{
		this.reviewRemark = reviewRemark;
	}
	
	//reviewDate
	public java.sql.Date getreviewDate()
	{
		return reviewDate;
	}
	public void setreviewDate(java.sql.Date reviewDate)
	{
		this.reviewDate = reviewDate;
	}
	
}

	
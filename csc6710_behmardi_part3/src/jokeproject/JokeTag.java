package jokeproject;

/***************************************************
 * JokeTag.java
 * This is a model class representing a jokeTag entity
 * @author Gwen Hickey
 *
 ***************************************************/

public class JokeTag 
{
		/* attributes of JokeTag class */
		protected int jokeId;
		protected String jokeTagWord;
		
	    /* constructors */
	    public JokeTag() 
	    {
	    }

	    public JokeTag(int jokeId, String jokeTagWord)
		{
			this.jokeId = jokeId;
			this.jokeTagWord = jokeTagWord;
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
		
		//jokeTagWord
		public String getjokeTagWord()
		{
			return jokeTagWord;
		}
		public void setjokeTagWord(String jokeTagWord)
		{
			this.jokeTagWord = jokeTagWord;
		}
}

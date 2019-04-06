package jokeproject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JokeExceptionHandler
{
	private Exception exception;
	
	public JokeExceptionHandler(Exception exception)
	{
		this.exception = exception;
	}
	
	public void showOnErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("errorMessage", exception.getMessage());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
        dispatcher.forward(request, response);
	}
}

package jokeproject;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/***************************************************
 * UserDAO.java
 * This class provides the servlet functionality
 * @author Shahram Behmardi Kalantari
 *
 ***************************************************/
public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private JokeDAO jokeDAO;
	private FriendDAO friendDAO;
	private JokeReviewDAO jokeReviewDAO;
	private JokeTagDAO jokeTagDAO;
	private FavoriteJokeDAO favoriteJokeDAO;
	private UserJokeTagDAO userJokeTagDAO;
	private UserJokeReviewDAO userJokeReviewDAO;
	private UserJokeNumSince03012018DAO userJokeNumSince03012018DAO;
	private UserFriendDAO userFriendDAO;
	private UserJokeExcellentReviewDAO userJokeExcellentReviewDAO;
	private UserReviewScoreDAO userReviewScoreDAO;
	private JokeReviewerDAO jokeReviewerDAO;
	
	/* implement the init method that runs once for the life cycle of servlet */
	public void init()
	{
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
        
        friendDAO = new FriendDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeDAO = new JokeDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeReviewDAO = new JokeReviewDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeTagDAO = new JokeTagDAO(jdbcURL, jdbcUsername, jdbcPassword);
		favoriteJokeDAO = new FavoriteJokeDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userJokeTagDAO = new UserJokeTagDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userJokeReviewDAO = new UserJokeReviewDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userJokeNumSince03012018DAO = new UserJokeNumSince03012018DAO(jdbcURL, jdbcUsername, jdbcPassword);
		userFriendDAO = new UserFriendDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userJokeExcellentReviewDAO = new UserJokeExcellentReviewDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userReviewScoreDAO = new UserReviewScoreDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeReviewerDAO = new JokeReviewerDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	/* implement the doPost method */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        doGet(request, response);
    }
	
	/* implement the doGet method */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        String action = request.getServletPath();
        
        try {
            switch (action) 
            {
	            case "/initTables":
	            	initializeDatabase(request, response);
	            	break;
	            case "/newUser":
	            	goToRegisterForm(request, response);
	                break;
	            case "/loginUser":
	            	loginUser(request, response);
	            	break;
	            case "/logoutUser":
	            	logoutUser(request, response);
	            	break;
	            case "/registerUser":
	                registerUser(request, response);
	                break;
	            case "/listAllUsers":
	            	listAllUsers(request, response);
	            	break;
	            case "/deleteUser":
	                deleteUser(request, response);
	                break;
	            case "/modifyUser":
	                goToUserEditForm(request, response);
	                break;
	            case "/updateUser":
	                updateUser(request, response);
	                break;
	            case "/newJoke":
	            	goToJokePostForm(request, response);
	                break;
	            case "/postJoke":
	                postJoke(request, response);
	                break;
	            case "/listAllJokes":
	            	listAllJokes(request, response);
	            	break;
	        	case "/listUserJokes":
	            	listUserJokes(request, response);
	            	break;
	            case "/deleteJoke":
	                deleteJoke(request, response);
	                break;
	            case "/modifyJoke":
	                goToJokeEditForm(request, response);
	                break;
	            case "/updateJoke":
	                updateJoke(request, response);
	                break;
	            case "/starJoke":
	            	addToFavoriteJokes(request, response);
	            	break;
	            case "/unstarJoke":
	            	removeFromFavoriteJokes(request, response);
	            	break;
	            case "/starUser":
	            	addToFriends(request, response);
	            	break;
	            case "/unstarUser":
	            	removeFromFriends(request, response);
	            	break;
	            case "/searchJoke":
	            	searchJoke(request, response);
	                break;
	            case "/listFriends":
	            	listFriends(request, response);
	            	break;
	            case "/listFavoritejokes":
	            	listFavoritejokes(request, response);
	            	break;
	            case "/newReview":
	            	goToReviewForm(request, response);
	            	break;
	            case "/submitReview":
	            	submitReview(request, response);
	            	break;
	            case "/editReview":
	            	editReview(request, response);
	            	break;
	            case "/updateReview":
	            	updateReview(request, response);
	            	break;
	            case "/removeReview":
	            	removeReview(request, response);
	            	break;
	            case "/goBack":
	            	goToLastPage(request, response);
	            	break;
	            case "/goToQueries":
	            	goToQueries(request, response);
	            	break;
	            case "/runQuery":
	            	runQuery(request, response);
	            	break;
	            case "/banUser":
	                //banUser(request, response);
	                //break;
	            case "/unbanUser":
	            	//unbanUser(request, response);
	                //break;
	            default:
	            	throw new ServletException("The action \"" + action + "\" has not been implemented yet!");
            }
        }
        catch (Exception exception)
        {
            JokeExceptionHandler jokeException = new JokeExceptionHandler(exception);
            jokeException.showOnErrorPage(request, response);
        }
    }
	
	/* insert a user into User table */
	private void initializeDatabase(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* add lastPage to session and set it to Login.jsp page */
		HttpSession session = request.getSession();
		session.setAttribute("lastPage", "Login.jsp");
		
		/* create all the database tables and populate them with tuples */
		favoriteJokeDAO.dropFavoriteJokeTable();
		jokeTagDAO.dropJokeTagTable();
		jokeReviewDAO.dropJokeReviewTable();
		friendDAO.dropFriendTable();
		jokeDAO.dropJokeTable();
		userDAO.dropUserTable();
		userDAO.createUserTable();
		jokeDAO.createJokeTable();
		friendDAO.createFriendTable();		
		jokeReviewDAO.createJokeReviewTable();
		jokeTagDAO.createJokeTagTable();
		favoriteJokeDAO.createFavoriteJokeTable();
		userDAO.initUserTable();		
		jokeDAO.initJokeTable();		
		friendDAO.initFriendTable();
		jokeReviewDAO.initJokereviewTable();
		jokeTagDAO.initJokeTagTable();
		favoriteJokeDAO.initFavoriteJokeTable();
		
		/* create all the database views */
		userJokeTagDAO.dropUserJokeTagView();
		userJokeTagDAO.createUserJokeTagView();
		userJokeReviewDAO.dropUserJokeReviewView();
		userJokeReviewDAO.createUserJokeReviewView();
		userJokeNumSince03012018DAO.dropUserJokeNumSince03012018View();
		userJokeNumSince03012018DAO.createUserJokeNumSince03012018View();
		userJokeExcellentReviewDAO.dropUserJokeExcellentReviewView();
		userJokeExcellentReviewDAO.createUserJokeExcellentReviewView();
		userFriendDAO.dropUserFriendView();
		userFriendDAO.createUserFriendView();
		userReviewScoreDAO.dropUserReviewScoreView();
		userReviewScoreDAO.createUserReviewScoreView();
		jokeReviewerDAO.dropJokeReviewerView();
		jokeReviewerDAO.createJokeReviewerView();
		
		/* show a message indicating successful logout */
		String message = "All tables are successfully initialized!";
		String color = "green";
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		
		/* go to login page */
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}
	
	/* go to registration form */
	private void goToRegisterForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* determine form variables */
		String formAction = new String("registerUser");
		String formText = new String("Please insert your account information:");
		String buttonText = new String("register");
		
		/* fill the form values of the Resigtration.jsp with the user info */
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
        dispatcher.forward(request, response);
	}
	
	/* insert a user into User table */
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the user information from the registration form */
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String ageStr = request.getParameter("age");
		int age = 0;
		if (!ageStr.isEmpty()) /* make sure ageStr is not empty */
		{
			age = Integer.parseInt(ageStr);
		}
		
		/* create a user instance with the provided data and insert it into database */
		User user = new User(userName, password, firstName, lastName, email, gender, age);
		
		/* if the required fields are blank, then throw an error to the user */
		if (userName.isBlank() || password.isBlank() || confirmPassword.isBlank() || email.isBlank() || !password.equals(confirmPassword))
		{
			String message = new String();
			if (!password.equals(confirmPassword))
			{
				message = "Password mismatch!";
			}
			else
			{
				message = "Please fill out all of the required fields!";
			}
			String color = "red";
			request.setAttribute("message", message);
			request.setAttribute("color", color);
			
			/* determine form variables */
			user.password = "";
			request.setAttribute("user", user);
			String formAction = new String("registerUser");
			String formText = new String("Please insert your account information:");
			String buttonText = new String("register");
			
			/* fill the form values of the Resigtration.jsp with the user info */
			request.setAttribute("formAction", formAction);
			request.setAttribute("formText", formText);
			request.setAttribute("buttonText", buttonText);
			
			/* go to the registration page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
			dispatcher.forward(request, response);
		}
		else /* successful match between password and its confirmation */
		{
			/* insert the user to database */
			userDAO.insertUser(user);
	
			/* go to the login page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	/* check if user is in database and it's user name and password match the User table */
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userName and password from Login Form */
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		/* create a new User object and put the user information inside it */
		User user = new User();
		user = userDAO.getUser(userName);
		
		/* check the credential of the user */
		if (userName == null || password == null || user == null || !password.equals(user.password)) /* unsuccessful login */
		{
			/* show a message indicating successful logout */
			String message = "The user name or password is not correct, please try again!";
			String color = "red";
			request.setAttribute("message", message);
			request.setAttribute("color", color);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}
		else /* successful login */
		{
			/* add userId to session */
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getUserId());
			
			/* go to the UserAccount in the browser */
			listAllJokes(request, response);
		}		
	}
	
	/* check if user is in database and it's user name and password match the User table */
	private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* remove userId from session */
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
		
		/* show a message indicating successful logout */
		String message = "You are successfully logged out!";
		String color = "green";
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);		
	}
	
	/* list all users */
	private void listAllUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the list of users from database */
		List<User> userList = userDAO.getUserList();
		
		/* list all users in the browser */
		listUsers(request, response, userList);
	}
	
	/* list all users */
	private void listFriends(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the list of users from database */
		List<User> friendList = friendDAO.getFriendList(sessionUserId);		
		
		/* list all users in the browser */
		listUsers(request, response, friendList);
	}
	
	/* list users */
	private void listUsers(HttpServletRequest request, HttpServletResponse response, List<User> userList) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* show a message on top of the table */
		String message;
		String color;
		if (userList == null || userList.isEmpty())
		{
			message = "no users to show!";
			color = "red";
		}
		else
		{
			message = "List of users:";
			color = "green";
		}
		
		if (userList.isEmpty())
		{
			userList = null;
		}
		
		/* show the list of users to root user */
		request.setAttribute("userList", userList);
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("gender", user.getGender());
		request.setAttribute("userName", user.getUserName());
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserAccount.jsp");
        dispatcher.forward(request, response);
	}
	
	/* delete a user */
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the userId from the request */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* delete the user if it's not the root */
		if (userId != 1)
		{
			userDAO.deleteUser(userId);
		}
		
		/* list all users in the browser */
		listAllUsers(request, response);
	}
	
	/* go to User edit form */
	private void goToUserEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the userId of the user */
		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = new User();
		
		if (sessionUserId == 1) /* if it's the root user trying to modify */
		{
			/* get the user information based on the userId */
			user = userDAO.getUser(userId);
		}
		else /* the current logged in user is trying to modify its profile */
		{			
			/* get the user information based on the userId */
			user = userDAO.getUser(sessionUserId);
		}
			
		/* determine form variables */
		String formAction = new String("updateUser");
		String formText = new String("Please modify your account information:");
		String buttonText = new String("save");
		
		/* fill the form values of the Resigtration.jsp with the user info */
		request.setAttribute("user", user);
		request.setAttribute("confirmPassword", user.getPassword());
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		
		/* send the updated Registration.jsp to the browser */
        RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
        dispatcher.forward(request, response);
	}
	
	/* update user information */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the userId of the user */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* if it's the current user trying to edit his profile or it's the root user */
		if (sessionUserId == userId || sessionUserId == 1)
		{
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String ageStr = request.getParameter("age");
			int age = 0;
			if (!ageStr.isEmpty()) /* make sure ageStr is not empty */
			{
				age = Integer.parseInt(ageStr);
			}
			User user = new User(userId, userName, password, firstName, lastName, email, gender, age);
			
			if (!password.isEmpty() && password.equals(confirmPassword)) /* successful match between password and its confirmation */
			{
				/* update the user information in the database */
				userDAO.updateUser(user);
				
				if (sessionUserId == 1) /* if it's the root user */
				{
					/* list all users in the browser */
					listAllUsers(request, response);
				}
				else
				{
					listAllJokes(request, response);
				}				
			}
			else
			{
				/* show a message indicating successful logout */
				String message = "Password mismatch!";
				String color = "red";
				request.setAttribute("message", message);
				request.setAttribute("color", color);
				
				/* determine form variables */
				user.password = "";
				request.setAttribute("user", user);
				String formAction = new String("updateUser");
				String formText = new String("Please modify your account information:");
				String buttonText = new String("save");
				
				/* fill the form values of the Resigtration.jsp with the user info */
				request.setAttribute("formAction", formAction);
				request.setAttribute("formText", formText);
				request.setAttribute("buttonText", buttonText);
				
				/* go to the registration page */
				RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
				dispatcher.forward(request, response);
			}
		}
		else /* if somebody is trying to modify somebody's else profile */
		{
			/* go to the registration page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("logoutUser");
			dispatcher.forward(request, response);
		}
	}
	
	/* go to joke posting form */
	private void goToJokePostForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* determine form variables */
		String formAction = new String("postJoke");
		String formText = new String("Please insert your joke:");
		String buttonText = new String("post");
		String gender = user.getGender();
		
		/* fill the form values of the JokePost.jsp with the user info */
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		request.setAttribute("gender", gender);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("JokePost.jsp");
        dispatcher.forward(request, response);
	}
	
	/* insert a joke into Joke table */
	private void postJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String tags = request.getParameter("tags");
		Date date = Date.valueOf(LocalDate.now());
		
		/* create a user instance with the provided data and insert it into database */
		Joke joke = new Joke(title, description, date, sessionUserId);
		int jokeId = jokeDAO.insertJoke(joke);
		
		/* split the comma separated tags and insert them into database */
		String tag[] = tags.split("\\s*,\\s*");
		for (int i = 0; i < tag.length; i++)
		{
			jokeTagDAO.insertJokeTag(new JokeTag(jokeId, tag[i]));
		}

		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* list all jokes */
	private void listAllJokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		List<Joke> jokeList = jokeDAO.getJokeList();
		
		/* list the jokes in the browser */
		listJokes(request, response, jokeList);
	}
	
	/* list user's jokes */
	private void listUserJokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the user's userId */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		List<Joke> jokeList = jokeDAO.getUserJokes(userId);
		
		/* list the jokes in the browser */
		listJokes(request, response, jokeList);
	}
	
	/* list favorite jokes */
	private void listFavoritejokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get user's favorite jokes */
		List<Joke> jokeList = favoriteJokeDAO.getFavJokes(sessionUserId);
		
		/* list the jokes in the browser */
		listJokes(request, response, jokeList);
	}
	
	/* list jokes */
	private void listJokes(HttpServletRequest request, HttpServletResponse response, List<Joke> jokeList) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the list of user's favorite joke from database */
		List<Joke> favoriteJokeList = favoriteJokeDAO.getFavoriteJokeList(sessionUserId);
		if (favoriteJokeList.isEmpty())
		{
			favoriteJokeList = null;
		}
		
		/* get the list of user's friends from database */
		List<User> friendList = friendDAO.getFriendList(sessionUserId);
		if (friendList.isEmpty())
		{
			friendList = null;
		}
		
		if (jokeList.isEmpty())
		{
			jokeList = null;
		}
		
		/* get the list of joke reviews from database */
		List<JokeReview> jokeReviewList = jokeReviewDAO.getJokeReviewList();
		if (jokeReviewList.isEmpty())
		{
			jokeReviewList = null;
		}
		
		/* show a message on top of the table */
		String message;
		String color;
		if (jokeList == null)
		{
			message = "no jokes to show!";
			color = "red";
		}
		else
		{
			message = "list of jokes:";
			color = "green";
		}

		/* show the list of user's jokes */
		String gender = user.getGender();
		request.setAttribute("user", user);
		request.setAttribute("sessionUserId", sessionUserId);
		request.setAttribute("userDAO", userDAO);
		request.setAttribute("jokeList", jokeList);
		request.setAttribute("favoriteJokeList", favoriteJokeList);
		request.setAttribute("friendList", friendList);
		request.setAttribute("jokeReviewList", jokeReviewList);
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("gender", gender);
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserAccount.jsp");
        dispatcher.forward(request, response);
	}
	
	/* delete a joke */
	private void deleteJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the jokeId and its owner userId from the request */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int jokeUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* check if the session's user is the owner of the joke or it's the root user */
		if (sessionUserId == 1 || sessionUserId == jokeUserId)
		{
			jokeDAO.deleteJoke(jokeId);
		}
		
		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* go to joke form */
	private void goToJokeEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the jokeId and its owner userId from the request */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int jokeUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* check if the user is the owner of the joke */
		if (sessionUserId == 1 || sessionUserId == jokeUserId)
		{
			/* get the user information based on the userId */
			Joke joke = jokeDAO.getJoke(jokeId);
			
			/* determine form variables */
			String formAction = new String("updateJoke");
			String formText = new String("Please modify your joke:");
			String buttonText = new String("save");
			String gender = user.getGender();
			
			/* fill the form values of the JokePost.jsp with the user info */
			request.setAttribute("joke", joke);
			request.setAttribute("formAction", formAction);
			request.setAttribute("formText", formText);
			request.setAttribute("buttonText", buttonText);
			request.setAttribute("gender", gender);
			
			/* send the updated JokePost.jsp to the browser */
	        RequestDispatcher dispatcher = request.getRequestDispatcher("JokePost.jsp");
	        dispatcher.forward(request, response);
		}
		else /* if it's not the joke owner, do nothing */
		{
			/* list the jokes in the browser */
			listAllJokes(request, response);
		}
	}
	
	/* update a joke in Joke table */
	private void updateJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Date date = Date.valueOf(LocalDate.now());
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* create a user instance with the provided data and insert it into database */
		Joke joke = new Joke(jokeId, title, description, date, userId);
		
		/* check if the user is the owner of the joke */
		if (sessionUserId == 1 || sessionUserId == userId)
		{
			jokeDAO.updateJoke(joke);
		}

		/* list the users in the browser */
		listAllJokes(request, response);
	}
	
	/* add the joke to list of the users favorite jokes */
	private void addToFavoriteJokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the jokeId from the form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		
		/* add the joke to the user's favorite joke list */
		favoriteJokeDAO.insertFavoriteJoke(new FavoriteJoke(jokeId, sessionUserId));
		
		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* remove the joke from list of the users favorite jokes */
	private void removeFromFavoriteJokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the jokeId from the form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		
		/* add the joke to the user's favorite joke list */
		favoriteJokeDAO.deleteFavoriteJoke(new FavoriteJoke(jokeId, sessionUserId));
		
		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* add the user to list of the users friends */
	private void addToFriends(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the friend userId from the form */
		int friendUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* make sure sessionUserId is not equal to friendUserId */
		if (sessionUserId != friendUserId)
		{
			/* add the joke to the user's favorite joke list */
			friendDAO.insertFriend(new Friend(sessionUserId, friendUserId));
		}
		
		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* remove the user from list of the users friends */
	private void removeFromFriends(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the friend userId from the form */
		int friendUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* make sure sessionUserId is not equal to friendUserId */
		if (sessionUserId != friendUserId)
		{
			/* add the joke to the user's favorite joke list */
			friendDAO.deleteFriend(new Friend(sessionUserId, friendUserId));
		}
		
		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* remove the user from list of the users friends */
	private void searchJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* create a joke list */
		List<Joke> jokeList = new ArrayList<Joke>();
		
		/* get the tag from the form */
		String tag = request.getParameter("searchTag");
		
		/* find all the jokes with the specified tag */
		jokeList = jokeDAO.getJokeTagList(tag);
		
		if (!tag.isBlank())
		{
			/* show the list of found jokes */
			request.setAttribute("searchTag", tag);
			
			/* list the jokes in the browser */
			listJokes(request, response, jokeList);
		}
		else
		{
			/* list all jokes in the browser */
			listAllJokes(request, response);
		}
	}
	
	/* go to review form to add a review */
	private void goToReviewForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the score from request */
		int score = 1;
		String scoreStr = request.getParameter("scoreStr");
		if (scoreStr == null)
		{
			scoreStr = "poor";
		}
		
		switch (scoreStr) 
		{
        	case "poor":
        		score = 1;
        		break;
        	case "fair":
        		score = 2;
        		break;
        	case "good":
        		score = 3;
        		break;
        	case "excellent":
        		score = 4;
        		break;
		}
		
		/* determine form variables */
		String formAction = new String("submitReview");
		String formText = new String("Please Review the joke:");
		String buttonText = new String("review");
		String gender = user.getGender();
		
		/* get the joke */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		Joke joke = jokeDAO.getJoke(jokeId);
		
		/* fill the form values of the Review.jsp with the user info */
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		request.setAttribute("gender", gender);
		request.setAttribute("score", score);
		request.setAttribute("scoreStr", scoreStr);
		request.setAttribute("joke", joke);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Review.jsp");
        dispatcher.forward(request, response);
	}
	
	/* submit a review */
	private void submitReview(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{	
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		String remarks = request.getParameter("remarks");
		String score = request.getParameter("scoreStr");
		Date date = Date.valueOf(LocalDate.now());
		
		/* create a user instance with the provided data and insert it into database */
		JokeReview jokeReview = new JokeReview(jokeId, sessionUserId, score, remarks, date);
		jokeReviewDAO.insertJokeReview(jokeReview);

		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* go to review form to add a review */
	private void editReview(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the jokeId & userId from the request */
		int userId = Integer.parseInt(request.getParameter("userId"));
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		String scoreStr = request.getParameter("scoreStr");
		
		JokeReview jokeReview = new JokeReview();
		if (sessionUserId == 1 || sessionUserId == userId)
		{
			/* get the review from database */
			jokeReview = jokeReviewDAO.getJokeReview(userId, jokeId);
		}
		
		/* get the score from request */
		int score = 1;
		if (scoreStr == null || scoreStr.isEmpty())
		{
			scoreStr = jokeReview.getreviewScore();
		}
		
		switch (scoreStr) 
		{
        	case "poor":
        		score = 1;
        		break;
        	case "fair":
        		score = 2;
        		break;
        	case "good":
        		score = 3;
        		break;
        	case "excellent":
        		score = 4;
        		break;
		}
		
		/* determine form variables */
		String formAction = new String("updateReview");
		String formText = new String("Please modify your joke review:");
		String buttonText = new String("save");
		String gender = user.getGender();
		
		/* fill the form values of the Review.jsp with the user info */
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		request.setAttribute("gender", gender);
		request.setAttribute("score", score);
		request.setAttribute("scoreStr", scoreStr);
		request.setAttribute("jokeReview", jokeReview);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Review.jsp");
        dispatcher.forward(request, response);
	}
	
	/* update the review in the database */
	private void updateReview(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		String remarks = request.getParameter("remarks");
		String score = request.getParameter("scoreStr");
		Date date = Date.valueOf(LocalDate.now());
		
		if (sessionUserId == 1 || sessionUserId == userId)
		{
			/* create a user instance with the provided data and insert it into database */
			JokeReview jokeReview = new JokeReview(jokeId, userId, score, remarks, date);
			jokeReviewDAO.updateJokeReview(jokeReview);
		}

		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* remove the review from the database */
	private void removeReview(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int userId = Integer.parseInt(request.getParameter("userId"));

		if (sessionUserId == 1 || sessionUserId == userId)
		{
			/* delete the joke review from the database */
			jokeReviewDAO.deleteJokeReview(userId, jokeId);
		}

		/* list the jokes in the browser */
		listAllJokes(request, response);
	}
	
	/* remove the review from the database */
	private void goToLastPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		String lastPage = session.getAttribute("lastPage").toString();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(lastPage);
        dispatcher.forward(request, response);
	}
	
	/* go to Queries.jsp */
	private void goToQueries(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the list of users from database */
		List<User> dropDownUserList = userDAO.getUserList();
		
		/* set the page attributes */
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("gender", user.getGender());
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("dropDownUserList", dropDownUserList);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("Queries.jsp");
        dispatcher.forward(request, response);
	}
	
	/* go to Queries.jsp */
	private void runQuery(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the list of users from database */
		List<User> dropDownUserList = userDAO.getUserList();
		
		/* get userName and password from Login Form */
		String query = request.getParameter("Queries");
		String tagX = request.getParameter("tagX");
		String tagY = request.getParameter("tagY");
		String userIdXStr = request.getParameter("UserX");
		int userIdX = Integer.parseInt(userIdXStr);
		String userIdYStr = request.getParameter("UserY");
		int userIdY = Integer.parseInt(userIdYStr);
		
		List<User> userList = new ArrayList<User>();
		List<User> userList2 = new ArrayList<User>();
		List<Joke> jokeList = new ArrayList<Joke>();
		
		switch (query)
		{
			case "Query2":
				userList = userJokeTagDAO.getQueryResult(tagX, tagY);
				break;
			case "Query4":
				jokeList = userJokeReviewDAO.getQueryResult(userIdX);
				break;
			case "Query5":
				userList = userJokeNumSince03012018DAO.getQueryResult();
				break;
			case "Query6":
				userList = userFriendDAO.getQueryResult(userIdX, userIdY);
				break;
			case "Query7":
				userList = userJokeExcellentReviewDAO.getQueryResult();
				break;
			case "Query8":
				userList = userReviewScoreDAO.getQueryResult();
				break;
			case "Query9":
				userList = userReviewScoreDAO.getQuery2Result();
				break;
			case "Query10":
				userList = userJokeReviewDAO.getQuery2Result();
				break;
			case "Query11":
				jokeReviewerDAO.getQueryResult();
				userList = jokeReviewerDAO.getUserList1();
				userList2 = jokeReviewerDAO.getUserList2();
				break;
			default:
				break;
		}
		
		/* show a message on top of the table */
		String message = "";
		String color = "";
		if (userList.isEmpty()) {userList = null;}
		if (userList2.isEmpty()) {userList2 = null;}
		if (jokeList.isEmpty()) {jokeList = null;}
		if (query.equals("Query4"))
		{
			if (jokeList == null)
			{
				message = "no jokes to show!";
				color = "red";
			}
			else
			{
				message = "list of jokes:";
				color = "green";
			}
		}
		else
		{
			if (userList == null)
			{
				message = "no users to show!";
				color = "red";
			}
			else
			{
				message = "list of users:";
				color = "green";
			}
		}
		
		/* set the page attributes */
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("gender", user.getGender());
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("dropDownUserList", dropDownUserList);
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		request.setAttribute("userList", userList);
		request.setAttribute("userList2", userList2);
		request.setAttribute("jokeList", jokeList);
		request.setAttribute(query, "selected=selected");
		request.setAttribute("selectedUserX", userIdXStr);
		request.setAttribute("selectedUserY", userIdYStr);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("Queries.jsp");
        dispatcher.forward(request, response);
	}
}
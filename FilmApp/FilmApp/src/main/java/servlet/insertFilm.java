package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FilmDAO;
import model.Film;

@WebServlet(
	    name = "insertFilm",
	    urlPatterns = {"/insertFilm"}
	)
public class insertFilm extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public insertFilm() {
		super();
	}
	// doPost() for creating a new film data in the database
	protected void doPost (HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		// Declare DAO instance
		FilmDAO dao = new FilmDAO();
		// Extract all params sent by the user
		int fId = Integer.parseInt(req.getParameter("id"));
		String fTitle = req.getParameter("title");
		int fYear = Integer.parseInt(req.getParameter("year"));
		String fDirector = req.getParameter("director");
		String fStars = req.getParameter("stars");
		String fReview = req.getParameter("review");
		Film newF = new Film(fId, fTitle, fYear, fDirector, fStars, fReview);
		
		int result = dao.insertFilm(newF);
		
		System.out.println(result);
	}
}

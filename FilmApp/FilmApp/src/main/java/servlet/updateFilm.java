package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FilmDAO;
import model.Film;


@WebServlet(
	    name = "updateFilm",
	    urlPatterns = {"/updateFilm"}
	)
public class updateFilm extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	public updateFilm() {
		super();
	}
	// doPut() is used for updating the data
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// Declare new DAO instance
		FilmDAO dao = new FilmDAO();
		// Extract all param data from the URL
		int fId = Integer.parseInt(req.getParameter("id"));
		String fTitle = req.getParameter("title");
		int fYear = Integer.parseInt(req.getParameter("year"));
		String fDirector = req.getParameter("director");
		String fStars = req.getParameter("stars");
		String fReview = req.getParameter("review");
		Film newF = new Film(fId, fTitle, fYear, fDirector, fStars, fReview);
		int result = dao.updateFilm(newF);
		System.out.println(result);
		
	}
}

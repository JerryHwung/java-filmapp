package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FilmDAO;

@WebServlet(
	    name = "DeleteFilm",
	    urlPatterns = {"/deleteFilm"}
	)
public class deleteFilm extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public deleteFilm() {
		super();
	}
	// Using doDelete() for doing the delete
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// Declare film DAO
		FilmDAO dao = new FilmDAO();
		// Extract id from URL and parse the string as integer
		int id = Integer.parseInt(req.getParameter("id"));
		int result = dao.deleteFilm(id);
		System.out.println(result);
		
	}
}

package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FilmDAO;
import model.Film;


@WebServlet(
	    name = "getFilms",
	    urlPatterns = {"/getFilm"}
	)
public class getFilm extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public getFilm() {
		super();
	}
	// Used doGet() for GET request
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		// Declare instances
		FilmDAO dao = new FilmDAO();
		String title = req.getParameter("title");
		ArrayList<Film> films = dao.getFilm(title);
		
		req.setAttribute("films", films);
		// Extract data about format from URL
		String format = req.getParameter("format");
	    String outputPage;
	    if ("xml".equals(format)) {
	    	// If user chose XML
	      resp.setContentType("text/xml");
	      outputPage = "/WEB-INF/results/films-xml.jsp";
	    } else if ("json".equals(format)) {
	    	// If user chose JSON
	      resp.setContentType("text/javascript");
	      outputPage = "/WEB-INF/results/films-json.jsp";
	    } else if ("text".equals(format)){
	    	// If user chose plain text/ String
		      resp.setContentType("text/plain");
		      outputPage = "/WEB-INF/results/films-string.jsp";
	    } else {
	    	// JSON is chosen as default when user did not chose any format
		    	resp.setContentType("text/javascript");
		    	outputPage = "/WEB-INF/results/films-json.jsp";
	    }
	    RequestDispatcher dispatcher =
	      req.getRequestDispatcher(outputPage);
	    dispatcher.include(req, resp);
	}
}

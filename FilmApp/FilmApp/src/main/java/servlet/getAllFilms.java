package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
	    name = "getAllFilms",
	    urlPatterns = {"/getAllFilms"}
	)
public class getAllFilms extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public getAllFilms() {
		super();
	}
	// Use doGet() for retrieving all films from database
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		FilmDAO dao = new FilmDAO();
		// Create an arrayList for results
		ArrayList<Film> films = dao.getAllFilms();
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
	    	// JSON is default if no format is chosen
	    	resp.setContentType("text/javascript");
	    	outputPage = "/WEB-INF/results/films-json.jsp";
	    }
	    RequestDispatcher dispatcher = 
	      req.getRequestDispatcher(outputPage);
	    dispatcher.include(req, resp); 
	}
}

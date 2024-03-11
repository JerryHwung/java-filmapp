package DAO;

import java.sql.*;
import java.util.ArrayList;

import model.Film;

public class FilmDAO {
	// Declare variables
	Film myFilm = null;
	Connection conn = null;
	Statement stmt = null;
	String user = "hwungh";
    String password = "Klapverj3";
    String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;
	
	public FilmDAO() {}
	// Create cnnection to database
	private void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) {System.out.println(e);}
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch(SQLException se) {System.out.println(se);}
	}
	private void closeConnection() {
		try {
			conn.close();
		} catch(SQLException e){e.printStackTrace();}
	}
	
	private Film getNextFilm(ResultSet rs) {
		Film thisFilm = null;
		try {
			thisFilm = new Film(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getInt("year"),
				rs.getString("director"),
				rs.getString("stars"),
				rs.getString("review"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return thisFilm;
	}
	// Retrieve all films
	public ArrayList<Film> getAllFilms(){
		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();
		
		try {
			String sql = "select * from films";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while(rs.next()) {
				myFilm = getNextFilm(rs);
				allFilms.add(myFilm);
			}
			stmt.close();
			closeConnection();
		} catch(SQLException e) {e.printStackTrace();}
		return allFilms;
	}
	// Retrieve film by ID
	public Film getFilmByID(int id){
		   
		openConnection();
		myFilm=null;
	    // Create select statement and execute it
		try{
		    String sql = "select * from films where id="+id;
		    ResultSet rs = stmt.executeQuery(sql);
		    System.out.println(sql);
	    // Retrieve the results
		    while(rs.next()){
		    	myFilm = getNextFilm(rs);
		    }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return myFilm;
   }

	// Returns an array list of films with matching name(or Substring)
	public ArrayList<Film> getFilm(String title) {
		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();
		
		try {
			String sql = "select * from films where title like '%"+title+"%';";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while(rs.next()) {
				myFilm = getNextFilm(rs);
				allFilms.add(myFilm);
			}
			stmt.close();
			closeConnection();
		} catch(SQLException e) {e.printStackTrace();}
		return allFilms;
	}
	// Method for insert
	public int insertFilm(Film f) {
		openConnection();
		try {
			String sql = "insert into films (id,title,year,director,stars,review)"+
						 "values (?,?,?,?,?,?)";
			// Create a prepared statement for a cleaner query above
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, f.getId());
			statement.setString(2, f.getTitle());
			statement.setInt(3, f.getYear());
			statement.setString(4, f.getDirector());
			statement.setString(5, f.getStars());
			statement.setString(6, f.getReview());
			statement.executeUpdate();
			System.out.println(sql);
			// 1 will be printed out if query execute successfully
			return 1;
			} catch(SQLException e) {
			e.printStackTrace();
			// 0 is returned when query is does not execute successfully
			return 0;
		}
	}
	// Method for update
	public int updateFilm(Film f) {
		openConnection();
		try {
			String sql = "update films set title=?, year=?, director=?, stars=?, review=? where id=?";
			// Declare a prepared statement here
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, f.getTitle());
			statement.setInt(2, f.getYear());
			statement.setString(3, f.getDirector());
			statement.setString(4, f.getStars());
			statement.setString(5, f.getReview());
			statement.setInt(6, f.getId());
			statement.executeUpdate();
			System.out.println(sql);
			return 1;
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	// Method to delete
	public int deleteFilm(int id) {
		openConnection();
		try {
			String sql = "delete from films where id = "+id;
			stmt.executeUpdate(sql);
			System.out.println(sql);
			return 1;
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}

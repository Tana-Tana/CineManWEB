package dao;

import model.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO extends DAO{

    public MovieDAO() {
        super();
    }

    public List<Movie> getListMovie()
    {
        List<Movie> listMovie = new ArrayList<>();
        String sql = "SELECT * FROM tblmovie";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = getConnection().prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");
                Movie movie = new Movie(id, name, genre, duration);
                listMovie.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Khong lay duoc list movie");
            throw new RuntimeException(e);
        }

        return listMovie;
    }

    public List<Movie> searchMovieByName(String searchTerm) {
        List<Movie> listMovie = new ArrayList<>();
        String sql = "SELECT * FROM tblmovie WHERE LOWER(name) LIKE ?";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = getConnection().prepareStatement(sql);
            pstm.setString(1, "%" + searchTerm.toLowerCase() + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");
                Movie movie = new Movie(id, name, genre, duration);
                listMovie.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Khong tim kiem duoc movie");
            throw new RuntimeException(e);
        }

        return listMovie;
    }
}

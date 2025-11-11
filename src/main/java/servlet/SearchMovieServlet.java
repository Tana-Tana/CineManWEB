package servlet;

import dao.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchMovieServlet", urlPatterns = "/search-movie")
public class SearchMovieServlet extends HttpServlet {
    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        movieDAO = new MovieDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTerm = req.getParameter("searchTerm");
        
        List<Movie> listMovie;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            listMovie = movieDAO.getListMovie();
        } else {
            listMovie = movieDAO.searchMovieByName(searchTerm.trim());
        }
        
        req.setAttribute("listMovie", listMovie);
        req.getRequestDispatcher("/JSP/MovieTicketSaleFrame.jsp").forward(req, resp);
    }
}


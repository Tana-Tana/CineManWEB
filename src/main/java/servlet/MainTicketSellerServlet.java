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

@WebServlet(name = "MainTicketSellerServlet", urlPatterns = "/main-ticketSeller")
public class MainTicketSellerServlet extends HttpServlet {
    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        movieDAO = new MovieDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/JSP/MainTicketSellerFrame.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Movie> listMovie = movieDAO.getListMovie();
        req.setAttribute("listMovie", listMovie);
        req.getSession().setAttribute("listMovie", listMovie);
        req.getRequestDispatcher("/JSP/MovieTicketSaleFrame.jsp").forward(req,resp);
    }
}

package servlet;

import dao.ScreeningRoomScheduleDAO;
import dao.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import model.ScreeningRoomSchedule;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MovieListServlet", urlPatterns = "/movie-list")
public class MovieListServlet extends HttpServlet {

    ScreeningRoomScheduleDAO screeningRoomScheduleDAO;

    @Override
    public void init() throws ServletException {
        screeningRoomScheduleDAO = new ScreeningRoomScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/JSP/MovieTicketSaleFrame.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String movieIdStr = req.getParameter("movieId");
        String movieName = req.getParameter("movieName");
        String movieGenre = req.getParameter("movieGenre");
        String movieDurationStr = req.getParameter("movieDuration");
        
        int movieId = Integer.parseInt(movieIdStr);;
        int movieDuration = Integer.parseInt(movieDurationStr);

        Movie movie = new Movie(movieId, movieName, movieGenre, movieDuration);
        req.setAttribute("movie", movie);
        List<ScreeningRoomSchedule> listScreeningRoomSchedule = screeningRoomScheduleDAO.getListScreeningRoomScheduleByMovie(movie);
        req.setAttribute("listScreeningRoomSchedule", listScreeningRoomSchedule);
        
        // Lưu vào session để có thể quay lại
        req.getSession().setAttribute("movie", movie);
        req.getSession().setAttribute("listScreeningRoomSchedule", listScreeningRoomSchedule);

        req.getRequestDispatcher("/JSP/ScreeningScheduleFrame.jsp").forward(req, resp);
    }
}

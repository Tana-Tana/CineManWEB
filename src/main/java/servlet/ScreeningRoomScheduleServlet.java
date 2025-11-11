package servlet;

import dao.ScreeningRoomScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ScreeningRoomSchedule;

import java.io.IOException;

@WebServlet(name = "ScreeningRoomScheduleServlet", urlPatterns = "/screening-room-schedule")
public class ScreeningRoomScheduleServlet extends HttpServlet {

    private ScreeningRoomScheduleDAO screeningRoomScheduleDAO;

    @Override
    public void init() throws ServletException {
        screeningRoomScheduleDAO = new ScreeningRoomScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("movie") != null) {
            req.setAttribute("movie", req.getSession().getAttribute("movie"));
        }
        if (req.getSession().getAttribute("listScreeningRoomSchedule") != null) {
            req.setAttribute("listScreeningRoomSchedule", req.getSession().getAttribute("listScreeningRoomSchedule"));
        }
        req.getRequestDispatcher("/JSP/ScreeningScheduleFrame.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String screeningRoomScheduleIdStr = req.getParameter("screeningRoomScheduleId");

        int screeningRoomScheduleId = Integer.parseInt(screeningRoomScheduleIdStr);
        ScreeningRoomSchedule screeningRoomSchedule = screeningRoomScheduleDAO.getScreeningRoomScheduleById(screeningRoomScheduleId);

        req.setAttribute("screeningRoomSchedule", screeningRoomSchedule);
        req.getRequestDispatcher("/JSP/MovieTicketFrame.jsp").forward(req, resp);
    }
}


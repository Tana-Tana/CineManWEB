package servlet;

import dao.CustomerDAO;
import dao.SeatDAO;
import dao.ScreeningRoomScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import model.Seat;
import model.ScreeningRoomSchedule;
import model.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookTicketServlet", urlPatterns = "/book-ticket")
public class BookTicketServlet extends HttpServlet {

    private SeatDAO seatDAO;
    private ScreeningRoomScheduleDAO screeningRoomScheduleDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        seatDAO = new SeatDAO();
        screeningRoomScheduleDAO = new ScreeningRoomScheduleDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String screeningRoomScheduleIdStr = req.getParameter("screeningRoomScheduleId");
        
        if (screeningRoomScheduleIdStr == null || screeningRoomScheduleIdStr.trim().isEmpty()) {
            Object sessionSrsId = req.getSession().getAttribute("screeningRoomScheduleId");
            if (sessionSrsId != null) {
                screeningRoomScheduleIdStr = sessionSrsId.toString();
            }
        }
        
        if (screeningRoomScheduleIdStr != null && !screeningRoomScheduleIdStr.trim().isEmpty()) {
            try {
                int screeningRoomScheduleId = Integer.parseInt(screeningRoomScheduleIdStr.trim());
                ScreeningRoomSchedule screeningRoomSchedule = screeningRoomScheduleDAO.getScreeningRoomScheduleById(screeningRoomScheduleId);
                
                if (screeningRoomSchedule != null) {
                    req.setAttribute("screeningRoomSchedule", screeningRoomSchedule);
                    req.removeAttribute("success");
                    req.removeAttribute("error");
                    
                    List<Integer> selectedSeatIds = (List<Integer>) req.getSession().getAttribute("selectedSeatIds");
                    if (selectedSeatIds != null && !selectedSeatIds.isEmpty()) {
                        req.setAttribute("selectedSeatIds", selectedSeatIds);
                    }
                    
                    String memberCard = (String) req.getSession().getAttribute("memberCard");
                    if (memberCard != null && !memberCard.trim().isEmpty()) {
                        req.setAttribute("memberCard", memberCard);
                    }
                } else {
                    req.setAttribute("error", "Không tìm thấy thông tin lịch chiếu.");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Mã lịch chiếu không hợp lệ.");
            }
        } else {
            req.setAttribute("error", "Không có thông tin lịch chiếu.");
        }
        
        req.getRequestDispatcher("/JSP/MovieTicketFrame.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seatNumberStr = req.getParameter("seatNumber");
        String memberCard = req.getParameter("memberCard");
        String screeningRoomScheduleIdStr = req.getParameter("screeningRoomScheduleId");

        if (seatNumberStr == null || seatNumberStr.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng chọn ghế ngồi");
            String srsId = req.getParameter("screeningRoomScheduleId");
            if (srsId != null && !srsId.trim().isEmpty()) {
                ScreeningRoomSchedule srs = screeningRoomScheduleDAO.getScreeningRoomScheduleById(Integer.parseInt(srsId));
                req.setAttribute("screeningRoomSchedule", srs);
            }
            req.getRequestDispatcher("/JSP/MovieTicketFrame.jsp").forward(req, resp);
            return;
        }

        int screeningRoomScheduleId = Integer.parseInt(screeningRoomScheduleIdStr);

        String[] seatNumberArray = seatNumberStr.split(",");
        List<Integer> seatIds = new ArrayList<>();
        
        for (String seatNum : seatNumberArray) {
            int seatId = Integer.parseInt(seatNum.trim());
            seatIds.add(seatId);
        }

        ScreeningRoomSchedule screeningRoomSchedule = screeningRoomScheduleDAO.getScreeningRoomScheduleById(screeningRoomScheduleId);
        
        if (screeningRoomSchedule == null) {
            req.setAttribute("error", "Không tìm thấy thông tin lịch chiếu.");
            req.getRequestDispatcher("/JSP/MovieTicketFrame.jsp").forward(req, resp);
            return;
        }
        
        List<Ticket> tickets = new ArrayList<>();
        for (int seatId : seatIds) {
            Seat seat = seatDAO.getSeatById(seatId);
            if (seat != null) {
                if (seat.isBooked()) {
                    req.setAttribute("error", "Ghế số " + seatId + " đã được đặt. Vui lòng chọn ghế khác.");
                    req.setAttribute("screeningRoomSchedule", screeningRoomSchedule);
                    req.getRequestDispatcher("/JSP/MovieTicketFrame.jsp").forward(req, resp);
                    return;
                }
                Ticket ticket = new Ticket(screeningRoomSchedule, seat);
                tickets.add(ticket);
            }
        }
        
        if (tickets.isEmpty()) {
            req.setAttribute("error", "Không thể tạo vé. Vui lòng thử lại.");
            req.setAttribute("screeningRoomSchedule", screeningRoomSchedule);
            req.getRequestDispatcher("/JSP/MovieTicketFrame.jsp").forward(req, resp);
            return;
        }
        
        Customer customer = null;
        if (memberCard != null && !memberCard.trim().isEmpty()) {
            customer = customerDAO.getCustomerByCardCode(memberCard.trim());
        }
        
        req.getSession().setAttribute("screeningRoomScheduleId", String.valueOf(screeningRoomScheduleId));
        req.getSession().setAttribute("tickets", tickets);
        req.getSession().setAttribute("screeningRoomSchedule", screeningRoomSchedule);
        if (customer != null) {
            req.getSession().setAttribute("customer", customer);
        } else {
            req.getSession().removeAttribute("customer");
        }
        if (memberCard != null && !memberCard.trim().isEmpty()) {
            req.getSession().setAttribute("memberCard", memberCard.trim());
        } else {
            req.getSession().removeAttribute("memberCard");
        }
        req.getSession().setAttribute("selectedSeatIds", seatIds);
        
        req.setAttribute("screeningRoomSchedule", screeningRoomSchedule);
        req.setAttribute("tickets", tickets);
        req.setAttribute("customer", customer);
        
        req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
    }
}


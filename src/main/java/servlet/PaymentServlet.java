package servlet;

import dao.BillDAO;
import dao.SeatDAO;
import dao.TicketDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bill;
import model.Customer;
import model.Member;
import model.Ticket;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymentServlet", urlPatterns = "/payment")
public class PaymentServlet extends HttpServlet {
    
    private TicketDAO ticketDAO;
    private BillDAO billDAO;
    private SeatDAO seatDAO;

    @Override
    public void init() throws ServletException {
        ticketDAO = new TicketDAO();
        billDAO = new BillDAO();
        seatDAO = new SeatDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Object ticketsObj = req.getSession().getAttribute("tickets");
            Object ticketSellerObj = req.getSession().getAttribute("ticketSeller");
            
            if (ticketsObj == null || !(ticketsObj instanceof List)) {
                req.setAttribute("error", "Không tìm thấy thông tin vé.");
                req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
                return;
            }
            
            if (ticketSellerObj == null || !(ticketSellerObj instanceof Member)) {
                req.setAttribute("error", "Không tìm thấy thông tin nhân viên bán vé.");
                req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
                return;
            }
            
            @SuppressWarnings("unchecked")
            List<Ticket> tickets = (List<Ticket>) ticketsObj;
            Member member = (Member) ticketSellerObj;
        
        if (tickets.isEmpty()) {
            req.setAttribute("error", "Danh sách vé trống.");
            req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
            return;
        }
        
        float totalPayment = 0;
        for (Ticket ticket : tickets) {
            totalPayment += ticket.getSeat().getCost();
        }
        
        Bill bill = new Bill(totalPayment, member.getId(), tickets);
        
        int billId = billDAO.insertBill(bill);
        
        if (billId <= 0) {
            req.setAttribute("error", "Không thể tạo hóa đơn. Vui lòng thử lại.");
            req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
            return;
        }
        
        Integer memberId = null;
        Object customerObj = req.getSession().getAttribute("customer");
        if (customerObj != null && customerObj instanceof Customer) {
            Customer customer = (Customer) customerObj;
            memberId = customer.getId();
        }
        
        boolean success = ticketDAO.insertTickets(tickets, billId, memberId);
        
        if (success) {
            boolean allSeatsUpdated = true;
            for (Ticket ticket : tickets) {
                int seatId = ticket.getSeat().getId();
                boolean seatUpdated = seatDAO.updateSeatBookingStatus(seatId, true);
                if (!seatUpdated) {
                    allSeatsUpdated = false;
                }
            }
            
            req.getSession().setAttribute("paymentSuccess", true);
            req.getSession().setAttribute("bill", bill);
            req.getSession().removeAttribute("selectedSeatIds");
            req.getSession().removeAttribute("memberCard");
            req.getSession().removeAttribute("customer");
            
            req.getRequestDispatcher("/JSP/MainTicketSellerFrame.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Không thể lưu vé. Vui lòng thử lại.");
            req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
        }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            req.getRequestDispatcher("/JSP/PaymentFrame.jsp").forward(req, resp);
        }
    }
}



package servlet;

import dao.CustomerDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import model.Member;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("success") != null) {
            request.setAttribute("success", session.getAttribute("success"));
            session.removeAttribute("success");
        }
        request.getRequestDispatcher("/JSP/LoginFrame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        Member member = userDAO.checkLogin(username, pass);
        if (member != null) {
            if ("customer".equals(member.getRole())) {
                Customer customer = customerDAO.getCustomer(member);
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);

                request.getRequestDispatcher("/JSP/MainCustomerFrame.jsp").forward(request, response);
            } else if ("ticketSeller".equals(member.getRole())) {
                HttpSession session = request.getSession();
                session.setAttribute("ticketSeller", member);
                request.getRequestDispatcher("/JSP/MainTicketSellerFrame.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Vai trò không được hỗ trợ.");
                request.getRequestDispatcher("/JSP/LoginFrame.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
            request.getRequestDispatcher("/JSP/LoginFrame.jsp").forward(request, response);
        }
    }
}

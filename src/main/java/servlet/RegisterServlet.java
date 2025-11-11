package servlet;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/JSP/RegistFrame.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");
        String name = request.getParameter("name");
        String phone = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String dob = request.getParameter("birthOfDate");

        if (!pass.equals(confirmPass)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/JSP/RegistFrame.jsp").forward(request, response);
            return;
        }

        if (userDAO.checkUserExists(user)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("/JSP/RegistFrame.jsp").forward(request, response);
            return;
        }

        Customer customer = new Customer();
        customer.setUsername(user);
        customer.setPassword(pass);
        customer.setName(name);
        customer.setPhoneNumber(phone);
        customer.setEmail(email);
        customer.setBirthOfDate(dob);

        boolean success = userDAO.registerCustomer(customer);

        if (success) {
            request.setAttribute("success", "Đăng ký tài khoản thành công");
            request.getRequestDispatcher("/JSP/LoginFrame.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình đăng ký");
            request.getRequestDispatcher("/JSP/RegistFrame.jsp").forward(request, response);
        }
    }
}

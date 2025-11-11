package servlet;

import dao.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

import java.io.IOException;

@WebServlet(name = "RegisterMemberServlet", urlPatterns = "/register-membershipCard")
public class RegisterMemberServlet extends HttpServlet {

    private CustomerDAO customerDAO;

    @Override
    public void init(){
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Họ và tên không được để trống.");
            req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
            return;
        }

        if (name.trim().length() < 2) {
            req.setAttribute("error", "Họ và tên phải có ít nhất 2 ký tự.");
            req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
            return;
        }

        if (email != null && !email.trim().isEmpty() && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            req.setAttribute("error", "Định dạng email không hợp lệ.");
            req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
            return;
        }

        if (phoneNumber != null && !phoneNumber.trim().isEmpty() && !phoneNumber.matches("^[0-9]{10,11}$")) {
            req.setAttribute("error", "Số điện thoại phải là 10 hoặc 11 chữ số.");
            req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
            return;
        }

        Customer customer = (Customer) req.getSession().getAttribute("customer");
        /*if (customer == null) {
            req.setAttribute("error", "Vui lòng đăng nhập trước khi đăng ký thẻ thành viên.");
            req.getRequestDispatcher("/JSP/LoginFrame.jsp").forward(req, resp);
            return;
        }*/

        customer.setName(name.trim());
        customer.setEmail(email != null && !email.trim().isEmpty() ? email.trim() : null);
        customer.setPhoneNumber(phoneNumber != null && !phoneNumber.trim().isEmpty() ? phoneNumber.trim() : null);

        try {
            boolean updateSuccess = customerDAO.setCustomer(customer);

            if (updateSuccess) {
                req.getSession().setAttribute("customer", customer);
                req.setAttribute("success", "Đăng ký thẻ thành viên thành công!");
                req.getRequestDispatcher("/JSP/MainCustomerFrame.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Không thể cập nhật thông tin. Vui lòng thử lại.");
                req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Đã xảy ra lỗi máy chủ. Vui lòng thử lại sau.";

            String exceptionMsg = e.getMessage().toLowerCase();
            if (exceptionMsg.contains("duplicate entry") || exceptionMsg.contains("unique constraint failed")) {
                if (exceptionMsg.contains("email")) {
                    errorMessage = "Email này đã được sử dụng bởi một tài khoản khác.";
                } else if (exceptionMsg.contains("phonenumber")) {
                    errorMessage = "Số điện thoại này đã được sử dụng bởi một tài khoản khác.";
                } else {
                    errorMessage = "Email hoặc Số điện thoại đã tồn tại trong hệ thống.";
                }
            }
            req.setAttribute("error", errorMessage);
            req.getRequestDispatcher("/JSP/MemberRegistrationFrame.jsp").forward(req, resp);
        }
    }
}
package dao;

import model.Customer;
import model.Member;
import utils.JdbcConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends DAO{

    public UserDAO() {
        super();
    }

    public Member checkLogin(String username, String password) {
        String sql = "SELECT * FROM tblmember WHERE username = ? AND password = ?";
        Member member = null;

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setUsername(rs.getString("username"));
                    member.setName(rs.getString("name"));
                    member.setPhoneNumber(rs.getString("phoneNumber"));
                    member.setEmail(rs.getString("email"));
                    member.setRole(rs.getString("role"));
                }
            }
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi kiểm tra đăng nhập: " + e.getMessage());
        }
        return member;
    }

    public boolean registerCustomer(Customer customer) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            int memberId = -1;

            String sqlInsertMember = "INSERT INTO tblmember (username, password, name, phoneNumber, email, role) VALUES (?, ?, ?, ?, ?, ?)";
            ps = getConnection().prepareStatement(sqlInsertMember, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getName());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, customer.getEmail());
            ps.setString(6, "customer");

            int rowsAffectedMember = ps.executeUpdate();

            if (rowsAffectedMember == 0) {
                throw new SQLException("them member that bai");
            }

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                memberId = rs.getInt(1);
            } else {
                throw new SQLException("them member that bai nen kh lay duoc id");
            }

            String sqlInsertCustomer = "INSERT INTO tblcustomer (tblMemberId, birthOfDate) VALUES (?, ?)";
            ps = getConnection().prepareStatement(sqlInsertCustomer, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, memberId);
            
            if (customer.getBirthOfDate() != null && !customer.getBirthOfDate().trim().isEmpty()) {
                ps.setString(2, customer.getBirthOfDate());
            }

            int rowsAffectedCustomer = ps.executeUpdate();

            if (rowsAffectedCustomer == 0) {
                throw new SQLException("Them customer that bai.");
            }

            getConnection().commit();
            success = true;
            System.out.println("dang ky thanh cong: " + memberId);

        } catch (SQLException e) {
            if (getConnection() != null) {
                try {
                    getConnection().rollback();
                } catch (SQLException ex) {
                    System.err.println("loi rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        }

        return success;
    }

    public boolean checkUserExists(String username) {
        String sql = "SELECT 1 FROM tblmember WHERE username = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi kiểm tra username tồn tại: " + e.getMessage());
            return true;
        }
    }


}


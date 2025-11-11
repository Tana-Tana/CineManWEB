package dao;

import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillDAO extends DAO {
    
    public BillDAO() {
        super();
    }
    public int insertBill(Bill bill) {
        String sql = "INSERT INTO tblBill (totalPayment, tblTicketSellerId) VALUES (?, ?)";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            Connection conn = getConnection();
            
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setFloat(1, bill.getTotalPayment());
            pstm.setInt(2, bill.getTicketSellerId());
            
            int rowsAffected = pstm.executeUpdate();
            
            if (rowsAffected > 0) {
                rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    int billId = rs.getInt(1);
                    bill.setId(billId);
                    conn.commit();
                    return billId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return -1;
    }
}


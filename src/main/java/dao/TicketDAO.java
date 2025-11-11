package dao;

import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TicketDAO extends DAO {
    
    public TicketDAO() {
        super();
    }

    public int insertTicket(Ticket ticket) {
        String sql = "INSERT INTO tblTicket (tblScreeningRoomScheduleId, tblSeatId) VALUES (?, ?)";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try {
            pstm = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, ticket.getScreeningRoomSchedule().getId());
            pstm.setInt(2, ticket.getSeat().getId());
            
            int rowsAffected = pstm.executeUpdate();
            
            if (rowsAffected > 0) {
                rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    

    public boolean insertTickets(List<Ticket> tickets, int billId, Integer memberId) {
        String sql = "INSERT INTO tblTicket (tblScreeningRoomScheduleId, tblSeatId, tblBillid, tblMemberid) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = null;
        
        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);
            pstm = conn.prepareStatement(sql);
            
            for (Ticket ticket : tickets) {
                int scheduleId = ticket.getScreeningRoomSchedule().getId();
                int seatId = ticket.getSeat().getId();
                pstm.setInt(1, scheduleId);
                pstm.setInt(2, seatId);
                pstm.setInt(3, billId);
                if (memberId != null) {
                    pstm.setInt(4, memberId);
                } else {
                    pstm.setNull(4, java.sql.Types.INTEGER);
                }
                pstm.addBatch();
            }
            
            int[] results = pstm.executeBatch();
            conn.commit();
            
            boolean allSuccess = true;
            for (int result : results) {
                if (result <= 0) {
                    allSuccess = false;
                }
            }
            
            return allSuccess;
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (getConnection() != null) {
                    getConnection().setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



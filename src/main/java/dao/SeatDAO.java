package dao;

import model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO extends DAO {

    public SeatDAO() {
        super();
    }

    public List<Seat> getListSeatByScreeningRoomScheduleId(int screeningRoomScheduleId) {
        List<Seat> listSeat = new ArrayList<>();
        String sql = "SELECT * FROM tblSeat WHERE tblScreeningRoomScheduleId = ?";
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            try {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, screeningRoomScheduleId);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String seatType = rs.getString("seatType");
                float cost = rs.getFloat("cost");
                
                boolean isBooked = false;
                try {
                    isBooked = rs.getBoolean("isBooked");
                } catch (SQLException e) {
                    try {
                        isBooked = rs.getBoolean("booked");
                    } catch (SQLException ex) {
                    }
                }

                Seat seat = new Seat(id, seatType, cost, isBooked);
                listSeat.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listSeat;
    }

    public Seat getSeatById(int seatId) {
        String sql = "SELECT * FROM tblSeat WHERE id = ?";
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            try {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, seatId);
            rs = pstm.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String seatType = rs.getString("seatType");
                float cost = rs.getFloat("cost");
                boolean isBooked = rs.getBoolean("isBooked");

                return new Seat(id, seatType, cost, isBooked);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean updateSeatBookingStatus(int seatId, boolean isBooked) {
        String sql = "UPDATE tblSeat SET isBooked = ? WHERE id = ?";
        PreparedStatement pstm = null;

        try {
            Connection conn = getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setBoolean(1, isBooked);
            pstm.setInt(2, seatId);
            int rowsAffected = pstm.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (getConnection() != null) {
                    getConnection().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


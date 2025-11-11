package dao;

import model.Movie;
import model.ScreeningRoom;
import model.ScreeningRoomSchedule;
import model.ScreeningSchedule;
import model.Seat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScreeningRoomScheduleDAO extends DAO {
    
    private SeatDAO seatDAO;
    
    public ScreeningRoomScheduleDAO() {
        super();
        seatDAO = new SeatDAO();
    }

    public List<ScreeningRoomSchedule> getListScreeningRoomScheduleByMovie(Movie movie) {
        List<ScreeningRoomSchedule> listSRS = new ArrayList<>();
        
        String sql = "SELECT srs.id as srsId, srs.tblScreeningScheduleId, srs.tblScreeningRoomId, " +
                    "ss.id as ssId, ss.showDate, ss.showTime, " +
                    "sr.id as srId, sr.roomNumber " +
                    "FROM tblScreeningRoomSchedule srs " +
                    "INNER JOIN tblScreeningSchedule ss ON srs.tblScreeningScheduleId = ss.id " +
                    "INNER JOIN tblScreeningRoom sr ON srs.tblScreeningRoomId = sr.id " +
                    "WHERE ss.tblMovieId = ? " +
                    "ORDER BY ss.showDate ASC, ss.showTime ASC";
        
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = getConnection().prepareStatement(sql);
            pstm.setInt(1, movie.getId());
            rs = pstm.executeQuery();

            while (rs.next()) {
                int ssId = rs.getInt("ssId");
                String showDate = rs.getString("showDate");
                String showTime = rs.getString("showTime");
                ScreeningSchedule screeningSchedule = new ScreeningSchedule(ssId, showDate, showTime, movie);
                
                int srId = rs.getInt("srId");
                String roomNumber = rs.getString("roomNumber");
                ScreeningRoom screeningRoom = new ScreeningRoom(srId, roomNumber);
                
                int srsId = rs.getInt("srsId");
                List<Seat> listSeat = seatDAO.getListSeatByScreeningRoomScheduleId(srsId);
                
                ScreeningRoomSchedule screeningRoomSchedule = new ScreeningRoomSchedule(
                    srsId,
                    screeningSchedule, 
                    screeningRoom, 
                    listSeat
                );
                listSRS.add(screeningRoomSchedule);
            }

        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach ScreeningRoomSchedule theo Movie");
            throw new RuntimeException(e);
        }

        listSRS = sortScreeningRoomScheduleByDateAndTime(listSRS);
        return listSRS;
    }

    public ScreeningRoomSchedule getScreeningRoomScheduleById(int screeningRoomScheduleId) {
        String sql = "SELECT srs.id as srsId, srs.tblScreeningScheduleId, srs.tblScreeningRoomId, " +
                    "ss.id as ssId, ss.showDate, ss.showTime, ss.tblMovieId, " +
                    "sr.id as srId, sr.roomNumber, " +
                    "m.id as movieId, m.name as movieName, m.genre as movieGenre, m.duration as movieDuration " +
                    "FROM tblScreeningRoomSchedule srs " +
                    "INNER JOIN tblScreeningSchedule ss ON srs.tblScreeningScheduleId = ss.id " +
                    "INNER JOIN tblScreeningRoom sr ON srs.tblScreeningRoomId = sr.id " +
                    "INNER JOIN tblMovie m ON ss.tblMovieId = m.id " +
                    "WHERE srs.id = ?";
        
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = getConnection().prepareStatement(sql);
            pstm.setInt(1, screeningRoomScheduleId);
            rs = pstm.executeQuery();

            if (rs.next()) {
                int movieId = rs.getInt("movieId");
                String movieName = rs.getString("movieName");
                String movieGenre = rs.getString("movieGenre");
                int movieDuration = rs.getInt("movieDuration");
                Movie movie = new Movie(movieId, movieName, movieGenre, movieDuration);
                
                int ssId = rs.getInt("ssId");
                String showDate = rs.getString("showDate");
                String showTime = rs.getString("showTime");
                ScreeningSchedule screeningSchedule = new ScreeningSchedule(ssId, showDate, showTime, movie);
                
                int srId = rs.getInt("srId");
                String roomNumber = rs.getString("roomNumber");
                ScreeningRoom screeningRoom = new ScreeningRoom(srId, roomNumber);
                
                int srsId = rs.getInt("srsId");
                List<Seat> listSeat = seatDAO.getListSeatByScreeningRoomScheduleId(srsId);
                
                ScreeningRoomSchedule screeningRoomSchedule = new ScreeningRoomSchedule(
                    srsId,
                    screeningSchedule, 
                    screeningRoom, 
                    listSeat
                );
                return screeningRoomSchedule;
            }

        } catch (SQLException e) {
            System.out.println("Loi khi lay ScreeningRoomSchedule theo ID");
            throw new RuntimeException(e);
        }

        return null;
    }

    private List<ScreeningRoomSchedule> sortScreeningRoomScheduleByDateAndTime(List<ScreeningRoomSchedule> listSRS) {
        listSRS.sort(new Comparator<ScreeningRoomSchedule>() {
            @Override
            public int compare(ScreeningRoomSchedule srs1, ScreeningRoomSchedule srs2) {
                ScreeningSchedule ss1 = srs1.getScreeningSchedule();
                ScreeningSchedule ss2 = srs2.getScreeningSchedule();

                if (ss1.getShowDate().compareTo(ss2.getShowDate()) == 0) {
                    return ss1.getShowTime().compareTo(ss2.getShowTime());
                }
                return ss1.getShowDate().compareTo(ss2.getShowDate());
            }
        });
        return listSRS;
    }
}

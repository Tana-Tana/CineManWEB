package model;

public class Ticket {
    private int id;
    private ScreeningRoomSchedule screeningRoomSchedule;
    private Seat seat;
    
    public Ticket() {}
    
    public Ticket(int id, ScreeningRoomSchedule screeningRoomSchedule, Seat seat) {
        this.id = id;
        this.screeningRoomSchedule = screeningRoomSchedule;
        this.seat = seat;
    }
    
    public Ticket(ScreeningRoomSchedule screeningRoomSchedule, Seat seat) {
        this.screeningRoomSchedule = screeningRoomSchedule;
        this.seat = seat;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public ScreeningRoomSchedule getScreeningRoomSchedule() {
        return screeningRoomSchedule;
    }
    
    public void setScreeningRoomSchedule(ScreeningRoomSchedule screeningRoomSchedule) {
        this.screeningRoomSchedule = screeningRoomSchedule;
    }
    
    public Seat getSeat() {
        return seat;
    }
    
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}

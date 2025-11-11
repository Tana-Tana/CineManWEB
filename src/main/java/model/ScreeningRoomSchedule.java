package model;

import java.util.List;

public class ScreeningRoomSchedule {
    private int id;
    private ScreeningSchedule screeningSchedule;
    private ScreeningRoom screeningRoom;
    private List<Seat> listSeat;

    public ScreeningRoomSchedule(int id, ScreeningSchedule screeningSchedule, ScreeningRoom screeningRoom, List<Seat> listSeat) {
        this.id = id;
        this.screeningSchedule = screeningSchedule;
        this.screeningRoom = screeningRoom;
        this.listSeat = listSeat;
    }

    public ScreeningRoomSchedule(ScreeningSchedule screeningSchedule, ScreeningRoom screeningRoom, List<Seat> listSeat) {
        this.screeningSchedule = screeningSchedule;
        this.screeningRoom = screeningRoom;
        this.listSeat = listSeat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScreeningSchedule getScreeningSchedule() {
        return screeningSchedule;
    }

    public void setScreeningSchedule(ScreeningSchedule screeningSchedule) {
        this.screeningSchedule = screeningSchedule;
    }

    public ScreeningRoom getScreeningRoom() {
        return screeningRoom;
    }

    public void setScreeningRoom(ScreeningRoom screeningRoom) {
        this.screeningRoom = screeningRoom;
    }

    public List<Seat> getListSeat() {
        return listSeat;
    }

    public void setListSeat(List<Seat> listSeat) {
        this.listSeat = listSeat;
    }
}

package model;

public class ScreeningRoom {
    private int id;
    private String roomNumber;

    public ScreeningRoom(int id, String roomNumber) {
        this.id = id;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}

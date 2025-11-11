package model;

public class Seat {
    private int id;
    private String seatType;
    private float cost;
    private boolean isBooked;

    public Seat(int id, String seatType, float cost, boolean isBooked) {
        this.id = id;
        this.seatType = seatType;
        this.cost = cost;
        this.isBooked = isBooked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}

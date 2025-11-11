package model;

public class ScreeningSchedule {
    private int id;
    private String showDate;
    private String showTime;
    private Movie movie;

    public ScreeningSchedule(int id, String showDate, String showTime, Movie movie) {
        this.id = id;
        this.showDate = showDate;
        this.showTime = showTime;
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

package model;

public class MembershipCard {
    private int id;
    private String code;
    private int earnedPoint;

    public MembershipCard() {}
    public MembershipCard(String code, int earnedPoint) {
        this.code = code;
        this.earnedPoint = earnedPoint;
    }

    public MembershipCard(int id, String code, int earnedPoint) {
        this.id = id;
        this.code = code;
        this.earnedPoint = earnedPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEarnedPoint() {
        return earnedPoint;
    }

    public void setEarnedPoint(int earnedPoint) {
        this.earnedPoint = earnedPoint;
    }
}



package model;

public class Customer extends Member {
    private String birthOfDate;
    private MembershipCard membershipCard;

    public Customer() {
        super();
        super.setRole("customer");
    }

    public Customer(int id, String username, String password, String name, String phoneNumber, String email, String role,String birthOfDate, MembershipCard membershipCard) {
        super(id, username, password, name, phoneNumber, email, role);
        super.setRole("customer");
        this.birthOfDate = birthOfDate;
        this.membershipCard = membershipCard;
    }

    public String getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(String birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }
}
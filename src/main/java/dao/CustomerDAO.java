package dao;

import model.Customer;
import model.Member;
import model.MembershipCard;

import java.sql.*;

public class CustomerDAO extends DAO{
    public CustomerDAO() {
        super();
    }

    public Customer getCustomer(Member member) {
        Customer customer = null;
        PreparedStatement psCustomer = null;
        PreparedStatement psCard = null;
        ResultSet rsCustomer = null;
        ResultSet rsCard = null;

        try {
            getConnection().commit();
            String sqlCustomer = "SELECT tblMemberId, birthOfDate FROM tblcustomer WHERE tblMemberId = ?";
            psCustomer = getConnection().prepareStatement(sqlCustomer);
            psCustomer.setInt(1, member.getId());
            rsCustomer = psCustomer.executeQuery();

            String birthOfDate = null;
            if (rsCustomer.next()) {
                birthOfDate = rsCustomer.getString("birthOfDate");
            } else {
                /*System.out.println("khong thay ban ghi trong tblcustomer cho memberId: " + member.getId() + ", dang tao moi...");
                PreparedStatement psInsert = null;
                try {
                    String sqlInsertCustomer = "INSERT IGNORE INTO tblcustomer (tblMemberId, birthOfDate) VALUES (?, ?)";
                    psInsert = getConnection().prepareStatement(sqlInsertCustomer);
                    psInsert.setInt(1, member.getId());
                    psInsert.setString(2, null);
                    int rowsAffected = psInsert.executeUpdate();
                    if (rowsAffected > 0) {
                        getConnection().commit();
                        System.out.println("Đã tạo bản ghi mới trong tblcustomer cho memberId: " + member.getId());
                        rsCustomer.close();
                        psCustomer.close();
                        psCustomer = getConnection().prepareStatement(sqlCustomer);
                        psCustomer.setInt(1, member.getId());
                        rsCustomer = psCustomer.executeQuery();
                        if (rsCustomer.next()) {
                            birthOfDate = rsCustomer.getString("birthOfDate");
                        }
                    } else {
                        // INSERT IGNORE trả về 0 nghĩa là bản ghi đã tồn tại
                        // Commit transaction hiện tại để có thể thấy dữ liệu đã commit từ connection khác
                        getConnection().commit();
                        System.out.println("Không insert được (bản ghi đã tồn tại), đang query lại sau khi commit...");
                        rsCustomer.close();
                        psCustomer.close();
                        psCustomer = getConnection().prepareStatement(sqlCustomer);
                        psCustomer.setInt(1, member.getId());
                        rsCustomer = psCustomer.executeQuery();
                        if (rsCustomer.next()) {
                            birthOfDate = rsCustomer.getString("birthOfDate");
                            System.out.println("Đã tìm thấy bản ghi sau khi query lại");
                        } else {
                            System.err.println("Cảnh báo: Vẫn không tìm thấy bản ghi sau khi commit và query lại!");
                        }
                    }
                    if (psInsert != null) {
                        psInsert.close();
                    }
                } catch (SQLException e) {
                    String errorMsg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                    if (errorMsg.contains("duplicate") || errorMsg.contains("primary")) {
                        // Bản ghi đã tồn tại, commit transaction và query lại
                        try {
                            getConnection().commit();
                            System.out.println("Bản ghi đã tồn tại (duplicate exception), đang query lại sau khi commit...");
                            if (rsCustomer != null) rsCustomer.close();
                            if (psCustomer != null) psCustomer.close();
                            psCustomer = getConnection().prepareStatement(sqlCustomer);
                            psCustomer.setInt(1, member.getId());
                            rsCustomer = psCustomer.executeQuery();
                            if (rsCustomer.next()) {
                                birthOfDate = rsCustomer.getString("birthOfDate");
                                System.out.println("Đã lấy được dữ liệu sau khi query lại");
                            } else {
                                System.err.println("Cảnh báo: Vẫn không tìm thấy bản ghi sau duplicate exception và query lại!");
                            }
                        } catch (SQLException ex) {
                            System.err.println("Lỗi khi query lại sau duplicate: " + ex.getMessage());
                        }
                    } else {
                        System.err.println("Lỗi khi tạo bản ghi trong tblcustomer: " + e.getMessage());
                    }
                    if (psInsert != null) {
                        try {
                            psInsert.close();
                        } catch (SQLException ex) {
                            // Ignore
                        }
                    }
                }*/
            }

            customer = new Customer(member.getId(), member.getUsername(), member.getPassword(), 
                    member.getName(), member.getPhoneNumber(), member.getEmail(), member.getRole(), 
                    birthOfDate, null);

            String sqlCard = "SELECT id, code, earnedPoint FROM tblmembershipcard WHERE tblMemberId = ?";
            psCard = getConnection().prepareStatement(sqlCard);
            psCard.setInt(1, member.getId());
            rsCard = psCard.executeQuery();

            if (rsCard.next()) {
                MembershipCard card = new MembershipCard(rsCard.getInt("id"), rsCard.getString("code"), rsCard.getInt("earnedPoint"));
                customer.setMembershipCard(card);
            } else {
                customer.setMembershipCard(null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy thông tin customer: " + e.getMessage());
        }
        return customer;
    }

    public boolean setCustomer(Customer customer) throws SQLException {
        PreparedStatement psUpdateMember = null;
        PreparedStatement psInsertCard = null;
        PreparedStatement psCheckCustomer = null;
        PreparedStatement psInsertCustomer = null;
        ResultSet rsCard = null;

        String sqlUpdateMember = "UPDATE tblMember SET name = ?, phoneNumber = ?, email = ? WHERE id = ?";
        String sqlInsertCard = "INSERT INTO tblMembershipCard (code, earnedPoint, tblMemberId) VALUES (?, ?, ?)";
        String sqlCheckCustomer = "SELECT 1 FROM tblcustomer WHERE tblMemberId = ?";

        try {
            getConnection().setAutoCommit(false);
            psCheckCustomer = getConnection().prepareStatement(sqlCheckCustomer);
            psCheckCustomer.setInt(1, customer.getId());

            psUpdateMember = getConnection().prepareStatement(sqlUpdateMember);
            psUpdateMember.setString(1, customer.getName());
            psUpdateMember.setString(2, customer.getPhoneNumber());
            psUpdateMember.setString(3, customer.getEmail());
            psUpdateMember.setInt(4, customer.getId());

            int rowAffected = psUpdateMember.executeUpdate();
            if(rowAffected == 0) {
                System.out.println("cap nhat thong tin that bai");
                getConnection().rollback();
                return false;
            }

            if (customer.getMembershipCard() == null) {
                MembershipCard card = new MembershipCard("CINEMAN" + customer.getId(), 0);
                psInsertCard = getConnection().prepareStatement(sqlInsertCard, Statement.RETURN_GENERATED_KEYS);
                psInsertCard.setString(1, card.getCode());
                psInsertCard.setInt(2, card.getEarnedPoint());
                psInsertCard.setInt(3, customer.getId());
                rowAffected = psInsertCard.executeUpdate();

                if (rowAffected == 0) {
                    System.out.println("them the thanh vien that bai");
                    getConnection().rollback();
                    return false;
                }

                rsCard = psInsertCard.getGeneratedKeys();
                if (rsCard.next()) {
                    card.setId(rsCard.getInt(1));
                }

                customer.setMembershipCard(card);
            }

            getConnection().commit();
            return true;
        } catch (SQLException e) {
            if (getConnection() != null) {
                try{
                    getConnection().rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        }
    }

    public Customer getCustomerByCardCode(String cardCode) {
        if (cardCode == null || cardCode.trim().isEmpty()) {
            return null;
        }

        Customer customer = null;
        PreparedStatement psCard = null;
        PreparedStatement psMember = null;
        PreparedStatement psCustomer = null;
        ResultSet rsCard = null;
        ResultSet rsMember = null;
        ResultSet rsCustomer = null;

        try {
            String sqlCard = "SELECT id, code, earnedPoint, tblMemberId FROM tblMembershipCard WHERE code = ?";
            psCard = getConnection().prepareStatement(sqlCard);
            psCard.setString(1, cardCode.trim());
            rsCard = psCard.executeQuery();

            if (rsCard.next()) {
                int memberId = rsCard.getInt("tblMemberId");
                MembershipCard card = new MembershipCard(
                        rsCard.getInt("id"),
                        rsCard.getString("code"),
                        rsCard.getInt("earnedPoint")
                );

                String sqlMember = "SELECT id, username, password, name, phoneNumber, email, role FROM tblMember WHERE id = ?";
                psMember = getConnection().prepareStatement(sqlMember);
                psMember.setInt(1, memberId);
                rsMember = psMember.executeQuery();

                if (rsMember.next()) {
                    Member member = new Member(
                            rsMember.getInt("id"),
                            rsMember.getString("username"),
                            rsMember.getString("password"),
                            rsMember.getString("name"),
                            rsMember.getString("phoneNumber"),
                            rsMember.getString("email"),
                            rsMember.getString("role")
                    );

                    String sqlCustomer = "SELECT birthOfDate FROM tblCustomer WHERE tblMemberId = ?";
                    psCustomer = getConnection().prepareStatement(sqlCustomer);
                    psCustomer.setInt(1, memberId);
                    rsCustomer = psCustomer.executeQuery();

                    if (rsCustomer.next()) {
                        customer = new Customer(
                                member.getId(),
                                member.getUsername(),
                                member.getPassword(),
                                member.getName(),
                                member.getPhoneNumber(),
                                member.getEmail(),
                                member.getRole(),
                                rsCustomer.getString("birthOfDate"),
                                card
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm Customer theo mã thẻ: " + e.getMessage());
            e.printStackTrace();
        }

        return customer;
    }
}

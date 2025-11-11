<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Bán Vé - CineMan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainTicketSeller.css">
</head>

<body>
    <div class="container">
        <div class="header">
            <div class="welcome-user">
                Xin chào, <strong>${sessionScope.ticketSeller.name}</strong>
            </div>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                Đăng xuất
            </a>
        </div>

        <div class="action-buttons">
            <a href="#" class="action-btn print-card-btn" onclick="printMembershipCard()">
                In thẻ thành viên
            </a>
            <form action="${pageContext.request.contextPath}/main-ticketSeller" method="post">
                <button type="submit" class="action-btn sell-ticket-btn">
                    Bán vé xem phim
                </button>
            </form>
        </div>
    </div>

    <script>
        function printMembershipCard() {
            alert('Chức năng in thẻ thành viên chưa có vì không thuộc module');
        }
        
        <c:if test="${sessionScope.paymentSuccess == true}">
            alert('Thanh toán thành công! Hệ thống đang in vé và hóa đơn...');
            <c:remove var="paymentSuccess" scope="session"/>
            <c:remove var="tickets" scope="session"/>
            <c:remove var="bill" scope="session"/>
        </c:if>
    </script>

</body>

</html>


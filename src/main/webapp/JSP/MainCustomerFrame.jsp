<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang Khách Hàng</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainCustomer.css">
</head>

<body>
    <c:if test="${empty sessionScope.customer}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <div class="container">
        <div class="header">
            <div class="welcome-user">
                Xin chào, <strong>${sessionScope.customer.name}</strong>
            </div>

            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                Đăng xuất
            </a>
        </div>

        <div class="user-profile">
            <h2>Thông tin cá nhân</h2>

            <div class="info-row">
                <strong>Tên đăng nhập:</strong>
                <span>${sessionScope.customer.username}</span>
            </div>
            <div class="info-row">
                <strong>Họ và tên:</strong>
                <span>${sessionScope.customer.name}</span>
            </div>
            <div class="info-row">
                <strong>Email:</strong>
                <span>${sessionScope.customer.email}</span>
            </div>
            <div class="info-row">
                <strong>Số điện thoại:</strong>
                <span>${sessionScope.customer.phoneNumber}</span>
            </div>

            <div class="membership-section">
                <c:choose>

                    <c:when test="${empty sessionScope.customer.membershipCard}">
                        <a href="${pageContext.request.contextPath}/register-membershipCard"
                            class="register-card-btn">
                            Đăng ký thẻ thành viên
                        </a>
                    </c:when>

                    <c:when test="${not empty sessionScope.customer.membershipCard}">
                        <h3>Thẻ thành viên</h3>
                        <div class="info-row">
                            <strong>Mã thẻ:</strong>
                            <span>${sessionScope.customer.membershipCard.code}</span>
                        </div>
                        <div class="info-row">
                            <strong>Điểm tích lũy:</strong>
                            <span>${sessionScope.customer.membershipCard.earnedPoint}</span>
                        </div>
                    </c:when>
                    
                </c:choose>
            </div>
        </div>

    </div>

</body>

</html>
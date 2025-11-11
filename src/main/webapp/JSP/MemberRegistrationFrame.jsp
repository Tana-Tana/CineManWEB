<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký thẻ thành viên - CineMan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/memberRegistration.css">
</head>
<body>
    <c:if test="${empty sessionScope.customer}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <div class="container">
        <div class="header-section">
            <h1>Đăng ký thẻ thành viên</h1>
            <p class="subtitle">Vui lòng kiểm tra và cập nhật thông tin của bạn trước khi đăng ký thẻ thành viên</p>
        </div>

        <div class="form-card">
            <c:if test="${not empty requestScope.error}">
                <div class="error-message">${requestScope.error}</div>
            </c:if>
            
            <c:if test="${not empty requestScope.success}">
                <div class="success-message">${requestScope.success}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register-membershipCard" method="post" id="registrationForm">
                <div class="form-section">
                    <h3 class="section-title">Thông tin cá nhân</h3>
                    
                    <div class="form-group">
                        <label for="username">Tên đăng nhập:</label>
                        <input type="text" id="username" name="username" 
                               value="${sessionScope.customer.username}" 
                               readonly 
                               class="readonly-field">
                        <span class="field-note">Không thể thay đổi</span>
                    </div>

                    <div class="form-group">
                        <label for="name">Họ và tên: <span class="required">*</span></label>
                        <input type="text" id="name" name="name" 
                               value="${sessionScope.customer.name}" 
                               required 
                               placeholder="Nhập họ và tên">
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" 
                               value="${sessionScope.customer.email != null ? sessionScope.customer.email : ''}" 
                               placeholder="Nhập địa chỉ email">
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Số điện thoại:</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" 
                               value="${sessionScope.customer.phoneNumber != null ? sessionScope.customer.phoneNumber : ''}" 
                               placeholder="Nhập số điện thoại"
                               pattern="[0-9]{10,11}">
                        <span class="field-note">Số điện thoại từ 10-11 chữ số</span>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <span class="btn-icon">✓</span>
                        Đăng ký thẻ thành viên
                    </button>
                    <a href="${pageContext.request.contextPath}/main-customer" class="btn btn-secondary">
                        <span class="btn-icon">←</span>
                        Quay lại
                    </a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>


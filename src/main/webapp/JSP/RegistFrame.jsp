<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - CineMan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
    <div class="container">
        <h2>Đăng ký tài khoản</h2>

        <p class="error-message">${requestScope.error}</p>
        <form action="${pageContext.request.contextPath}/register" method="post" id = "RegisterForm">

            <div class="form-group">
                <label for="username">Tên đăng nhập:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <div class="form-group">
                <label for="name">Họ và tên:</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="phoneNumber">Số điện thoại:</label>
                <input type="text" id="phoneNumber" name="phoneNumber">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email">
            </div>
            <div class="form-group">
                <label for="birthOfDate">Ngày sinh:</label>
                <input type="date" id="birthOfDate" name="birthOfDate" placeholder="dd/mm/yyyy">
            </div>

            <button type="submit" class="btn">Đăng ký</button>
        </form>

        <div class="link">
            <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
        </div>
    </div>
</body>
</html>
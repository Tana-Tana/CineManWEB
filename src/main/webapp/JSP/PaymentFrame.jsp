<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa Đơn Đặt Vé - CineMan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment.css">
</head>
<body>
    <div class="container">
        <div class="invoice-header">
            <h1>HÓA ĐƠN ĐẶT VÉ</h1>
        </div>

        <div class="invoice-content">
            <div class="section">
                <h2 class="section-title">Thông Tin Suất Chiếu</h2>
                <div class="info-grid">
                    <div class="info-item">
                        <span class="label">Tên phim:</span>
                        <span class="value">${screeningRoomSchedule.screeningSchedule.movie.name}</span>
                    </div>
                    <div class="info-item">
                        <span class="label">Ngày chiếu:</span>
                        <span class="value">${screeningRoomSchedule.screeningSchedule.showDate}</span>
                    </div>
                    <div class="info-item">
                        <span class="label">Giờ chiếu:</span>
                        <span class="value">${screeningRoomSchedule.screeningSchedule.showTime}</span>
                    </div>
                    <div class="info-item">
                        <span class="label">Phòng chiếu:</span>
                        <span class="value">${screeningRoomSchedule.screeningRoom.roomNumber}</span>
                    </div>
                </div>
            </div>

            <c:if test="${not empty customer}">
                <div class="section">
                    <h2 class="section-title">Thông Tin Thành Viên</h2>
                    <div class="info-grid">
                        <div class="info-item">
                            <span class="label">Họ và tên:</span>
                            <span class="value">${customer.name}</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Mã thẻ:</span>
                            <span class="value">${customer.membershipCard.code}</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Điểm tích lũy:</span>
                            <span class="value">${customer.membershipCard.earnedPoint}</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Email:</span>
                            <span class="value">${customer.email}</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Số điện thoại:</span>
                            <span class="value">${customer.phoneNumber}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="section">
                <h2 class="section-title">Danh Sách Vé</h2>
                <table class="ticket-table">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Số ghế</th>
                            <th>Loại ghế</th>
                            <th>Giá vé</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ticket" items="${tickets}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${ticket.seat.id}</td>
                                <td>${ticket.seat.seatType}</td>
                                <td class="price">
                                    <fmt:formatNumber value="${ticket.seat.cost}" type="number" maxFractionDigits="0" />
                                    <span class="currency">đ</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="section total-section">
                <div class="total-row">
                    <span class="total-label">Tổng tiền:</span>
                    <span class="total-amount">
                        <c:set var="total" value="0" />
                        <c:forEach var="ticket" items="${tickets}">
                            <c:set var="total" value="${total + ticket.seat.cost}" />
                        </c:forEach>
                        <fmt:formatNumber value="${total}" type="number" maxFractionDigits="0" />
                        <span class="currency">đ</span>
                    </span>
                </div>
            </div>
        </div>

        <div class="invoice-footer">
            <div class="actions">
                <form action="${pageContext.request.contextPath}/payment" method="post" id="paymentForm">
                    <button type="submit" class="btn-print">Thanh Toán</button>
                </form>
                <a href="${pageContext.request.contextPath}/book-ticket?screeningRoomScheduleId=${screeningRoomSchedule.id}" class="btn-back">Quay Lại</a>
            </div>
            <div class="thank-you">
                <p>Cảm ơn bạn vì sự cố gắng đặt vé của bạn</p>
            </div>
        </div>
    </div>
</body>
</html>


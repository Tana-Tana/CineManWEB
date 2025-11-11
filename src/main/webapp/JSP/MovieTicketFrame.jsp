<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Vé Xem Phim - CineMan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movieTicket.css">
</head>

<body>
    <div class="container">
        <div class="header">
            <div class="welcome-user">
                Xin chào, <strong>${sessionScope.ticketSeller.name}</strong>
            </div>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/screening-room-schedule" class="back-btn">
                    Quay lại
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                    Đăng xuất
                </a>
            </div>
        </div>

        <c:if test="${not empty requestScope.screeningRoomSchedule}">
            <c:set var="srs" value="${requestScope.screeningRoomSchedule}" />
            
            <div class="movie-info-section">
                <h1>Đặt Vé Xem Phim</h1>
                <div class="info-grid">
                    <div class="info-item">
                        <span class="info-label">Phim:</span>
                        <span class="info-value">${srs.screeningSchedule.movie.name}</span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Phòng chiếu:</span>
                        <span class="info-value">Phòng ${srs.screeningRoom.roomNumber}</span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Ngày chiếu:</span>
                        <span class="info-value">${srs.screeningSchedule.showDate}</span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Giờ chiếu:</span>
                        <span class="info-value">${srs.screeningSchedule.showTime}</span>
                    </div>
                </div>
            </div>
            <div class="booking-form-section">
                <h2>Thông Tin Đặt Vé</h2>
                
                <c:if test="${not empty requestScope.success}">
                    <div class="success-message">
                        ${requestScope.success}
                    </div>
                </c:if>
                
                <c:if test="${not empty requestScope.error}">
                    <div class="error-message">
                        ${requestScope.error}
                    </div>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/book-ticket" method="post" class="booking-form">
                    <input type="hidden" name="screeningRoomScheduleId" value="${srs.id}">
                    <div class="form-group">
                        <label for="seatNumber">Ghế ngồi:</label>
                        <input type="text" id="seatNumber" name="seatNumber" class="form-input" readonly>
                    </div>
                    <div class="form-group">
                        <label for="memberCard">Mã thẻ thành viên:</label>
                        <input type="text" id="memberCard" name="memberCard" class="form-input" 
                               placeholder="Nhập mã thẻ thành viên (nếu có)" 
                               value="${requestScope.memberCard}">
                    </div>
                    <button type="submit" class="submit-btn">Xác Nhận Đặt Vé</button>
                </form>
            </div>

            <div class="seat-legend">
                <h3>Chú thích loại ghế:</h3>
                <div class="legend-items">
                    <c:set var="processedTypes" value="" />
                    <c:forEach var="seat" items="${srs.listSeat}">
                        <c:if test="${not fn:contains(processedTypes, seat.seatType)}">
                            <c:set var="costInt" value="${Math.round(seat.cost)}" />
                            <div class="legend-item">
                                <span class="legend-color available"></span>
                                <span class="legend-text">${seat.seatType}: ${costInt} VNĐ</span>
                            </div>
                            <c:set var="processedTypes" value="${processedTypes}${seat.seatType}," />
                        </c:if>
                    </c:forEach>
                    <div class="legend-item">
                        <span class="legend-color booked"></span>
                        <span class="legend-text">Đã đặt</span>
                    </div>
                    <div class="legend-item">
                        <span class="legend-color selected"></span>
                        <span class="legend-text">Đã chọn</span>
                    </div>
                </div>
            </div>

            <div class="seats-section">
                <h2>Chọn Ghế</h2>
                <div class="screen-indicator">MÀN HÌNH</div>
                <div class="seats-grid" id="seatsGrid">
                    <c:forEach var="seat" items="${srs.listSeat}">
                        <c:choose>
                            <c:when test="${seat.booked}">
                                <button type="button" 
                                        id="seat-${seat.id}"
                                        class="seat-btn booked" 
                                        disabled>
                                    <span class="seat-number">${seat.id}</span>
                                    <span class="seat-type">${seat.seatType}</span>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" 
                                        class="seat-btn available"
                                        data-seat-id="${seat.id}"
                                        onclick="selectSeat(this.getAttribute('data-seat-id'), this)">
                                    <span class="seat-number">${seat.id}</span>
                                    <span class="seat-type">${seat.seatType}</span>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </div>
            
            <c:if test="${not empty requestScope.selectedSeatIds}">
                <input type="hidden" id="restoredSeatIds" 
                       value="<c:forEach var="seatId" items="${requestScope.selectedSeatIds}" varStatus="status">${seatId}<c:if test="${!status.last}">,</c:if></c:forEach>">
            </c:if>
        </c:if>
        
        <c:if test="${empty requestScope.screeningRoomSchedule}">
            <div class="no-data">
                <p>Không tìm thấy thông tin lịch chiếu.</p>
            </div>
        </c:if>
    </div>

    <script>
        let selectedSeats = [];

        function selectSeat(seatIdStr, seatElement) {
            const seatId = parseInt(seatIdStr);
            
            if (seatElement.classList.contains('selected')) {
                seatElement.classList.remove('selected');
                selectedSeats = selectedSeats.filter(id => id !== seatId);
            } else {
                seatElement.classList.add('selected');
                selectedSeats.push(seatId);
            }
            
            document.getElementById('seatNumber').value = selectedSeats.join(', ');
        }

        document.addEventListener('DOMContentLoaded', function() {
            const successMessage = document.querySelector('.success-message');
            if (successMessage) {
                document.querySelectorAll('.seat-btn').forEach(btn => {
                    btn.classList.remove('selected');
                });
                document.getElementById('seatNumber').value = '';
                selectedSeats = [];
            }
            
            const restoredSeatIdsInput = document.getElementById('restoredSeatIds');
            if (restoredSeatIdsInput && restoredSeatIdsInput.value) {
                const selectedSeatIdsStr = restoredSeatIdsInput.value;
                const selectedSeatIds = selectedSeatIdsStr.split(',').map(id => parseInt(id.trim()));
                
                selectedSeatIds.forEach(function(seatId) {
                    const seatElement = document.querySelector('[data-seat-id="' + seatId + '"]');
                    if (seatElement && !seatElement.classList.contains('booked')) {
                        seatElement.classList.add('selected');
                        selectedSeats.push(seatId);
                    }
                });
                
                if (selectedSeats.length > 0) {
                    document.getElementById('seatNumber').value = selectedSeats.join(', ');
                }
            }
        });
    </script>
</body>

</html>


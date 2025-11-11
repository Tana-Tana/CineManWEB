<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch Chiếu Phim - CineMan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/screeningSchedule.css">
</head>

<body>
    <div class="container">
        <div class="header">
            <div class="welcome-user">
                Xin chào, <strong>${sessionScope.ticketSeller.name}</strong>
            </div>
            <div class="header-actions">
                <button onclick="history.back()" class="back-btn">
                    Quay lại
                </button>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                    Đăng xuất
                </a>
            </div>
        </div>

        <div class="movie-title-section">
            <h1>Lịch chiếu phim của phim ${not empty requestScope.movie ? requestScope.movie.name : sessionScope.movie.name}</h1>
        </div>

        <c:if test="${not empty requestScope.listScreeningRoomSchedule || not empty sessionScope.listScreeningRoomSchedule}">
            <c:set var="currentDate" value="" />
            <c:set var="isFirst" value="true" />
            
            <c:forEach var="srs" items="${not empty requestScope.listScreeningRoomSchedule ? requestScope.listScreeningRoomSchedule : sessionScope.listScreeningRoomSchedule}" varStatus="loop">
                <c:set var="showDate" value="${srs.screeningSchedule.showDate}" />
                
                <c:if test="${showDate != currentDate}">
                    <c:if test="${not isFirst}">
                        </div>
                        </div>
                    </c:if>
                    
                    <div class="date-group">
                        <h2 class="date-header">${showDate}</h2>
                        <div class="time-links">
                    
                    <c:set var="currentDate" value="${showDate}" />
                    <c:set var="isFirst" value="false" />
                </c:if>

                <c:set var="availableSeats" value="0" />
                <c:forEach var="seat" items="${srs.listSeat}">
                    <c:if test="${!seat.booked}">
                        <c:set var="availableSeats" value="${availableSeats + 1}" />
                    </c:if>
                </c:forEach>

                <form action="${pageContext.request.contextPath}/screening-room-schedule" method="post" class="time-link-form">
                    <input type="hidden" name="screeningRoomScheduleId" value="${srs.id}">
                    <button type="submit" class="time-link">
                        <span class="time">${srs.screeningSchedule.showTime}</span>
                        <span class="seats">${availableSeats} ghế trống</span>
                    </button>
                </form>
                
                <c:if test="${loop.last}">
                    </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>

        <c:if test="${empty requestScope.listScreeningRoomSchedule && empty sessionScope.listScreeningRoomSchedule}">
            <div class="no-schedule">
                <p>Không có lịch chiếu nào cho phim này.</p>
            </div>
        </c:if>
    </div>
</body>

</html>


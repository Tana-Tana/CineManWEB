<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bán Vé Xem Phim - CineMan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movieTicketSale.css">
</head>

<body>
    <div class="container">
        <div class="header">
            <div class="welcome-user">
                Xin chào, <strong>${sessionScope.ticketSeller.name}</strong>
            </div>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/main-ticketSeller" class="back-btn">
                    Quay lại
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                    Đăng xuất
                </a>
            </div>
        </div>
        
        <div class="search-section">
            <form action="${pageContext.request.contextPath}/search-movie" method="post" class="search-box">
                <input type="text" id="searchInput" name="searchTerm" class="search-input" 
                       placeholder="Tìm kiếm phim..." value="${param.searchTerm}">
                <button type="submit" class="search-btn">
                    <span>Tìm kiếm</span>
                </button>
            </form>
        </div>

        <div class="movies-grid">
            <c:forEach var="movie" items="${not empty requestScope.listMovie ? requestScope.listMovie : sessionScope.listMovie}">
                <div class="movie-card">
                    <div class="movie-info">
                        <h3 class="movie-title">${movie.name}</h3>
                        <div class="movie-details">
                            <p class="movie-genre"><strong>Thể loại:</strong> ${movie.genre}</p>
                            <p class="movie-duration"><strong>Thời lượng:</strong> ${movie.duration} phút</p>
                        </div>
                    </div>

                    <form action="${pageContext.request.contextPath}/movie-list" method="post" class="buy-ticket-form">
                        <input type="hidden" name="movieId" value="${movie.id}">
                        <input type="hidden" name="movieName" value="${movie.name}">
                        <input type="hidden" name="movieGenre" value="${movie.genre}">
                        <input type="hidden" name="movieDuration" value="${movie.duration}">
                        <button type="submit" class="buy-ticket-btn">
                            MUA VÉ
                        </button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty requestScope.listMovie && empty sessionScope.listMovie}">
            <div class="no-movies">
                <p>Không có phim nào được tìm thấy.</p>
            </div>
        </c:if>
    </div>

    <!-- <script>
        (function() {
            const searchInput = document.getElementById('searchInput');
            const searchForm = searchInput.closest('form');
            let timeoutId;

            searchInput.addEventListener('input', function(e) {
                clearTimeout(timeoutId);
                timeoutId = setTimeout(function() {
                    searchForm.submit();
                }, 500);
            });

            searchInput.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    clearTimeout(timeoutId);
                    searchForm.submit();
                }
            });
        })();
    </script> -->

</body>

</html>


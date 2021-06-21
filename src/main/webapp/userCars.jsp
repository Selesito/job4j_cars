<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Продажа автомобилей</title>
    <style>
        div#content {
            border: 3px solid gray; /* Белая рамка */
            border-radius: 10px; /* Радиус скругления */
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row pt-3" style="height: 150px">
        <h4 style="color: #ff0000">
            Bi-Bi-Car
        </h4>
        <div class="col">
            <ul class="nav float-right">
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link-sm text-dark" href="<%=request.getContextPath()%>/auth">Авторизоваться</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDDMenu" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <c:out value="${sessionScope.user.name}"/>
                        </a>
                        <div class="dropdown-menu text-center" aria-labelledby="navbarDDMenu">
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/all">Главная</a>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/addAds">Добавить объявление</a>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/auth?command=out">Выйти</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <jsp:include page="/adsUser" />
    <c:forEach items="${adsUser}" var="ad">
        <div class="col-12" id="content" style="margin-top: 25px; margin-top: 25px">
            <div class="row">
                <div class="col-5" style="margin-left: 25px; margin-top: 25px">
                    <img src="<c:url value='/photo?idAds=${ad.id}'/>"  width="400px" height="350px"/>
                </div>
                <div class="col-5">
                    <div class="row" style="margin-top: 25px">
                        <div class="col text-left">
                            <h5><c:out value="${ad.title}"/></h5>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col text-left">
                            <ul>
                                <li>Марка: <c:out value="${ad.car.model.brand.name}"/> <c:out value="${ad.car.model.name}"/></li>
                                <li>Двигатель: <c:out value="${ad.car.engine.name}"/></li>
                                <li>Трансмиссия: <c:out value="${ad.car.model.brand.name}"/></li>
                                <li>Пробег: <c:out value="${ad.car.mileage}"/></li>
                                <li>Год выпуска: <c:out value="${ad.car.year}"/></li>
                                <li>Кузов: <c:out value="${ad.car.bodyType.name}"/></li>
                                <li>Цвет: <c:out value="${ad.car.color.name}"/></li>
                                <li>Владельцев: <c:out value="${ad.car.owners}"/></li>
                                <li>VIN: <c:out value="${ad.car.vinNumber}"/></li>
                                <c:if test="${ad.status != true}">
                                    <li style="color: darkgreen"> Статус продажи: Продается</li>
                                </c:if>
                                <c:if test="${ad.status != false}">
                                    <li style="color: red"> Статус продажи: Продана</li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-1"><div class="col-md-3 text-right" style="margin-top: 25px">
                    <h5>Стоимость:</h5>
                    <h5><c:out value="${ad.price}"/>₽</h5>
                </div></div>
            </div>
            <div class="row" style="margin-bottom: 25px; margin-top: 25px">
                <div class="col-8" style="margin-left: 50px">
                    <h6><c:out value="${ad.description}"/></h6>
                </div>
                <div class="col-3" style="text-align: right">
                    <form action="<%=request.getContextPath()%>/sold?idAds=<c:out value="${ad.id}"/>" method="post">
                        <button class="btn btn-primary" type="submit" name="sold" value="sold" style="margin-bottom: 10px">Автомобиль продан!</button>
                        <button class="btn btn-danger" type="submit" name="sold" value="delete" style="margin-bottom: 10px">Удалить объявление!</button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>

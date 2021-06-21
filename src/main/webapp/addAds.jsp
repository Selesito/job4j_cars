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
    <title>Добавить новое обьявление!</title>
    <script>
        $(document).ready(function() {
            year();
        });

        function year() {
            for (let i = 2021; i >= 1950; i--) {
                $('#yearRelese').append('<option value=' + i + '>' + i + '</option>');
            }
        }

        function modelList(idBrand, idModels) {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/job4j_cars_war_exploded/models',
                data: 'idBrand=' + idBrand.value,
                dataType: 'json'
            }).done(function (data) {
                let models = ""
                for (let i = 0; i < data.length; i++) {
                    models += "<option value="+data[i]["id"]+">"
                        + data[i]["name"]
                        + "</option>>"
                }
                $('#model').html(models)
            })
        }
    </script>
</head>
<body>
<div class="container">
    <div class="row pt-3">
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
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/userCars.jsp">Мои объявления</a>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/auth?command=out">Выйти</a>
                        </div>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <div class="col-md-6 offset-md-3">
        <h6>Добавить объявление!</h6>
        <div class="card-body">
            <form action="<%=request.getContextPath()%>/addAds?" enctype="multipart/form-data" method="post">
                <div class="form-group">
                    <label for="nameAds">Название</label>
                    <input type="text" class="form-control" name="nameAds" title="Enter name ads..."
                           id="nameAds" value="" placeholder="Объявление" required>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="brand" name="brand"  title="Выберите марку..." required onchange="modelList(this, $('#model'));">
                            <option value="" selected>Выберите марку...</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="<c:out value="${brand.id}"/>"><c:out value="${brand.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="model" name="model" required>
                            <option value="" selected>Выберите модель....</option>
                        </select>
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="color" name="color" required>
                            <option value="" selected>Выберите цвет...</option>
                            <c:forEach items="${colors}" var="color">
                                <option value="<c:out value="${color.id}"/>"><c:out value="${color.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="bodyType" name="bodyType" required>
                            <option value="" selected>Выберите кузов...</option>
                            <c:forEach items="${bodyTypes}" var="body">
                                <option value="<c:out value="${body.id}"/>"><c:out value="${body.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="engine" name="engine" required>
                            <option value="" selected>Выберите тип двигателя...</option>
                            <c:forEach items="${engines}" var="engine">
                                <option value="<c:out value="${engine.id}"/>"><c:out value="${engine.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" name="power" title="Enter power..."
                               id="power" value="" placeholder="Мощность автомобиля..." required>
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="transmission" name="transmission" required>
                            <option value="" selected>Выберите трансмиссию...</option>
                            <c:forEach items="${transmissions}" var="transmission">
                                <option value="<c:out value="${transmission.id}"/>"><c:out value="${transmission.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" name="km" title="Enter km..."
                               id="km" value="" placeholder="Пробег автомобиля..." required>
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <input type="text" class="form-control" name="price" title="Price car..."
                               id="price" value="" placeholder="Цена автомобиля" required>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control" name="vin" title="Vin number car..." id="vin"
                               value="" required placeholder="VIN номер автомобиля">
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="yearRelese" name="yearRelese" required>
                            <option value="" selected>Год автомобиля...</option>
                        </select>
                    </div>
                    <div class="col">
                        <select class="custom-select mr-sm-2" id="owners" name="owners" required>
                            <option value="" selected disabled>Количество владельцев...</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3+">3+</option>
                            <option value="6+">6+</option>
                        </select>
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <label for="description">Описание</label>
                        <textarea class="form-control" id="description" name="description"
                                  minlength="10" maxlength="300" placeholder="Описание автомобиля..." rows="4" required></textarea>
                    </div>
                </div>
                <div class="row py-2">
                    <div class="col">
                        <label>Добавить фото!</label>
                        <input id="photo" name="file" type="file" class="custom-file">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary" onclick="return validate();">Добавить</button>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>

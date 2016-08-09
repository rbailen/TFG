<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Venta de Localidades</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <script type='text/javascript'src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
        <link href="<c:url value='/resources/css/bootstrap-theme.min.css'/>" rel="stylesheet">
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
        
        <script language="JavaScript" type="text/javascript">
        window.onload = function () {
            var idButton = document.getElementById("atrasButton");
            idButton.addEventListener("click", goBack);
        };
        
        function goBack() {
            window.history.back();
        }
        </script>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>

                <div id="navbar" class="navbar-collapse collapse">
                    
                    <!-- Barra de búsqueda -->
                    <form:form class="navbar-form navbar-left" role="search" method="POST" action="${pageContext.request.contextPath}/eventos/buscarEventos">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Buscar eventos" id="query" name="criterio">
                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span></button>
                            </div>
                        </div>
                    </form:form>
                    
                    <form class="navbar-form navbar-right">
                        <div class="form-group" style="color: red">
                            <c:out value='${sessionScope.user.nombre}'/> »
                        </div>

                        <a class="btn btn-success" role="button" data-toggle="modal" data-target="#myModal">Salir</a>

                        <div class="modal fade" id="myModal">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <!-- Modal Header -->
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Salir</h4>
                                    </div>
                                    <!-- Modal Body -->
                                    <div class="modal-body">
                                        <h3>¿Desea realmente salir?</h3>
                                    </div>
                                    <!-- Modal Footer -->
                                    <div class="modal-footer">
                                        <a class="btn btn-danger" role="button" href='<c:url value='/j_spring_security_logout'/>'>Salir</a>
                                        <button class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </form>
                </div><!--/.navbar-collapse -->
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <ul class="nav nav-pills nav-stacked">
                        <br>
                        <br>
                        <br>
                        <li class="active"><a href="<c:url value='/inicio'/>">Menú</a></li>
                        <li><a href="<c:url value='/usuarios/visualizaCliente/${sessionScope.user.idUsuario}'/>">Datos Personales</a></li>
                        <li><a href="<c:url value='/deportes/listado'/>">Ver Eventos</a></li>
                        <li><a href="<c:url value='/compras/historico/${sessionScope.user.idUsuario}'/>">Ver Compras Realizadas</a></li>
                    </ul>
                </div>

                <div class="col-md-10">
                    <br>
                    <br>
                    <br>
                    <h1>Resultados de la Búsqueda</h1>
                    
                    <p>&nbsp;<img align="left"  src="<c:url value='../resources/imagenes/buscador.jpg'/>" width="180" height="180"/>
                        <p style="color: red">${criterio}</p></p> <br style="line-height:130px">
                    <hr>
                    <h3>Eventos</h3>
                    <c:if test="${empty eventos}">
                        <center>NO HAY EVENTOS DISPONIBLES CON ESE CRITERIO DE BÚSQUEDA</center>
                        </c:if>
                        <c:if test="${not empty eventos}">

                        <c:forEach var="eventositem" items="${eventos}">
                            <div class="container">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="jumbotron" style="background: palegoldenrod">
                                            <p>${eventositem.nombre}</p>
                                            ${eventositem.competicion.nombre}<br>
                                            ${eventositem.lugar}, ${eventositem.ciudad}<br>
                                            ${eventositem.fecha}<br>
                                            ${eventositem.hora}<br><br>
                                            <p><a class="btn btn-warning btn-lg" href="<c:url value='/compras/altaCompra/${eventositem.idEvento}'/>" role="button">Ver entradas &raquo;</a></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <button type="button" id="atrasButton" class="btn btn-primary">Volver</button>
                </div>
                <div class="clearfix visible-lg"></div>
            </div>
            <hr>
        </div>
    </body>
</html>
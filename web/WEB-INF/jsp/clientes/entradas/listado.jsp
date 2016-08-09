<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Venta de Localidades</title>
        <link rel="stylesheet"  type="text/css" href="<c:url value='/resources/css/common.css'/>">
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

                <div class="col-md-9">
                    <br>
                    <br>
                    <br>
                    <h1>Entradas Deportes</h1>
                    <hr>
                    <h3>Próximos Eventos</h3>
                    <p>Seleccione las entradas que desea comprar</p>
                    <form:form name="compra" class="form-horizontal" method="POST" modelAttribute="compra">
                        <c:if test="${not empty entradas}">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <tr class="text-capitalize">
                                        <th>Id</th>
                                        <th>Descripción</th>
                                        <th>Precio</th
                                        <th></th>
                                        <th></th>
                                    </tr>
                                    <c:forEach var="c" items="${entradas}">
                                        <tr class="table table-striped">
                                            <td>${c.idEntrada}</td>
                                            <td>${c.descripcion}</td>
                                            <td>${c.precio} €</td>

                                            <td><form:checkbox path="entradas" value="${c.idEntrada}"/></td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                        <th>
                                            <button name="enviar" type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;Comprar</button>
                                        </th>
                                    </tr>
                                </table>
                            </div>
                        </c:if>
                        <form:errors cssClass="error" path="entradas"/><br>
                    </form:form>
                    <hr>
                    <button type="button" id="atrasButton" class="btn btn-primary">Volver</button>

                    <div class="clearfix visible-lg"></div>
                </div>
                <hr>
            </div>

    </body>
</html>
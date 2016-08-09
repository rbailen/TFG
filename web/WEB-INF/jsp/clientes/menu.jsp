<%@page import="es.ujaen.tfg.modelos.Deporte"%>
<%@page import="es.ujaen.tfg.servicios.ServicioDeporte"%>
<%@page import="java.util.List"%>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
        
        <style type="text/css">
            h2{
                margin: 0;     
                color: #666;
                padding-top: 30px;
                font-size: 28px;
                font-family: "trebuchet ms", sans-serif;    
            }
            .item{
                background: #333;    
                text-align: center;
                height: 300px;
            }
            .carousel{
                margin-top: 20px;
            }
            .bs-example{
                    margin: 20px;
            }
        </style>
  
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
                        <h2>Bienvenido/a a la aplicación de venta de localidades deportivas</h2>

                        <div class="bs-example">
                            <div id="myCarousel" class="carousel slide" data-interval="3000" data-ride="carousel">
                                <!-- Carousel indicators -->
                                <ol class="carousel-indicators">
                                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                    <li data-target="#myCarousel" data-slide-to="1"></li>
                                    <li data-target="#myCarousel" data-slide-to="2"></li>
                                </ol>   
                                
                                <!-- Carousel items -->
                               
                                    <div class="carousel-inner">
                                        <div class="active item">
                                            <a href="<c:url value='/deportes/listado'/> "><img src="<c:url value='/resources/imagenes/sports.jpg'/>" width="809" height="301"></a> 
                                            <div class="carousel-caption">
                                                <p>Compre entradas para alguno de nuestros deportes</p>
                                            </div>
                                        </div>
                                        <c:forEach var="deporte" items="${deportes}">
                                            <div class="item">
                                                <a href="<c:url value='/deportes/verDeporte/${deporte.idDeporte}'/> "><img src="<c:url value='/resources/imagenes/deportes//${deporte.idDeporte}.jpg'/>" width="809" height="301"></a> 
                                                <div class="carousel-caption">
                                                    <p>Compre entradas para ${deporte.nombre}</p>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    
                                <!-- Carousel nav -->
                                <a class="carousel-control left" href="#myCarousel" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                </a>
                                <a class="carousel-control right" href="#myCarousel" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                </a>
                            </div>
                        </div>

                    </div>
            </div>
            <hr>
        </div>

    </body>
</html>
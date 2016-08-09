<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <script type="text/javascript">
            $(function () {
                $('#myModal').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var idCliente = button.data('id');
                    var modal = $(this);
                    modal.find('#btEliminar').attr('href', '<c:url value="/usuarios/borraCliente/"/>' + idCliente);
                });
            });
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

                        <a class="btn btn-success" role="button" data-toggle="modal" data-target="#salir">Salir</a>

                        <div class="modal fade" id="salir">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <!-- Modal Header -->
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="salir">&times;</button>
                                        <h4 class="modal-title">Salir</h4>
                                    </div>
                                    <!-- Modal Body -->
                                    <div class="modal-body">
                                        <h3>¿Desea realmente salir?</h3>
                                    </div>
                                    <!-- Modal Footer -->
                                    <div class="modal-footer">
                                        <a class="btn btn-danger" role="button" href='<c:url value='/j_spring_security_logout'/>'>Salir</a>
                                        <button class="btn btn-primary" data-dismiss="salir">Cancelar</button>
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
                    <h1>Visualizar Datos Personales</h1>

                    <div class="row mark">
                        <div class="col-lg-6">
                            <h4>Id Cliente</h4>
                            <p>${user.idUsuario}</p>

                            <h4>Nombre</h4>
                            <p>${user.nombre}</p>

                            <h4>Correo</h4>
                            <p>${user.correo}</p>
                        </div>

                        <div class="col-lg-6">
                            <h4>Ciudad</h4>
                            <p>${user.ciudad}</p>

                            <h4>Usuario</h4>
                            <p>${user.usuario}</p>

                            <h4>Contraseña</h4>
                            <p>${user.clave}</p>
                        </div>
                    </div>
                    <br>
                    <a href="<c:url value='/usuarios/editar/${sessionScope.user.idUsuario}'/>" class="btn btn-primary">
                        <span class="glyphicon glyphicon-edit"></span>&nbsp;Editar
                    </a>
                        
                    <a class="btn btn-danger" role="button" data-id="${user.idUsuario}" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-trash"></span>&nbsp;Eliminar</a>
                    
                    <a href="<c:url value='/inicio'/>" class="btn btn-success" role="button">Volver</a>

                    <div class="clearfix visible-lg"></div>
                </div>
                <hr>
            </div>

            <!-- Modales -->
            <div class="modal fade" id="myModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Eliminar</h4>
                        </div>
                        <!-- Modal Body -->
                        <div class="modal-body">
                            <h3>¿Desea realmente eliminar?</h3>
                        </div>
                        <!-- Modal Footer -->
                        <div class="modal-footer">
                            <a class="btn btn-danger" id="btEliminar" role="button" href=""/>Eliminar</a>
                            <button class="btn btn-primary" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>   
            
    </body>
</html>
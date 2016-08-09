<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                        <li class="active"><a href="<c:url value='/main'/>">Menú</a></li>
                        <li><a href="<c:url value='/usuarios/listadoClientes'/>">Gestión de Usuarios</a></li>
                        <li><a href="<c:url value='/deportes/listadoDeportes'/>">Gestión de Deportes</a></li>
                        <li><a href="<c:url value='/competiciones/listadoCompeticiones'/>">Gestión de Competiciones</a></li>
                        <li><a href="<c:url value='/participantes/listadoParticipantes'/>">Gestión de Participantes</a></li>
                        <li><a href="<c:url value='/eventos/listadoEventos'/>">Gestión de Eventos</a></li>
                        <li><a href="<c:url value='/entradas/listadoEntradas'/>">Gestión de Entradas</a></li>
                        <li><a href="<c:url value='/compras/listadoCompras'/>">Gestión de Compras</a></li>
                    </ul>
                </div>
                <div class="col-md-9">
                    <br>
                    <br>
                    <h2>Panel de Administración</h2>
                    <p>Es usted el administrador del sistema </p>
                </div>
            </div>
            <hr>
        </div>

    </body>
</html>
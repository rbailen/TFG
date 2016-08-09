<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

                <div class="col-md-10">
                    <br>
                    <br>
                    <br>
                    <h1>Listado de Usuarios</h1>
                    <c:if test="${empty clientes}">
                        <center>NO HAY USUARIOS DISPONIBLES</center>
                        </c:if>
                        <c:if test="${not empty clientes}">

                        <div class="table-responsive">
                            <table class="table table-striped">
                                <tr class="text-capitalize">
                                    <th>Id</th>
                                    <th>Nombre</th>
                                    <th>Correo</th>
                                    <th>Ciudad</th>
                                    <th>Usuario</th>
                                    <th>Clave</th>
                                    <th>Edad</th>
                                    <th>Rol</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:forEach var="c" items="${clientes}">
                                    <c:set var="qry" value="${c.idUsuario}"/>
                                    <tr class="table table-striped">
                                        <td>${c.idUsuario}</td>
                                        <td>${c.nombre}</td>
                                        <td>${c.correo}</td>
                                        <td>${c.ciudad}</td>
                                        <td>${c.usuario}</td>
                                        <td>${c.clave}</td>
                                        <td>${c.edad?"Sí":"No"}</td>
                                        <td>${c.rol}</td>
                                        <td>
                                            <a href="<c:url value='/usuarios/editaCliente/${qry}'/>" class="btn btn-primary">
                                                <span class="glyphicon glyphicon-edit"></span>&nbsp;Editar
                                            </a>
                                        </td>
                                        <td>
                                            <a class="btn btn-danger" role="button" data-id="${c.idUsuario}" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-trash"></span>&nbsp;Eliminar</a>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>
                        </div>
                    </c:if>
                    <a href="<c:url value='/usuarios/altaCliente'/>" class="btn btn-success">
                        <span class="glyphicon glyphicon-plus"></span>&nbsp;Nuevo Usuario
                    </a>
                </div>
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
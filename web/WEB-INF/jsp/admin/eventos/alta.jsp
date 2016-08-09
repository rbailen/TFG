<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

                <div class="col-md-4">
                    <br>
                    <br>
                    <br>
                    <h2>Alta de Evento</h2>
                    <div class="error">
                        <!-- Mensaje indicando que hay que agregar primero eventos -->
                        ${mensaje6}
                        <!-- Mensaje indicando que hay que agregar primero eventos para poder poner en venta las entradas -->
                        ${eventoEntrada}
                    </div>
                    
                    <form:form class="form-horizontal" method="POST" modelAttribute="evento" >
                        <form:label path="nombre" >Nombre:</form:label><br><form:input cssClass="form-control" path="nombre" />
                        <form:errors cssClass="error" path="nombre"/><br>

                        <form:label path="fecha">Fecha:</form:label><br><form:input cssClass="form-control" type="date" path="fecha"/>
                        <form:errors cssClass="error" path="fecha"/><br> 
                        
                        <form:label path="hora">Hora:</form:label><br><form:input cssClass="form-control" type="time" path="hora" />
                        <form:errors cssClass="error" path="hora"/><br> 

                        <form:label path="lugar">Lugar:</form:label><br><form:input cssClass="form-control" path="lugar" />
                        <form:errors cssClass="error" path="lugar"/><br> 

                        <form:label path="ciudad">Ciudad:</form:label><br><form:input cssClass="form-control" path="ciudad" />
                        <form:errors cssClass="error" path="ciudad"/><br>

                        <div class="form-group">
                            <div class="col-lg-12">
                                <form:label path="pais" >País:</form:label><br>
                                <c:if test="${not empty paises}">
                                    <form:select cssClass="form-control" path="pais.idPais">
                                        <form:option value="0" label="-- Seleccionar país --" />
                                        <form:options items="${paises}" itemLabel="nombre" itemValue="idPais" />
                                    </form:select>
                                </c:if>
                                <form:errors cssClass="error" path="pais"/>
                            </div>
                        </div>

                        <strong><p class="form-control-static">Número de Entradas:</p></strong>
                        <input type="number" name="numEntradas" class="form-control" min="0" value="0"><br>
                        
                    </div>

                    <div class="col-md-1"></div>

                    <div class="col-md-4">
                        <br style="line-height:9">
                        <div class="form-group">
                            <div class="col-lg-12">
                                <form:label path="deporte" >Deporte:</form:label><br>
                                <c:if test="${not empty deportes}">
                                    <form:select cssClass="form-control" path="deporte.idDeporte">
                                        <form:option value="0" label="-- Seleccionar deporte --" />
                                        <form:options items="${deportes}" itemLabel="nombre" itemValue="idDeporte" />
                                    </form:select>
                                </c:if> 
                                <form:errors cssClass="error" path="deporte"/><br>
                            </div>
                        </div>
                        
                        <br style="line-height:4">        

                        <div class="form-group">
                            <div class="col-lg-12">
                                <form:label path="competicion" >Competición:</form:label><br>
                                <c:if test="${not empty competiciones}">
                                    <form:select cssClass="form-control" path="competicion.idCompeticion">
                                        <form:option value="0" label="-- Seleccionar competición --" />
                                        <form:options items="${competiciones}" itemLabel="nombre" itemValue="idCompeticion"/>
                                    </form:select>
                                </c:if>
                                <form:errors cssClass="error" path="competicion"/><br>
                            </div>
                        </div>

                        <br style="line-height:4">

                        <form:label path="participantes">Participantes:</form:label><br>
                            <div class="form-group">
                                <div class="col-lg-10">
                                <form:checkboxes element="li" items="${participantes}" path="participantes" itemLabel="nombre" itemValue="idParticipante"/>
                                <form:errors cssClass="error" path="participantes"/><br>
                            </div>
                        </div>

                        <br style="line-height:8">

                        <div class="form-group">
                            <div class="col-lg-10">
                                <button name="enviar" type="submit" class="btn btn-success">Guardar</button>
                                <button name="enviar" type="reset" class="btn btn-info">Reestablecer</button>
                                <a href="<c:url value='/eventos/listadoEventos'/> "class="btn btn-primary" role="button">Volver</a>
                            </div>
                        </div>
                    </form:form>
                </div>
                <div class="clearfix visible-lg"></div>
            </div>
            <hr>
        </div>

    </body>
</html>
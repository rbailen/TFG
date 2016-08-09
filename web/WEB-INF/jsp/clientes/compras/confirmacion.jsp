<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                    var idCompra = button.data('id');
                    var modal = $(this);
                    modal.find('#btEliminar').attr('href', '<c:url value="/compras/borraCompra/"/>' + idCompra);
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

                <div class="col-md-9">
                    <br>
                    <br>
                    <br>
                    <h1>Entradas Deportes</h1>
                    <hr>
                    <h3>Confirmación de Compra</h3>
                    <hr>
                    <div>
                        <b style="color: black"> Compra nº
                        <p style="color: red"> ${compra.idCompra}<br/>
                    </div>
                    
                    <div>
                        <b style="color: black"> Cliente
                        <p style="color: red"> ${compra.usuario.nombre}<br/>
                    </div>

                    <div>
                        <b style="color: black"> Fecha de Compra
                        <p style="color: red"> ${compra.fechaCompra}<br/>
                    </div>
                    
                    <div>
                        <b style="color: black"> Total
                        <p style="color: red"> ${compra.total} €<br/>
                    </div>
                    
                    <hr>
                    <h4>Tipo de Entrada</h4>
                    <c:forEach var="entrada" items="${entradasCompra}">
                        <c:set var="idEvento" value="${entrada.evento.idEvento}"/>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="jumbotron" style="background: #d0e9c6">
                                        Descripción
                                        <div style="color: red">
                                            <p>${entrada.descripcion}</p>
                                        </div>
                                        
                                        Deporte
                                        <div style="color: red">
                                            <p>${entrada.evento.deporte.nombre}</p>
                                        </div>
                                        
                                        Precio
                                        <div style="color: red">
                                             <p>${entrada.precio} €</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                        <table class='table-bordeless'>
                            <tr class="table-bordeless">
                                <th>
                                    <a class="btn btn-danger" role="button" data-id="${compra.idCompra}" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-remove"></span>&nbsp;Cancelar Compra</a>
                                </th>
                                <th>&nbsp;&nbsp;</th>
                                
                                <th>
                            
                            <!-- Formulario de Pago con Paypal -->
                            <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">
                                
                                <% int var=1;%>
                                
                                <c:forEach var="entrada" items="${entradasCompra}" varStatus="i">
                                    <input type="hidden" name="cmd" value="_cart">
                                    <input type="hidden" name="upload" value="1">
                                    <input type="hidden" name="lc" value="ES">
                                    <input type="hidden" name="currency_code" value="EUR">
                                    <input type="hidden" name="business" value="rb389-facilitator@hotmail.com">

                                    <input type='hidden' name="item_name_<%out.println(var);%>" value="${entrada.descripcion}">
                                    <input type="hidden" name="amount_<%out.println(var);%>" value="${entrada.precio}">

                                    <input type="hidden" name="paymentaction" value="sale">
                                    
                                    <% var = var + 1;%>
                                </c:forEach>
                                    
                                <input width="90" height="32" type="image" src="https://www.paypal.com/es_ES/i/btn/x-click-but02.gif" border="0" name="submit" alt="Realice pagos con PayPal: es rápido, gratis y seguro">
                            </form>
                                
                                </th>
                                
                            </tr>
                        </table>

                    <div class="clearfix visible-lg"></div>
                </div>
                <hr>
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
                            <h4 class="modal-title">Cancelar</h4>
                        </div>
                        <!-- Modal Body -->
                        <div class="modal-body">
                            <h3>¿Desea cancelar la compra?</h3>
                        </div>
                        <!-- Modal Footer -->
                        <div class="modal-footer">
                            <a class="btn btn-danger" id="btEliminar" role="button" href=""/>Sí</a>
                            <button class="btn btn-primary" data-dismiss="modal">No</button>
                        </div>
                    </div>
                </div>
            </div>                

    </body>
</html>
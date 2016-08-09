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
        
        <style type="text/css">
            .borde_discontinuo{
                border-style:dashed;
            } 
    
            
            @media print

            {
                .noprint {display:none;}

                body {
                    font-family: sans-serif;
                    font-size: 20px;
                    color: red;
                }
                
                div.salto_page{
                    display:block;
                    page-break-before:always;
                }
                
            }
            
        </style>
        
        <script language="JavaScript" type="text/javascript">
        window.onload = function () {
            var idButton = document.getElementById("imprimirButton");
            idButton.addEventListener("click", imprimir);
        };
        
        function imprimir() {
            window.print();
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
                <div class="col-md-5">
                    <br>
                    <br>
                    <br>
                    
                    <c:if test="${not empty mensaje}">
                        <div class="error">
                            <p>${mensaje}</p>
                        </div>
                        <a href="<c:url value='/compras/historico/${compra.usuario.idUsuario}'/> "class="btn btn-primary" role="button">Volver</a>
                    </c:if>
                    
                    <c:if test="${empty mensaje}">

                        <div class="noprint">
                            <h2>Confirmación de Pago</h2>
                            <p>Su compra ha sido pagada</p>
                            <p style="color: red">${imprimir}</p>
                        </div>

                        <strong>Id Compra:</strong> ${compra.idCompra}<br>
                        <strong>Usuario:</strong> ${compra.usuario.nombre}<br>
                        <strong>Fecha de Compra:</strong> ${compra.fechaCompra}<br>
                        <strong>Fecha de Pago: </strong> ${compra.fechaPago}<br>
                        <strong>Precio Total:</strong> ${compra.total} €<br>

                        <center>
                            <strong>ENTRADAS</strong>
                        </center>
                        <table>
                            <c:forEach var="entrada" items="${entradasCompra}">
                                <c:set var="varIdEntrada" value="${entrada.idEntrada}"/>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <tr class="borde_discontinuo">
                                                <th>
                                            <div class="jumbotron" style="background: #a6e1ec">
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
                                            </th>
                                            </tr> 

                                            <tr class="borde_discontinuo">
                                                <th>
                                                    <!-- Capturo el código de barras obtenido para la compra -->
                                                    <img src="http://www.qrplanet.com/generador/qr_img.php?d=Id+compra:${compra.idCompra}+-+Id+Usuario:+${compra.usuario.idUsuario}+-+Deporte:+${entrada.evento.deporte.nombre}+-+Precio+entrada:+${entrada.precio}+euros&e=M&color=000000&s=7" />
                                                </th>
                                            </tr>

                                            <tr>
                                                <c:if test="${entrada.idEntrada < varIdEntrada}">
                                                    <th><div class="salto_page"><br><br></div></th>
                                                </c:if>
                                            </tr>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </table>
                        <div class="noprint">
                            <br><br>
                            <button type="button" id="imprimirButton" class="btn btn-inver"><span class="glyphicon glyphicon-print"></span>&nbsp;Imprimir entradas</button>
                            <a href="<c:url value='/compras/historico/${compra.usuario.idUsuario}'/> "class="btn btn-primary" role="button">Volver</a>
                         </div>
                         
                    </c:if>
                    
                </div>
                
                <div class="noprint">
                    <div class="col-md-2"></div>
                </div>             

                <div class="clearfix visible-lg"></div>
            </div>
            <div class="noprint">
                <hr>
            </div>
        </div>

    </body>
</html>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
        
        <style type="text/css">
            .borde_discontinuo{
                border-style:dashed;
            } 
    
            .btn-vertical-slider{ margin-left:35px; cursor:pointer;}
            a {  cursor:pointer;}
            .carousel.vertical .carousel-inner .item {
              -webkit-transition: 0.6s ease-in-out top;
                 -moz-transition: 0.6s ease-in-out top;
                  -ms-transition: 0.6s ease-in-out top;
                   -o-transition: 0.6s ease-in-out top;
                      transition: 0.6s ease-in-out top;
            }

             .carousel.vertical .active {
              top: 0;
            }

             .carousel.vertical .next {
              top: 100%;
            }

             .carousel.vertical .prev {
              top: -100%;
            }

             .carousel.vertical .next.left,
            .carousel.vertical .prev.right {
              top: 0;
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
                                            <th><div class="salto_page"><br><br></div></th>
                                        </tr>
                                        
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </table>
                    
                    <div class="noprint">
                        <br><br>
                        <button type="button" id="imprimirButton" class="btn btn-inver"><span class="glyphicon glyphicon-print"></span>&nbsp;Imprimir entradas</button>
                        <a href="<c:url value='/inicio'/> "class="btn btn-primary" role="button">Volver al inicio</a>
                    </div>
                </div>
                
                <div class="noprint">
                    <div class="col-md-2"></div>
                </div>
                
                <div class="noprint">
                    <div class="col-md-5">
                        <br><br><br>
                        <h2>Sugerencias</h2>
                        <div id="myCarousel" class="vertical-slider carousel vertical slide col-md-12" data-ride="carousel">
                            <div class="row">
                                <div class="col-md-4">
                                    <span data-slide="next" class="btn-vertical-slider glyphicon glyphicon-circle-arrow-up "
                                          style="font-size: 30px"></span>  
                                </div>
                                <div class="col-md-8"> 
                                </div>
                            </div>
                            <br />
                            <!-- Carousel items -->
                            <div class="carousel-inner">
                                <div class="item active">
                                    <div class="row">
                                        <div class="col-xs-6 col-sm-5 col-md-5">
                                            <a href="<c:url value='/deportes/listado'/> "><img src="<c:url value='/resources/imagenes/sports.jpg'/>" class="thumbnail" width="151" height="151"></a> 
                                        </div>
                                        <div class="col-xs-6 col-sm-7 col-md-7">
                                            Compre entradas para alguno de nuestros deportes
                                        </div>
                                    </div>
                                    <!--/row-fluid-->
                                </div>

                                <!--/item-->
                                <c:forEach var="deporte" items="${deportes}">
                                    <div class="item ">
                                        <div class="row">
                                            <div class="col-xs-6 col-sm-5 col-md-5">
                                                <a href="<c:url value='/deportes/verDeporte/${deporte.idDeporte}'/> "><img src="<c:url value='/resources/imagenes/deportes/${deporte.idDeporte}.jpg'/>" class="thumbnail" width="151" height="151"></a> 
                                            </div>
                                            <div class="col-xs-6 col-sm-7 col-md-7">
                                                Compre entradas para ${deporte.nombre}
                                            </div>

                                        </div>
                                        <!--/row-fluid-->
                                    </div>
                                </c:forEach>
                                <!--/item-->
                            </div>
                            <div class="row">
                                <div class="col-md-4">
                                    <span data-slide="prev" class="btn-vertical-slider glyphicon glyphicon-circle-arrow-down"
                                          style="color: Black; font-size: 30px"></span>
                                </div>
                                <div class="col-md-8">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <script language="JavaScript" type="text/javascript">
                   $(document).ready(function () {

                        $('.btn-vertical-slider').on('click', function () {

                            if ($(this).attr('data-slide') === 'next') {
                                $('#myCarousel').carousel('next');
                            }
                            if ($(this).attr('data-slide') === 'prev') {
                                $('#myCarousel').carousel('prev');
                            }

                        });
                    });
                </script>               

                <div class="clearfix visible-lg"></div>
            </div>
            <div class="noprint">
                <hr>
            </div>
        </div>

    </body>
</html>
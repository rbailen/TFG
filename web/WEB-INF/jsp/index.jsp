<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet"  type="text/css" href="<c:url value='/resources/css/common.css'/>">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Venta de Localidades</title>
        <script type='text/javascript'src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <!-- Bootstrap core CSS -->
        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="<c:url value='/resources/css/jumbotron.css'/>" rel="stylesheet">

        <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
        <script src="<c:url value='/resources/assets/js/ie-emulation-modes-warning.js'/>"></script>

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
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
                    <form class="navbar-form navbar-left">
                        <div class="error">
                            <%
                                String error = (String) request.getAttribute("error");
                                if (error != null && error.trim().equals("true")) {
                                    out.println("Los datos suministrados no son válidos. Vuelva a introducirlos");
                                }
                            %>
                        </div>
                    </form>
                </div>

                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right" method="POST" action='j_spring_security_check'>
                        <div class="form-group">
                            <input type='text' name='j_username' placeholder="Usuario" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type='password' name='j_password' placeholder="Contraseña" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Entrar</button>
                        <button type="reset" class="btn btn-info">Restablecer</button>
                    </form>
                </div><!--/.navbar-collapse -->
            </div>
        </nav>

        <div class="container">
            <!-- Example row of columns -->
            <div class="row">
                <br><br>
                <div class="col-md-6">
                    <h2>Venta de Localidades Deportivas</h2>
                    <img src="<c:url value='/resources/imagenes/ticket.jpg'/>" width="350" height="350" class="img-responsive">
                    <p>Trabajo Fin de Grado</p>
                    <p>Curso 2014-2015</p>
                    <p>Grado en Ingeniería Informática</p>
                    <p>Escuela Politécnica Superior de Jaén</p>
                </div>
                <div class="col-md-5">
                    <h2>Regístrese</h2>
                    <form:form class="form-horizontal" method="POST" modelAttribute="cliente" action="registro">
                        <form:label path="nombre" >Nombre:</form:label><br><form:input cssClass="form-control" path="nombre" />
                        <form:errors cssClass="error" path="nombre"/> <br>

                        <form:label path="correo" >Correo:</form:label><br><form:input cssClass="form-control" path="correo" />
                        <form:errors cssClass="error" path="correo"/> <br>

                        <form:label path="ciudad" >Ciudad:</form:label><br><form:input cssClass="form-control" path="ciudad" />
                        <form:errors cssClass="error" path="ciudad"/> <br>

                        <form:label path="usuario" >Usuario:</form:label><br><form:input cssClass="form-control" path="usuario" />
                        <form:errors cssClass="error" path="usuario"/> <br>

                        <form:label path="clave" >Clave:</form:label><br><form:password cssClass="form-control" path="clave" />
                        <form:errors cssClass="error" path="clave"/><br>

                        <form:label path="edad">¿Es mayor de 18 años?</form:label>&nbsp;<form:checkbox path="edad"/>
                        <form:errors cssClass="error" path="edad"/><br>
                        
                        <button name="enviar" type="submit" class="btn btn-success">Guardar</button>
                    </form:form>
                    <br>
                    <div class="form-group" style="color: red">
                        <!-- Mensaje de registro correcto -->
                        <h4>${mensaje}</h4>
                    </div>
                </div>
            </div>

            <hr>

            <footer>
                <p>&copy; Ramón Bailén Sánchez</p>
            </footer>
        </div> <!-- /container -->


        <!-- Bootstrap core JavaScript
            ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <!--
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        -->
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="<c:url value='/resources/assets/js/ie10-viewport-bug-workaround.js'/>"></script>
    </body>
</html>
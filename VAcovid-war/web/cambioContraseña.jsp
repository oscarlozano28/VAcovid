<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : menu
    Created on : 9/04/2021, 03:45:10 PM
    Author     : Cristian Duarte
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    HttpSession objsession = request.getSession(false);
    String usuario = (String)objsession.getAttribute("usuario1");
    if(usuario.equals("")){
        response.sendRedirect("loginUsuario.jsp");
    }
    
        
    
    
 %>

<sql:setDataSource var="bd" driver="org.apache.derby.jdbc.ClientDriver" url="jdbc:derby://localhost:1527/vacovid" user="root" password="admin" />

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="static/style2.css">
    <link rel="stylesheet" href="static/normalize.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="shortcut icon" href="img/flavicon.png">
    <title>VAcovid</title>
</head>

<body>
    <header>
        <div class="header__logo contenedor">
            <a href="menu.jsp">
                <img href="#" src="img/VAcovid_logo.png" alt="Logo VAcovid">
            </a>
        </div>
        <nav class="header__nav contenedor">
            <li class="header__nav__item">
                <a class="header__nav__item__text" href="menu.jsp">Inicio</a>
            </li>
            <li class="header__nav__item">
                <a class="header__nav__item__text" href="covid-19_logiado.jsp">Covid-19</a>
            </li>
            <li class="header__nav__item">
                <a class="header__nav__item__text" href="plan_vacunacion_logiado.jsp">Plan de vacunación</a>
            </li>
            <li class="header__nav__item">
                <a class="header__nav__item__text" href="#">Contacto</a>
            </li class="header__nav__item">
            <li class="header__nav__item">
                <a class="header__nav__item__text profile_boton" href="#">
                    <div class="profile">
                        <sql:query var="inicial" dataSource="${bd}">
                           SELECT substr(u.nombre,1,1)AS nombre FROM USUARIO u WHERE identificacion =<%=usuario%>
                        </sql:query>  
                        <c:forEach var = "row" items = "${inicial.rows}">
                            <h3>${row.nombre}</h3>
                        </c:forEach>
                    </div>
                </a>
            </li>
        </nav>
    </header>

    <main class="main__plan__cc">
        <div class="container__usuario plan_logiado">
            <div class="usuario__opcion">
                <a href=""> <h2>Cambiar contraseña</h2></a>
            </div>
            <div class="usuario__opcion">
                <a href="actualizarDatos.jsp"><h2>Actualizar información</h2></a>
            </div>
            <div class="usuario__opcion">
                <h2>Logout</h2>
            </div>
        </div>
        <div class="main__plan__content">
            <h1>CAMBIAR CONTRASEÑA</h1>
        </div>
    </main>

    <div class="form_contraseña">
        <form class="form_con" action="./CambioContrase_a" method="POST">
            <fieldset>
                <div class="titulo_form">
                    <h2>Cambio de contraseña</h2>
                </div>
                <div class="form__contentCon">
                    <div class="form__contentCon__campo">
                        <h3>Contraseña Actual</h3>
                        <input type="password" name="contraActual" required />
                    </div>
                    <div class="form__contentCon__campo">
                        <h3>Nueva contraseña</h3>
                        <input type="password" name="contraNueva"  required />
                    </div>
                    <div class="form__contentCon__campo">
                        <h3>Confirmar nueva contraseña:</h3>
                        <input type="password" name="contraConfirmar"  required />
                    </div>
                    <div class="form__contentCon__campo">
                        <input id="button_cam" type="submit" name="action" value="Enviar" required />
                    </div>
                </div>
            </fieldset>
        </form>

    </div>
    <footer>
        <div class="footer__logo contenedor">
            <img src="img/VAcovid_logo.png" alt="Logo VAcovid">
        </div>
        <div class="footer__text">
            <h4>Copyright @ 2021 VAcovid</h4>
        </div>
    </footer>
    <script src="js/opciones_usuario.js"></script>
</body>
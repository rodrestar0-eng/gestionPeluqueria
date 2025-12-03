<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/usuario">Peluquería Gestión</a>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">

                <!-- PERFIL -->
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/usuario?accion=perfil">
                        Mi Perfil
                    </a>
                </li>

                <% if (usuario.getTipoUsuario() == 2) { %>

                    <!-- HORARIOS -->
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/horarios?idTrabajador=<%=usuario.getIdUsuario()%>">
                            Gestionar Horarios
                        </a>
                    </li>

                    <!-- BLOQUEOS (ajusta si tu servlet es otro) -->
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/bloqueos?idTrabajador=<%=usuario.getIdUsuario()%>">
                            Gestionar Bloqueos
                        </a>
                    </li>

                    <!-- MIS CITAS -->
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/cita?accion=misCitasPeluquero&idTrabajador=<%=usuario.getIdUsuario()%>">
                            Mis Citas
                        </a>
                    </li>

                <% } %>

            </ul>

            <!-- LOGOUT CORRECTO -->
            <form action="<%= request.getContextPath() %>/usuario" method="get" class="d-flex">
                <input type="hidden" name="accion" value="cerrarSesion">
                <button type="submit" class="btn btn-outline-light">Logout</button>
            </form>

        </div>
    </div>
</nav>

<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h1 class="card-title">Bienvenido, <%= usuario.getNombre() %>!</h1>
            <p class="card-text">Selecciona una opción del menú superior.</p>
        </div>
    </div>
</div>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Cliente" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect(request.getContextPath() + "/jspCliente/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel del Cliente</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-4">

    <h2 class="fw-bold mb-4">Bienvenido, <%= cliente.getNombre() %></h2>

    <div class="row g-4">

        <!-- Nueva Cita -->
        <div class="col-md-4">
            <div class="card shadow-sm text-center p-4">
                <h5 class="fw-bold">Reservar una nueva cita</h5>
                <p>Elige servicio, peluquero, fecha y hora.</p>
                <a href="<%= request.getContextPath() %>/cita?accion=misCitasCliente" 
                   class="btn btn-primary w-100">
                    Reservar ahora
                </a>
            </div>
        </div>

        <!-- Mis Citas -->
        <div class="col-md-4">
            <div class="card shadow-sm text-center p-4">
                <h5 class="fw-bold">Mis Citas</h5>
                <p>Consulta tus citas pasadas y futuras.</p>
                <a href="<%= request.getContextPath() %>/cita?accion=misCitasCliente" 
                   class="btn btn-success w-100">
                    Ver citas
                </a>
            </div>
        </div>

        <!-- Perfil -->
        <div class="col-md-4">
            <div class="card shadow-sm text-center p-4">
                <h5 class="fw-bold">Mi perfil</h5>
                <p>Consulta y edita tus datos personales.</p>
                <a href="<%= request.getContextPath() %>/cliente?accion=perfil"
                   class="btn btn-secondary w-100">
                    Ver perfil
                </a>
            </div>
        </div>

    </div>

    <div class="mt-4 text-center">
        <a href="<%= request.getContextPath() %>/cliente?accion=cerrarSesion" 
           class="btn btn-danger">
            Cerrar sesión
        </a>
    </div>

</div>

</body>
</html>

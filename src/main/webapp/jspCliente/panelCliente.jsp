<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Cliente" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel del Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .card:hover {
            transform: translateY(-5px);
            transition: transform 0.3s ease;
        }
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
    </style>
</head>

<body class="bg-light">
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold text-center">Panel del Cliente</h1>
            <p class="lead text-center">Bienvenido, <%= cliente.getNombre() %>. Gestiona tus citas y perfil fácilmente.</p>
        </div>
    </div>

    <div class="container">
        <div class="row g-4">
            <!-- Nueva Cita -->
            <div class="col-md-4">
                <div class="card shadow-sm text-center p-4 h-100">
                    <div class="card-body d-flex flex-column">
                        <i class="bi bi-calendar-plus-fill text-primary mb-3" style="font-size: 3rem;"></i>
                        <h5 class="card-title fw-bold">Reservar una nueva cita</h5>
                        <p class="card-text">Elige servicio, peluquero, fecha y hora.</p>
                        <a href="<%= request.getContextPath() %>/cita?accion=crearFormulario" class="btn btn-primary mt-auto w-100">
                            Reservar ahora
                        </a>
                    </div>
                </div>
            </div>

            <!-- Mis Citas -->
            <div class="col-md-4">
                <div class="card shadow-sm text-center p-4 h-100">
                    <div class="card-body d-flex flex-column">
                        <i class="bi bi-calendar-check-fill text-success mb-3" style="font-size: 3rem;"></i>
                        <h5 class="card-title fw-bold">Mis Citas</h5>
                        <p class="card-text">Consulta tus citas pasadas y futuras.</p>
                        <a href="<%= request.getContextPath() %>/cita?accion=misCitasCliente" class="btn btn-success mt-auto w-100">
                            Ver citas
                        </a>
                    </div>
                </div>
            </div>

            <!-- Perfil -->
            <div class="col-md-4">
                <div class="card shadow-sm text-center p-4 h-100">
                    <div class="card-body d-flex flex-column">
                        <i class="bi bi-person-circle text-secondary mb-3" style="font-size: 3rem;"></i>
                        <h5 class="card-title fw-bold">Mi perfil</h5>
                        <p class="card-text">Consulta y edita tus datos personales.</p>
                        <a href="<%= request.getContextPath() %>/cliente?accion=perfil" class="btn btn-secondary mt-auto w-100">
                            Ver perfil
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-5 text-center">
            <a href="<%= request.getContextPath() %>/cliente?accion=cerrarSesion" class="btn btn-danger btn-lg">
                <i class="bi bi-box-arrow-right me-2"></i>Cerrar sesión
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
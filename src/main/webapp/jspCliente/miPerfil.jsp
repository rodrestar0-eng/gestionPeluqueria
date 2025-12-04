<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Cliente" %>

<%
Cliente cliente = (Cliente) session.getAttribute("cliente");
if (cliente == null) {
    response.sendRedirect(request.getContextPath() + "/cliente?accion=perfil");
    return;
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
            color: #333;
            padding: 1.5rem 0;
            margin-bottom: 1.5rem;
        }
        .form-card {
            border: none;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .form-card:hover {
            box-shadow: 0 8px 16px rgba(0,0,0,0.2);
            transition: box-shadow 0.3s ease;
        }
        .mb-3-reduced {
            margin-bottom: 1rem !important;
        }
    </style>
</head>

<body class="bg-light">
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold text-center">Mi Perfil</h1>
            <p class="lead text-center">Actualiza tus datos personales y gestiona tu cuenta.</p>
        </div>
    </div>

    <div class="container">
        <a href="cliente?accion=panel" class="btn btn-secondary mb-3">
            <i class="bi bi-arrow-left me-2"></i>Volver al inicio
        </a>

        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>
                <%= request.getAttribute("mensaje") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <%= request.getAttribute("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <div class="card form-card p-4">
                    <div class="card-body">
                        <form action="cliente" method="post">
                            <input type="hidden" name="accion" value="actualizar">
                            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

                            <!-- Nombre y Apellido en fila -->
                            <div class="row">
                                <div class="col-md-6 mb-3-reduced">
                                    <label class="form-label">
                                        <i class="bi bi-person-fill text-primary me-2"></i>Nombre:
                                    </label>
                                    <input type="text" name="nombre" class="form-control" value="<%= cliente.getNombre() %>" required>
                                </div>
                                <div class="col-md-6 mb-3-reduced">
                                    <label class="form-label">
                                        <i class="bi bi-person-fill-add text-primary me-2"></i>Apellido:
                                    </label>
                                    <input type="text" name="apellido" class="form-control" value="<%= cliente.getApellido() %>" required>
                                </div>
                            </div>

                            <!-- Teléfono y Email en fila -->
                            <div class="row">
                                <div class="col-md-6 mb-3-reduced">
                                    <label class="form-label">
                                        <i class="bi bi-telephone-fill text-success me-2"></i>Teléfono:
                                    </label>
                                    <input type="tel" name="telefono" class="form-control" value="<%= cliente.getTelefono() %>" required>
                                </div>
                                <div class="col-md-6 mb-3-reduced">
                                    <label class="form-label">
                                        <i class="bi bi-envelope-fill text-warning me-2"></i>Email:
                                    </label>
                                    <input type="email" name="email" class="form-control" value="<%= cliente.getEmail() %>" required>
                                </div>
                            </div>

                            <!-- Cambiar contraseña -->
                            <div class="mb-3">
                                <label class="form-label">
                                    <i class="bi bi-lock-fill text-danger me-2"></i>Nueva contraseña (opcional):
                                </label>
                                <input type="password" name="contrasena" class="form-control" placeholder="Dejar en blanco para mantener la actual">
                            </div>

                            <div class="d-grid mt-3">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-save-fill me-2"></i>Guardar Cambios
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Eliminar cuenta -->
        <div class="text-center mt-3">
            <a class="btn btn-outline-danger btn-lg" href="cliente?accion=eliminar" onclick="return confirm('¿Seguro que deseas eliminar tu cuenta? Esta acción no se puede deshacer')">
                <i class="bi bi-trash-fill me-2"></i>Eliminar mi cuenta
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
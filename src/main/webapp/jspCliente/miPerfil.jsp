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

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <h2 class="fw-bold mb-4">👤 Mi Perfil</h2>

<a href="cliente?accion=panel" class="btn btn-secondary mb-3">⬅ Volver al inicio</a>
    <% if (request.getAttribute("mensaje") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("mensaje") %>
        </div>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <form action="cliente" method="post" class="card p-4 shadow-sm">

        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

        <!-- Nombre -->
        <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control"
                   value="<%= cliente.getNombre() %>" required>
        </div>

        <!-- Apellido -->
        <div class="mb-3">
            <label class="form-label">Apellido:</label>
            <input type="text" name="apellido" class="form-control"
                   value="<%= cliente.getApellido() %>" required>
        </div>

        <!-- Teléfono -->
        <div class="mb-3">
            <label class="form-label">Teléfono:</label>
            <input type="tel" name="telefono" class="form-control"
                   value="<%= cliente.getTelefono() %>" required>
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label class="form-label">Email:</label>
            <input type="email" name="email" class="form-control"
                   value="<%= cliente.getEmail() %>" required>
        </div>

        <!-- Cambiar contraseña -->
        <div class="mb-3">
            <label class="form-label">Nueva contraseña (opcional):</label>
            <input type="password" name="contrasena" class="form-control"
                   placeholder="Dejar en blanco para mantener la actual">
        </div>

        <button type="submit" class="btn btn-primary w-100 mt-2">
            Guardar Cambios
        </button>

    </form>

    <!-- Eliminar cuenta -->
    <div class="text-end mt-3">
        <a class="btn btn-outline-danger"
           href="cliente?accion=eliminar"
           onclick="return confirm('¿Seguro que deseas eliminar tu cuenta? Esta acción no se puede deshacer')">
            🗑 Eliminar mi cuenta
        </a>
    </div>

</div>

</body>
</html>

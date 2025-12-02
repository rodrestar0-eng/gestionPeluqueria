<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro Cliente - Peluquería</title>

    <!-- BOOTSTRAP 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f8f9fa;
        }
        .card {
            border-radius: 15px;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand fw-bold">Peluquería</a>
    </div>
</nav>

<!-- REGISTER FORM -->
<div class="container d-flex justify-content-center align-items-center" style="height: 90vh;">
    <div class="col-md-6">
        <div class="card shadow p-4">

            <h3 class="text-center fw-bold mb-3">Registro de Cliente</h3>
            <p class="text-center text-muted mb-4">Crea tu cuenta para reservar citas</p>

            <!-- Mensaje de error -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-danger text-center"><%= error %></div>
            <% } %>

            <form action="<%= request.getContextPath() %>/app" method="post">
                <input type="hidden" name="accion" value="registrarCliente">

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Apellidos</label>
                        <input type="text" name="apellidos" class="form-control" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Teléfono</label>
                    <input type="text" name="telefono" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Contraseña</label>
                        <input type="password" name="contrasena" class="form-control" required>
                    </div>

                    <div class="col-md-6 mb-4">
                        <label class="form-label">Repite la Contraseña</label>
                        <input type="password" name="repetirContrasena" class="form-control" required>
                    </div>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-success btn-lg">Crear Cuenta</button>
                </div>
            </form>

            <div class="text-center mt-4">
                ¿Ya tienes cuenta? <a href="login.jsp" class="fw-bold">Inicia sesión</a>
            </div>

        </div>
    </div>
</div>

</body>
</html>

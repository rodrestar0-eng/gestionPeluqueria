<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Peluquería</title>

    <!-- BOOTSTRAP 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f2f2f2;
        }
        .card {
            border-radius: 15px;
        }
        .logo {
            font-size: 30px;
            font-weight: bold;
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

<!-- FORM LOGIN -->
<div class="container d-flex justify-content-center align-items-center" style="height: 80vh;">
    <div class="col-md-5">
        <div class="card shadow p-4">

            <h3 class="text-center mb-3 fw-bold">Iniciar Sesión</h3>
            <p class="text-center text-muted mb-4">Clientes y empleados inician sesión aquí</p>

            <!-- Mensaje de error -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="alert alert-danger text-center"><%= error %></div>
            <% } %>

            <!-- LOGIN ÚNICO -->
            <form action="MainServlet" method="post">

                <input type="hidden" name="accion" value="login">

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Contraseña</label>
                    <input type="password" name="contrasena" class="form-control" required>
                </div>

                <div class="d-grid mt-4">
                    <button type="submit" class="btn btn-primary btn-lg">Entrar</button>
                </div>
            </form>

            <div class="text-center mt-4">
                ¿No tienes cuenta como cliente?
                <a href="registroCliente.jsp" class="fw-bold">Registrarse</a>
            </div>

        </div>
    </div>
</div>

</body>
</html>

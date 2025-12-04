<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Peluquería</title>

    <!-- BOOTSTRAP 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ICONS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .navbar {
            background: rgba(0, 0, 0, 0.8) !important;
            backdrop-filter: blur(10px);
        }
        .card {
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
            border: none;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
        }
        .form-control {
            border-radius: 10px;
            border: 1px solid #ddd;
            padding-left: 40px;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .input-group-text {
            background: transparent;
            border: none;
            color: #667eea;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 10px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
        }
        .alert {
            border-radius: 10px;
        }
        .logo {
            font-size: 30px;
            font-weight: bold;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow fixed-top">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3" href="<%= request.getContextPath() %>/jsp/index.jsp"><i class="bi bi-scissors me-2"></i>Peluquería</a>
        <a class="navbar-brand fw-bold" href="<%= request.getContextPath() %>/jsp/index.jsp"><i class="bi bi-arrow-left me-2"></i>Volver</a>
    </div>
</nav>

<!-- FORM LOGIN -->
<div class="container d-flex justify-content-center align-items-center flex-grow-1" style="padding-top: 70px;">
    <div class="col-md-5">
        <div class="card shadow p-5">

            <div class="text-center mb-4">
                <i class="bi bi-person-circle text-primary fs-1 mb-3"></i>
                <h3 class="fw-bold">Iniciar Sesión</h3>
                <p class="text-muted">Clientes y empleados inician sesión aquí</p>
            </div>

            <!-- Si viene un error desde MainServlet -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="alert alert-danger text-center"><i class="bi bi-exclamation-triangle me-2"></i><%= error %></div>
            <% } %>

            <form action="<%= request.getContextPath() %>/app" method="post">

                <input type="hidden" name="accion" value="login">

                <div class="mb-3">
                    <label class="form-label fw-bold">Email</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                        <input type="email" name="email" class="form-control" placeholder="tuemail@ejemplo.com" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Contraseña</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-lock"></i></span>
                        <input type="password" name="contrasena" class="form-control" placeholder="Tu contraseña" required>
                    </div>
                </div>

                <div class="d-grid mt-4">
                    <button type="submit" class="btn btn-primary btn-lg"><i class="bi bi-box-arrow-in-right me-2"></i>Entrar</button>
                </div>
            </form>

            <div class="text-center mt-4">
                ¿No tienes cuenta como cliente?  
                <a href="<%= request.getContextPath() %>/jsp/registroCliente.jsp" class="fw-bold text-primary"><i class="bi bi-person-plus me-1"></i>Registrarse</a>
            </div>

        </div>
    </div>
</div>

<!-- BOOTSTRAP JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
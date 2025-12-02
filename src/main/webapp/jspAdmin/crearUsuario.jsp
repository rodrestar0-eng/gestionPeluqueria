<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>

<%
    // Seguridad: solo admin
    Usuario admin = (Usuario) session.getAttribute("usuario");
    if (admin == null || admin.getTipoUsuario() != 1) {
        response.sendRedirect("/jsp/login.jsp");
        return;
    }

    // Especialidades pasadas desde AdminController
    List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Usuario</title>

    <style>
        body { font-family: Arial; padding: 15px; }
        .form-box {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            border: 1px solid #ccc;
            width: 450px;
        }
        input, select {
            width: 100%;
            padding: 6px;
            margin-top: 8px;
            margin-bottom: 15px;
        }
        button {
            background: #0275d8;
            color: white;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background: #01549b;
        }
        .hidden { display: none; }
    </style>

    <script>
        function mostrarEspecialidades() {
            let tipo = document.getElementById("tipoUsuario").value;
            let div = document.getElementById("box-especialidades");

            if (tipo === "2") div.style.display = "block";
            else div.style.display = "none";
        }
    </script>

</head>

<body>

<h2>Crear Nuevo Usuario</h2>

<a href="admin?accion=panel">⬅ Volver al Panel</a>

<br><br>

<div class="form-box">

<form action="admin" method="post">
    <input type="hidden" name="accion" value="crearUsuario">

    <label>Nombre:</label>
    <input type="text" name="nombre" required>

    <label>Apellido:</label>
    <input type="text" name="apellido" required>

    <label>Email:</label>
    <input type="email" name="email" required>

    <label>Contraseña:</label>
    <input type="password" name="contrasena" required>

    <label>Teléfono:</label>
    <input type="text" name="telefono">

    <label>Tipo de Usuario:</label>
    <select name="tipoUsuario" id="tipoUsuario" onchange="mostrarEspecialidades()" required>
        <option value="">Seleccione...</option>
        <option value="1">Administrador</option>
        <option value="3">Recepcionista</option>
        <option value="2">Peluquero / Trabajador</option>
    </select>

    <!-- ESPECIALIDADES SOLO SI TIPO = 2 -->
    <div id="box-especialidades" class="hidden">
        <label>Especialidades del trabajador:</label>

        <%
            if (especialidades != null) {
                for (Especialidad e : especialidades) {
        %>
            <div>
                <input type="checkbox" name="especialidades" value="<%= e.getIdEspecialidad() %>">
                <%= e.getNombre() %>
            </div>
        <%
                }
            }
        %>
    </div>

    <br>
    <button type="submit">Crear Usuario</button>

</form>

</div>

</body>
</html>

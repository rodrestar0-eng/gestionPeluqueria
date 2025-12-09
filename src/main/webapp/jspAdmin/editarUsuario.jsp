<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>

<%
    // Seguridad: solo admin
    Usuario admin = (Usuario) session.getAttribute("usuario");
    if (admin == null || admin.getTipoUsuario() != 1) {
        response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
        return;
    }

    // Datos enviados desde el AdminController
    Usuario usuarioEditar = (Usuario) request.getAttribute("usuarioEdit");
    List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
    List<Integer> especialidadesUsuario = (List<Integer>) request.getAttribute("especialidadesTrabajador");
    String error = (String) request.getAttribute("error");

%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>

    <style>
        body { font-family: Arial; padding: 15px; }
        .form-box {
            background: #f8f9fa; padding: 15px; border-radius: 8px;
            border: 1px solid #ccc; width: 450px;
        }
        input, select { width: 100%; padding: 6px; margin-top: 8px; margin-bottom: 15px; }
        button {
            background: #0275d8; color: white; border: none;
            padding: 10px 15px; cursor: pointer; border-radius: 5px;
        }
        button:hover { background: #01549b; }
        .hidden { display: none; }
    </style>

    <script>
        function mostrarEspecialidades() {
            let tipo = document.getElementById("tipoUsuario").value;
            let box = document.getElementById("box-especialidades");
            box.style.display = (tipo === "2") ? "block" : "none";
        }
    </script>
</head>

<body>

<h2>Editar Usuario</h2>
<% if (error != null) { %>
    <div style="background:#f8d7da; color:#721c24; padding:10px; border-radius:5px; border:1px solid #f5c6cb; margin-bottom:15px;">
        <strong>Error:</strong> <%= error %>
    </div>
<% } %>

<a href="admin?accion=listarUsuarios">⬅ Volver al Panel</a>
<br><br>

<div class="form-box">
    <form action="admin" method="post">

        <input type="hidden" name="accion" value="actualizarUsuario">
        <input type="hidden" name="idUsuario" value="<%= usuarioEditar.getIdUsuario() %>">

        <label>Nombre:</label>
        <input type="text" name="nombre" value="<%= usuarioEditar.getNombre() %>" required>

        <label>Apellido:</label>
        <input type="text" name="apellido" value="<%= usuarioEditar.getApellido() %>" required>

        <label>Email:</label>
        <input type="email" name="email" value="<%= usuarioEditar.getEmail() %>" required>

        <label>Teléfono:</label>
        <input type="text" name="telefono" value="<%= usuarioEditar.getTelefono() %>">

        <label>Tipo de Usuario:</label>
        <select name="tipoUsuario" id="tipoUsuario" onchange="mostrarEspecialidades()" required>
            <option value="1" <%= usuarioEditar.getTipoUsuario() == 1 ? "selected" : "" %>>Administrador</option>
            <option value="3" <%= usuarioEditar.getTipoUsuario() == 3 ? "selected" : "" %>>Recepcionista</option>
            <option value="2" <%= usuarioEditar.getTipoUsuario() == 2 ? "selected" : "" %>>Peluquero / Trabajador</option>
        </select>

        <!-- ESPECIALIDADES SOLO SI TIPO = 2 -->
      


        <br>
        <button type="submit">Guardar Cambios</button>
    </form>
</div>

</body>
</html>

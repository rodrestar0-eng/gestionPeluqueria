<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lista cargada desde el controlador
        List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
    %>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>Mi Perfil</h2>
            </div>
            <div class="card-body">

                <!-- AHORA USA actualizarMiPerfil -->
                <form method="post" action="<%= request.getContextPath() %>/usuario">
                    
                    <!-- Acción correcta -->
                    <input type="hidden" name="accion" value="actualizarMiPerfil">

                    <div class="mb-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" class="form-control" name="nombre"
                               value="<%= usuario.getNombre() %>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Apellido</label>
                        <input type="text" class="form-control" name="apellido"
                               value="<%= usuario.getApellido() %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control" name="email"
                               value="<%= usuario.getEmail() %>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Teléfono</label>
                        <input type="text" class="form-control" name="telefono"
                               value="<%= usuario.getTelefono() %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Especialidades</label>
                        <ul class="list-group">
                            <%
                                if (especialidades != null) {
                                    for (Especialidad esp : especialidades) {
                            %>
                                <li class="list-group-item"><%= esp.getNombre() %></li>
                            <%
                                    }
                                }
                            %>
                        </ul>
                    </div>

                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>

                    <a href="<%= request.getContextPath() %>/usuario?accion=inicio"
                       class="btn btn-secondary">Volver</a>

                </form>
            </div>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Cita" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Citas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != 2) {
            response.sendRedirect("indexUsuario.jsp");
            return;
        }
        List<Cita> misCitas = (List<Cita>) request.getAttribute("misCitas");
    %>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>Historial de Citas</h2>
            </div>
            <div class="card-body">
                <table class="table table-striped">
                    <thead>
                        <tr><th>ID Cita</th><th>ID Cliente</th><th>ID Servicio</th><th>Fecha</th><th>Hora Inicio</th><th>Hora Fin</th><th>Estado</th><th>Acción</th></tr>
                    </thead>
                    <tbody>
                        <%
                            if (misCitas != null) {
                                for (Cita cita : misCitas) {
                        %>
                            <tr>
                                <td><%= cita.getIdCita() %></td>
                                <td><%= cita.getIdCliente() %></td>
                                <td><%= cita.getIdServicio() %></td>
                                <td><%= cita.getFechaCita() %></td>
                                <td><%= cita.getHoraInicio() %></td>
                                <td><%= cita.getHoraFin() %></td>
                                <td><%= cita.getEstado() == 1 ? "Pendiente" : cita.getEstado() == 2 ? "Completada" : "Cancelada" %></td>
                                <td>
                                    <form method="post" action="CitaServlet" class="d-inline">
                                        <input type="hidden" name="action" value="updateEstado">
                                        <input type="hidden" name="idCita" value="<%= cita.getIdCita() %>">
                                        <select name="nuevoEstado" class="form-select d-inline w-auto me-2">
                                            <option value="1" <%= cita.getEstado() == 1 ? "selected" : "" %>>Pendiente</option>
                                            <option value="2" <%= cita.getEstado() == 2 ? "selected" : "" %>>Completada</option>
                                            <option value="3" <%= cita.getEstado() == 3 ? "selected" : "" %>>Cancelada</option>
                                        </select>
                                        <button type="submit" class="btn btn-primary btn-sm">Actualizar</button>
                                    </form>
                                </td>
                            </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
					<a href="<%= request.getContextPath() %>/usuario?accion=inicio" 
   						class="btn btn-secondary">Volver</a>            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
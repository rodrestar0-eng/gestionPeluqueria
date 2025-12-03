<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.BloqueoPeluquero" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Bloqueos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != 2) {
            response.sendRedirect("indexUsuario.jsp");
            return;
        }
        List<BloqueoPeluquero> bloqueos = (List<BloqueoPeluquero>) request.getAttribute("bloqueos");
    %>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>Gestionar Bloqueos (Vacaciones)</h2>
            </div>
            <div class="card-body">
               <form method="post" action="<%= request.getContextPath() %>/bloqueos">
    <input type="hidden" name="accion" value="crear">
    <input type="hidden" name="idTrabajador" value="<%= usuario.getIdUsuario() %>">
    
    <div class="mb-3">
        <label for="fechaInicio" class="form-label">Fecha Inicio</label>
        <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" required>
    </div>

    <div class="mb-3">
        <label for="fechaFin" class="form-label">Fecha Fin</label>
        <input type="date" class="form-control" id="fechaFin" name="fechaFin" required>
    </div>

    						<div class="mb-3">
        					<label for="motivo" class="form-label">Motivo</label>
      				  		<textarea class="form-control" id="motivo" name="motivo"></textarea>
   							 </div>

  						  <button type="submit" class="btn btn-primary">Agregar Bloqueo</button>
						</form>

            </div>
        </div>
        
        <div class="card mt-4">
            <div class="card-header">
                <h3>Bloqueos Existentes</h3>
            </div>
            <div class="card-body">
                <table class="table table-striped">
                    <thead>
                        <tr><th>ID</th><th>Fecha Inicio</th><th>Fecha Fin</th><th>Motivo</th><th>Acción</th></tr>
                    </thead>
                    <tbody>
                        <%
                            if (bloqueos != null) {
                                for (BloqueoPeluquero bloqueo : bloqueos) {
                        %>
                            <tr>
                                <td><%= bloqueo.getIdBloqueo() %></td>
                                <td><%= bloqueo.getFechaInicio() %></td>
                                <td><%= bloqueo.getFechaFin() %></td>
                                <td><%= bloqueo.getMotivo() %></td>
                                <td>
                                    <form method="post" action="<%= request.getContextPath() %>/bloqueos" class="d-inline">
   									 <input type="hidden" name="accion" value="eliminar">
    								<input type="hidden" name="idBloqueo" value="<%= bloqueo.getIdBloqueo() %>">
    								<input type="hidden" name="idTrabajador" value="<%= usuario.getIdUsuario() %>">
    								<button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="es.peluqueria.gestion.modelo.Servicio" %>

<%
    Servicio s = (Servicio) request.getAttribute("servicio");
Integer idEsp = (Integer) request.getAttribute("idEspecialidad");
java.util.List<es.peluqueria.gestion.modelo.Especialidad> especialidades = 
     (java.util.List<es.peluqueria.gestion.modelo.Especialidad>) request.getAttribute("especialidades");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Servicio</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="p-4">

<h3>Editar Servicio</h3>

<form action="admin" method="post" class="mt-3">

    <input type="hidden" name="accion" value="actualizarServicio">
    <input type="hidden" name="idServicio" value="<%= s.getIdServicio() %>">

    <label>Nombre</label>
    <input type="text" name="nombre" value="<%= s.getNombre() %>" class="form-control" required>

    <label class="mt-2">Precio (€)</label>
    <input type="number" step="0.01" name="precio"
           value="<%= s.getPrecio() %>" class="form-control" required>

    <label class="mt-2">Duración (minutos)</label>
    <input type="number" name="duracion" min="1"
           value="<%= s.getDuracionMinutos() %>" class="form-control" required>
           <label class="mt-2">Especialidad</label>
<select name="idEspecialidad" class="form-control" required>
    <% 
        for (es.peluqueria.gestion.modelo.Especialidad esp : especialidades) {
            boolean selected = (idEsp != null && idEsp == esp.getIdEspecialidad());
    %>
        <option value="<%= esp.getIdEspecialidad() %>" <%= selected ? "selected" : "" %>>
            <%= esp.getNombre() %>
        </option>
    <% } %>
</select>
           

    <label class="mt-2">Descripción</label>
    <textarea name="descripcion" class="form-control"><%= s.getDescripcion() %></textarea>

    <button class="btn btn-primary mt-3">Guardar Cambios</button>
    <a href="admin?accion=listarServicios" class="btn btn-secondary mt-3">Volver</a>
</form>

</body>
</html>

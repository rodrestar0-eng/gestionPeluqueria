<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Servicio</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="p-4">

<h3>Crear Nuevo Servicio</h3>

<form action="admin" method="post" class="mt-3">

    <input type="hidden" name="accion" value="crearServicio">

    <label>Nombre</label>
    <input type="text" name="nombre" class="form-control" required>

    <label class="mt-2">Precio (€)</label>
    <input type="number" name="precio" class="form-control" step="0.01" min="0.1" required>

    <label class="mt-2">Duración (minutos)</label>
    <input type="number" name="duracion" class="form-control" min="1" required>
    

    <label class="mt-2">Descripción</label>
    <textarea name="descripcion" class="form-control"></textarea>
    <label class="mt-2">Especialidad</label>
<select name="idEspecialidad" class="form-control" required>
    <option value="">-- Seleccionar especialidad --</option>
    <% 
        java.util.List<es.peluqueria.gestion.modelo.Especialidad> especialidades = 
            (java.util.List<es.peluqueria.gestion.modelo.Especialidad>) request.getAttribute("especialidades");

        for (es.peluqueria.gestion.modelo.Especialidad esp : especialidades) {
    %>
        <option value="<%= esp.getIdEspecialidad() %>">
            <%= esp.getNombre() %>
        </option>
    <% } %>
</select>
    

    <button class="btn btn-success mt-3">Crear Servicio</button>
    <a href="admin?accion=panel" class="btn btn-secondary mt-3">Volver</a>
</form>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>

<%
    Especialidad esp = (Especialidad) request.getAttribute("especialidad");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Editar Especialidad</title>

<style>
    body { font-family: Arial; }
    label { display:block; margin-top:10px; }
    input[type="text"] { width: 300px; padding: 6px; }
    .btn {
        margin-top: 15px;
        padding: 8px 14px;
        background: #0275d8;
        color: white;
        border-radius: 5px;
        text-decoration:none;
    }
    .btn:hover {
        background: #01549b;
    }
</style>

<script>
function validar() {
    const nombre = document.getElementById("nombre").value.trim();
    if (nombre === "") {
        alert("El nombre no puede estar vacío");
        return false;
    }
    return confirm("¿Confirmar cambios?");
}
</script>

</head>
<body>

<h2>Editar Especialidad</h2>

<form action="admin" method="POST" onsubmit="return validar();">
    <input type="hidden" name="accion" value="actualizarEspecialidad">
    <input type="hidden" name="idEspecialidad" value="<%= esp.getIdEspecialidad() %>">

    <label>Nombre:</label>
    <input type="text" id="nombre" name="nombre" value="<%= esp.getNombre() %>">

    <br>

    <button type="submit" class="btn">Guardar</button>
    <a class="btn" href="admin?accion=listarEspecialidades">Volver</a>
</form>

</body>
</html>

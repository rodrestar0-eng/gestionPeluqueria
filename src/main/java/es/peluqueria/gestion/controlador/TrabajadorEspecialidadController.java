package es.peluqueria.gestion.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.peluqueria.gestion.servicio.TrabajadorEspecialidadService;
import es.peluqueria.gestion.servicio.EspecialidadService;
import es.peluqueria.gestion.modelo.Especialidad;
import es.peluqueria.gestion.modelo.TrabajadorEspecialidad;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/trabajador-especialidades")
public class TrabajadorEspecialidadController extends HttpServlet {

    private TrabajadorEspecialidadService service = new TrabajadorEspecialidadService();
    private EspecialidadService especialidadService = new EspecialidadService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        int idTrabajador = Integer.parseInt(req.getParameter("idTrabajador"));

        // 1️ Cargar especialidades asignadas al trabajador
        List<TrabajadorEspecialidad> asignadas = service.obtenerEspecialidadesPorTrabajador(idTrabajador);

     // Convertir IDs → Especialidad completa
     List<Especialidad> especialidadesConNombre = new ArrayList<>();

     for (TrabajadorEspecialidad te : asignadas) {
         Especialidad esp = especialidadService.obtenerPorId(te.getIdEspecialidad());
         if (esp != null) {
             especialidadesConNombre.add(esp);
         }
     }

     req.setAttribute("especialidades", especialidadesConNombre);

        // 2️ Cargar TODAS las especialidades (necesarias para el <select>)
        List<Especialidad> todas = especialidadService.listarTodas();
        req.getSession().setAttribute("listaTodasEspecialidades", todas);

        // 3️ Enviar al JSP
        req.getRequestDispatcher("jspAdmin/trabajador_especialidades.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        int idTrabajador = Integer.parseInt(req.getParameter("idTrabajador"));
        int idEspecialidad = Integer.parseInt(req.getParameter("idEspecialidad"));

        if ("asignar".equals(accion)) {
            service.asignarEspecialidad(idTrabajador, idEspecialidad);
        }

        if ("eliminar".equals(accion)) {
            service.eliminarAsignacion(idTrabajador, idEspecialidad);
        }

        resp.sendRedirect("trabajador-especialidades?idTrabajador=" + idTrabajador);
    }
}

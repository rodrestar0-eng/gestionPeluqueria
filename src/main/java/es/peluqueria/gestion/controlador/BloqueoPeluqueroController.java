package es.peluqueria.gestion.controlador;

import java.io.IOException;
import java.time.LocalDate;

import es.peluqueria.gestion.modelo.BloqueoPeluquero;
import es.peluqueria.gestion.servicio.BloqueoPeluqueroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bloqueos")
public class BloqueoPeluqueroController extends HttpServlet {

    private BloqueoPeluqueroService service = new BloqueoPeluqueroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idTrabajador = Integer.parseInt(req.getParameter("idTrabajador"));
        req.setAttribute("bloqueos", service.obtenerBloqueosPorTrabajador(idTrabajador));
        req.getRequestDispatcher("jspUsuario/gestionBloqueos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("crear".equals(accion)) {

            int idTrabajador = Integer.parseInt(req.getParameter("idTrabajador"));
            LocalDate inicio = LocalDate.parse(req.getParameter("fechaInicio"));
            LocalDate fin = LocalDate.parse(req.getParameter("fechaFin"));
            String motivo = req.getParameter("motivo");

            //Validar primero
            String error = service.validarBloqueo(idTrabajador, inicio, fin, motivo);

            if (error != null) {
                req.setAttribute("error", error);
            } else {
                // Crear solo si está validado
                BloqueoPeluquero b = new BloqueoPeluquero();
                b.setIdTrabajador(idTrabajador);
                b.setFechaInicio(inicio);
                b.setFechaFin(fin);
                b.setMotivo(motivo);

                service.crearBloqueo(b);
                req.setAttribute("mensaje", "Bloqueo creado correctamente.");
            }

           
            req.setAttribute("bloqueos", service.obtenerBloqueosPorTrabajador(idTrabajador));
            req.getRequestDispatcher("jspUsuario/gestionBloqueos.jsp").forward(req, resp);

            return;  
        }

        if ("eliminar".equals(accion)) {
            int idBloqueo = Integer.parseInt(req.getParameter("idBloqueo"));
            int idTrabajador = Integer.parseInt(req.getParameter("idTrabajador"));

            service.eliminarBloqueo(idBloqueo);

            resp.sendRedirect("bloqueos?idTrabajador=" + idTrabajador);
            return;
        }
    }

}

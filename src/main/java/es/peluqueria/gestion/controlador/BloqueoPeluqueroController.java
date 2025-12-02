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
        req.getRequestDispatcher("bloqueos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("crear".equals(accion)) {
            BloqueoPeluquero b = new BloqueoPeluquero();
            b.setIdTrabajador(Integer.parseInt(req.getParameter("idTrabajador")));
            b.setFechaInicio(LocalDate.parse(req.getParameter("fechaInicio")));
            b.setFechaFin(LocalDate.parse(req.getParameter("fechaFin")));
            b.setMotivo(req.getParameter("motivo"));

            service.crearBloqueo(b);
        }

        if ("eliminar".equals(accion)) {
            int idBloqueo = Integer.parseInt(req.getParameter("idBloqueo"));
            service.eliminarBloqueo(idBloqueo);
        }

        resp.sendRedirect("bloqueos?idTrabajador=" + req.getParameter("idTrabajador"));
    }
}

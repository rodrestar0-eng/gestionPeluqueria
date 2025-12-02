package es.peluqueria.gestion.controlador;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import es.peluqueria.gestion.modelo.Cita;
import es.peluqueria.gestion.modelo.HorarioPeluquero;
import es.peluqueria.gestion.servicio.CitaService;
import es.peluqueria.gestion.servicio.HorarioPeluqueroService;
import es.peluqueria.gestion.servicio.ServicioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/horarios")
public class HorarioPeluqueroController extends HttpServlet {

    private HorarioPeluqueroService horarioService = new HorarioPeluqueroService();
    private CitaService citaService = new CitaService();
    private ServicioService servicioService = new ServicioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        // idTrabajador puede venir por parámetro o (si el peluquero está logueado) por sesión
        String sId = req.getParameter("idTrabajador");
        int idTrabajador;
        if (sId != null && !sId.isBlank()) {
            idTrabajador = Integer.parseInt(sId);
        } else {
            // intentar obtener del usuario en sesión (si corresponde)
            Object u = req.getSession().getAttribute("usuario");
            if (u != null) {
                try {
                    java.lang.reflect.Method m = u.getClass().getMethod("getIdUsuario");
                    idTrabajador = (int) m.invoke(u);
                } catch (Exception e) {
                    resp.sendRedirect("index.jsp");
                    return;
                }
            } else {
                resp.sendRedirect("jsp/login.jsp");
                return;
            }
        }

        // Fecha de inicio de la semana: lunes de la semana actual (puedes pasar ?weekStart=YYYY-MM-DD para otra semana)
        LocalDate weekStart;
        String weekStartParam = req.getParameter("weekStart");
        if (weekStartParam != null && !weekStartParam.isBlank()) {
            weekStart = LocalDate.parse(weekStartParam);
        } else {
            LocalDate today = LocalDate.now();
            weekStart = today.with(DayOfWeek.MONDAY);
        }
        LocalDate weekEnd = weekStart.plusDays(6);

        // 1) horarios del trabajador (bloques)
        List<HorarioPeluquero> horarios = horarioService.obtenerHorariosPorTrabajador(idTrabajador);

        // 2) citas del peluquero (todas) -> filtramos por semana y agrupamos por fecha
        List<Cita> todasCitas = citaService.listarPorPeluquero(idTrabajador);
        if (todasCitas == null) todasCitas = new ArrayList<>();

        // enriquecemos las citas con nombre del servicio (si no lo tienen)
        for (Cita c : todasCitas) {
            if (c.getIdServicio() > 0) {
                try {
                    String nombreServicio = servicioService.obtenerPorId(c.getIdServicio()).getNombre();
                    c.setNombreServicio(nombreServicio);
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        // filtrar por la semana actual
        List<Cita> citasSemana = todasCitas.stream()
                .filter(c -> c.getFechaCita() != null &&
                        ( !c.getFechaCita().isBefore(weekStart) && !c.getFechaCita().isAfter(weekEnd) ))
                .collect(Collectors.toList());

        // agrupar por fecha
        Map<LocalDate, List<Cita>> citasPorDia = new HashMap<>();
        for (LocalDate d = weekStart; !d.isAfter(weekEnd); d = d.plusDays(1)) {
            LocalDate fecha = d;
            List<Cita> porDia = citasSemana.stream()
                    .filter(c -> fecha.equals(c.getFechaCita()))
                    .sorted(Comparator.comparing(Cita::getHoraInicio, Comparator.nullsLast(String::compareTo)))
                    .collect(Collectors.toList());
            citasPorDia.put(fecha, porDia);
        }

        // pasar datos al JSP
        List<LocalDate> fechasSemana = new ArrayList<>();
        for (LocalDate d = weekStart; !d.isAfter(weekEnd); d = d.plusDays(1)) fechasSemana.add(d);

        req.setAttribute("idTrabajador", idTrabajador);
        req.setAttribute("fechasSemana", fechasSemana);
        req.setAttribute("citasPorDia", citasPorDia);
        req.setAttribute("horarios", horarios);
        req.setAttribute("weekStart", weekStart.toString());

        // forward al JSP del peluquero
        req.getRequestDispatcher("/jspUsuario/gestionHorarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        if ("crear".equals(accion)) {
            // crear horario
            try {
                HorarioPeluquero h = new HorarioPeluquero();
                h.setIdTrabajador(Integer.parseInt(req.getParameter("idTrabajador")));
                h.setDiaSemana(Integer.parseInt(req.getParameter("diaSemana")));
                h.setHoraInicio(req.getParameter("horaInicio"));
                h.setHoraFin(req.getParameter("horaFin"));
                horarioService.guardarHorario(h);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if ("eliminar".equals(accion)) {
            try {
                int idHorario = Integer.parseInt(req.getParameter("idHorario"));
                horarioService.eliminarHorario(idHorario);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // redirigir a la misma vista (mantener weekStart si venía)
        String idTrab = req.getParameter("idTrabajador");
        String weekStart = req.getParameter("weekStart");
        String url = "horarios?idTrabajador=" + idTrab;
        if (weekStart != null && !weekStart.isBlank()) url += "&weekStart=" + weekStart;
        resp.sendRedirect(url);
    }
}

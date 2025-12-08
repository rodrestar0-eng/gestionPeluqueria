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
import es.peluqueria.gestion.servicio.ClienteService;
import es.peluqueria.gestion.servicio.BloqueoPeluqueroService;
import es.peluqueria.gestion.modelo.BloqueoPeluquero;


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
    private ClienteService clienteService = new ClienteService();
    private BloqueoPeluqueroService bloqueoService = new BloqueoPeluqueroService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int idTrabajador;
        String sId = req.getParameter("idTrabajador");

        if (sId != null && !sId.isBlank()) {
            idTrabajador = Integer.parseInt(sId);
        } else {
            Object u = req.getSession().getAttribute("usuario");
            if (u != null) {
                try {
                    idTrabajador = (int) u.getClass().getMethod("getIdUsuario").invoke(u);
                } catch (Exception e) {
                    resp.sendRedirect("index.jsp");
                    return;
                }
            } else {
                resp.sendRedirect("jsp/login.jsp");
                return;
            }
        }

        // Semana seleccionada
        LocalDate weekStart;
        String weekStartParam = req.getParameter("weekStart");

        if (weekStartParam != null && !weekStartParam.isBlank()) {
            weekStart = LocalDate.parse(weekStartParam);
        } else {
            weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        }

        LocalDate weekEnd = weekStart.plusDays(6);

        // Horarios base
        List<HorarioPeluquero> horarios = horarioService.obtenerHorariosPorTrabajador(idTrabajador);

        // Citas
        List<Cita> citas = citaService.listarPorPeluquero(idTrabajador);
        if (citas == null) citas = new ArrayList<>();

        // Añadir nombre del servicio y del cliente
        for (Cita c : citas) {

            if (c.getIdServicio() > 0) {
                try {
                    c.setNombreServicio(servicioService.obtenerPorId(c.getIdServicio()).getNombre());
                } catch (Exception ignored) {}
            }

            if (c.getIdCliente() > 0) {
                try {
                    c.setNombreCliente(clienteService.obtenerPorId(c.getIdCliente()).getNombre());
                } catch (Exception ignored) {}
            }
        }

        // Agrupar citas por día
        Map<LocalDate, List<Cita>> citasPorDia = new LinkedHashMap<>();

        for (LocalDate d = weekStart; !d.isAfter(weekEnd); d = d.plusDays(1)) {
            LocalDate fechaActual = d;

            List<Cita> citasDia = citas.stream()
                    .filter(c -> fechaActual.equals(c.getFechaCita()))
                    .sorted(Comparator.comparing(Cita::getHoraInicio, Comparator.nullsLast(String::compareTo)))
                    .collect(Collectors.toList());

            citasPorDia.put(fechaActual, citasDia);
        }
        
     // ==============================
     // BLOQUEOS DEL PELUQUERO
     // ==============================

     List<BloqueoPeluquero> bloqueos = bloqueoService.obtenerBloqueosPorTrabajador(idTrabajador);

     Set<LocalDate> diasBloqueados = new HashSet<>();

     if (bloqueos != null) {
         for (BloqueoPeluquero b : bloqueos) {

             LocalDate inicio = b.getFechaInicio();
             LocalDate fin = b.getFechaFin();

             for (LocalDate d = inicio; !d.isAfter(fin); d = d.plusDays(1)) {
                 diasBloqueados.add(d);
             }
         }
     }


        // Enviar al JSP
        req.setAttribute("idTrabajador", idTrabajador);
        req.setAttribute("weekStart", weekStart.toString());
        req.setAttribute("weekPrev", weekStart.minusWeeks(1).toString());
        req.setAttribute("weekNext", weekStart.plusWeeks(1).toString());
        req.setAttribute("citasPorDia", citasPorDia);
        req.setAttribute("horarios", horarios);
        req.setAttribute("diasBloqueados", diasBloqueados);


        req.getRequestDispatcher("/jspUsuario/gestionHorarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        switch (accion) {

            case "crearHorario":
                try {
                    HorarioPeluquero h = new HorarioPeluquero();
                    h.setIdTrabajador(Integer.parseInt(req.getParameter("idTrabajador")));
                    h.setDiaSemana(Integer.parseInt(req.getParameter("diaSemana")));
                    h.setHoraInicio(req.getParameter("horaInicio"));
                    h.setHoraFin(req.getParameter("horaFin"));
                    horarioService.guardarHorario(h);
                } catch (Exception e) { e.printStackTrace(); }
                break;

            case "eliminarHorario":
                try {
                    int idHorario = Integer.parseInt(req.getParameter("idHorario"));
                    horarioService.eliminarHorario(idHorario);
                } catch (Exception e) { e.printStackTrace(); }
                break;

            case "cambiarEstadoCita":
                try {
                    int idCita = Integer.parseInt(req.getParameter("idCita"));
                    int nuevoEstado = Integer.parseInt(req.getParameter("estado"));
                    citaService.actualizarEstado(idCita, nuevoEstado);
                } catch (Exception e) { e.printStackTrace(); }
                break;
        }

        String idTrab = req.getParameter("idTrabajador");
        String weekStart = req.getParameter("weekStart");

        String url = "horarios?idTrabajador=" + idTrab;
        if (weekStart != null && !weekStart.isEmpty()) url += "&weekStart=" + weekStart;

        resp.sendRedirect(url);
    }
}

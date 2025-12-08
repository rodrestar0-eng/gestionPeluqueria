package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.DAO.EspecialidadServicioDAO;
import es.peluqueria.gestion.modelo.Cita;
import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.modelo.Servicio;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.servicio.BloqueoPeluqueroService;
import es.peluqueria.gestion.servicio.CitaService;
import es.peluqueria.gestion.servicio.ClienteService;
import es.peluqueria.gestion.servicio.ServicioService;
import es.peluqueria.gestion.servicio.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cita")
public class CitaController extends HttpServlet {

    private CitaService citaService;
    private UsuarioService usuarioService;
    private ServicioService servicioService;
    private BloqueoPeluqueroService bloqueoService;


    @Override
    public void init() throws ServletException {
        this.citaService = new CitaService();
        this.usuarioService = new UsuarioService();
        this.servicioService = new ServicioService();
        this.bloqueoService = new BloqueoPeluqueroService();

    }

    private void prepararFormularioCrear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Usuario> peluqueros = usuarioService.listarPeluqueros();
        request.setAttribute("peluqueros", peluqueros);

        List<Servicio> servicios = servicioService.listarTodos();
        request.setAttribute("servicios", servicios);

        request.getRequestDispatcher("/jspCliente/crearCita.jsp").forward(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {

            case "ver":
                mostrarCita(request, response);
                break;

            case "cancelar":
			try {
				cancelarCita(request, response);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
                break;

            case "misCitasCliente":
                try {
                    listarCitasCliente(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;  

            case "misCitasPeluquero":
                listarCitasPeluquero(request, response);
                break;
                
            case "detallePeluquero":
                verDetallePeluquero(request, response);
                break;
            case "filtrarPeluqueros":
                filtrarPeluqueros(request, response);
                break;

                
            case "crearFormulario":
                prepararFormularioCrear(request, response);
                break;
                
            case "cambiarEstado":
                try {
                    int idCita = Integer.parseInt(request.getParameter("idCita"));
                    int nuevoEstado = Integer.parseInt(request.getParameter("estado")); // 1,2,3
                    Cita cita = citaService.obtenerPorId(idCita);
                    if (cita != null) {
                        cita.setEstado(nuevoEstado);
                        citaService.actualizarCita(cita);
                    }
                    // volver a la pantalla de horarios del peluquero si viene el idTrabajador
                    String idTrab = request.getParameter("idTrabajador");
                    if (idTrab != null && !idTrab.isBlank()) {
                        response.sendRedirect("horarios?idTrabajador=" + idTrab);
                    } else {
                        response.sendRedirect("cita?accion=misCitasPeluquero");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("index.jsp");
                }
                break;


            default:
                listarTodas(request, response);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        switch (accion) {

            case "crear":
                crearCita(request, response);
                break;

            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }


    // ======================================================
    //                   MÉTODOS DEL CONTROLADOR
    // ======================================================


    private void crearCita(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Cita cita = new Cita();
            cita.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
            int idPeluquero = Integer.parseInt(request.getParameter("idPeluquero"));
            cita.setIdPeluquero(idPeluquero);
            cita.setIdServicio(Integer.parseInt(request.getParameter("idServicio")));
            LocalDate fechaSolicitada = java.time.LocalDate.parse(request.getParameter("fecha"));
            cita.setFechaCita(fechaSolicitada);
            cita.setHoraInicio(request.getParameter("horaInicio"));
            cita.setEstado(1);

            // --------------- Validación: fecha demasiado lejana ---------------
            LocalDate fechaMaxima = LocalDate.now().plusYears(2);
            if (fechaSolicitada.isAfter(fechaMaxima)) {
                request.setAttribute("error", "La fecha seleccionada es demasiado lejana en el tiempo (más de 2 años).");
                List<Usuario> peluqueros = usuarioService.listarPeluqueros();
                List<Servicio> servicios = servicioService.listarTodos();
                request.setAttribute("peluqueros", peluqueros);
                request.setAttribute("servicios", servicios);
                request.setAttribute("selectedPeluquero", idPeluquero);
                request.setAttribute("selectedServicio", cita.getIdServicio());
                request.setAttribute("selectedFecha", fechaSolicitada.toString());
                request.setAttribute("selectedHora", cita.getHoraInicio());
                request.getRequestDispatcher("/jspCliente/crearCita.jsp").forward(request, response);
                return;
            }
            // ----------------------------------------------------------------

            // --------------- comprobación de bloqueos ---------------
            if (bloqueoService != null && bloqueoService.estaBloqueado(idPeluquero, fechaSolicitada)) {
                request.setAttribute("error", "No se pudo registrar la cita: el peluquero no está disponible en esa fecha.");
                List<Usuario> peluqueros = usuarioService.listarPeluqueros();
                List<Servicio> servicios = servicioService.listarTodos();
                request.setAttribute("peluqueros", peluqueros);
                request.setAttribute("servicios", servicios);
                request.setAttribute("selectedPeluquero", idPeluquero);
                request.setAttribute("selectedServicio", cita.getIdServicio());
                request.setAttribute("selectedFecha", fechaSolicitada.toString());
                request.setAttribute("selectedHora", cita.getHoraInicio());
                request.getRequestDispatcher("/jspCliente/crearCita.jsp").forward(request, response);
                return;
            }
            // ----------------------------------------------------------------

            boolean ok = citaService.registrarCita(cita);

            if (ok) {
                response.sendRedirect("cita?accion=misCitasCliente");
            } else {
                request.setAttribute("error", "No se pudo registrar la cita");
                List<Usuario> peluqueros = usuarioService.listarPeluqueros();
                List<Servicio> servicios = servicioService.listarTodos();
                request.setAttribute("peluqueros", peluqueros);
                request.setAttribute("servicios", servicios);
                request.getRequestDispatcher("/jspCliente/crearCita.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar la cita.");
            try {
                List<Usuario> peluqueros = usuarioService.listarPeluqueros();
                List<Servicio> servicios = servicioService.listarTodos();
                request.setAttribute("peluqueros", peluqueros);
                request.setAttribute("servicios", servicios);
            } catch (Exception ignore) {}
            request.getRequestDispatcher("/jspCliente/crearCita.jsp").forward(request, response);
        }
    }

    private void verDetallePeluquero(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Cita cita = citaService.obtenerPorId(id);
            if (cita == null) {
                response.sendRedirect("cita?accion=misCitasPeluquero");
                return;
            }

            ClienteService clienteService = new ClienteService();
            String nombreCliente = clienteService.obtenerNombreCompletoPorId(cita.getIdCliente());

            request.setAttribute("cita", cita);
            request.setAttribute("nombreCliente", nombreCliente);

            request.getRequestDispatcher("jspUsuario/detalleCitaPeluquero.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cita?accion=misCitasPeluquero");
        }
    }



    private void mostrarCita(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Cita cita = citaService.obtenerPorId(id);

            if (cita == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            Usuario peluquero = usuarioService.obtenerPorId(cita.getIdPeluquero());
            request.setAttribute("peluquero", peluquero);

            Servicio servicio = servicioService.obtenerPorId(cita.getIdServicio());
            request.setAttribute("servicio", servicio);

            request.setAttribute("cita", cita);

            request.getRequestDispatcher("/jspCliente/detalleCita.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }


    private void cancelarCita(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {

        int idCita = Integer.parseInt(request.getParameter("id"));

        HttpSession sesion = request.getSession(false);

        if (sesion != null && sesion.getAttribute("cliente") != null) {

            String error = citaService.validarCancelacionCliente(idCita);

            if (error != null) {
                sesion.setAttribute("error", error);
                response.sendRedirect("cita?accion=misCitasCliente");
                return;
            }
        }

        //ADMIN Y PELUQUERO CANCELAN SIEMPRE
        citaService.eliminarCita(idCita);

        if (sesion != null && sesion.getAttribute("usuario") != null) {
            Usuario u = (Usuario) sesion.getAttribute("usuario");

            if (u.getTipoUsuario() == 2) {
                response.sendRedirect("cita?accion=misCitasPeluquero");
                return;
            }

            if (u.getTipoUsuario() == 1) {
                response.sendRedirect("cita?accion=listar");
                return;
            }
        }

        response.sendRedirect("cita?accion=misCitasCliente");
    }




    private void listarTodas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Cita> lista = citaService.listarTodas();
        request.setAttribute("citas", lista);

        request.getRequestDispatcher("misCitas.jsp").forward(request, response);
    }


    private void listarCitasCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("cliente") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int idCliente = ((Cliente) sesion.getAttribute("cliente")).getIdCliente();

        List<Cita> citas = citaService.listarPorCliente(idCliente);

        if (citas == null) citas = new ArrayList<>();

        UsuarioService usuarioService = new UsuarioService();
        ServicioService servicioService = new ServicioService();

        for (Cita c : citas) {
            c.setNombrePeluquero(usuarioService.obtenerNombrePorId(c.getIdPeluquero()));
            c.setNombreServicio(servicioService.obtenerNombrePorId(c.getIdServicio()));
        }

        request.setAttribute("citas", citas);

        // RUTA CORRECTA SEGÚN TUS ARCHIVOS
        request.getRequestDispatcher("/jspCliente/misCitas.jsp").forward(request, response);
    }

    private void listarCitasPeluquero(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        Usuario peluquero = (Usuario) sesion.getAttribute("usuario");

        List<Cita> citas = citaService.listarPorPeluquero(peluquero.getIdUsuario());

        ClienteService clienteService = new ClienteService();

        for (Cita c : citas) {
            c.setNombreCliente(clienteService.obtenerNombreCompletoPorId(c.getIdCliente()));
        }

        request.setAttribute("misCitas", citas);
        request.getRequestDispatcher("jspUsuario/misCitas.jsp").forward(request, response);
    }
    
    private void filtrarPeluqueros(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String sIdServicio = request.getParameter("idServicio");
        if (sIdServicio == null || sIdServicio.isBlank()) {
            out.println("<option value=''>Seleccione un servicio primero</option>");
            return;
        }

        int idServicio;
        try {
            idServicio = Integer.parseInt(sIdServicio);
        } catch (NumberFormatException e) {
            out.println("<option value=''>Servicio inválido</option>");
            return;
        }

        Integer idEspecialidad = servicioService.obtenerEspecialidadDeServicio(idServicio);

        if (idEspecialidad == null) {
            out.println("<option value=''>No hay peluqueros para este servicio</option>");
            return;
        }

        // Obtener los peluqueros con esa especialidad
        List<Usuario> peluqueros = usuarioService.listarPeluquerosPorEspecialidad(idEspecialidad);
        if(peluqueros != null)
        out.println("<option value=''>Seleccione...</option>");

        if (peluqueros == null || peluqueros.isEmpty()) {
            out.println("<option value=''>No hay peluqueros disponibles</option>");
            return;
        }

        for (Usuario p : peluqueros) {
            out.printf("<option value='%d'>%s %s</option>%n",
                    p.getIdUsuario(),
                    p.getNombre() != null ? p.getNombre() : "",
                    p.getApellido() != null ? p.getApellido() : "");
        }
    }


//    private void listarCitasPeluquero(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession sesion = request.getSession(false);
//        if (sesion == null || sesion.getAttribute("usuario") == null) {
//        	response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
//            return;
//        }
//
//        Usuario peluquero = (Usuario) sesion.getAttribute("usuario");
//
//        List<Cita> citas = citaService.listarPorPeluquero(peluquero.getIdUsuario());
//        request.setAttribute("citas", citas);
//
//        request.getRequestDispatcher("jspUsuario/misCitas.jsp").forward(request, response);
//    }
}

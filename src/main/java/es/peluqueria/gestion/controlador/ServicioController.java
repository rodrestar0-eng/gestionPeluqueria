package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Servicio;
import es.peluqueria.gestion.servicio.ServicioService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ServicioController extends HttpServlet {

    private ServicioService servicioService;

    @Override
    public void init() {
        servicioService = new ServicioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {

            case "listar":
                listarServicios(request, response);
                break;

            case "obtener":
                obtenerServicio(request, response);
                break;

            case "buscar":
                buscarServicios(request, response);
                break;

            default:
                listarServicios(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "crear":
                crearServicio(request, response);
                break;

            case "actualizar":
                actualizarServicio(request, response);
                break;

            case "eliminar":
                eliminarServicio(request, response);
                break;

            default:
                response.sendRedirect("servicio?action=listar");
        }
    }

    // =====================================================================
    // MÉTODOS
    // =====================================================================

    private void listarServicios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Servicio> servicios = servicioService.listarTodos();
        request.setAttribute("servicios", servicios);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/servicios/listaServicios.jsp");
        dispatcher.forward(request, response);
    }

    private void obtenerServicio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Servicio servicio = servicioService.obtenerPorId(id);
        request.setAttribute("servicio", servicio);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/servicios/verServicio.jsp");
        dispatcher.forward(request, response);
    }

    private void buscarServicios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");

        List<Servicio> servicios = servicioService.buscarPorNombre(nombre);
        request.setAttribute("servicios", servicios);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/servicios/listaServicios.jsp");
        dispatcher.forward(request, response);
    }

    private void crearServicio(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Servicio s = new Servicio();
        s.setNombre(request.getParameter("nombre"));
        s.setPrecio(new java.math.BigDecimal(request.getParameter("precio")));
        s.setDuracionMinutos(Integer.parseInt(request.getParameter("duracion")));
        s.setDescripcion(request.getParameter("descripcion"));

        int idEspecialidad = Integer.parseInt(request.getParameter("idEspecialidad"));

        servicioService.registrarServicio(s, idEspecialidad);

        response.sendRedirect("servicio?action=listar");
    }


    private void actualizarServicio(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Servicio s = new Servicio();
        s.setIdServicio(Integer.parseInt(request.getParameter("id")));
        s.setNombre(request.getParameter("nombre"));
        s.setPrecio(new java.math.BigDecimal(request.getParameter("precio")));
        s.setDuracionMinutos(Integer.parseInt(request.getParameter("duracion")));
        s.setDescripcion(request.getParameter("descripcion"));

        int idEspecialidad = Integer.parseInt(request.getParameter("idEspecialidad"));

        servicioService.actualizarServicio(s, idEspecialidad);

        response.sendRedirect("servicio?action=listar");
    }


    private void eliminarServicio(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        servicioService.eliminarServicio(id);

        response.sendRedirect("servicio?action=listar");
    }
}

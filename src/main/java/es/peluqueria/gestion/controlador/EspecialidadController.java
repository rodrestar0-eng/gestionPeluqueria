package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Especialidad;
import es.peluqueria.gestion.servicio.EspecialidadService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/especialidades")
public class EspecialidadController extends HttpServlet {

    private EspecialidadService especialidadService;

    @Override
    public void init() throws ServletException {
        especialidadService = new EspecialidadService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "new":
                mostrarFormularioNuevo(request, response);
                break;

            case "edit":
                cargarEspecialidadParaEditar(request, response);
                break;

            case "delete":
                eliminarEspecialidad(request, response);
                break;

            default:
                listarEspecialidades(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "";

        switch (action) {
            case "insert":
                insertarEspecialidad(request, response);
                break;

            case "update":
                actualizarEspecialidad(request, response);
                break;

            default:
                response.sendRedirect("especialidades");
        }
    }

    // ======================================================
    // MÉTODOS GET
    // ======================================================

    private void listarEspecialidades(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Especialidad> lista = especialidadService.listarTodas();
        request.setAttribute("especialidades", lista);

        request.getRequestDispatcher("views/especialidades/lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("views/especialidades/form.jsp").forward(request, response);
    }

    private void cargarEspecialidadParaEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Especialidad esp = especialidadService.obtenerPorId(id);

        request.setAttribute("especialidad", esp);

        request.getRequestDispatcher("views/especialidades/form.jsp").forward(request, response);
    }

    private void eliminarEspecialidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        especialidadService.eliminarEspecialidad(id);

        response.sendRedirect("especialidades");
    }

    // ======================================================
    // MÉTODOS POST
    // ======================================================

    private void insertarEspecialidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Especialidad esp = new Especialidad();
        esp.setNombre(request.getParameter("nombre"));

        especialidadService.registrarEspecialidad(esp);

        response.sendRedirect("especialidades");
    }

    private void actualizarEspecialidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Especialidad esp = new Especialidad();
        esp.setIdEspecialidad(Integer.parseInt(request.getParameter("idEspecialidad")));
        esp.setNombre(request.getParameter("nombre"));

        especialidadService.actualizarEspecialidad(esp);

        response.sendRedirect("especialidades");
    }
}

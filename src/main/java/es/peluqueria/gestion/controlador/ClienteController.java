package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Especialidad;
import es.peluqueria.gestion.modelo.TrabajadorEspecialidad;
import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.servicio.ClienteService;
import es.peluqueria.gestion.servicio.TrabajadorEspecialidadService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/cliente")
public class ClienteController extends HttpServlet {

    private ClienteService clienteService;

    @Override
    public void init() throws ServletException {
        this.clienteService = new ClienteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "inicio";

        switch (accion) {

            case "cerrarSesion":
                cerrarSesion(request, response);
                break;

            case "perfil":
                mostrarPerfil(request, response);
                break;

            case "eliminar":
                eliminarCliente(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/jspCliente/panelCliente.jsp");
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        switch (accion) {

            case "registrar":
                registrarCliente(request, response);
                break;

            case "login":
                loginCliente(request, response);
                break;

            case "actualizar":
                actualizarCliente(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                break;
        }
    }


    // ================================
    // REGISTRAR
    // ================================
    private void registrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cliente c = new Cliente();
        c.setNombre(request.getParameter("nombre"));
        c.setApellido(request.getParameter("apellido"));
        c.setEmail(request.getParameter("email"));
        c.setTelefono(request.getParameter("telefono"));
        c.setContrasena(request.getParameter("contrasena"));

        boolean registrado = clienteService.registrarCliente(c);

        if (!registrado) {
            request.setAttribute("error", "Ya existe un cliente con ese email o teléfono.");
            request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }


    // ================================
    // LOGIN
    // ================================
    private void loginCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String pass = request.getParameter("contrasena");

        Cliente cliente = clienteService.login(email, pass);

        if (cliente == null) {
            request.setAttribute("error", "Email o contraseña incorrectos.");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        HttpSession sesion = request.getSession();
        sesion.setAttribute("cliente", cliente);

        response.sendRedirect(request.getContextPath() + "/cliente?accion=perfil");
    }


    // ================================
    // PERFIL
    // ================================
 // ================================
 // PERFIL
 // ================================
    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // ⭐ Cargar especialidades del peluquero
        TrabajadorEspecialidadService tes = new TrabajadorEspecialidadService();
        List<Especialidad> especialidades = tes.obtenerEspecialidadesCompletas(usuario.getIdUsuario());

        request.setAttribute("especialidades", especialidades);

        request.getRequestDispatcher("jspUsuario/miPerfil.jsp").forward(request, response);
    }



 // ================================
 // ACTUALIZAR CLIENTE
 // ================================
 private void actualizarCliente(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

     HttpSession sesion = request.getSession(false);
     if (sesion == null || sesion.getAttribute("cliente") == null) {
         response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
         return;
     }

     Cliente actual = (Cliente) sesion.getAttribute("cliente");

     actual.setNombre(request.getParameter("nombre"));
     actual.setApellido(request.getParameter("apellido"));
     actual.setEmail(request.getParameter("email"));
     actual.setTelefono(request.getParameter("telefono"));
     actual.setContrasena(request.getParameter("contrasena"));

     boolean ok = clienteService.actualizarCliente(actual);

     if (!ok) {
         request.setAttribute("error", "No se pudo actualizar el perfil.");
         request.getRequestDispatcher("/jspCliente/miPerfil.jsp").forward(request, response);
         return;
     }

     sesion.setAttribute("cliente", actual);

     request.setAttribute("mensaje", "Datos actualizados correctamente.");
     request.getRequestDispatcher("/jspCliente/miPerfil.jsp").forward(request, response);
 }



    // ================================
    // ELIMINAR CLIENTE
    // ================================
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("cliente") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        Cliente c = (Cliente) sesion.getAttribute("cliente");

        clienteService.eliminarCliente(c.getIdCliente());
        sesion.invalidate();

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }


    // ================================
    // CERRAR SESIÓN
    // ================================
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion != null) sesion.invalidate();

        response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
    }
}

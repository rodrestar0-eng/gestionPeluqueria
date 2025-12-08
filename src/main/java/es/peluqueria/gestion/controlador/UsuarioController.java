package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.modelo.Especialidad;
import es.peluqueria.gestion.servicio.UsuarioService;
import es.peluqueria.gestion.servicio.TrabajadorEspecialidadService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuario")
public class UsuarioController extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        this.usuarioService = new UsuarioService();
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
                eliminarUsuario(request, response);
                break;

            case "listar":
                listarTrabajadores(request, response);
                break;

            case "inicio":
                request.getRequestDispatcher("jspUsuario/indexUsuario.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        switch (accion) {

            case "login":
                loginUsuario(request, response);
                break;

            case "registrar":
                registrarUsuario(request, response);
                break;

            case "actualizar":
                actualizarUsuario(request, response);
                break;
               
            case "actualizarMiPerfil":
                actualizarMiPerfil(request, response);
                break;
               
            case "logout":
            	cerrarSesion(request, response);
                
               

            default:
                response.sendRedirect("jsp/index.jsp");
                break;
        }
    }


    // =====================================================
    //                  MÉTODOS DEL CONTROLADOR
    // =====================================================

    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        // ⭐ CARGAR ESPECIALIDADES DEL PELUQUERO
        TrabajadorEspecialidadService tes = new TrabajadorEspecialidadService();
        List<Especialidad> especialidades = tes.obtenerEspecialidadesCompletas(usuario.getIdUsuario());

        request.setAttribute("especialidades", especialidades);

        request.getRequestDispatcher("jspUsuario/miPerfil.jsp").forward(request, response);
    }


    private void loginUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        Usuario usuario = usuarioService.login(email, contrasena);

        if (usuario == null) {
            request.setAttribute("error", "Credenciales incorrectas.");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuario", usuario);

        switch (usuario.getTipoUsuario()) {

            case 1:
                response.sendRedirect("adminDashboard.jsp");
                break;

            case 2:
                response.sendRedirect("jspUsuario/indexUsuario.jsp");
                break;

            default:
                response.sendRedirect("jspUsuario/indexUsuario.jsp");
                break;
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario u = new Usuario();
        u.setNombre(request.getParameter("nombre"));
        u.setApellido(request.getParameter("apellido"));
        u.setEmail(request.getParameter("email"));
        u.setContrasena(request.getParameter("contrasena"));

        int tipo = Integer.parseInt(request.getParameter("tipoUsuario"));
        u.setTipoUsuario(tipo);
        u.setTelefono(request.getParameter("telefono"));

        boolean ok = usuarioService.registrarUsuario(u);

        if (!ok) {
            request.setAttribute("error", "Ya existe un usuario con ese email.");
            request.getRequestDispatcher("registroUsuario.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
    }
    
    private void actualizarMiPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Recogemos datos
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        // --- VALIDACIONES ---
        String error = validarPerfil(nombre, apellido, email, telefono, usuario.getIdUsuario());

        if (error != null) {
            request.setAttribute("error", error);

            // recargar especialidades
            TrabajadorEspecialidadService tes = new TrabajadorEspecialidadService();
            List<Especialidad> especialidades = tes.obtenerEspecialidadesCompletas(usuario.getIdUsuario());
            request.setAttribute("especialidades", especialidades);

            request.getRequestDispatcher("jspUsuario/miPerfil.jsp").forward(request, response);
            return;
        }

        // Si llegamos aquí, los datos son válidos
        usuario.setNombre(nombre.trim());
        usuario.setApellido(apellido.trim());
        usuario.setEmail(email.trim());
        usuario.setTelefono(telefono.trim());

        boolean ok = usuarioService.actualizarPerfilUsuario(usuario);

        if (ok) {
            request.getSession().setAttribute("usuario", usuario);
            request.setAttribute("mensaje", "Cambios guardados correctamente.");
        } else {
            request.setAttribute("error", "No se pudo actualizar tu perfil.");
        }

        // recargar especialidades
        TrabajadorEspecialidadService tes = new TrabajadorEspecialidadService();
        List<Especialidad> especialidades = tes.obtenerEspecialidadesCompletas(usuario.getIdUsuario());
        request.setAttribute("especialidades", especialidades);

        request.getRequestDispatcher("jspUsuario/miPerfil.jsp").forward(request, response);
    }



    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        Usuario u = (Usuario) sesion.getAttribute("usuario");

        u.setNombre(request.getParameter("nombre"));
        u.setApellido(request.getParameter("apellido"));
        u.setEmail(request.getParameter("email"));
        u.setContrasena(request.getParameter("contrasena"));
        u.setTelefono(request.getParameter("telefono"));
        boolean ok = usuarioService.actualizarUsuario(u);

        if (!ok) {
            request.setAttribute("error", "Error al actualizar los datos.");
            request.getRequestDispatcher("perfilUsuario.jsp").forward(request, response);
            return;
        }

        sesion.setAttribute("usuario", u);
        request.setAttribute("mensaje", "Datos actualizados correctamente.");
        request.getRequestDispatcher("perfilUsuario.jsp").forward(request, response);
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        usuarioService.eliminarUsuario(id);

        response.sendRedirect("admin?accion=listarUsuarios");
    }

    private void listarTrabajadores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Usuario> trabajadores = usuarioService.listarPorTipo(2);

            request.setAttribute("trabajadores", trabajadores);

            request.getRequestDispatcher("jspAdmin/panelAdmin.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion != null) sesion.invalidate();

        response.sendRedirect("index.jsp");
    }
    private String validarPerfil(String nombre, String apellido, String email, String telefono, int idUsuario) {

        // Nombre obligatorio, solo letras
        if (nombre == null || nombre.trim().isEmpty())
            return "El nombre es obligatorio.";

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,50}$"))
            return "El nombre solo puede contener letras y espacios.";

        // Apellido opcional pero válido
        if (apellido != null && !apellido.trim().isEmpty()) {
            if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,50}$"))
                return "El apellido solo puede contener letras.";
        }

        // Email obligatorio y con formato
        if (email == null || email.trim().isEmpty())
            return "El correo es obligatorio.";

        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,6}$"))
            return "Formato de correo no válido.";

        // Teléfono opcional
        if (telefono != null && !telefono.isEmpty()) {
            if (!telefono.matches("^[0-9]{9,15}$"))
                return "El teléfono debe contener entre 9 y 15 números.";
        }

        // Comprobar email duplicado
        if (usuarioService.emailExisteParaOtroUsuario(email, idUsuario)) {
            return "Ese correo ya está siendo utilizado por otro usuario.";
        }

        return null; // correcto
    }


}

package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.servicio.UsuarioService;

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

            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    // =====================================================
    //                  MÉTODOS DEL CONTROLADOR
    // =====================================================


    // ---------- LOGIN ----------
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

        // Redirección por tipo de usuario
        switch (usuario.getTipoUsuario()) {

            case 1: // Administrador
                response.sendRedirect("adminDashboard.jsp");
                break;

            case 2: // Peluquero
                response.sendRedirect("jspUsuario/indexUsuario.jsp");
                break;

            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }


    // ---------- REGISTRO ----------
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

        response.sendRedirect("/jsp/login.jsp");
    }


    // ---------- PERFIL ----------
    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("/jsp/login.jsp");
            return;
        }

        request.getRequestDispatcher("jspUsuario/miPerfil.jsp").forward(request, response);
    }


    // ---------- ACTUALIZAR ----------
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("/jsp/login.jsp");
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


    // ---------- ELIMINAR ----------
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("/jsp/login.jsp");
            return;
        }

        // ⬅️ Recuperar el ID que viene del enlace
        int id = Integer.parseInt(request.getParameter("id"));

        usuarioService.eliminarUsuario(id);

        response.sendRedirect("admin?accion=listarUsuarios");
    }

    //Listar
    
    private void listarTrabajadores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Tipo 2 = Peluquero / trabajador
            List<Usuario> trabajadores = usuarioService.listarPorTipo(2);

            request.setAttribute("trabajadores", trabajadores);

            request.getRequestDispatcher("jspAdmin/panelAdmin.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }



    // ---------- CERRAR SESIÓN ----------
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion != null) sesion.invalidate();

        response.sendRedirect("index.jsp");
    }
}

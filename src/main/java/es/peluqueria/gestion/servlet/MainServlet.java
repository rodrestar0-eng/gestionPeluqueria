package es.peluqueria.gestion.servlet;

import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.servicio.ClienteService;
import es.peluqueria.gestion.servicio.UsuarioService;
import es.peluqueria.gestion.util.HashUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/app")
public class MainServlet extends HttpServlet {

    private ClienteService clienteService;
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        clienteService = new ClienteService();
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "inicio";

        switch (accion) {

            case "logout":
                logout(request, response);
                break;

            case "login":
                mostrarLogin(request, response);
                break;

            default:
                request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
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
                loginGeneral(request, response);
                break;
            case "registrarCliente":
                registrarCliente(request, response);
                break;


            default:
                response.sendRedirect(request.getContextPath() +"/jsp/index.jsp");
                break;
        }
    }


    // ======================================================
    //                   MÉTODOS DEL CONTROLADOR
    // ======================================================


    /** Mostrar login.jsp */
    private void mostrarLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }



    /** LOGIN GENERAL PARA CLIENTES, PELUQUEROS Y ADMIN */
    private void loginGeneral(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("contrasena");

        // Intentar login como cliente
        Cliente cliente = clienteService.obtenerPorEmail(email);

        if (cliente != null) {
            // Comparar la contraseña ingresada con el hash almacenado en la base de datos
            if (HashUtil.checkPassword(password, cliente.getContrasena())) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("cliente", cliente);

                response.sendRedirect(request.getContextPath() + "/jspCliente/panelCliente.jsp");
                return;
            }
        }

     // Intentar login como trabajador (usuario)
        Usuario usuario = usuarioService.obtenerPorEmail(email);

        if (usuario != null) {

            boolean passwordCorrecta = 
                    usuario.getContrasena().equals(password) || 
                    HashUtil.checkPassword(password, usuario.getContrasena());

            if (passwordCorrecta) {

                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", usuario);

                if (usuario.getTipoUsuario() == 1) { // ADMIN
                    response.sendRedirect("admin?accion=panel");
                    return;
                }

                if (usuario.getTipoUsuario() == 2 || usuario.getTipoUsuario() == 3) { // PELUQUERO
                    response.sendRedirect(request.getContextPath() + "/jspUsuario/indexUsuario.jsp");
                    return;
                }
            }else {
            request.setAttribute("error", "Contraseña incorrecta");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            }
        }

        // Si nadie coincide:
        request.setAttribute("error", "Credenciales incorrectas");
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }
    
    private void registrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String repetir = request.getParameter("repetirContrasena");

        if (!contrasena.equals(repetir)) {
            request.setAttribute("error", "Las contraseñas no coinciden");
            request.getRequestDispatcher("/jsp/registroCliente.jsp").forward(request, response);
            return;
        }

        Cliente nuevo = new Cliente();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellidos);
        nuevo.setTelefono(telefono);
        nuevo.setEmail(email);
        nuevo.setContrasena(contrasena);

        clienteService.registrarCliente(nuevo);

        response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
    }





    /** LOGOUT LIMPIO */
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion != null) sesion.invalidate();

        response.sendRedirect(request.getContextPath() +"/jsp/index.jsp");
    }
}

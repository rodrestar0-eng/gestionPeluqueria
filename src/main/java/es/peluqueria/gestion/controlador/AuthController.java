package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.servicio.AuthService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class AuthController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String pass  = request.getParameter("contrasena");

        Object resultado = authService.login(email, pass);

        if (resultado == null) {
            request.setAttribute("error", "Credenciales incorrectas.");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();

        // ========== CLIENTE ==========
        if (resultado instanceof Cliente cliente) {
            session.setAttribute("cliente", cliente);
            response.sendRedirect("cliente/panelCliente.jsp");
            return;
        }

        // ========== USUARIO (empleado/admin) ==========
        if (resultado instanceof Usuario usuario) {
            session.setAttribute("usuario", usuario);

            switch (usuario.getTipoUsuario()) {
                case 1: response.sendRedirect("admin/panelAdmin.jsp"); break;
                case 2: response.sendRedirect("usuario/indexUsuario.jsp"); break;
                case 3: response.sendRedirect("usuario/indexUsuario.jsp"); break;
                default:
                    response.sendRedirect("/jsp/login.jsp");
            }
        }
    }
}

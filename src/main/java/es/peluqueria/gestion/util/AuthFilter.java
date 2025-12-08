package es.peluqueria.gestion.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();

        // ==============================================================
        //        RUTAS PÚBLICAS (sin login)
        // ==============================================================
        boolean recursoEstatico =
                uri.contains("/css") || uri.contains("/js") ||
                uri.contains("/img") || uri.endsWith(".png") ||
                uri.endsWith(".jpg") || uri.endsWith(".jpeg") ||
                uri.endsWith(".woff") || uri.endsWith(".ttf");

        boolean paginasPublicas =
                uri.endsWith("index.jsp") ||
                uri.endsWith("login.jsp")||
                uri.contains("cliente?accion=registrar") ||
                uri.contains("cliente?accion=guardarRegistro");

        if (recursoEstatico || paginasPublicas) {
            chain.doFilter(req, res);
            return;
        }

        // ==============================================================
        //        VALIDAR SESIÓN
        // ==============================================================

        boolean usuarioLogueado = (session != null && session.getAttribute("usuario") != null);
        boolean clienteLogueado = (session != null && session.getAttribute("cliente") != null);

        // 1. Acceso a controladores de cliente
        if (uri.contains("/cita") && uri.contains("misCitasCliente")) {
            if (!clienteLogueado) {
                response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
                return;
            }
        }

        // 2. Acceso a controladores de peluquero o admin
        if (uri.contains("/cita") && uri.contains("misCitasPeluquero")) {
            if (!usuarioLogueado) {
                response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
                return;
            }
        }

        // 3. Acceso general al controlador de usuarios (solo admin)
     // Controlador "usuario" → solo admin
        if (uri.contains("/usuario") && !uri.contains("/jspUsuario/")) {
            if (!usuarioLogueado) {
                response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
                return;
            }

            
        }

        // 4. Acceso general al controlador de servicios, especialidades, etc (solo admin)
        if (uri.contains("/servicio") || uri.contains("/especialidad")) {
            if (!usuarioLogueado) {
                response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
                return;
            }
        }

        // Si pasa todas las validaciones → permitir acceso
        chain.doFilter(req, res);
    }
}

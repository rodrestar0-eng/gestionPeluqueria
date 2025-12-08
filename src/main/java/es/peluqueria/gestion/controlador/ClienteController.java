package es.peluqueria.gestion.controlador;

import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.servicio.ClienteService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Properties;

// IMPORTS CORRECTOS DE JAVAMAIL
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.Authenticator;


@WebServlet("/cliente")
public class ClienteController extends HttpServlet {

    private ClienteService clienteService;

    @Override
    public void init() throws ServletException {
        this.clienteService = new ClienteService();
    }

    // ================================
    // GET
    // ================================
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

    // ================================
    // POST
    // ================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        switch (accion) {

            case "registrar":                          
                registrarCliente(request, response);
                break;

            case "generarCodigoRegistro":              
                generarCodigoRegistro(request, response);
                break;
                
            case "reenviarCodigo":
               reenviarCodigo(request,response);
                break;


            case "verificarCodigo":                    // NUEVO
                verificarCodigo(request, response);
                break;

            case "login":
                loginCliente(request, response);
                break;

            case "actualizar":
                actualizarCliente(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
                break;
        }
    }


    private void reenviarCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 HttpSession ses2 = request.getSession();
         String emailDestino2 = (String) ses2.getAttribute("emailRegistro");

         if (emailDestino2 == null) {
             request.setAttribute("error", "No se encontró el email del usuario en sesión.");
             request.getRequestDispatcher("/jsp/registroCliente.jsp").forward(request, response);
             return;
         }

         // Generar nuevo código
         int nuevoCodigo = (int) (Math.random() * 900000) + 100000;

         ses2.setAttribute("codigoVerificacion", nuevoCodigo);

         enviarCodigoVerificacion(emailDestino2, nuevoCodigo);

         request.setAttribute("mensaje", "Código reenviado correctamente");
         request.getRequestDispatcher("jsp/verificarCodigo.jsp").forward(request, response);
		
	}

	// ================================
    // 1) GENERAR CÓDIGO 
    // ================================
    private void generarCodigoRegistro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        // Si el email ya existe → no seguimos
        if (clienteService.existeEmail(email)) {
            request.setAttribute("error", "Ya existe un cliente con ese email.");
            request.getRequestDispatcher("jsp/registroCliente.jsp").forward(request, response);
            return;
        }

        // Crear cliente temporal
        Cliente c = new Cliente();
        c.setNombre(request.getParameter("nombre"));
        c.setApellido(request.getParameter("apellido"));
        c.setEmail(email);
        c.setTelefono(request.getParameter("telefono"));
        c.setContrasena(request.getParameter("contrasena"));

        // Generar código
        int codigo = (int) (Math.random() * 900000) + 100000;

        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientePendiente", c);
        sesion.setAttribute("codigoVerificacion", codigo);

        sesion.setAttribute("emailRegistro", email);

        // Enviar email
        enviarCodigoVerificacion(email, codigo);

        // Ir al JSP donde introduce el código
        request.getRequestDispatcher("jsp/verificarCodigo.jsp").forward(request, response);
    }



    // ================================
    // 2) VERIFICAR CÓDIGO 
    // ================================
    private void verificarCodigo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();

        Integer codigoCorrecto = (Integer) sesion.getAttribute("codigoVerificacion");
        Cliente clientePendiente = (Cliente) sesion.getAttribute("clientePendiente");

        if (codigoCorrecto == null || clientePendiente == null) {
            response.sendRedirect("jsp/registroCliente.jsp");
            return;
        }

        int codigoIngresado = Integer.parseInt(request.getParameter("codigoIngresado"));

        if (codigoIngresado != codigoCorrecto) {
            request.setAttribute("error", "Código incorrecto.");
            request.getRequestDispatcher("jsp/verificarCodigo.jsp").forward(request, response);
            return;
        }

        // Registrar definitivamente
        clienteService.registrarCliente(clientePendiente);

        // Limpiar sesión temporal
        sesion.removeAttribute("clientePendiente");
        sesion.removeAttribute("codigoVerificacion");

        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }


    // ================================
    // MÉTODO DE ENVÍO DE EMAIL
    // ================================
    private void enviarCodigoVerificacion(String emailDestino, int codigo) {

        final String remitente = "rodrestar0@gmail.com";
        final String password = "rtrvdznaplltfvna";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remitente, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject("Código de verificación");
            message.setText("Tu código es: " + codigo);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    // ================================
    // (RESTO DEL CONTROLADOR: LOGIN, PERFIL, ACTUALIZAR, ELIMINAR, LOGOUT)
    // ================================
    private void registrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // YA NO SE USA
        response.sendRedirect("/jsp/registroCliente.jsp");
    }

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


    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("cliente") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        Cliente clienteSesion = (Cliente) sesion.getAttribute("cliente");
        Cliente clienteRefrescado = clienteService.obtenerPorId(clienteSesion.getIdCliente());

        if (clienteRefrescado == null) {
            sesion.invalidate();
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        sesion.setAttribute("cliente", clienteRefrescado);
        request.setAttribute("cliente", clienteRefrescado);

        request.getRequestDispatcher("/jspCliente/miPerfil.jsp").forward(request, response);
    }


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

        response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
    }


    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion != null) sesion.invalidate();

        response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
    }
}

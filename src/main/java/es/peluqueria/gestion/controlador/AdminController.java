package es.peluqueria.gestion.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import es.peluqueria.gestion.modelo.Servicio;
import es.peluqueria.gestion.modelo.Especialidad;
import es.peluqueria.gestion.modelo.Usuario;

import es.peluqueria.gestion.servicio.CitaService;
import es.peluqueria.gestion.servicio.EspecialidadService;
import es.peluqueria.gestion.servicio.ServicioService;
import es.peluqueria.gestion.servicio.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();
    private ServicioService servicioService = new ServicioService();
    private EspecialidadService especialidadService = new EspecialidadService();
    private CitaService citaService = new CitaService();

    // =====================================================
    //                     GET
    // =====================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != 1) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String accion = req.getParameter("accion");
        if (accion == null) accion = "panel";

        try {

            switch (accion) {

                case "listarUsuarios":
                    req.setAttribute("todosUsu", usuarioService.listarTodos());
                    req.getRequestDispatcher("jspAdmin/listaUsuarios.jsp").forward(req, resp);
                    return;

                case "listarServicios":
                    req.setAttribute("servicios", servicioService.listarTodos());
                    req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                    return;

                case "listarEspecialidades":
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/listaEspecialidades.jsp").forward(req, resp);
                    return;

                // ------------------------------
                // FORMULARIO CREAR SERVICIO
                // ------------------------------
                case "nuevoServicio":
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearServicio.jsp").forward(req, resp);
                    return;


                case "nuevaEspecialidad":
                    req.getRequestDispatcher("jspAdmin/crearEspecialidad.jsp").forward(req, resp);
                    return;

                // ------------------------------
                // FORMULARIO CREAR USUARIO / TRABAJADOR
                // ------------------------------
                case "nuevoUsuario":

                    // enviar especialidades al JSP
                    req.setAttribute("especialidades", especialidadService.listarTodas());

                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;

                // ------------------------------
                // EDITAR SERVICIO
                // ------------------------------
                case "editarServicio":

                    int idServicio = Integer.parseInt(req.getParameter("id"));

                    // 1. Cargar servicio
                    Servicio servicio = servicioService.obtenerPorId(idServicio);

                    if (servicio == null) {
                        // si no existe, rediriges a lista o error
                        resp.sendRedirect("admin?accion=panel");
                        return;
                    }

                    req.setAttribute("servicio", servicio);

                    // 2. Obtener especialidad actual
                    Integer idEspecialidadActual = servicioService.obtenerEspecialidadDeServicio(idServicio);
                    req.setAttribute("idEspecialidadActual", idEspecialidadActual);

                    // 3. Cargar todas las especialidades para el <select>
                    req.setAttribute("especialidades", especialidadService.listarTodas());

                    // 4. Continuar hacia JSP
                    req.getRequestDispatcher("jspAdmin/editarServicio.jsp").forward(req, resp);
                    return;

                    
                    
                    
                    
                // ------------------------------
                // EDITAR USUARIO
                // ------------------------------    
               
                 case "editarUsuario":

                     int idUsuario = Integer.parseInt(req.getParameter("id"));
                     Usuario usuarioEdit = usuarioService.obtenerPorId(idUsuario);

                     // Si no existe → vuelve a la lista
                     if (usuarioEdit == null) {
                         resp.sendRedirect("admin?accion=listarUsuarios");
                         return;
                     }

                     // Si es trabajador, cargar especialidades
                     req.setAttribute("especialidades", especialidadService.listarTodas());
                     req.setAttribute("usuarioEdit", usuarioEdit);

                     req.getRequestDispatcher("/jspAdmin/editarUsuario.jsp").forward(req, resp);
                     return;
                     
                 case "editarEspecialidad":
                	    int idEsp = Integer.parseInt(req.getParameter("id"));
                	    Especialidad esp = especialidadService.obtenerPorId(idEsp);
                	    req.setAttribute("especialidad", esp);
                	    req.getRequestDispatcher("jspAdmin/editarEspecialidad.jsp").forward(req, resp);
                	    return;

                	case "eliminarEspecialidad":
                	    int idEliminar = Integer.parseInt(req.getParameter("id"));
                	    especialidadService.eliminarEspecialidad(idEliminar);
                	    resp.sendRedirect("admin?accion=listarEspecialidades");
                	    return;
                	    
                	case "eliminarServicio":
                	    int idEliminarServ = Integer.parseInt(req.getParameter("id"));

                	    boolean eliminado = servicioService.eliminarServicio(idEliminarServ);

                	    if (!eliminado) {
                	        req.setAttribute("error", "No se puede eliminar este servicio porque tiene citas activas.");
                	    }

                	    req.setAttribute("servicios", servicioService.listarTodos());
                	    req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                	    return;
                	    
                	    




                // ------------------------------
                // PANEL PRINCIPAL
                // ------------------------------
                case "panel":
                default:

                    req.setAttribute("todosUsu", usuarioService.listarTodos());
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.setAttribute("servicios", servicioService.listarTodos());
                    req.setAttribute("trabajadores", usuarioService.listarPorTipo(2));

                    req.getRequestDispatcher("jspAdmin/panelAdmin.jsp").forward(req, resp);
                    return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

    // =====================================================
    //                     POST
    // =====================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        try {

            // =====================================================
            //               CREAR SERVICIO
            // =====================================================
            if ("crearServicio".equals(accion)) {

                Servicio s = new Servicio();
                s.setNombre(req.getParameter("nombre"));
                s.setDuracionMinutos(Integer.parseInt(req.getParameter("duracion")));
                s.setPrecio(new BigDecimal(req.getParameter("precio")));
                s.setDescripcion(req.getParameter("descripcion"));

                int idEspecialidad = Integer.parseInt(req.getParameter("idEspecialidad"));
                servicioService.registrarServicio(s, idEspecialidad);

                resp.sendRedirect("admin?accion=panel");
                return;
            }

            // =====================================================
            //            ACTUALIZAR SERVICIO
            // =====================================================
            if ("actualizarServicio".equals(accion)) {

                Servicio s = new Servicio();
                s.setIdServicio(Integer.parseInt(req.getParameter("idServicio")));
                s.setNombre(req.getParameter("nombre"));
                s.setDuracionMinutos(Integer.parseInt(req.getParameter("duracion")));
                s.setPrecio(new BigDecimal(req.getParameter("precio")));
                s.setDescripcion(req.getParameter("descripcion"));

                int idEspecialidad = Integer.parseInt(req.getParameter("idEspecialidad"));
                servicioService.actualizarServicio(s, idEspecialidad);

                resp.sendRedirect("admin?accion=listarServicios");
                return;
            }
            
            
         // =====================================================
            //          ACTUALIZAR USUARIO
            //=====================================================
            if ("actualizarUsuario".equals(accion)) {

            	int id = Integer.parseInt(req.getParameter("idUsuario"));

            	Usuario u = new Usuario();
            	u.setIdUsuario(id);
            	u.setNombre(req.getParameter("nombre"));
            	u.setApellido(req.getParameter("apellido"));
            	u.setEmail(req.getParameter("email"));
            	u.setTelefono(req.getParameter("telefono"));
            	u.setTipoUsuario(Integer.parseInt(req.getParameter("tipoUsuario")));

            		// Solo actualizar contraseña si se escribió una nueva
            	String nuevaPass = req.getParameter("contrasena");
            	if (nuevaPass != null && !nuevaPass.isBlank()) {
            		u.setContrasena(nuevaPass);
   }		 else {
	   			// obtener la actual para no perderla
	   			Usuario original = usuarioService.obtenerPorId(id);
	   			u.setContrasena(original.getContrasena());
  }

            	// Guardar datos del usuario
            	usuarioService.actualizarUsuario(u);

  // ------------------------------
  // ESPECIALIDADES (solo si tipo = 2)
  // ------------------------------
  if (u.getTipoUsuario() == 2) {

      String[] idsEsp = req.getParameterValues("especialidades");

      // limpia todas las especialidades del usuario
      especialidadService.eliminarTodasEspecialidadDeTrabajador(id);

      // vuelve a insertarlas si no está vacío
      if (idsEsp != null) {
          for (String idEsp : idsEsp) {
              especialidadService.asignarEspecialidadATrabajador(id, Integer.parseInt(idEsp));
          }
      }
  } else {
      // Si NO es trabajador, borrar relaciones por si antes lo era
      especialidadService.eliminarTodasEspecialidadDeTrabajador(id);
  }

  resp.sendRedirect("admin?accion=listarUsuarios");
  return;
}


            // =====================================================
            //             CREAR ESPECIALIDAD
            // =====================================================
            if ("crearEspecialidad".equals(accion)) {

                Especialidad e = new Especialidad();
                e.setNombre(req.getParameter("nombre"));

                especialidadService.registrarEspecialidad(e);

                resp.sendRedirect("admin?accion=panel");
                return;
            }

            // =====================================================
            //             CREAR USUARIO / TRABAJADOR
            // =====================================================
            if ("crearUsuario".equals(accion)) {

                Usuario u = new Usuario();
                u.setNombre(req.getParameter("nombre"));
                u.setApellido(req.getParameter("apellido"));
                u.setEmail(req.getParameter("email"));
                u.setContrasena(req.getParameter("contrasena"));
                int tipo = Integer.parseInt(req.getParameter("tipoUsuario"));
                u.setTipoUsuario(tipo);
                u.setTelefono(req.getParameter("telefono"));
                boolean correcto = usuarioService.registrarUsuario(u);

             // ahora obtenemos el usuario ya insertado
             Usuario creado = usuarioService.obtenerPorEmail(u.getEmail());

             int idNuevo = (creado != null) ? creado.getIdUsuario() : -1;


                // Si es trabajador → guardar especialidades
                if (tipo == 2) {
                    String[] idsSel = req.getParameterValues("especialidades");

                    if (idsSel != null) {
                        List<Integer> lista = new ArrayList<>();
                        for (String id : idsSel) lista.add(Integer.parseInt(id));

                        usuarioService.asignarEspecialidades(idNuevo, lista);
                    }
                }

                resp.sendRedirect("admin?accion=listarUsuarios");
                return;
            }
            
            if ("actualizarEspecialidad".equals(accion)) {

                Especialidad esp = new Especialidad();
                esp.setIdEspecialidad(Integer.parseInt(req.getParameter("idEspecialidad")));
                esp.setNombre(req.getParameter("nombre"));

                especialidadService.actualizarEspecialidad(esp);

                resp.sendRedirect("admin?accion=listarEspecialidades");
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}

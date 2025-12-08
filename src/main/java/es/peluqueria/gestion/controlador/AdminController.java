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

                case "nuevoServicio":
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearServicio.jsp").forward(req, resp);
                    return;

                case "nuevaEspecialidad":
                    req.getRequestDispatcher("jspAdmin/crearEspecialidad.jsp").forward(req, resp);
                    return;

                case "nuevoUsuario":
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;

                case "editarServicio":
                    try {
                        int idServicio = Integer.parseInt(req.getParameter("id"));
                        Servicio servicio = servicioService.obtenerPorId(idServicio);

                        if (servicio == null) {
                            req.setAttribute("error", "El servicio solicitado no existe.");
                            req.setAttribute("servicios", servicioService.listarTodos());
                            req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                            return;
                        }

                        Integer idEspecialidadActual = servicioService.obtenerEspecialidadDeServicio(idServicio);
                        req.setAttribute("servicio", servicio);
                        req.setAttribute("idEspecialidadActual", idEspecialidadActual);
                        req.setAttribute("especialidades", especialidadService.listarTodas());
                        req.getRequestDispatcher("jspAdmin/editarServicio.jsp").forward(req, resp);
                        return;

                    } catch (NumberFormatException ex) {
                        req.setAttribute("error", "ID de servicio inválido.");
                        req.setAttribute("servicios", servicioService.listarTodos());
                        req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                        return;
                    }

                case "editarUsuario":
                    try {
                        int idUsuario = Integer.parseInt(req.getParameter("id"));
                        Usuario usuarioEdit = usuarioService.obtenerPorId(idUsuario);

                        if (usuarioEdit == null) {
                            req.setAttribute("error", "Usuario no encontrado.");
                            req.setAttribute("todosUsu", usuarioService.listarTodos());
                            req.getRequestDispatcher("jspAdmin/listaUsuarios.jsp").forward(req, resp);
                            return;
                        }

                        req.setAttribute("especialidades", especialidadService.listarTodas());
                        req.setAttribute("usuarioEdit", usuarioEdit);
                        req.getRequestDispatcher("/jspAdmin/editarUsuario.jsp").forward(req, resp);
                        return;

                    } catch (NumberFormatException ex) {
                        req.setAttribute("error", "ID de usuario inválido.");
                        req.setAttribute("todosUsu", usuarioService.listarTodos());
                        req.getRequestDispatcher("jspAdmin/listaUsuarios.jsp").forward(req, resp);
                        return;
                    }

                case "editarEspecialidad":
                    try {
                        int idEsp = Integer.parseInt(req.getParameter("id"));
                        Especialidad esp = especialidadService.obtenerPorId(idEsp);
                        if (esp == null) {
                            req.setAttribute("error", "Especialidad no encontrada.");
                            req.setAttribute("especialidades", especialidadService.listarTodas());
                            req.getRequestDispatcher("jspAdmin/listaEspecialidades.jsp").forward(req, resp);
                            return;
                        }
                        req.setAttribute("especialidad", esp);
                        req.getRequestDispatcher("jspAdmin/editarEspecialidad.jsp").forward(req, resp);
                        return;
                    } catch (NumberFormatException ex) {
                        req.setAttribute("error", "ID de especialidad inválido.");
                        req.setAttribute("especialidades", especialidadService.listarTodas());
                        req.getRequestDispatcher("jspAdmin/listaEspecialidades.jsp").forward(req, resp);
                        return;
                    }

                case "eliminarEspecialidad":
                    try {
                        int idEliminar = Integer.parseInt(req.getParameter("id"));
                        boolean ok = especialidadService.eliminarEspecialidad(idEliminar);
                        if (!ok) req.setAttribute("error", "No se pudo eliminar la especialidad.");
                    } catch (Exception e) {
                        req.setAttribute("error", "Error al eliminar especialidad.");
                    }
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/listaEspecialidades.jsp").forward(req, resp);
                    return;

                case "eliminarServicio":
                    try {
                        int idEliminarServ = Integer.parseInt(req.getParameter("id"));
                        boolean eliminado = servicioService.eliminarServicio(idEliminarServ);
                        if (!eliminado) {
                            req.setAttribute("error", "No se puede eliminar este servicio porque tiene citas activas.");
                            req.setAttribute("servicios", servicioService.listarTodos());
                            req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                            return;
                        } else {
                            resp.sendRedirect("admin?accion=listarServicios");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        req.setAttribute("error", "ID de servicio inválido.");
                        req.setAttribute("servicios", servicioService.listarTodos());
                        req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                        return;
                    }

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
            req.setAttribute("error", "Error interno.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
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

            // =======================
            // CREAR SERVICIO
            // =======================
            if ("crearServicio".equals(accion)) {

                String nombre = req.getParameter("nombre");
                String durStr = req.getParameter("duracion");
                String precioStr = req.getParameter("precio");
                String idEspStr = req.getParameter("idEspecialidad");

                // validaciones básicas
                if (nombre == null || nombre.isBlank()) {
                    req.setAttribute("error", "El nombre del servicio no puede estar vacío.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearServicio.jsp").forward(req, resp);
                    return;
                }
                int duracion;
                BigDecimal precio;
                int idEspecialidad;
                try {
                    duracion = Integer.parseInt(durStr);
                    precio = new BigDecimal(precioStr);
                    idEspecialidad = Integer.parseInt(idEspStr);
                } catch (Exception e) {
                    req.setAttribute("error", "Duración, precio o especialidad inválidos.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearServicio.jsp").forward(req, resp);
                    return;
                }
                if (duracion <= 0) {
                    req.setAttribute("error", "La duración debe ser mayor que 0.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearServicio.jsp").forward(req, resp);
                    return;
                }
                if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                    req.setAttribute("error", "El precio debe ser mayor que 0.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearServicio.jsp").forward(req, resp);
                    return;
                }

                Servicio s = new Servicio();
                s.setNombre(nombre);
                s.setDuracionMinutos(duracion);
                s.setPrecio(precio);
                s.setDescripcion(req.getParameter("descripcion"));

                servicioService.registrarServicio(s, idEspecialidad);

                resp.sendRedirect("admin?accion=panel");
                return;
            }

            // ======================
            // ACTUALIZAR SERVICIO
            // ======================
            if ("actualizarServicio".equals(accion)) {

                String nombre = req.getParameter("nombre");
                String durStr = req.getParameter("duracion");
                String precioStr = req.getParameter("precio");
                String idEspStr = req.getParameter("idEspecialidad");
                String idServStr = req.getParameter("idServicio");

                if (nombre == null || nombre.isBlank()) {
                    req.setAttribute("error", "El nombre del servicio no puede estar vacío.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                    return;
                }

                try {
                    int idServicio = Integer.parseInt(idServStr);
                    int duracion = Integer.parseInt(durStr);
                    BigDecimal precio = new BigDecimal(precioStr);
                    int idEspecialidad = Integer.parseInt(idEspStr);

                    if (duracion <= 0 || precio.compareTo(BigDecimal.ZERO) <= 0) {
                        req.setAttribute("error", "Duración o precio inválidos.");
                        req.setAttribute("idEspecialidad", idEspecialidad);
                        req.setAttribute("servicio", servicioService.obtenerPorId(idServicio));
                        req.setAttribute("especialidades", especialidadService.listarTodas());
                        req.getRequestDispatcher("jspAdmin/editarServicio.jsp").forward(req, resp);
                        return;
                    }

                    Servicio s = new Servicio();
                    s.setIdServicio(idServicio);
                    s.setNombre(nombre);
                    s.setDuracionMinutos(duracion);
                    s.setPrecio(precio);
                    s.setDescripcion(req.getParameter("descripcion"));

                    servicioService.actualizarServicio(s, idEspecialidad);
                    resp.sendRedirect("admin?accion=listarServicios");
                    return;

                } catch (NumberFormatException nfe) {
                    req.setAttribute("error", "Datos numéricos inválidos.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/listaServicios.jsp").forward(req, resp);
                    return;
                }
            }

            // ======================
            // CREAR USUARIO
            // ======================
            if ("crearUsuario".equals(accion)) {

                String nombre = req.getParameter("nombre");
                String apellido = req.getParameter("apellido");
                String email = req.getParameter("email");
                String pass = req.getParameter("contrasena");
                String tipoStr = req.getParameter("tipoUsuario");

                if (nombre == null || nombre.isBlank() ||
                    apellido == null || apellido.isBlank() ||
                    email == null || email.isBlank() ||
                    pass == null || pass.isBlank() ) {

                    req.setAttribute("error", "Todos los campos obligatorios deben rellenarse.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;
                }

                if (!email.contains("@")) {
                    req.setAttribute("error", "Email inválido.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;
                }

                int tipo;
                try {
                    tipo = Integer.parseInt(tipoStr);
                } catch (NumberFormatException ex) {
                    req.setAttribute("error", "Tipo de usuario inválido.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;
                }

                // comprobar email duplicado
                Usuario existe = usuarioService.obtenerPorEmail(email);
                if (existe != null) {
                    req.setAttribute("error", "Ya existe un usuario con ese email.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;
                }

                Usuario u = new Usuario();
                u.setNombre(nombre);
                u.setApellido(apellido);
                u.setEmail(email);
                u.setContrasena(pass);
                u.setTelefono(req.getParameter("telefono"));
                u.setTipoUsuario(tipo);

                boolean correcto = usuarioService.registrarUsuario(u);

                if (!correcto) {
                    req.setAttribute("error", "No se pudo crear el usuario.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.getRequestDispatcher("jspAdmin/crearUsuario.jsp").forward(req, resp);
                    return;
                }

                Usuario creado = usuarioService.obtenerPorEmail(email);
                int idNuevo = (creado != null) ? creado.getIdUsuario() : -1;

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

            // ======================
            // ACTUALIZAR USUARIO
            // ======================
            if ("actualizarUsuario".equals(accion)) {

                int id = Integer.parseInt(req.getParameter("idUsuario"));

                if (req.getParameter("nombre") == null || req.getParameter("nombre").isBlank() ||
                    req.getParameter("apellido") == null || req.getParameter("apellido").isBlank() ||
                    req.getParameter("email") == null || req.getParameter("email").isBlank()) {

                    req.setAttribute("error", "Nombre, apellido y email son obligatorios.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.setAttribute("usuarioEdit", usuarioService.obtenerPorId(id));
                    
                    req.getRequestDispatcher("/jspAdmin/editarUsuario.jsp").forward(req, resp);
                    return;
                }

                String email = req.getParameter("email");
                Usuario otro = usuarioService.obtenerPorEmail(email);
                if (otro != null && otro.getIdUsuario() != id) {
                    req.setAttribute("error", "El email ya está en uso por otro usuario.");
                    req.setAttribute("especialidades", especialidadService.listarTodas());
                    req.setAttribute("usuarioEdit", usuarioService.obtenerPorId(id));
                    req.getRequestDispatcher("/jspAdmin/editarUsuario.jsp").forward(req, resp);
                    return;
                }

                Usuario u = new Usuario();
                u.setIdUsuario(id);
                u.setNombre(req.getParameter("nombre"));
                u.setApellido(req.getParameter("apellido"));
                u.setEmail(email);
                u.setTelefono(req.getParameter("telefono"));
                u.setTipoUsuario(Integer.parseInt(req.getParameter("tipoUsuario")));

                String nuevaPass = req.getParameter("contrasena");
                if (nuevaPass != null && !nuevaPass.isBlank()) {
                    u.setContrasena(nuevaPass);
                } else {
                    Usuario original = usuarioService.obtenerPorId(id);
                    u.setContrasena(original.getContrasena());
                }

                usuarioService.actualizarUsuario(u);

                especialidadService.eliminarTodasEspecialidadDeTrabajador(id);
                if (u.getTipoUsuario() == 2) {
                    String[] idsEsp = req.getParameterValues("especialidades");
                    if (idsEsp != null) {
                        for (String idEsp : idsEsp) {
                            especialidadService.asignarEspecialidadATrabajador(id, Integer.parseInt(idEsp));
                        }
                    }
                }

                resp.sendRedirect("admin?accion=listarUsuarios");
                return;
            }

            // ======================
            // CREAR ESPECIALIDAD
            // ======================
            if ("crearEspecialidad".equals(accion)) {

                String nombre = req.getParameter("nombre");
                if (nombre == null || nombre.isBlank()) {
                    req.setAttribute("error", "El nombre no puede estar vacío.");
                    req.getRequestDispatcher("jspAdmin/crearEspecialidad.jsp").forward(req, resp);
                    return;
                }

                Especialidad e = new Especialidad();
                e.setNombre(nombre);
                especialidadService.registrarEspecialidad(e);

                resp.sendRedirect("admin?accion=panel");
                return;
            }

            // ======================
            // ACTUALIZAR ESPECIALIDAD
            // ======================
            if ("actualizarEspecialidad".equals(accion)) {

                try {
                    Especialidad esp = new Especialidad();
                    esp.setIdEspecialidad(Integer.parseInt(req.getParameter("idEspecialidad")));
                    String nombre = req.getParameter("nombre");
                    if (nombre == null || nombre.isBlank()) {
                        req.setAttribute("error", "El nombre de la especialidad no puede estar vacío.");
                        req.setAttribute("especialidad", especialidadService.obtenerPorId(esp.getIdEspecialidad()));
                        req.getRequestDispatcher("jspAdmin/editarEspecialidad.jsp").forward(req, resp);
                        return;
                    }
                    esp.setNombre(nombre);
                    especialidadService.actualizarEspecialidad(esp);
                    resp.sendRedirect("admin?accion=listarEspecialidades");
                    return;
                } catch (NumberFormatException nfe) {
                    req.setAttribute("error", "ID de especialidad inválido.");
                    req.getRequestDispatcher("jspAdmin/listaEspecialidades.jsp").forward(req, resp);
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error interno en la operación.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}

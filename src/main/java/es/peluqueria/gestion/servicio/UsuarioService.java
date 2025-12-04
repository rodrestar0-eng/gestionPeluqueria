package es.peluqueria.gestion.servicio;

import es.peluqueria.gestion.DAO.UsuarioDAO;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.util.HashUtil;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Registrar (admin crea usuarios en general)
    public boolean registrarUsuario(Usuario usuario) {
        try {
            if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
                if (usuarioDAO.obtenerPorEmail(usuario.getEmail()) != null) return false;
            }
            usuario.setContrasena(HashUtil.hashPassword(usuario.getContrasena()));
            return usuarioDAO.insertar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void asignarEspecialidades(int idUsuario, List<Integer> especialidades) {
        try {
            usuarioDAO.asignarEspecialidades(idUsuario, especialidades);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Login: compara hash
    public Usuario login(String email, String contrasena) {
        try {
            Usuario u = usuarioDAO.obtenerPorEmail(email);
            if (u == null) return null;
            String hashedInput = HashUtil.hashPassword(contrasena);
            return hashedInput.equals(u.getContrasena()) ? u : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // CRUD y finders
    public Usuario obtenerPorId(int id) {
        try { return usuarioDAO.obtenerPorId(id); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public Usuario obtenerPorEmail(String email) {
        try { return usuarioDAO.obtenerPorEmail(email); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public List<Usuario> listarTodos() {
        try { return usuarioDAO.listarTodos(); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public List<Usuario> listarPorTipo(int tipo) {
        try { return usuarioDAO.listarPorTipo(tipo); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public boolean actualizarUsuario(Usuario usuario) {
        try {
            if (usuario.getContrasena() != null && !usuario.getContrasena().isBlank()) {
                usuario.setContrasena(HashUtil.hashPassword(usuario.getContrasena()));
            } else {
                Usuario actual = usuarioDAO.obtenerPorId(usuario.getIdUsuario());
                if (actual != null) usuario.setContrasena(actual.getContrasena());
            }
            return usuarioDAO.actualizar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarPerfilUsuario(Usuario usuario) {
        try {
            return usuarioDAO.actualizarPerfilUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Usuario> listarPeluqueros() {
        try {
            return usuarioDAO.listarPorTipo(2); // 2 = peluquero
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String obtenerNombrePorId(int idUsuario) throws SQLException {
        Usuario u = usuarioDAO.obtenerPorId(idUsuario);
        return (u != null) ? u.getNombre() : "Sin asignar";
    }
    
    public List<Usuario> listarPeluquerosPorEspecialidad(Integer idEspecialidad) {
        if (idEspecialidad == null) return java.util.Collections.emptyList();
        try {
            return usuarioDAO.listarPeluquerosPorEspecialidad(idEspecialidad);
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }





    public boolean eliminarUsuario(int id) {
        try { return usuarioDAO.eliminar(id); } catch (Exception e) { e.printStackTrace(); return false; }
    }
}

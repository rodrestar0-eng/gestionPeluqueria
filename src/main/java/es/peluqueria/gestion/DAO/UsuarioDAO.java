	package es.peluqueria.gestion.DAO;
	
	import es.peluqueria.gestion.bbdd.DBConnection;
	import es.peluqueria.gestion.modelo.Usuario;
	
	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	
	/**
	 * DAO completo para la entidad Usuario.
	 * Gestiona las operaciones CRUD y búsquedas por campos clave.
	 */
	public class UsuarioDAO {
	
	    // =========================
	    // MÉTODO: INSERTAR
	    // =========================
	    public boolean insertar(Usuario usuario) throws SQLException {
	        String sql = "INSERT INTO USUARIOS (NOMBRE, APELLIDO, EMAIL, CONTRASENA, TIPO_USUARIO, telefono) VALUES (?, ?, ?, ?, ?, ?)";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setString(1, usuario.getNombre());
	            ps.setString(2, usuario.getApellido());
	            ps.setString(3, usuario.getEmail());
	            ps.setString(4, usuario.getContrasena());
	            ps.setInt(5, usuario.getTipoUsuario());
	            ps.setString(6, usuario.getTelefono());
	
	            return ps.executeUpdate() > 0;
	        }
	    }
	
	    // =========================
	    // MÉTODO: OBTENER POR ID
	    // =========================
	    public Usuario obtenerPorId(int idUsuario) throws SQLException {
	        String sql = "SELECT * FROM USUARIOS WHERE ID_USUARIO = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setInt(1, idUsuario);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return mapearUsuario(rs);
	                }
	            }
	        }
	        return null;
	    }
	    
	    public boolean actualizarPerfilUsuario(Usuario usuario) throws SQLException {
	        String sql = "UPDATE USUARIOS SET NOMBRE = ?, APELLIDO = ?, EMAIL = ?, TELEFONO = ? " +
	                     "WHERE ID_USUARIO = ?";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, usuario.getNombre());
	            ps.setString(2, usuario.getApellido());
	            ps.setString(3, usuario.getEmail());
	            ps.setString(4, usuario.getTelefono());
	            ps.setInt(5, usuario.getIdUsuario());

	            return ps.executeUpdate() > 0;
	        }
	    }
	    public boolean emailExisteParaOtroUsuario(String email, int idUsuario) {
	        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE EMAIL = ? AND ID_USUARIO != ?";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, email);
	            ps.setInt(2, idUsuario);

	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return false;
	    }


	
	    // =========================
	    // MÉTODO: OBTENER POR EMAIL
	    // =========================
	    public Usuario obtenerPorEmail(String email) throws SQLException {
	        String sql = "SELECT * FROM USUARIOS WHERE EMAIL = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setString(1, email);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return mapearUsuario(rs);
	                }
	            }
	        }
	        return null;
	    }
	 // ============================================
	 // ASIGNAR ESPECIALIDADES A UN TRABAJADOR
	 // ============================================
	    public boolean asignarEspecialidades(int idUsuario, List<Integer> especialidadesSeleccionadas) {
	        String deleteSQL = "DELETE FROM TRABAJADOR_ESPECIALIDAD WHERE ID_USUARIO = ?";
	        String insertSQL = "INSERT INTO TRABAJADOR_ESPECIALIDAD (ID_USUARIO, ID_ESPECIALIDAD) VALUES (?, ?)";

	        try (Connection conn = DBConnection.getConnection()) {

	            // 1. Borrar todas las especialidades actuales del usuario
	            try (PreparedStatement psDelete = conn.prepareStatement(deleteSQL)) {
	                psDelete.setInt(1, idUsuario);
	                psDelete.executeUpdate();
	            }

	            // 2. Insertar solo las seleccionadas en el formulario
	            try (PreparedStatement psInsert = conn.prepareStatement(insertSQL)) {
	                for (Integer idEsp : especialidadesSeleccionadas) {
	                    psInsert.setInt(1, idUsuario);
	                    psInsert.setInt(2, idEsp);
	                    psInsert.addBatch();
	                }
	                psInsert.executeBatch();
	            }

	            return true;

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }


	    public void eliminarEspecialidadesDeUsuario(int idUsuario) throws SQLException {
	        String sql = "DELETE FROM TRABAJADOR_ESPECIALIDAD WHERE ID_USUARIO = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, idUsuario);
	            ps.executeUpdate();
	        }
	    }

	
	    // =========================
	    // MÉTODO: ACTUALIZAR
	    // =========================
	    public boolean actualizar(Usuario usuario) throws SQLException {
	        String sql = "UPDATE USUARIOS SET NOMBRE = ?, APELLIDO = ?, EMAIL = ?, CONTRASENA = ?, TIPO_USUARIO = ? " +
	                     "WHERE ID_USUARIO = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setString(1, usuario.getNombre());
	            ps.setString(2, usuario.getApellido());
	            ps.setString(3, usuario.getEmail());
	            ps.setString(4, usuario.getContrasena());
	            ps.setInt(5, usuario.getTipoUsuario());
	            ps.setInt(6, usuario.getIdUsuario());
	
	            return ps.executeUpdate() > 0;
	        }
	    }
	
	    // =========================
	    // MÉTODO: ELIMINAR
	    // =========================
	    public boolean eliminar(int idUsuario) throws SQLException {
	        String sql = "DELETE FROM USUARIOS WHERE ID_USUARIO = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setInt(1, idUsuario);
	            return ps.executeUpdate() > 0;
	        }
	    }
	
	    // =========================
	    // MÉTODO: LISTAR TODOS
	    // =========================
	    public List<Usuario> listarTodos() throws SQLException {
	        String sql = "SELECT * FROM USUARIOS ORDER BY ID_USUARIO";
	        List<Usuario> usuarios = new ArrayList<>();
	
	        try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	
	            while (rs.next()) {
	                usuarios.add(mapearUsuario(rs));
	            }
	        }
	        return usuarios;
	    }
	
	    // =========================
	    // MÉTODO: LISTAR POR TIPO
	    // =========================
	    public List<Usuario> listarPorTipo(int tipoUsuario) throws SQLException {
	        String sql = "SELECT * FROM USUARIOS WHERE TIPO_USUARIO = ? ORDER BY NOMBRE";
	        List<Usuario> usuarios = new ArrayList<>();
	
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setInt(1, tipoUsuario);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    usuarios.add(mapearUsuario(rs));
	                }
	            }
	        }
	        return usuarios;
	    }
	    
	    public List<Usuario> listarPeluquerosPorEspecialidad(int idEspecialidad) throws SQLException {
	        List<Usuario> lista = new ArrayList<>();

	        String sql = """
	            SELECT u.* 
	            FROM USUARIOS u
	            INNER JOIN TRABAJADOR_ESPECIALIDAD ue ON u.ID_USUARIO = ue.ID_TRABAJADOR
	            WHERE ue.ID_ESPECIALIDAD = ? 
	              AND u.TIPO_USUARIO = 2
	            ORDER BY u.NOMBRE
	        """;

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, idEspecialidad);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                lista.add(mapearUsuario(rs));  // <-- ahora se mapea COMPLETO correctamente
	            }
	        }

	        return lista;
	    }

	
	    // =========================
	    // MÉTODO AUXILIAR: MAPEAR RESULTSET → OBJETO
	    // =========================
	    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
	        Usuario u = new Usuario();
	        u.setIdUsuario(rs.getInt("ID_USUARIO"));
	        u.setNombre(rs.getString("NOMBRE"));
	        u.setApellido(rs.getString("APELLIDO"));
	        u.setEmail(rs.getString("EMAIL"));
	        u.setContrasena(rs.getString("CONTRASENA"));
	        u.setTelefono(rs.getString("telefono"));
	        u.setTipoUsuario(rs.getInt("TIPO_USUARIO"));
	        return u;
	    }
	}

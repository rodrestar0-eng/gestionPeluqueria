package es.peluqueria.gestion.DAO;

import es.peluqueria.gestion.bbdd.DBConnection;
import es.peluqueria.gestion.modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO completo para la entidad Cliente.
 * Gestiona todas las operaciones CRUD y búsquedas específicas.
 */
public class ClienteDAO {

    // =========================
    // MÉTODO: INSERTAR
    // =========================
    public boolean insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO CLIENTES (NOMBRE, APELLIDO, TELEFONO, EMAIL, CONTRASENA, FECHA_REGISTRO) " +
                     "VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getContrasena());

            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // MÉTODO: OBTENER POR ID
    // =========================
    public Cliente obtenerPorId(int idCliente) throws SQLException {
        String sql = "SELECT * FROM CLIENTES WHERE ID_CLIENTE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    // =========================
    // MÉTODO: OBTENER POR EMAIL
    // =========================
    public Cliente obtenerPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM CLIENTES WHERE EMAIL = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    // =========================
    // MÉTODO: OBTENER POR TELÉFONO
    // =========================
    public Cliente obtenerPorTelefono(String telefono) throws SQLException {
        String sql = "SELECT * FROM CLIENTES WHERE TELEFONO = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    // =========================
    // MÉTODO: ACTUALIZAR
    // =========================
    public boolean actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE CLIENTES SET NOMBRE = ?, APELLIDO = ?, TELEFONO = ?, EMAIL = ?, CONTRASENA = ? " +
                     "WHERE ID_CLIENTE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getContrasena());
            ps.setInt(6, cliente.getIdCliente());

            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // MÉTODO: ELIMINAR
    // =========================
    public boolean eliminar(int idCliente) throws SQLException {
        String sql = "DELETE FROM CLIENTES WHERE ID_CLIENTE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        }
    }

    // =========================
    // MÉTODO: LISTAR TODOS
    // =========================
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM CLIENTES ORDER BY ID_CLIENTE";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        }
        return clientes;
    }

    // =========================
    // MÉTODO AUXILIAR: MAPEO DE RESULTSET → OBJETO
    // =========================
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("ID_CLIENTE"));
        c.setNombre(rs.getString("NOMBRE"));
        c.setApellido(rs.getString("APELLIDO"));
        c.setTelefono(rs.getString("TELEFONO"));
        c.setEmail(rs.getString("EMAIL"));
        c.setContrasena(rs.getString("CONTRASENA"));
        java.sql.Date fechaSql = rs.getDate("FECHA_REGISTRO");
        if (fechaSql != null) {
            c.setFechaRegistro(fechaSql.toLocalDate());
        }
        return c;
    }
}

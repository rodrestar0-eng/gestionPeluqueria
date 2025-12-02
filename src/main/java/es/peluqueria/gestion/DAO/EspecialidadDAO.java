package es.peluqueria.gestion.DAO;

import es.peluqueria.gestion.bbdd.DBConnection;
import es.peluqueria.gestion.modelo.Especialidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAO {

    // Insertar nueva especialidad
    public boolean insertar(Especialidad esp) throws SQLException {
        String sql = "INSERT INTO ESPECIALIDADES (NOMBRE) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, esp.getNombre());
            return ps.executeUpdate() > 0;
        }
    }

    // Obtener por ID
    public Especialidad obtenerPorId(int id) throws SQLException {
        String sql = "SELECT ID_ESPECIALIDAD, NOMBRE FROM ESPECIALIDADES WHERE ID_ESPECIALIDAD = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    // Obtener por nombre exacto (case-insensitive)
    public Especialidad obtenerPorNombre(String nombre) throws SQLException {
        String sql = "SELECT ID_ESPECIALIDAD, NOMBRE FROM ESPECIALIDADES WHERE UPPER(NOMBRE) = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre == null ? null : nombre.trim().toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    // Listar todas
    public List<Especialidad> listarTodas() throws SQLException {
        List<Especialidad> lista = new ArrayList<>();
        String sql = "SELECT ID_ESPECIALIDAD, NOMBRE FROM ESPECIALIDADES ORDER BY NOMBRE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // Actualizar (solo nombre)
    public boolean actualizar(Especialidad esp) throws SQLException {
        String sql = "UPDATE ESPECIALIDADES SET NOMBRE = ? WHERE ID_ESPECIALIDAD = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, esp.getNombre());
            ps.setInt(2, esp.getIdEspecialidad());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar por id
    public boolean eliminar(int idEspecialidad) throws SQLException {
        String sql = "DELETE FROM TRABAJADOR_ESPECIALIDAD WHERE ID_ESPECIALIDAD = ?"; // limpiar relaciones si existen
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEspecialidad);
            ps.executeUpdate(); // no comprobamos resultado, luego borramos la propia especialidad
        }

        String sql2 = "DELETE FROM ESPECIALIDADES WHERE ID_ESPECIALIDAD = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.setInt(1, idEspecialidad);
            return ps2.executeUpdate() > 0;
        }
    }

    // Mapeo
    private Especialidad mapear(ResultSet rs) throws SQLException {
        Especialidad e = new Especialidad();
        e.setIdEspecialidad(rs.getInt("ID_ESPECIALIDAD"));
        e.setNombre(rs.getString("NOMBRE"));
        return e;
    }
}

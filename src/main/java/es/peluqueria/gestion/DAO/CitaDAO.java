package es.peluqueria.gestion.DAO;

import es.peluqueria.gestion.bbdd.DBConnection;
import es.peluqueria.gestion.modelo.Cita;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    // INSERTAR
    public boolean insertar(Cita cita) throws SQLException {
        String sql = "INSERT INTO CITAS (ID_CLIENTE, ID_PELUQUERO, ID_SERVICIO, FECHA_HORA_CITA, HORA_FIN, ESTADO) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdCliente());
            ps.setInt(2, cita.getIdPeluquero());
            ps.setInt(3, cita.getIdServicio());

            if (cita.getFechaCita() != null && cita.getHoraInicio() != null && !cita.getHoraInicio().isBlank()) {
                LocalDate ld = cita.getFechaCita();
                LocalTime lt = LocalTime.parse(cita.getHoraInicio());
                LocalDateTime ldt = LocalDateTime.of(ld, lt);
                ps.setTimestamp(4, Timestamp.valueOf(ldt));
            } else {
                ps.setTimestamp(4, null);
            }

            ps.setString(5, cita.getHoraFin());
            ps.setInt(6, cita.getEstado());

            return ps.executeUpdate() > 0;
        }
    }

    // OBTENER POR ID
    public Cita obtenerPorId(int idCita) throws SQLException {
        String sql = """
            SELECT c.*, s.NOMBRE AS NOMBRE_SERVICIO
            FROM CITAS c
            LEFT JOIN SERVICIOS s ON c.ID_SERVICIO = s.ID_SERVICIO
            WHERE c.ID_CITA = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCita);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCita(rs);
            }
        }
        return null;
    }

    public boolean actualizarEstado(int idCita, int nuevoEstado) {

        String sql = "UPDATE CITAS SET ESTADO = ?, FECHA_HORA_CITA = FECHA_HORA_CITA WHERE ID_CITA = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idCita);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR TODAS (con nombre de servicio)
    public List<Cita> listarTodas() throws SQLException {

        String sql = """
            SELECT c.*, s.NOMBRE AS NOMBRE_SERVICIO
            FROM CITAS c
            LEFT JOIN SERVICIOS s ON c.ID_SERVICIO = s.ID_SERVICIO
            ORDER BY FECHA_HORA_CITA DESC
        """;

        List<Cita> citas = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) citas.add(mapearCita(rs));
        }
        return citas;
    }

    // LISTAR POR CLIENTE (con nombre de servicio)
    public List<Cita> listarPorCliente(int idCliente) throws SQLException {

        String sql = """
            SELECT c.*, s.NOMBRE AS NOMBRE_SERVICIO
            FROM CITAS c
            LEFT JOIN SERVICIOS s ON c.ID_SERVICIO = s.ID_SERVICIO
            WHERE c.ID_CLIENTE = ?
            ORDER BY FECHA_HORA_CITA DESC
        """;

        List<Cita> citas = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) citas.add(mapearCita(rs));
            }
        }
        return citas;
    }

    // LISTAR POR PELUQUERO (con nombre de servicio)
    public List<Cita> listarPorPeluquero(int idPeluquero) throws SQLException {

        String sql = """
            SELECT c.*, s.NOMBRE AS NOMBRE_SERVICIO
            FROM CITAS c
            LEFT JOIN SERVICIOS s ON c.ID_SERVICIO = s.ID_SERVICIO
            WHERE c.ID_PELUQUERO = ?
            ORDER BY FECHA_HORA_CITA DESC
        """;

        List<Cita> citas = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPeluquero);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) citas.add(mapearCita(rs));
            }
        }
        return citas;
    }

    public boolean actualizar(Cita cita) throws SQLException {

        String sql = """
            UPDATE CITAS SET 
            ID_CLIENTE=?, ID_PELUQUERO=?, ID_SERVICIO=?, FECHA_HORA_CITA=?, 
            HORA_FIN=?, ESTADO=?
            WHERE ID_CITA=?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdCliente());
            ps.setInt(2, cita.getIdPeluquero());
            ps.setInt(3, cita.getIdServicio());

            if (cita.getFechaCita() != null && cita.getHoraInicio() != null && !cita.getHoraInicio().isBlank()) {
                LocalDate ld = cita.getFechaCita();
                LocalTime lt = LocalTime.parse(cita.getHoraInicio());
                LocalDateTime ldt = LocalDateTime.of(ld, lt);
                ps.setTimestamp(4, Timestamp.valueOf(ldt));
            } else {
                ps.setTimestamp(4, null);
            }

            ps.setString(5, cita.getHoraFin());
            ps.setInt(6, cita.getEstado());
            ps.setInt(7, cita.getIdCita());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idCita) throws SQLException {
        String sql = "DELETE FROM CITAS WHERE ID_CITA = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            return ps.executeUpdate() > 0;
        }
    }

    public int contarCitasActivasPorServicio(int idServicio) throws SQLException {

        String sql = "SELECT COUNT(*) FROM CITAS WHERE ID_SERVICIO = ? AND ESTADO = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idServicio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // MAPEADOR COMPLETO (incluye nombreServicio)
    private Cita mapearCita(ResultSet rs) throws SQLException {
        Cita c = new Cita();

        c.setIdCita(rs.getInt("ID_CITA"));
        c.setIdCliente(rs.getInt("ID_CLIENTE"));
        c.setIdPeluquero(rs.getInt("ID_PELUQUERO"));
        c.setIdServicio(rs.getInt("ID_SERVICIO"));

        Timestamp ts = rs.getTimestamp("FECHA_HORA_CITA");
        if (ts != null) {
            LocalDateTime ldt = ts.toLocalDateTime();
            c.setFechaCita(ldt.toLocalDate());

            LocalTime inicio = ldt.toLocalTime().withSecond(0).withNano(0);
            c.setHoraInicio(String.format("%02d:%02d", inicio.getHour(), inicio.getMinute()));
        }

        c.setHoraFin(rs.getString("HORA_FIN"));
        c.setEstado(rs.getInt("ESTADO"));

        // *** NUEVO: nombre del servicio ***
        try { 
            c.setNombreServicio(rs.getString("NOMBRE_SERVICIO"));
        } catch (Exception ignored) {}

        return c;
    }
}

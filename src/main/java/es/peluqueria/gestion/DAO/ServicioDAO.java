package es.peluqueria.gestion.DAO;

import es.peluqueria.gestion.modelo.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import es.peluqueria.gestion.bbdd.DBConnection;

public class ServicioDAO {

    private static final String SELECT_ALL =
        "SELECT * FROM SERVICIOS ORDER BY NOMBRE";
    private static final String UPDATE_SQL =
        "UPDATE SERVICIOS SET NOMBRE=?, PRECIO=?, DURACION_MINUTOS=?, DESCRIPCION=? WHERE ID_SERVICIO=?";
    private static final String DELETE_SQL =
        "DELETE FROM SERVICIOS WHERE ID_SERVICIO=?";
    private static final String SELECT_BY_ID =
        "SELECT * FROM SERVICIOS WHERE ID_SERVICIO=?";

    public int insertar(Servicio s) {
        String sql = "INSERT INTO SERVICIOS (NOMBRE, PRECIO, DURACION_MINUTOS, DESCRIPCION) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, s.getNombre());
            ps.setBigDecimal(2, s.getPrecio());
            ps.setInt(3, s.getDuracionMinutos());
            ps.setString(4, s.getDescripcion());

            int filas = ps.executeUpdate();
            if (filas == 0) return -1;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);  // ID_SERVICIO generado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    

    public Servicio obtenerPorId(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Servicio s = new Servicio();
                s.setIdServicio(rs.getInt("ID_SERVICIO"));
                s.setNombre(rs.getString("NOMBRE"));
                s.setPrecio(rs.getBigDecimal("PRECIO"));
                s.setDuracionMinutos(rs.getInt("DURACION_MINUTOS"));
                s.setDescripcion(rs.getString("DESCRIPCION"));
                return s;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Servicio> listarTodos() {
        List<Servicio> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                Servicio s = new Servicio();
                s.setIdServicio(rs.getInt("ID_SERVICIO"));
                s.setNombre(rs.getString("NOMBRE"));
                s.setPrecio(rs.getBigDecimal("PRECIO"));
                s.setDuracionMinutos(rs.getInt("DURACION_MINUTOS"));
                s.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
 // Buscar servicios por nombre 
    public List<Servicio> buscarPorNombre(String nombre) throws SQLException {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT ID_SERVICIO, NOMBRE, PRECIO, DURACION_MINUTOS, DESCRIPCION " +
                     "FROM SERVICIOS WHERE UPPER(NOMBRE) LIKE ? ORDER BY NOMBRE";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (nombre == null ? "" : nombre.trim().toUpperCase()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Servicio s = new Servicio();
                    s.setIdServicio(rs.getInt("ID_SERVICIO"));
                    s.setNombre(rs.getString("NOMBRE"));
                    s.setPrecio(rs.getBigDecimal("PRECIO"));
                    s.setDuracionMinutos(rs.getInt("DURACION_MINUTOS"));
                    s.setDescripcion(rs.getString("DESCRIPCION"));
                    lista.add(s);
                }
            }
        }
        return lista;
    }


    public boolean actualizar(Servicio s) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, s.getNombre());
            ps.setBigDecimal(2, s.getPrecio());
            ps.setInt(3, s.getDuracionMinutos());
            ps.setString(4, s.getDescripcion());
            ps.setInt(5, s.getIdServicio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

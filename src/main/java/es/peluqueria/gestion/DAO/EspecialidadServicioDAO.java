package es.peluqueria.gestion.DAO;

import es.peluqueria.gestion.bbdd.DBConnection;
import java.sql.*;

public class EspecialidadServicioDAO {

    // Inserta la relación
    public boolean asignarEspecialidad(int idServicio, int idEspecialidad) {
        String sql = "INSERT INTO ESPECIALIDAD_SERVICIO (ID_SERVICIO, ID_ESPECIALIDAD) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idServicio);
            ps.setInt(2, idEspecialidad);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar especialidad de un servicio
    public boolean actualizarEspecialidad(int idServicio, int idEspecialidad) {
        String sql = "UPDATE ESPECIALIDAD_SERVICIO SET ID_ESPECIALIDAD=? WHERE ID_SERVICIO=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEspecialidad);
            ps.setInt(2, idServicio);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener especialidad asociada a un servicio
    public Integer obtenerEspecialidadDeServicio(int idServicio) {
        String sql = "SELECT ID_ESPECIALIDAD FROM ESPECIALIDAD_SERVICIO WHERE ID_SERVICIO=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idServicio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID_ESPECIALIDAD");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Eliminar relación
    public boolean eliminar(int idServicio) {
        String sql = "DELETE FROM ESPECIALIDAD_SERVICIO WHERE ID_SERVICIO=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idServicio);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

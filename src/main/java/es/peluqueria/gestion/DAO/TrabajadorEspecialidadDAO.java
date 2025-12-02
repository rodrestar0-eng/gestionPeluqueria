package es.peluqueria.gestion.DAO;

import java.sql.*;
import java.util.*;

import es.peluqueria.gestion.modelo.TrabajadorEspecialidad;
import es.peluqueria.gestion.bbdd.DBConnection;

public class TrabajadorEspecialidadDAO {

	public boolean asignarEspecialidad(int idTrabajador, int idEspecialidad) {

	    String sql = "INSERT INTO TRABAJADOR_ESPECIALIDAD (ID_TRABAJADOR, ID_ESPECIALIDAD) VALUES (?, ?)";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idTrabajador);
	        ps.setInt(2, idEspecialidad);

	        int filas = ps.executeUpdate();
	        return filas > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    public List<TrabajadorEspecialidad> obtenerPorTrabajador(int idTrabajador) {
        List<TrabajadorEspecialidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM TRABAJADOR_ESPECIALIDAD WHERE ID_TRABAJADOR = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTrabajador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TrabajadorEspecialidad te = new TrabajadorEspecialidad();
                te.setIdTrabajador(rs.getInt("ID_TRABAJADOR"));
                te.setIdEspecialidad(rs.getInt("ID_ESPECIALIDAD"));
                lista.add(te);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminarAsignacion(int idTrabajador, int idEspecialidad) {

        String sql = "DELETE FROM TRABAJADOR_ESPECIALIDAD WHERE ID_TRABAJADOR = ? AND ID_ESPECIALIDAD = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTrabajador);
            ps.setInt(2, idEspecialidad);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarEspecialidadesDeTrabajador(int idTrabajador) {
        String sql = "DELETE FROM TRABAJADOR_ESPECIALIDAD WHERE ID_TRABAJADOR = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTrabajador);
            ps.executeUpdate(); // no necesitamos el número exacto, interpretamos ejecución OK como true
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

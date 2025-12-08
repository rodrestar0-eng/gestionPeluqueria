package es.peluqueria.gestion.DAO;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import es.peluqueria.gestion.bbdd.DBConnection;
import es.peluqueria.gestion.modelo.BloqueoPeluquero;

public class BloqueoPeluqueroDAO {

    public void insertar(BloqueoPeluquero bloqueo) {
        String sql = "INSERT INTO BLOQUEOS_PELUQUERO (ID_TRABAJADOR, FECHA_INICIO, FECHA_FIN, MOTIVO) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bloqueo.getIdTrabajador());
            ps.setDate(2, Date.valueOf(bloqueo.getFechaInicio()));
            ps.setDate(3, Date.valueOf(bloqueo.getFechaFin()));
            ps.setString(4, bloqueo.getMotivo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BloqueoPeluquero> listarPorTrabajador(int idTrabajador) {
        List<BloqueoPeluquero> lista = new ArrayList<>();
        String sql = "SELECT * FROM BLOQUEOS_PELUQUERO WHERE ID_TRABAJADOR = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTrabajador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BloqueoPeluquero b = new BloqueoPeluquero();
                b.setIdBloqueo(rs.getInt("ID_BLOQUEO"));
                b.setIdTrabajador(rs.getInt("ID_TRABAJADOR"));
                b.setFechaInicio(rs.getDate("FECHA_INICIO").toLocalDate());
                b.setFechaFin(rs.getDate("FECHA_FIN").toLocalDate());
                b.setMotivo(rs.getString("MOTIVO"));
                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean existeSolape(int idTrabajador, LocalDate inicio, LocalDate fin) {

        String sql = "SELECT COUNT(*) FROM BLOQUEOS_PELUQUERO " +
                     "WHERE ID_TRABAJADOR = ? " +
                     "AND (FECHA_INICIO <= ? AND FECHA_FIN >= ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTrabajador);
            ps.setDate(2, Date.valueOf(fin));
            ps.setDate(3, Date.valueOf(inicio));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void eliminar(int idBloqueo) {
        String sql = "DELETE FROM BLOQUEOS_PELUQUERO WHERE ID_BLOQUEO = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBloqueo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

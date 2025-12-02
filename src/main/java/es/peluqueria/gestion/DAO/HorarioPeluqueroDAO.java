package es.peluqueria.gestion.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import es.peluqueria.gestion.bbdd.DBConnection;
import es.peluqueria.gestion.modelo.HorarioPeluquero;

public class HorarioPeluqueroDAO {

    public List<HorarioPeluquero> obtenerPorTrabajador(int idTrabajador) {
        List<HorarioPeluquero> lista = new ArrayList<>();

        String sql = "SELECT * FROM HORARIOS_PELUQUERO WHERE ID_TRABAJADOR = ? ORDER BY DIA_SEMANA, HORA_INICIO";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTrabajador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HorarioPeluquero h = new HorarioPeluquero();
                h.setIdHorario(rs.getInt("ID_HORARIO"));
                h.setIdTrabajador(rs.getInt("ID_TRABAJADOR"));
                h.setDiaSemana(rs.getInt("DIA_SEMANA"));
                h.setHoraInicio(rs.getString("HORA_INICIO"));
                h.setHoraFin(rs.getString("HORA_FIN"));
                lista.add(h);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }

    public void insertarHorario(HorarioPeluquero h) {
        String sql = "INSERT INTO HORARIOS_PELUQUERO (ID_TRABAJADOR, DIA_SEMANA, HORA_INICIO, HORA_FIN) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, h.getIdTrabajador());
            ps.setInt(2, h.getDiaSemana());
            ps.setString(3, h.getHoraInicio());
            ps.setString(4, h.getHoraFin());

            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminarHorario(int idHorario) {
        String sql = "DELETE FROM HORARIOS_PELUQUERO WHERE ID_HORARIO = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHorario);
            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void actualizarHorario(HorarioPeluquero h) {
        String sql = "UPDATE HORARIOS_PELUQUERO SET DIA_SEMANA=?, HORA_INICIO=?, HORA_FIN=? WHERE ID_HORARIO=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, h.getDiaSemana());
            ps.setString(2, h.getHoraInicio());
            ps.setString(3, h.getHoraFin());
            ps.setInt(4, h.getIdHorario());

            ps.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }
}

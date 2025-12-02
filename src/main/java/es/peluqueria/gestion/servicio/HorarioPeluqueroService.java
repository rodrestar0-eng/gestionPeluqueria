package es.peluqueria.gestion.servicio;

import java.util.List;
import es.peluqueria.gestion.DAO.HorarioPeluqueroDAO;
import es.peluqueria.gestion.modelo.HorarioPeluquero;

public class HorarioPeluqueroService {

    private HorarioPeluqueroDAO dao = new HorarioPeluqueroDAO();

    public List<HorarioPeluquero> obtenerHorariosPorTrabajador(int idTrabajador) {
        return dao.obtenerPorTrabajador(idTrabajador);
    }

    public void guardarHorario(HorarioPeluquero h) {
        dao.insertarHorario(h);
    }

    public void eliminarHorario(int idHorario) {
        dao.eliminarHorario(idHorario);
    }

    public void actualizarHorario(HorarioPeluquero h) {
        dao.actualizarHorario(h);
    }
}

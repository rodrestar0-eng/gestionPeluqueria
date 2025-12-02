package es.peluqueria.gestion.servicio;

import java.util.List;

import es.peluqueria.gestion.DAO.TrabajadorEspecialidadDAO;
import es.peluqueria.gestion.modelo.TrabajadorEspecialidad;

public class TrabajadorEspecialidadService {

    private TrabajadorEspecialidadDAO dao = new TrabajadorEspecialidadDAO();

    public void asignarEspecialidad(int idTrabajador, int idEspecialidad) {
        dao.asignarEspecialidad(idTrabajador, idEspecialidad);
    }

    public List<TrabajadorEspecialidad> obtenerEspecialidadesPorTrabajador(int idTrabajador) {
        return dao.obtenerPorTrabajador(idTrabajador);
    }

    public void eliminarAsignacion(int idTrabajador, int idEspecialidad) {
        dao.eliminarAsignacion(idTrabajador, idEspecialidad);
    }
}

package es.peluqueria.gestion.servicio;

import java.util.ArrayList;
import java.util.List;

import es.peluqueria.gestion.DAO.TrabajadorEspecialidadDAO;
import es.peluqueria.gestion.modelo.Especialidad;
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
    public List<Integer> obtenerTrabajadoresPorEspecialidad(int idEspecialidad) {
        return dao.obtenerTrabajadoresPorEspecialidad(idEspecialidad);
    }


    //  MÉTODO PARA DEVOLVER ESPECIALIDADES COMPLETAS
    public List<Especialidad> obtenerEspecialidadesCompletas(int idTrabajador) {

        // Este es el método correcto
        List<TrabajadorEspecialidad> asignaciones = dao.obtenerPorTrabajador(idTrabajador);

        EspecialidadService espService = new EspecialidadService();

        List<Especialidad> resultado = new ArrayList<>();

        for (TrabajadorEspecialidad te : asignaciones) {
            Especialidad esp = espService.obtenerPorId(te.getIdEspecialidad());
            if (esp != null) {
                resultado.add(esp);
            }
        }

        return resultado;
    }

}

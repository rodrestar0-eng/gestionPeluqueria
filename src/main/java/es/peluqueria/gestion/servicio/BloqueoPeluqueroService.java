package es.peluqueria.gestion.servicio;

import java.time.LocalDate;
import java.util.List;

import es.peluqueria.gestion.DAO.BloqueoPeluqueroDAO;
import es.peluqueria.gestion.modelo.BloqueoPeluquero;

public class BloqueoPeluqueroService {

    private BloqueoPeluqueroDAO bloqueoDAO = new BloqueoPeluqueroDAO();

    public void crearBloqueo(BloqueoPeluquero bloqueo) {
        bloqueoDAO.insertar(bloqueo);
    }

    public List<BloqueoPeluquero> obtenerBloqueosPorTrabajador(int idTrabajador) {
        return bloqueoDAO.listarPorTrabajador(idTrabajador);
    }

    public void eliminarBloqueo(int idBloqueo) {
        bloqueoDAO.eliminar(idBloqueo);
    }

    /**
     * Comprueba si el trabajador está bloqueado para la fecha indicada.
     * Devuelve true si existe al menos un bloqueo cuyo rango [fechaInicio..fechaFin]
     * contiene 'fecha'.
     */
    public boolean estaBloqueado(int idTrabajador, LocalDate fecha) {
        List<BloqueoPeluquero> bloqueos = obtenerBloqueosPorTrabajador(idTrabajador);
        if (bloqueos == null) return false;
        for (BloqueoPeluquero b : bloqueos) {
            if (b.getFechaInicio() == null || b.getFechaFin() == null) continue;
            if ((!fecha.isBefore(b.getFechaInicio())) && (!fecha.isAfter(b.getFechaFin()))) {
                return true;
            }
        }
        return false;
    }
}

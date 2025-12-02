package es.peluqueria.gestion.servicio;

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
}

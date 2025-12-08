package es.peluqueria.gestion.servicio;

import es.peluqueria.gestion.DAO.CitaDAO;
import es.peluqueria.gestion.DAO.EspecialidadServicioDAO;
import es.peluqueria.gestion.DAO.ServicioDAO;
import es.peluqueria.gestion.modelo.Servicio;

import java.util.List;

public class ServicioService {

    private final ServicioDAO servicioDAO;
    private final CitaDAO citaDAO = new CitaDAO();

    public ServicioService() {
        this.servicioDAO = new ServicioDAO();
        
    }

    /**
     * Inserta un nuevo servicio en la base de datos.
     * Valida los datos básicos antes de realizar la inserción.
     */
    public boolean registrarServicio(Servicio servicio, int idEspecialidad) {

        try {
            if (servicio.getNombre() == null || servicio.getNombre().isEmpty()) {
                System.err.println("⚠️ Error: el nombre del servicio no puede estar vacío.");
                return false;
            }
            if (servicio.getPrecio() == null || servicio.getPrecio().doubleValue() <= 0) {
                System.err.println("⚠️ Error: el precio debe ser mayor que 0.");
                return false;
            }

            //  Insertamos servicio y obtenemos ID generado
            int idServicioNuevo = servicioDAO.insertar(servicio);

            if (idServicioNuevo == -1) {
                System.err.println("❌ Error insertando servicio.");
                return false;
            }

            //  Guardamos relación SERVICIO → ESPECIALIDAD
            EspecialidadServicioDAO espServDAO = new EspecialidadServicioDAO();
            return espServDAO.asignarEspecialidad(idServicioNuevo, idEspecialidad);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Actualiza un servicio existente.
     */
    public boolean actualizarServicio(Servicio servicio, int idEspecialidad) {
        try {
            if (servicio.getIdServicio() <= 0) {
                System.err.println("⚠️ Error: ID de servicio no válido.");
                return false;
            }

            boolean ok = servicioDAO.actualizar(servicio);
            if (!ok) return false;

            // Actualizar la especialidad asociada
            EspecialidadServicioDAO espServDAO = new EspecialidadServicioDAO();
            return espServDAO.actualizarEspecialidad(servicio.getIdServicio(), idEspecialidad);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Elimina un servicio por su ID.
     */
    public boolean eliminarServicio(int idServicio) {
        try {
            int citasActivas = citaDAO.contarCitasActivasPorServicio(idServicio);

            if (citasActivas > 0) {
                System.err.println("❌ No se puede eliminar el servicio: tiene citas activas.");
                return false;
            }

            return servicioDAO.eliminar(idServicio);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Obtiene un servicio por su ID.
     */
    public Servicio obtenerPorId(int idServicio) {
        try {
            return servicioDAO.obtenerPorId(idServicio);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String obtenerNombrePorId(int idServicio) {
        Servicio s = servicioDAO.obtenerPorId(idServicio);
        return (s != null) ? s.getNombre() : "Desconocido";
    }

    /**
     * Busca servicios por nombre (parcial, sin importar mayúsculas).
     */
    public List<Servicio> buscarPorNombre(String nombre) {
        try {
            return servicioDAO.buscarPorNombre(nombre);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    /**
     * Devuelve una lista con todos los servicios.
     */
    public List<Servicio> listarTodos() {
        try {
            return servicioDAO.listarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Integer obtenerEspecialidadDeServicio(int idServicio) {
        try {
            EspecialidadServicioDAO dao = new EspecialidadServicioDAO();
            return dao.obtenerEspecialidadDeServicio(idServicio);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

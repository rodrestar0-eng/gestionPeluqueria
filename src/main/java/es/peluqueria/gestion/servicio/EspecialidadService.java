package es.peluqueria.gestion.servicio;

import es.peluqueria.gestion.DAO.EspecialidadDAO;
import es.peluqueria.gestion.DAO.UsuarioDAO;
import es.peluqueria.gestion.DAO.TrabajadorEspecialidadDAO; // Asumimos que tienes este DAO
import es.peluqueria.gestion.modelo.Especialidad;
import es.peluqueria.gestion.modelo.Usuario;

import java.util.List;

public class EspecialidadService {

    private final EspecialidadDAO especialidadDAO;
    private final UsuarioDAO usuarioDAO;
    private final TrabajadorEspecialidadDAO trabajadorEspecialidadDAO;

    public EspecialidadService() {
        this.especialidadDAO = new EspecialidadDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.trabajadorEspecialidadDAO = new TrabajadorEspecialidadDAO();
    }

    /**
     * Crea una nueva especialidad.
     * Devuelve false si ya existe una especialidad con el mismo nombre.
     */
    public boolean registrarEspecialidad(Especialidad esp) {
        try {
            if (esp.getNombre() == null || esp.getNombre().isBlank()) {
                return false;
            }
            Especialidad existente = especialidadDAO.obtenerPorNombre(esp.getNombre());
            if (existente != null) {
                return false; // ya existe
            }
            return especialidadDAO.insertar(esp);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Devuelve una especialidad por ID.
     */
    public Especialidad obtenerPorId(int id) {
        try {
            return especialidadDAO.obtenerPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelve una especialidad por nombre exacto.
     */
    public Especialidad obtenerPorNombre(String nombre) {
        try {
            return especialidadDAO.obtenerPorNombre(nombre);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Lista todas las especialidades ordenadas alfabéticamente.
     */
    public List<Especialidad> listarTodas() {
        try {
            return especialidadDAO.listarTodas();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Actualiza el nombre de una especialidad.
     * Devuelve false si el nuevo nombre ya existe en otra especialidad.
     */
    public boolean actualizarEspecialidad(Especialidad esp) {
        try {
            if (esp.getNombre() == null || esp.getNombre().isBlank()) {
                return false;
            }
            Especialidad existente = especialidadDAO.obtenerPorNombre(esp.getNombre());
            if (existente != null && existente.getIdEspecialidad() != esp.getIdEspecialidad()) {
                return false;
            }
            return especialidadDAO.actualizar(esp);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina una especialidad si no está asignada a un trabajador.
     * (Puedes añadir esta validación si quieres en DAO con FK).
     */
    public boolean eliminarEspecialidad(int idEspecialidad) {
        try {
            return especialidadDAO.eliminar(idEspecialidad);
        } catch (Exception e) {
            return false;
        }
    }

    // ============================
    // NUEVAS FUNCIONES PARA ASIGNAR Y ELIMINAR ESPECIALIDADES A TRABAJADORES
    // ============================

    /**
     * Asignar una especialidad a un trabajador
     */
    public boolean asignarEspecialidadATrabajador(int idTrabajador, int idEspecialidad) {
        try {
            // Verificamos que el trabajador y la especialidad existan
            Usuario trabajador = usuarioDAO.obtenerPorId(idTrabajador);
            Especialidad especialidad = especialidadDAO.obtenerPorId(idEspecialidad);

            if (trabajador != null && especialidad != null) {
                // Llamamos al DAO para hacer la asignación
                return trabajadorEspecialidadDAO.asignarEspecialidad(idTrabajador, idEspecialidad);
            }
            return false; // Si no se encuentran los datos, no se asigna
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Eliminar la especialidad asignada de un trabajador
     */
    public boolean eliminarEspecialidadDeTrabajador(int idTrabajador, int idEspecialidad) {
        try {
            return trabajadorEspecialidadDAO.eliminarAsignacion(idTrabajador, idEspecialidad);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarTodasEspecialidadDeTrabajador(int idTrabajador) {
        try {
            return trabajadorEspecialidadDAO.eliminarEspecialidadesDeTrabajador(idTrabajador);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

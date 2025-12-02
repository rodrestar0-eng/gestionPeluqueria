package es.peluqueria.gestion.servicio;

import es.peluqueria.gestion.DAO.CitaDAO;
import es.peluqueria.gestion.DAO.ClienteDAO;
import es.peluqueria.gestion.DAO.UsuarioDAO;
import es.peluqueria.gestion.DAO.ServicioDAO;

import es.peluqueria.gestion.modelo.Cita;
import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.modelo.Servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CitaService {

    private final CitaDAO citaDAO;
    private final ClienteDAO clienteDAO;
    private final UsuarioDAO usuarioDAO;
    private final ServicioDAO servicioDAO;

    public CitaService() {
        this.citaDAO = new CitaDAO();
        this.clienteDAO = new ClienteDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.servicioDAO = new ServicioDAO();
    }

    /**
     * Registrar una cita con TODA la lógica real.
     */
    public boolean registrarCita(Cita cita) {
        try {
            // ===== VALIDACIONES PREVIAS =====

            Cliente c = clienteDAO.obtenerPorId(cita.getIdCliente());
            if (c == null) return false;

            Usuario peluquero = usuarioDAO.obtenerPorId(cita.getIdPeluquero());
            if (peluquero == null) return false;

            Servicio servicio = servicioDAO.obtenerPorId(cita.getIdServicio());
            if (servicio == null) return false;

            // ===== VALIDAR FECHA =====
            LocalDate hoy = LocalDate.now();
            if (cita.getFechaCita().isBefore(hoy)) return false;

            // ===== PARSEAR HORAS =====
            LocalTime horaInicio = LocalTime.parse(cita.getHoraInicio());
            LocalTime horaFin = horaInicio.plusMinutes(servicio.getDuracionMinutos());

            // Guardamos horaFin ya calculada
            cita.setHoraFin(horaFin.toString());

            // ===== VALIDAR HORARIO DE APERTURA =====
            LocalTime abre = LocalTime.of(9, 0);
            LocalTime cierra = LocalTime.of(20, 0);

            if (horaInicio.isBefore(abre) || horaFin.isAfter(cierra)) return false;

            // ===== VALIDAR QUE NO SEAN HORAS PASADAS MIENTRAS ES HOY =====
            if (cita.getFechaCita().equals(hoy)) {
                if (horaInicio.isBefore(LocalTime.now())) return false;
            }

            // ===== VALIDAR SOLAPAMIENTOS DEL PELUQUERO =====
            List<Cita> citasPeluquero = citaDAO.listarPorPeluquero(cita.getIdPeluquero());
            LocalDateTime inicioNueva = LocalDateTime.of(cita.getFechaCita(), horaInicio);
            LocalDateTime finNueva = LocalDateTime.of(cita.getFechaCita(), horaFin);

            for (Cita existente : citasPeluquero) {
                if (!existente.getFechaCita().equals(cita.getFechaCita())) continue;

                LocalTime hi = LocalTime.parse(existente.getHoraInicio());
                LocalTime hf = LocalTime.parse(existente.getHoraFin());

                LocalDateTime iniEx = LocalDateTime.of(existente.getFechaCita(), hi);
                LocalDateTime finEx = LocalDateTime.of(existente.getFechaCita(), hf);

                boolean solapa =
                        (inicioNueva.isBefore(finEx) && finNueva.isAfter(iniEx));

                if (solapa) return false;
            }

            // ===== SI TODO ES CORRECTO, GUARDAR =====
            return citaDAO.insertar(cita);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cita obtenerPorId(int id) {
        try {
            return citaDAO.obtenerPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Cita> listarPorCliente(int idCliente) {
        try {
            return citaDAO.listarPorCliente(idCliente);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Cita> listarPorPeluquero(int idPeluquero) {
        try {
            return citaDAO.listarPorPeluquero(idPeluquero);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean actualizarCita(Cita cita) {
        try {
            return citaDAO.actualizar(cita);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarCita(int idCita) {
        try {
            return citaDAO.eliminar(idCita);
        } catch (Exception e) {
            return false;
        }
    }
    public List<Cita> listarTodas() {
        try {
            return citaDAO.listarTodas();
        } catch (Exception e) {
            return null;
        }
    }

}

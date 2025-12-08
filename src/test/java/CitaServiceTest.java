

import es.peluqueria.gestion.modelo.Cita;
import es.peluqueria.gestion.servicio.CitaService;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias de CitaService.
 * Capítulo 10 – Memoria del proyecto
 */
class CitaServiceTest {

    private CitaService citaService;

    @BeforeEach
    void setUp() {
        citaService = new CitaService();
    }

   
    /**
     * Test: comprobar que el método detectarSolapamiento() detecta un solapamiento real.
     */
    @Test
    void testValidarSolapamiento() {
        LocalDateTime inicio1 = LocalDateTime.of(2025, 5, 10, 10, 0);
        LocalDateTime fin1    = LocalDateTime.of(2025, 5, 10, 10, 30);

        LocalDateTime inicio2 = LocalDateTime.of(2025, 5, 10, 10, 15);
        LocalDateTime fin2    = LocalDateTime.of(2025, 5, 10, 10, 45);

        boolean solapa = citaService.haySolapamiento(inicio1, fin1, inicio2, fin2);

        assertTrue(solapa, "❌ ERROR: No se detectó un solapamiento evidente.");
    }

    /**
     * Test: validación del cálculo automático de hora fin según duración.
     */
    @Test
    void testCalcularHoraFin() {
        LocalDateTime inicio = LocalDateTime.of(2025, 5, 10, 11, 0);

        LocalDateTime finCalculado = citaService.calcularHoraFin(inicio, 30);

        assertEquals(LocalDateTime.of(2025, 5, 10, 11, 30),
                finCalculado,
                "❌ ERROR: La hora fin no coincide con la duración del servicio.");
    }

    /**
     * Test: creación básica de un objeto Cita con datos correctos.
     */
    @Test
    void testCrearCitaConDatosCorrectos() {
        Cita cita = new Cita();
        cita.setIdCliente(1);
        cita.setIdPeluquero(2);
        cita.setIdServicio(3);

        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        cita.setFechaHoraCita(inicio);

        assertNotNull(cita, "❌ ERROR: El objeto cita es nulo.");
        assertTrue(cita.getIdCliente() > 0, "❌ ERROR: ID cliente no válido.");
        assertTrue(cita.getIdPeluquero() > 0, "❌ ERROR: ID peluquero no válido.");
        assertTrue(cita.getIdServicio() > 0, "❌ ERROR: ID servicio no válido.");
        assertTrue(cita.getFechaHoraCita().isAfter(LocalDateTime.now()),
                "❌ ERROR: La fecha de la cita debe ser futura.");
    }
}

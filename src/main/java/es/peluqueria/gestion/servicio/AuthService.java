package es.peluqueria.gestion.servicio;

import es.peluqueria.gestion.DAO.ClienteDAO;
import es.peluqueria.gestion.DAO.UsuarioDAO;
import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.modelo.Usuario;
import es.peluqueria.gestion.util.HashUtil;

public class AuthService {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Devuelve:
     *   - Cliente si es cliente
     *   - Usuario si es admin/recepcionista/peluquero
     *   - null si credenciales incorrectas
     */
    public Object login(String email, String contrasena) {
        try {
            // -------- 1) BUSCAR CLIENTE --------
            Cliente cliente = clienteDAO.obtenerPorEmail(email);
            if (cliente != null) {
                String hashInput = HashUtil.hashPassword(contrasena);
                if (hashInput.equals(cliente.getContrasena())) {
                    return cliente;
                }
            }

            // -------- 2) BUSCAR USUARIO (empleados) --------
            Usuario usuario = usuarioDAO.obtenerPorEmail(email);
            if (usuario != null) {
                String hashInput = HashUtil.hashPassword(contrasena);
                if (hashInput.equals(usuario.getContrasena())) {
                    return usuario;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // no encontrado
    }
}

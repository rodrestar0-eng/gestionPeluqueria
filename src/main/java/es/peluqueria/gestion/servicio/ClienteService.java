package es.peluqueria.gestion.servicio;

import es.peluqueria.gestion.DAO.ClienteDAO;
import es.peluqueria.gestion.modelo.Cliente;
import es.peluqueria.gestion.util.HashUtil;

import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    // Registro (hashea contraseña)
    public boolean registrarCliente(Cliente cliente) {
        try {
            if (cliente.getEmail() != null && !cliente.getEmail().isBlank()) {
                if (clienteDAO.obtenerPorEmail(cliente.getEmail()) != null) return false;
            }
            if (cliente.getTelefono() != null && !cliente.getTelefono().isBlank()) {
                if (clienteDAO.obtenerPorTelefono(cliente.getTelefono()) != null) return false;
            }
            String hashed = HashUtil.hashPassword(cliente.getContrasena());
            cliente.setContrasena(hashed);
            return clienteDAO.insertar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login (compara hash)
    public Cliente login(String email, String contrasena) {
        try {
            Cliente c = clienteDAO.obtenerPorEmail(email);
            if (c == null) return null;
            String hashedInput = HashUtil.hashPassword(contrasena);
            return hashedInput.equals(c.getContrasena()) ? c : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // CRUD y finders
    public Cliente obtenerPorId(int id) {
        try { return clienteDAO.obtenerPorId(id); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public Cliente obtenerPorEmail(String email) {
        try { return clienteDAO.obtenerPorEmail(email); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public Cliente obtenerPorTelefono(String telefono) {
        try { return clienteDAO.obtenerPorTelefono(telefono); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public List<Cliente> listarTodos() {
        try { return clienteDAO.listarTodos(); } catch (Exception e) { e.printStackTrace(); return null; }
    }

    public boolean actualizarCliente(Cliente cliente) {
        try {
            // Si se actualiza la contraseña, hashearla
            if (cliente.getContrasena() != null && !cliente.getContrasena().isBlank()) {
                cliente.setContrasena(HashUtil.hashPassword(cliente.getContrasena()));
            } else {
                // si no trae contraseña, cargar la actual para mantenerla
                Cliente actual = clienteDAO.obtenerPorId(cliente.getIdCliente());
                if (actual != null) cliente.setContrasena(actual.getContrasena());
            }
            return clienteDAO.actualizar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        try { return clienteDAO.eliminar(id); } catch (Exception e) { e.printStackTrace(); return false; }
    }
}

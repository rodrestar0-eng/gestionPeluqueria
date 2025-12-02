package es.peluqueria.gestion.util;

import java.security.MessageDigest;

public class HashUtil {

    // Método para cifrar la contraseña (hash)
    public static String hashPassword(String password) {
        if (password == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");  // Algoritmo SHA-256
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();  // Devuelve el hash en formato hexadecimal
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

    // Método para comparar una contraseña con un hash
    public static boolean checkPassword(String password, String hashedPassword) {
        // Hasheamos la contraseña ingresada y comparamos con el valor almacenado
        return hashPassword(password).equals(hashedPassword);
    }
}

package com.cursojava.java.dao;

import com.cursojava.java.models.Usuario;
import java.util.List;
public interface UsuarioDao {
    List<Usuario> getUsuarios();
    void eliminarUsuario(Long id);

    void registrarUsuario(Usuario registro);

    Usuario iniciarSesion(Usuario datosLogin);

    Usuario enviarTel(Usuario datoTel);

}

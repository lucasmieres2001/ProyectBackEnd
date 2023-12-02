package com.cursojava.java.dao;

import com.cursojava.java.models.DataMedia;
import com.cursojava.java.models.Usuario;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface UsuarioDao {
    List<Usuario> getUsuarios();
    void eliminarUsuario(Long id);

    void registrarUsuario(Usuario registro);

    Usuario iniciarSesion(Usuario datosLogin);

    Usuario enviarTel(Usuario datoTel);

    Usuario imagenSubida(Usuario email, DataMedia datosArchivo);

    DataMedia getPost(Usuario email,DataMedia dataMedia);

}

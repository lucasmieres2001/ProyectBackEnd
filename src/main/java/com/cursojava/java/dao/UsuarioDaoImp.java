//Aplicamos polimorfismo y creamos la conexion con la base de datos
package com.cursojava.java.dao;
import com.cursojava.java.models.DataMedia;
import com.cursojava.java.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

//@Repository y @Transaccional son dos anotaciones las cuales son importantes para la conexion a la base de datos
//---------------------------------------------------------------------------------------------------------------
@Repository
@Transactional
//---------------------------------------------------------------------------------------------------------------
public class UsuarioDaoImp implements UsuarioDao{
    @PersistenceContext
    //EntityManager gestionará los datos enviados o pedidos a la base de datos.
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
       String peticion = "FROM Usuario";
        return  entityManager.createQuery(peticion).getResultList();

    }

    @Override
    public void eliminarUsuario(Long id){
        Usuario usuarioEliminado = entityManager.find(Usuario.class,id); //Tenemos que buscar el id para eliminarlo
    entityManager.remove(usuarioEliminado);
    }

    @Override
    public void registrarUsuario(Usuario registro){
    entityManager.merge(registro);
    }

    @Override
    public Usuario iniciarSesion(Usuario datosLogin) {
        String buscarUsuario = "FROM Usuario WHERE email = :email"; // Hibernate localiza nuestra entidad;
        List<Usuario> lista = entityManager.createQuery(buscarUsuario) // entityManager crea una lista con la info solicitada;
                .setParameter("email", datosLogin.getEmail()) // Decimos que es lo que queremos que nos imprima;
                .getResultList(); // Pedimos que nos pase el resultado de la lista;
        if(lista.isEmpty()){
            return null;
        }
        String passwordHashed = lista.get(0).getPassword(); // solicitamos que nos de el valor password de la lista;
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); //creamos el objeto Argon2 para usar hash;
        if (argon2.verify(passwordHashed,datosLogin.getPassword())){
            return lista.get(0);
        }else{
            return null;
        }

    }

    @Override
    @Transactional

    public Usuario enviarTel(Usuario datoTel) {
        String buscarID = "FROM Usuario WHERE email = :email";
        List<Usuario> listaID = entityManager.createQuery(buscarID)
                .setParameter("email",datoTel.getEmail()).getResultList();
        if(listaID.isEmpty()){
            return null;
        }
        long id = listaID.get(0).getId();

        Usuario busqueda = entityManager.find(Usuario.class,id);
        if(busqueda != null){
            busqueda.setTel(datoTel.getTel());
            return busqueda;
        }else{
            return null;
        }
    }

    @Override
    public Usuario imagenSubida(Usuario email, DataMedia datosArchivo) {
        String buscar = "FROM Usuario WHERE email = :email";
        List<Usuario> listado = entityManager.createQuery(buscar)
                .setParameter("email",email.getEmail()).getResultList();
        if(listado.isEmpty()){return null;};

        Long id = listado.get(0).getId();

        Usuario clase = entityManager.find(Usuario.class,id);
        if(clase != null){
            datosArchivo.setId_usuario(clase.getId());
            entityManager.merge(datosArchivo);
            return clase;
        }else {
            return null;
        }

    }

    @Override
    public DataMedia getPost(Usuario email,DataMedia dataMedia) {
        String buscar = "FROM Usuario WHERE email = :email";
        List<Usuario> listado = entityManager.createQuery(buscar)
                .setParameter("email",email.getEmail()).getResultList();
        if(listado.isEmpty()){return null;};
        Long id = listado.get(0).getId();
        Usuario clase = entityManager.find(Usuario.class,id);
        dataMedia.setId_usuario(clase.getId());
        String buscarInfo = "FROM DataMedia WHERE id_usuario = :id_usuario";
        List<DataMedia> listadoArchivo = entityManager.createQuery(buscarInfo)
                .setParameter("id_usuario",dataMedia.getId_usuario()).getResultList();
        if(listadoArchivo.isEmpty()){return null;}
        Long Id_Archivo = listadoArchivo.get(0).getId_archivo();
        return entityManager.find(DataMedia.class,Id_Archivo);

    }


}

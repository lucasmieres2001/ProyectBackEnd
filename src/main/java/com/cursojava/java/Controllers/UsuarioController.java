package com.cursojava.java.Controllers;

import com.cursojava.java.dao.UsuarioDao;
import com.cursojava.java.models.Usuario;
import com.cursojava.java.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UsuarioController {
    //----------------------------------------OBJETOS-------------------------------------------------------
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }
    //-----------------------------------------------------------------------------------------------------

    //---------------------------------------CONTROLADORES-------------------------------------------------
    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario (@PathVariable Long id){
        Usuario usuario = new Usuario();
        return usuario;
    }

    @RequestMapping(value = "api/usuariodos", method = RequestMethod.GET)
    public List<Usuario> getUsuarios (@RequestHeader(value = "Authorization")String token){
    if(!validarToken(token)){return null;}
        return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.DELETE)
    public void eliminar (@RequestHeader(value = "Authorization")String token,@PathVariable Long id){
        if(!validarToken(token)){return;}
        usuarioDao.eliminarUsuario(id);
    }

    @RequestMapping(value = "api/usuariodos", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario registro){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashPass = argon2.hash(1,1024,1,registro.getPassword());
        registro.setPassword(hashPass);
        usuarioDao.registrarUsuario(registro);
    }

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String iniciarSesion(@RequestBody Usuario datosLogin){
        Usuario usuarioLogueado = usuarioDao.iniciarSesion(datosLogin);
        if(usuarioLogueado != null) {
            String token = jwtUtil.create(String.valueOf(usuarioLogueado.getId()),
                    usuarioLogueado.getEmail()); //Acá estoy pidiendo el valor id y email para generar el token de inicio de sesión.
            return token;
        }else{
        return "error";
        }

    }

    @RequestMapping(value = "api/tel",method = RequestMethod.POST)
    public boolean enviarTel(@RequestHeader(value = "Authorization") String token,@RequestBody Usuario datoTel){
        if(!validarToken(token)){return false;}
        return usuarioDao.enviarTel(datoTel) != null;
    }
}
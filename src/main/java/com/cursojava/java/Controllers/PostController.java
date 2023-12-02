package com.cursojava.java.Controllers;

import com.cursojava.java.dao.UsuarioDao;
import com.cursojava.java.models.DataMedia;
import com.cursojava.java.models.Usuario;
import com.cursojava.java.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class PostController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    //-------------------------------------------------------------------------------------------------

    //------------------------------------------Controladores-----------------------------------------
    @RequestMapping(value = "api/getPost", method = RequestMethod.POST)
    public byte[] postImage(@RequestHeader("Authorization") String token, @RequestPart("email")Usuario email){
        if(!validarToken(token)){return null;}
        DataMedia dataMedia = new DataMedia();
        usuarioDao.getPost(email,dataMedia);
       if(usuarioDao.getPost(email,dataMedia) == null){return null;}
        String RutaImg = usuarioDao.getPost(email,dataMedia).getNombreArchivo();
        Path path = Paths.get(RutaImg);

        try {
            String typeReal = usuarioDao.getPost(email,dataMedia).getType();
            byte[] imagenByte = Files.readAllBytes(path); //Busca el archivo el cual está guardado en la ruta que le especificamos y lo transforma en bytes
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.valueOf(typeReal));//Aplico adaptaciones para mi archivo en concreto;
            httpHeaders.setContentLength(imagenByte.length);
            return imagenByte; // retorno la imagen
        }catch (IOException e){e.printStackTrace();}
        return null; // retorno null

    }


    @Value("${ruta.almacenamiento.imagenes}")
    private String rutaImg;
    @RequestMapping(value = "/api/img", method = RequestMethod.POST)
    public Usuario subirImagen(@RequestPart("img") MultipartFile imagen, @RequestPart("typeImg") String typeImg, @RequestPart("email") Usuario email,@RequestPart("typeReal") String typeReal,@RequestHeader("Authorization") String token){
        if(!validarToken(token)){return null;}
        DataMedia datosArchivo = new DataMedia();
        try {
            String nombreImg = "IMG_" + System.currentTimeMillis() +"." + typeImg;
            String RutaCompleta = rutaImg;
            Path UbicacionImg = Paths.get(RutaCompleta + "/" + nombreImg);
            String GuardarRutaBD = RutaCompleta + "/"+ nombreImg;
            Files.copy(imagen.getInputStream(), UbicacionImg, StandardCopyOption.REPLACE_EXISTING);
            datosArchivo.setNombreArchivo(GuardarRutaBD);
            datosArchivo.setType(typeReal);
            usuarioDao.imagenSubida(email,datosArchivo);
        } catch (IOException e) {

        }
        Usuario respuesta = usuarioDao.imagenSubida(email,datosArchivo);
        if(usuarioDao.imagenSubida(email,datosArchivo) != null){return respuesta;}
        return null;
    }
}

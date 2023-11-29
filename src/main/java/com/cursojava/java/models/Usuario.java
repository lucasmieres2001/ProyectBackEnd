package com.cursojava.java.models;
//Las clases del paquete 'Modelo' no son más que referencias de los valores que posea


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bd_usuarios")
public class Usuario {
    //Acá se encuentra el modelo/esquema el cual se deberá enviár la información del usuario.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "ID_usuario")
    private Long id;
    @Getter @Setter @Column(name = "Nombre")
    private String nombre;
    @Getter @Setter @Column(name = "Apellido")
    private String apellido;
    @Getter @Setter @Column(name = "Email")
    private String email;
    @Getter @Setter @Column(name = "Telefono")
    private String tel;
    @Getter @Setter @Column(name = "Contrasenia")
    private String password;
}

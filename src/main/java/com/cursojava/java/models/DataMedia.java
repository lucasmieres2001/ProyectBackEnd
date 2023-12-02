package com.cursojava.java.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bd_archivo")
public class DataMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "ID_Archivo")
    private Long id_archivo;
    @Getter @Setter @Column(name = "ID_Usuario")
    private Long id_usuario;
    @Getter @Setter @Column(name = "Tipo_De_Archivo")
    private String type;
    @Getter @Setter @Column(name = "Nombre_De_Archivo")
    private String nombreArchivo;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author kikej
 */



@Data
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Integer idAlumno;

    @NotNull(message = "El usuario no puede ser nulo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Pattern(regexp = "^[0-9\\-+() ]{7,20}$", message = "Formato de teléfono inválido")
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "estado", length = 20)
    private String estado;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
    private List<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
    private List<Atencion> atenciones;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
    private List<Asistencia> asistencias;
    
    
}
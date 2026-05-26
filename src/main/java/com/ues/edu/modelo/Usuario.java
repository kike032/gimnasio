/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotNull(message = "El rol no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Formato de correo inválido")
    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;

    @NotBlank(message = "La clave no puede estar vacía")
    @Column(name = "clave", nullable = false, length = 255)
    private String clave;

    @Column(name = "estado", nullable = false)
    private boolean estado = true;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Alumno alumno;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Atencion> atenciones;
}
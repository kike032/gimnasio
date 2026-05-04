/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.ues.edu.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 *
 * @author kikej
 */




/**
 * Test con EntityManager para crear la base de datos del Gimnasio
 * e insertar datos de prueba en cada tabla.
 *
 * REQUISITOS:
 *   - persistence.xml en src/main/resources/META-INF/
 *   - Base de datos MySQL corriendo en localhost:3306
 *   - hibernate.hbm2ddl.auto = create  (genera las tablas automáticamente)
 */
public class test {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            // 1. Inicializar EntityManagerFactory con la PU del persistence.xml
            emf = Persistence.createEntityManagerFactory("proyecto");
            em  = emf.createEntityManager();

            System.out.println("\nConexión establecida. Tablas creadas por Hibernate.\n");
        } catch (Exception e) {
            System.err.println("\nERROR: " + e.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.err.println("↩ Transacción revertida (rollback).");
            }
            e.printStackTrace();

        } finally {
            if (em  != null) em.close();
            if (emf != null) emf.close();
            System.out.println("\nRecursos liberados.");
        }
    }
}

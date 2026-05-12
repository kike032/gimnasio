package com.ues.edu.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class UsuarioDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Object[]> listar() {
        EntityManager em = emf.createEntityManager();

        List<Object[]> lista = em.createQuery(
                "SELECT u.idUsuario, "
                + "u.rol.nombreRol, "
                + "u.nombre, "
                + "u.correo, "
                + "u.estado "
                + "FROM Usuario u",
                Object[].class)
                .getResultList();

        em.close();

        return lista;
    }
}
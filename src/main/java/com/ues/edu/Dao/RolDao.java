/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

import com.ues.edu.modelo.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author DELL LATITUDE
 */
public class RolDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");


    public void guardar(Rol rol) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rol);
        em.getTransaction().commit();
        em.close();
    }

    public void actualizar(Rol rol) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(rol);
        em.getTransaction().commit();
        em.close();
    }

  
    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Rol p = em.find(Rol.class, id);
        if (p != null) {
            em.remove(p);
        }

        em.getTransaction().commit();
        em.close();
    }

 
    public List<Rol> listarPaginado(int pagina, int size, String busqueda) {

        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT p FROM Rol p WHERE (:busqueda IS NULL OR p.nombre LIKE :busqueda)";

        TypedQuery<Rol> query = em.createQuery(jpql, Rol.class);

        query.setParameter("busqueda", busqueda == null || busqueda.isEmpty()
                ? null : "%" + busqueda + "%");

        query.setFirstResult((pagina - 1) * size);
        query.setMaxResults(size);

        List<Rol> lista = query.getResultList();

        em.close();
        return lista;
    }
    
    public Rol buscarPorId(int id) {
    EntityManager em = emf.createEntityManager();
    Rol p = em.find(Rol.class, id);
    em.close();
    return p;
}
}

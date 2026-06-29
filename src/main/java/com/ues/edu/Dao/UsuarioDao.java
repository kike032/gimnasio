package com.ues.edu.Dao;

import com.ues.edu.modelo.Rol;
import com.ues.edu.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class UsuarioDao {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("proyecto");

    public List<Usuario> listar() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u ORDER BY u.idUsuario DESC",
                    Usuario.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public void guardar(Usuario usuario, Integer idRol) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Rol rol = em.find(Rol.class, idRol);

            if (rol == null) {
                throw new RuntimeException("El rol seleccionado no existe");
            }

            usuario.setRol(rol);
            em.persist(usuario);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public void actualizar(Usuario usuario, Integer idRol) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Usuario usuarioExistente = em.find(Usuario.class, usuario.getIdUsuario());

            if (usuarioExistente != null) {
                Rol rol = em.find(Rol.class, idRol);

                if (rol == null) {
                    throw new RuntimeException("El rol seleccionado no existe");
                }

                usuarioExistente.setRol(rol);
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setCorreo(usuario.getCorreo());
                usuarioExistente.setClave(usuario.getClave());
                usuarioExistente.setEstado(usuario.isEstado());

                em.merge(usuarioExistente);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public void eliminar(int idUsuario) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, idUsuario);

            if (usuario != null) {
                usuario.setEstado(false);
                em.merge(usuario);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }
}
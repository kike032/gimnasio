/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.ues.edu.test;

import com.ues.edu.modelo.Alumno;
import com.ues.edu.modelo.Asistencia;
import com.ues.edu.modelo.Atencion;
import com.ues.edu.modelo.Comprobante;
import com.ues.edu.modelo.Inscripcion;
import com.ues.edu.modelo.Pago;
import com.ues.edu.modelo.Rol;
import com.ues.edu.modelo.Servicio;
import com.ues.edu.modelo.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


public class test {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // ── ROLES ────────────────────────────────────────────────────────
            Rol r1 = new Rol();
            r1.setNombreRol("Administrador");
            r1.setDescripcion("Acceso total al sistema");
            r1.setEstado(true);
            em.persist(r1);

            Rol r2 = new Rol();
            r2.setNombreRol("Recepcionista");
            r2.setDescripcion("Gestión de alumnos e inscripciones");
            r2.setEstado(true);
            em.persist(r2);

            Rol r3 = new Rol();
            r3.setNombreRol("Entrenador");
            r3.setDescripcion("Registro de asistencias y atenciones");
            r3.setEstado(true);
            em.persist(r3);

            Rol r4 = new Rol();
            r4.setNombreRol("Cajero");
            r4.setDescripcion("Gestión de pagos y comprobantes");
            r4.setEstado(true);
            em.persist(r4);

            Rol r5 = new Rol();
            r5.setNombreRol("Supervisor");
            r5.setDescripcion("Revisión de reportes y estadísticas");
            r5.setEstado(true);
            em.persist(r5);

            Rol r6 = new Rol();
            r6.setNombreRol("Visitante");
            r6.setDescripcion("Acceso de solo lectura");
            r6.setEstado(true);
            em.persist(r6);

            // ── SERVICIOS ────────────────────────────────────────────────────
            Servicio s1 = new Servicio();
            s1.setNombreServicio("Membresía Mensual");
            s1.setDescripcion("Acceso ilimitado al gimnasio por un mes");
            s1.setEstado(true);          // true = ACTIVO
            s1.setPrecio(new BigDecimal("25.00"));
            em.persist(s1);

            Servicio s2 = new Servicio();
            s2.setNombreServicio("Membresía Trimestral");
            s2.setDescripcion("Acceso ilimitado por tres meses");
            s2.setEstado(true);
            s2.setPrecio(new BigDecimal("65.00"));
            em.persist(s2);

            Servicio s3 = new Servicio();
            s3.setNombreServicio("Clase de Spinning");
            s3.setDescripcion("Sesión grupal de ciclismo indoor 60 min");
            s3.setEstado(true);
            s3.setPrecio(new BigDecimal("8.00"));
            em.persist(s3);

            Servicio s4 = new Servicio();
            s4.setNombreServicio("Entrenamiento Personal");
            s4.setDescripcion("Sesión individualizada con entrenador");
            s4.setEstado(true);
            s4.setPrecio(new BigDecimal("20.00"));
            em.persist(s4);

            Servicio s5 = new Servicio();
            s5.setNombreServicio("Clase de Yoga");
            s5.setDescripcion("Sesión de yoga y relajación 45 min");
            s5.setEstado(true);
            s5.setPrecio(new BigDecimal("7.00"));
            em.persist(s5);

            Servicio s6 = new Servicio();
            s6.setNombreServicio("Sauna y Spa");
            s6.setDescripcion("Acceso a sauna, jacuzzi y área de relajación");
            s6.setEstado(true);
            s6.setPrecio(new BigDecimal("12.00"));
            em.persist(s6);
            

            // ── USUARIOS ─────────────────────────────────────────────────────
            Usuario u1 = new Usuario();
            u1.setNombre("Carlos Monterrosa");
            u1.setCorreo("admin@gimnasio.com");
            u1.setClave("clave123");
            u1.setEstado(true);          // true = ACTIVO
            u1.setRol(r1);
            em.persist(u1);

            Usuario u2 = new Usuario();
            u2.setNombre("María López");
            u2.setCorreo("recep@gimnasio.com");
            u2.setClave("clave123");
            u2.setEstado(true);
            u2.setRol(r2);
            em.persist(u2);

            Usuario u3 = new Usuario();
            u3.setNombre("José Hernández");
            u3.setCorreo("entrenador@gimnasio.com");
            u3.setClave("clave123");
            u3.setEstado(true);
            u3.setRol(r3);
            em.persist(u3);

            Usuario u4 = new Usuario();
            u4.setNombre("Ana Martínez");
            u4.setCorreo("cajero@gimnasio.com");
            u4.setClave("clave123");
            u4.setEstado(true);
            u4.setRol(r4);
            em.persist(u4);

            Usuario u5 = new Usuario();
            u5.setNombre("Roberto Flores");
            u5.setCorreo("supervisor@gimnasio.com");
            u5.setClave("clave123");
            u5.setEstado(true);
            u5.setRol(r5);
            em.persist(u5);

            Usuario u6 = new Usuario();
            u6.setNombre("Laura Rodríguez");
            u6.setCorreo("visita@gimnasio.com");
            u6.setClave("clave123");
            u6.setEstado(false);         // false = INACTIVO
            u6.setRol(r6);
            em.persist(u6);

            // ── ALUMNOS ──────────────────────────────────────────────────────
            Alumno a1 = new Alumno();
            a1.setNombre("Diego");
            a1.setApellido("Pérez");
            a1.setTelefono("7001-1234");
            a1.setDireccion("Col. Escalón, San Salvador");
            a1.setEstado(true);
            a1.setFechaRegistro(LocalDate.of(2024, 1, 10));
            a1.setUsuario(u1);
            em.persist(a1);

            Alumno a2 = new Alumno();
            a2.setNombre("Sofía");
            a2.setApellido("García");
            a2.setTelefono("7002-2345");
            a2.setDireccion("Res. Santa Elena, Antiguo Cuscatlán");
            a2.setEstado(true);
            a2.setFechaRegistro(LocalDate.of(2024, 2, 15));
            a2.setUsuario(u2);
            em.persist(a2);

            Alumno a3 = new Alumno();
            a3.setNombre("Luis");
            a3.setApellido("Ramírez");
            a3.setTelefono("7003-3456");
            a3.setDireccion("Col. Miramonte, San Salvador");
            a3.setEstado(true);
            a3.setFechaRegistro(LocalDate.of(2024, 3, 20));
            a3.setUsuario(u3);
            em.persist(a3);

            Alumno a4 = new Alumno();
            a4.setNombre("Valeria");
            a4.setApellido("Morales");
            a4.setTelefono("7004-4567");
            a4.setDireccion("Ciudad Merliot, La Libertad");
            a4.setEstado(true);
            a4.setFechaRegistro(LocalDate.of(2024, 4, 5));
            a4.setUsuario(u4);
            em.persist(a4);

            Alumno a5 = new Alumno();
            a5.setNombre("Andrés");
            a5.setApellido("Torres");
            a5.setTelefono("7005-5678");
            a5.setDireccion("Col. San Benito, San Salvador");
            a5.setEstado(false);         // false = INACTIVO
            a5.setFechaRegistro(LocalDate.of(2024, 5, 1));
            a5.setUsuario(u5);
            em.persist(a5);

            Alumno a6 = new Alumno();
            a6.setNombre("Camila");
            a6.setApellido("Vásquez");
            a6.setTelefono("7006-6789");
            a6.setDireccion("Soyapango, San Salvador");
            a6.setEstado(true);
            a6.setFechaRegistro(LocalDate.of(2024, 6, 18));
            a6.setUsuario(u6);
            em.persist(a6);

            // ── INSCRIPCIONES ────────────────────────────────────────────────
            Inscripcion i1 = new Inscripcion();
            i1.setEstado(true);
            i1.setFechaInscripcion(LocalDate.of(2024, 1, 11));
            i1.setAlumno(a1);
            i1.setServicio(s1);
            em.persist(i1);

            Inscripcion i2 = new Inscripcion();
            i2.setEstado(true);
            i2.setFechaInscripcion(LocalDate.of(2024, 2, 16));
            i2.setAlumno(a2);
            i2.setServicio(s2);
            em.persist(i2);

            Inscripcion i3 = new Inscripcion();
            i3.setEstado(true);
            i3.setFechaInscripcion(LocalDate.of(2024, 3, 21));
            i3.setAlumno(a3);
            i3.setServicio(s3);
            em.persist(i3);

            Inscripcion i4 = new Inscripcion();
            i4.setEstado(true);
            i4.setFechaInscripcion(LocalDate.of(2024, 4, 6));
            i4.setAlumno(a4);
            i4.setServicio(s1);
            em.persist(i4);

            Inscripcion i5 = new Inscripcion();
            i5.setEstado(false);         // false = INACTIVA
            i5.setFechaInscripcion(LocalDate.of(2024, 5, 2));
            i5.setAlumno(a5);
            i5.setServicio(s4);
            em.persist(i5);

            Inscripcion i6 = new Inscripcion();
            i6.setEstado(true);
            i6.setFechaInscripcion(LocalDate.of(2024, 6, 19));
            i6.setAlumno(a6);
            i6.setServicio(s2);
            em.persist(i6);

            // ── PAGOS ────────────────────────────────────────────────────────
            Pago pg1 = new Pago();
            pg1.setEstadoPago(true);     // true = PAGADO
            pg1.setFechaPago(LocalDate.of(2024, 1, 11));
            pg1.setMetodoPago("Efectivo");
            pg1.setMonto(new BigDecimal("25.00"));
            pg1.setInscripcion(i1);
            em.persist(pg1);

            Pago pg2 = new Pago();
            pg2.setEstadoPago(true);
            pg2.setFechaPago(LocalDate.of(2024, 2, 16));
            pg2.setMetodoPago("Tarjeta");
            pg2.setMonto(new BigDecimal("65.00"));
            pg2.setInscripcion(i2);
            em.persist(pg2);

            Pago pg3 = new Pago();
            pg3.setEstadoPago(true);
            pg3.setFechaPago(LocalDate.of(2024, 3, 21));
            pg3.setMetodoPago("Transferencia");
            pg3.setMonto(new BigDecimal("8.00"));
            pg3.setInscripcion(i3);
            em.persist(pg3);

            Pago pg4 = new Pago();
            pg4.setEstadoPago(true);
            pg4.setFechaPago(LocalDate.of(2024, 4, 6));
            pg4.setMetodoPago("Efectivo");
            pg4.setMonto(new BigDecimal("25.00"));
            pg4.setInscripcion(i4);
            em.persist(pg4);

            Pago pg5 = new Pago();
            pg5.setEstadoPago(false);    // false = PENDIENTE
            pg5.setFechaPago(LocalDate.of(2024, 5, 2));
            pg5.setMetodoPago("Tarjeta");
            pg5.setMonto(new BigDecimal("20.00"));
            pg5.setInscripcion(i5);
            em.persist(pg5);

            Pago pg6 = new Pago();
            pg6.setEstadoPago(true);
            pg6.setFechaPago(LocalDate.of(2024, 6, 19));
            pg6.setMetodoPago("Efectivo");
            pg6.setMonto(new BigDecimal("7.00"));
            pg6.setInscripcion(i6);
            em.persist(pg6);

            // ── COMPROBANTES ─────────────────────────────────────────────────
            Comprobante c1 = new Comprobante();
            c1.setNumeroComprobante("COMP-2024-001");
            c1.setFechaEmision(LocalDate.of(2024, 1, 11));
            c1.setTotal(new BigDecimal("25.00"));
            c1.setPago(pg1);
            em.persist(c1);

            Comprobante c2 = new Comprobante();
            c2.setNumeroComprobante("COMP-2024-002");
            c2.setFechaEmision(LocalDate.of(2024, 2, 16));
            c2.setTotal(new BigDecimal("65.00"));
            c2.setPago(pg2);
            em.persist(c2);

            Comprobante c3 = new Comprobante();
            c3.setNumeroComprobante("COMP-2024-003");
            c3.setFechaEmision(LocalDate.of(2024, 3, 21));
            c3.setTotal(new BigDecimal("8.00"));
            c3.setPago(pg3);
            em.persist(c3);

            Comprobante c4 = new Comprobante();
            c4.setNumeroComprobante("COMP-2024-004");
            c4.setFechaEmision(LocalDate.of(2024, 4, 6));
            c4.setTotal(new BigDecimal("25.00"));
            c4.setPago(pg4);
            em.persist(c4);

            Comprobante c5 = new Comprobante();
            c5.setNumeroComprobante("COMP-2024-005");
            c5.setFechaEmision(LocalDate.of(2024, 5, 2));
            c5.setTotal(new BigDecimal("20.00"));
            c5.setPago(pg5);
            em.persist(c5);

            Comprobante c6 = new Comprobante();
            c6.setNumeroComprobante("COMP-2024-006");
            c6.setFechaEmision(LocalDate.of(2024, 6, 19));
            c6.setTotal(new BigDecimal("7.00"));
            c6.setPago(pg6);
            em.persist(c6);

            // ── ASISTENCIAS ──────────────────────────────────────────────────
            Asistencia as1 = new Asistencia();
            as1.setFechaAsistencia(LocalDate.of(2024, 7, 1));
            as1.setHoraEntrada(LocalTime.of(6, 30));
            as1.setHoraSalida(LocalTime.of(8, 0));
            as1.setAlumno(a1);
            as1.setPago(pg1);
            as1.setServicio(s1);
            em.persist(as1);

            Asistencia as2 = new Asistencia();
            as2.setFechaAsistencia(LocalDate.of(2024, 7, 2));
            as2.setHoraEntrada(LocalTime.of(7, 0));
            as2.setHoraSalida(LocalTime.of(9, 0));
            as2.setAlumno(a2);
            as2.setPago(pg2);
            as2.setServicio(s2);
            em.persist(as2);

            Asistencia as3 = new Asistencia();
            as3.setFechaAsistencia(LocalDate.of(2024, 7, 3));
            as3.setHoraEntrada(LocalTime.of(8, 0));
            as3.setHoraSalida(LocalTime.of(9, 30));
            as3.setAlumno(a3);
            as3.setPago(pg3);
            as3.setServicio(s3);
            em.persist(as3);

            Asistencia as4 = new Asistencia();
            as4.setFechaAsistencia(LocalDate.of(2024, 7, 4));
            as4.setHoraEntrada(LocalTime.of(6, 0));
            as4.setHoraSalida(LocalTime.of(7, 30));
            as4.setAlumno(a4);
            as4.setPago(pg4);
            as4.setServicio(s1);
            em.persist(as4);

            Asistencia as5 = new Asistencia();
            as5.setFechaAsistencia(LocalDate.of(2024, 7, 5));
            as5.setHoraEntrada(LocalTime.of(9, 0));
            as5.setHoraSalida(LocalTime.of(10, 30));
            as5.setAlumno(a5);
            as5.setPago(pg5);
            as5.setServicio(s4);
            em.persist(as5);

            Asistencia as6 = new Asistencia();
            as6.setFechaAsistencia(LocalDate.of(2024, 7, 6));
            as6.setHoraEntrada(LocalTime.of(7, 30));
            as6.setHoraSalida(LocalTime.of(9, 0));
            as6.setAlumno(a6);
            as6.setPago(pg6);
            as6.setServicio(s2);
            em.persist(as6);

            // ── ATENCIONES ───────────────────────────────────────────────────
            Atencion at1 = new Atencion();
            at1.setDescripcion("Evaluación física inicial");
            at1.setFechaAtencion(LocalDate.of(2024, 7, 1));
            at1.setTipoAtencion("Evaluacion");
            at1.setAlumno(a1);
            at1.setInscripcion(i1);
            at1.setUsuario(u3);
            em.persist(at1);

            Atencion at2 = new Atencion();
            at2.setDescripcion("Ajuste de rutina de ejercicios");
            at2.setFechaAtencion(LocalDate.of(2024, 7, 2));
            at2.setTipoAtencion("Seguimiento");
            at2.setAlumno(a2);
            at2.setInscripcion(i2);
            at2.setUsuario(u3);
            em.persist(at2);

            Atencion at3 = new Atencion();
            at3.setDescripcion("Control de peso y medidas");
            at3.setFechaAtencion(LocalDate.of(2024, 7, 3));
            at3.setTipoAtencion("Medicion");
            at3.setAlumno(a3);
            at3.setInscripcion(i3);
            at3.setUsuario(u3);
            em.persist(at3);

            Atencion at4 = new Atencion();
            at4.setDescripcion("Consulta sobre nutrición");
            at4.setFechaAtencion(LocalDate.of(2024, 7, 4));
            at4.setTipoAtencion("Nutricion");
            at4.setAlumno(a4);
            at4.setInscripcion(i4);
            at4.setUsuario(u1);
            em.persist(at4);

            Atencion at5 = new Atencion();
            at5.setDescripcion("Revisión de progreso mensual");
            at5.setFechaAtencion(LocalDate.of(2024, 7, 5));
            at5.setTipoAtencion("Seguimiento");
            at5.setAlumno(a5);
            at5.setInscripcion(i5);
            at5.setUsuario(u2);
            em.persist(at5);

            Atencion at6 = new Atencion();
            at6.setDescripcion("Atención por lesión leve");
            at6.setFechaAtencion(LocalDate.of(2024, 7, 6));
            at6.setTipoAtencion("Medica");
            at6.setAlumno(a6);
            at6.setInscripcion(i6);
            at6.setUsuario(u3);
            em.persist(at6);

            em.getTransaction().commit();
            System.out.println("\n✅ Todos los datos insertados correctamente.\n");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.err.println("↩ Transacción revertida.");
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen())   em.close();
            if (emf != null && emf.isOpen()) emf.close();
            System.out.println("Recursos liberados.");
        }
    }
}
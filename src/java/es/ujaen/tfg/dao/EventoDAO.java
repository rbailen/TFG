package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.EventoErrorActualizar;
import es.ujaen.tfg.excepciones.EventoErrorEliminar;
import es.ujaen.tfg.excepciones.EventoErrorInsertar;
import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Evento;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Component
public class EventoDAO {
    
    @PersistenceContext
    private EntityManager em;

    public EventoDAO() {

    }

    @Cacheable(value = "eventos", key = "#idEvento")
    public Evento buscar(int idEvento) {
        return em.find(Evento.class, idEvento);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EventoErrorInsertar.class)
    public void insertar(Evento evento) throws EventoErrorInsertar {
        try {
            em.persist(evento);
        } catch (Exception e) {
            throw new EventoErrorInsertar();
        }
    }

    @CacheEvict(value = "eventos", key = "#evento.getIdEvento()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EventoErrorActualizar.class)
    public void actualizar(Evento evento) throws EventoErrorActualizar {
        try {
            em.merge(evento);
        } catch (Exception e) {
            throw new EventoErrorActualizar();
        }
    }

    /**
     *
     * @param evento
     * @throws EventoErrorEliminar
     */
    @CacheEvict(value = "eventos", key = "#evento.getIdEvento()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EventoErrorEliminar.class)
    public void eliminar(Evento evento) throws EventoErrorEliminar {
        try {
            em.remove(em.contains(evento) ? evento : em.merge(evento));
        } catch (Exception e) {
            throw new EventoErrorEliminar();
        }
    }
    
    //Devuelve todos los eventos del sistema
    public List<Evento> listadoEventos() { //Devuelve los eventos del sistema
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e").getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    //Dado un deporte, eliminamos los eventos que lo contengan
    /**
     *
     * @param deporte
     */
    @CacheEvict(value = "eventos", key = "#evento.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EventoErrorEliminar.class)
    public void eliminarPorDeporte(Deporte deporte) throws EventoErrorEliminar {
        List<Evento> eventos = em.createQuery("Select e from Evento e WHERE e.deporte = ?1")
                .setParameter(1, deporte)
                .getResultList();

        for (Evento evento : eventos) {
            try {
                em.remove(em.contains(evento) ? evento : em.merge(evento));
            } catch (Exception e) {
                throw new EventoErrorEliminar();
            }
        }
    }
    
    //Dado una competicion, eliminamos los eventos que lo contengan
    /**
     *
     * @param competicion
     */
    @CacheEvict(value = "eventos", key = "#evento.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EventoErrorEliminar.class)
    public void eliminarPorCompeticion(Competicion competicion) throws EventoErrorEliminar {
        List<Evento> eventos = em.createQuery("Select e from Evento e WHERE e.competicion = ?1")
                .setParameter(1, competicion)
                .getResultList();

        for (Evento evento : eventos) {
            try {
                em.remove(em.contains(evento) ? evento : em.merge(evento));
            } catch (Exception e) {
                throw new EventoErrorEliminar();
            }
        }
    }
    
    /* Dado un participante, devuelve los eventos en los que participa */
    public List<Evento> listadoEventosParticipante(int id) {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e JOIN e.participantes p WHERE p.idParticipante = ?1")
                .setParameter(1, id)
                .getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    /* Dado un participante, devuelve los eventos en los que participa cuyas entradas se encuentran a la venta */
    public List<Evento> eventosParticipante(int id) {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e JOIN e.participantes p WHERE p.idParticipante = ?1 AND e.vendida=true")
                .setParameter(1, id)
                .getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    /* Dado el id de una competición, devuelve la lista de eventos de dicha competición*/
    public List<Evento> listadoEventosCompeticion(int id) {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e WHERE e.competicion.idCompeticion = ?1")
                .setParameter(1, id)
                .getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    /* Dado el id de una competición, devuelve la lista de eventos de dicha 
    competición cuyas entradas se encuentran a a venta */
    public List<Evento> eventosCompeticion(int id) {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e WHERE e.competicion.idCompeticion = ?1 AND e.vendida=true")
                .setParameter(1, id)
                .getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    /* Dado el id de un deporte, devuelve la lista de eventos de dicho deporte cuyas entradas se encuentran a la venta */
    public List<Evento> eventosDeporte(int id) {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e WHERE e.deporte.idDeporte = ?1 AND e.vendida=true")
                .setParameter(1, id)
                .getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
     
    //Devuelve los eventos del sistema cuyas entradas no están aún a la venta
    public List<Evento> eventosEntradasNoPuestasEnVenta() {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e WHERE e.vendida=false").getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    //Devuelve los eventos del sistema cuyas entradas están a la venta
    public List<Evento> eventosEntradasPuestasEnVenta() {
        List<Evento> eventos = new ArrayList();
        List<Evento> lista = em.createQuery("Select e from Evento e WHERE e.vendida=true").getResultList();

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
    
    //Dado un criterio de búsqueda, devuelve los eventos que coinciden con él
    public List<Evento> buscarEventos(String criterio) {
        List<Evento> eventos = new ArrayList();
    
        Query query = em.createQuery("SELECT DISTINCT (e) from Evento e JOIN e.participantes p WHERE UPPER (e.deporte.nombre) LIKE :criterio OR UPPER (e.competicion.nombre) LIKE :criterio OR UPPER (e.ciudad) LIKE :criterio OR UPPER (p.nombre) LIKE :criterio");
        //Query query = em.createQuery("SELECT e from Evento e WHERE upper (e.deporte.nombre) LIKE :criterio");
        query.setParameter("criterio", '%' + criterio.toUpperCase() + '%');
        List<Evento> lista = query.getResultList();
        System.out.println("Número de eventos: "+lista.size());

        for (Evento evento : lista) {
            eventos.add(evento);
        }
        return Collections.unmodifiableList(eventos);
    }
}

package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.EntradaErrorActualizar;
import es.ujaen.tfg.excepciones.EntradaErrorEliminar;
import es.ujaen.tfg.excepciones.EntradaErrorInsertar;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Component
public class EntradaDAO {
    
    @PersistenceContext
    private EntityManager em;

    public EntradaDAO() {

    }
    
    @Cacheable(value = "entradas", key = "#idEntrada")
    public Entrada buscar(int idEntrada) {
        return em.find(Entrada.class, idEntrada);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EntradaErrorInsertar.class)
    public void insertar(Entrada entrada) throws EntradaErrorInsertar {
        try {
            em.persist(entrada);
        } catch (Exception e) {
            throw new EntradaErrorInsertar();
        }
    }
    
     @CacheEvict(value = "entradas", key = "#entrada.getIdEntrada()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EntradaErrorActualizar.class)
    public void actualizar(Entrada entrada) throws EntradaErrorActualizar {
        try {
            em.merge(entrada);
        } catch (Exception e) {
            throw new EntradaErrorActualizar();
        }
    }
    
    @CacheEvict(value = "entradas", key = "#entrada.getIdEntrada()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EntradaErrorEliminar.class)
    public void eliminar(Entrada entrada) throws EntradaErrorEliminar {
        try {
            em.remove(em.contains(entrada) ? entrada : em.merge(entrada));
        } catch (Exception e) {
            throw new EntradaErrorEliminar();
        }
    }
    
    public List<Entrada> listadoEntradas() { //Devuelve las entradas del sistema
        List<Entrada> entradas = new ArrayList();
        List<Entrada> lista = em.createQuery("Select e from Entrada e").getResultList();

        for (Entrada entrada : lista) {
            entradas.add(entrada);
        }
         return Collections.unmodifiableList(entradas);
    }
    
    //Dado el ID de un evento, devuelve las entradas de ese evento
    public List<Entrada> listadoEntradasEvento(int idEvento) {
        List<Entrada> entradas = new ArrayList();
        List<Entrada> lista = em.createQuery("Select e from Entrada e WHERE e.evento.idEvento = ?1")
                .setParameter(1, idEvento)
                .getResultList();

        for (Entrada entrada : lista) {
            entradas.add(entrada);
        }
        return Collections.unmodifiableList(entradas);
    }
    
    //Dado el ID de un evento, devuelve las entradas de ese evento que no están aún compradas
    public List<Entrada> entradasEvento(int idEvento) {
        List<Entrada> entradas = new ArrayList();
        List<Entrada> lista = em.createQuery("Select e from Entrada e WHERE e.evento.idEvento = ?1 AND e.comprada=false")
                .setParameter(1, idEvento)
                .getResultList();

        for (Entrada entrada : lista) {
            entradas.add(entrada);
        }
        return Collections.unmodifiableList(entradas);
    }
    
    //Dado el ID de un evento, devuelve las entradas de ese evento que están compradas
    public List<Entrada> entradasEventoCompradas(int idEvento) {
        List<Entrada> entradas = new ArrayList();
        List<Entrada> lista = em.createQuery("Select e from Entrada e WHERE e.evento.idEvento = ?1 AND e.comprada=true")
                .setParameter(1, idEvento)
                .getResultList();

        for (Entrada entrada : lista) {
            entradas.add(entrada);
        }
        return Collections.unmodifiableList(entradas);
    }
    
    //Dado el ID de un evento, devuelve el número de entradas de ese evento
    public Long numEntradasEvento(int idEvento){
        return em.createQuery("Select COUNT (e) from Entrada e WHERE e.evento.idEvento = ?1", Long.class)
                .setParameter(1, idEvento)
                .getSingleResult();
    }
    
    //Dado un evento, eliminamos las entradas que lo contengan
    /**
     *
     * @param evento
     */
    @CacheEvict(value = "entradas", key = "#entrada.getIdEntrada()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.EntradaErrorEliminar.class)
    public void eliminarEntradasEvento(Evento evento) throws EntradaErrorEliminar {
        List<Entrada> entradas = em.createQuery("Select e from Entrada e WHERE e.evento = ?1")
                .setParameter(1, evento)
                .getResultList();

        for (Entrada entrada : entradas) {
            try {
                em.remove(em.contains(entrada) ? entrada : em.merge(entrada));
            } catch (Exception e) {
                throw new EntradaErrorEliminar();
            }
        }
    }
    
    //Listado de entradas que no están compradas
    public List<Entrada> listadoEntradasCompradas() {
        List<Entrada> entradas = new ArrayList();
        List<Entrada> lista = em.createQuery("Select e from Entrada e WHERE e.comprada = true")
                .getResultList();

        for (Entrada entrada : lista) {
            entradas.add(entrada);
        }
        return Collections.unmodifiableList(entradas);
    }
    
}

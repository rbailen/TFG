package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.DeporteErrorActualizar;
import es.ujaen.tfg.excepciones.DeporteErrorEliminar;
import es.ujaen.tfg.excepciones.DeporteErrorInsertar;
import es.ujaen.tfg.modelos.Deporte;
import java.util.ArrayList;
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
public class DeporteDAO {
    
    @PersistenceContext
    private EntityManager em;

    public DeporteDAO() {

    }
    
    @Cacheable(value = "deportes", key = "#idDeporte")
    public Deporte buscar(int idDeporte) {
        return em.find(Deporte.class, idDeporte);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.DeporteErrorInsertar.class)
    public void insertar(Deporte deporte) throws DeporteErrorInsertar {
        try {
            em.persist(deporte);
        } catch (Exception e) {
            throw new DeporteErrorInsertar();
        }
    }
    
     @CacheEvict(value = "deportes", key = "#deporte.getIdDeporte()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.DeporteErrorActualizar.class)
    public void actualizar(Deporte deporte) throws DeporteErrorActualizar {
        try {
            em.merge(deporte);
        } catch (Exception e) {
            throw new DeporteErrorActualizar();
        }
    }
    
    @CacheEvict(value = "deportes", key = "#deporte.getIdDeporte()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.DeporteErrorEliminar.class)
    public void eliminar(Deporte deporte) throws DeporteErrorEliminar {
        try {
            em.remove(em.contains(deporte) ? deporte : em.merge(deporte));
        } catch (Exception e) {
            throw new DeporteErrorEliminar();
        }
    }
    
    public List<Deporte> listadoDeportes() { //Devuelve los deportes del sistema
        List<Deporte> deportes = new ArrayList();
        List<Deporte> lista = em.createQuery("Select d from Deporte d").getResultList();

        for (Deporte deporte : lista) {
            deportes.add(deporte);
        }
        return deportes;
    }
    
    public List<Deporte> deportesEntradas() { //Devuelve los deportes que contienen entradas a la venta y no están compradas
        List<Deporte> deportes = new ArrayList();
        List<Deporte> lista = em.createQuery("Select DISTINCT (e.deporte) from Evento e, Entrada f WHERE f.evento.idEvento=e.idEvento AND e.vendida=true AND f.comprada=false").getResultList();

        for (Deporte deporte : lista) {
            deportes.add(deporte);
        }
        return deportes;
    }
    
}
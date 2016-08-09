package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.CompeticionErrorActualizar;
import es.ujaen.tfg.excepciones.CompeticionErrorEliminar;
import es.ujaen.tfg.excepciones.CompeticionErrorInsertar;
import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Deporte;
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
public class CompeticionDAO {
    
    @PersistenceContext
    private EntityManager em;

    public CompeticionDAO() {

    }
    
    @Cacheable(value = "competiciones", key = "#idCompeticion")
    public Competicion buscar(int idCompeticion) {
        return em.find(Competicion.class, idCompeticion);
    }
    
    /**
     *
     * @param competicion
     * @throws CompeticionErrorInsertar
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.CompeticionErrorInsertar.class)
    public void insertar(Competicion competicion) throws CompeticionErrorInsertar {
        try {
            em.persist(competicion);
        } catch (Exception e) {
            throw new CompeticionErrorInsertar();
        }
    }
    
    @CacheEvict(value = "competiciones", key = "#competicion.getIdCompeticion()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.CompeticionErrorActualizar.class)
    public void actualizar(Competicion competicion) throws CompeticionErrorActualizar {
        try {
            em.merge(competicion);
        } catch (Exception e) {
            throw new CompeticionErrorActualizar();
        }
    }
    
    /**
     *
     * @param competicion
     * @throws CompeticionErrorEliminar
     */
    @CacheEvict(value = "competiciones", key = "#competicion.getIdCompeticion()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.CompeticionErrorEliminar.class)
    public void eliminar(Competicion competicion) throws CompeticionErrorEliminar {
        try {
            em.remove(em.contains(competicion) ? competicion : em.merge(competicion));
        } catch (Exception e) {
            throw new CompeticionErrorEliminar();
        }
    }
    
    public List<Competicion> listadoCompeticiones() { //Devuelve las competiciones del sistema
        List<Competicion> competiciones = new ArrayList();
        List<Competicion> lista = em.createQuery("Select c from Competicion c").getResultList();

        for (Competicion competicion : lista) {
            competiciones.add(competicion);
        }
        return Collections.unmodifiableList(competiciones);
    }
    
    //Dado el ID del deporte, devuelve las competiciones de dicho deporte
    public List<Competicion> competicionesPorDeporte(int id){
        List<Competicion> competiciones = new ArrayList();
        List<Competicion> lista = em.createQuery("Select c from Competicion c WHERE c.deporte.idDeporte = ?1")
                .setParameter(1, id)
                .getResultList();
        
        for (Competicion competicion : lista) {
            competiciones.add(competicion);
        }
        return Collections.unmodifiableList(competiciones);
    }
    
    //Dado un deporte, eliminamos las competiciones que lo contengan
    /**
     *
     * @param deporte
     * @throws CompeticionErrorEliminar
     */
    @CacheEvict(value = "competiciones", key = "#competicion.getIdCompeticion()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.CompeticionErrorEliminar.class)
    public void eliminarPorDeporte(Deporte deporte) throws CompeticionErrorEliminar {
        List<Competicion> competiciones = em.createQuery("Select c from Competicion c WHERE c.deporte = ?1")
                .setParameter(1, deporte)
                .getResultList();

        for (Competicion competicion : competiciones) {
            try {
                em.remove(em.contains(competicion) ? competicion : em.merge(competicion));
            } catch (Exception e) {
                throw new CompeticionErrorEliminar();
            }
        }
    }
}

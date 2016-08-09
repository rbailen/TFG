package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.ParticipanteErrorActualizar;
import es.ujaen.tfg.excepciones.ParticipanteErrorEliminar;
import es.ujaen.tfg.excepciones.ParticipanteErrorInsertar;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Participante;
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
public class ParticipanteDAO {
    
    @PersistenceContext
    private EntityManager em;

    public ParticipanteDAO() {

    }
    
    @Cacheable(value = "participantes", key = "#idParticipante")
    public Participante buscar(int idParticipante) {
        return em.find(Participante.class, idParticipante);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.ParticipanteErrorInsertar.class)
    public void insertar(Participante participante) throws ParticipanteErrorInsertar {
        try {
            em.persist(participante);
        } catch (Exception e) {
            throw new ParticipanteErrorInsertar();
        }
    }
    
     @CacheEvict(value = "participantes", key = "#participante.getIdParticipante()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.ParticipanteErrorActualizar.class)
    public void actualizar(Participante participante) throws ParticipanteErrorActualizar {
        try {
            em.merge(participante);
        } catch (Exception e) {
            throw new ParticipanteErrorActualizar();
        }
    }
    
    /**
     *
     * @param participante
     * @throws ParticipanteErrorEliminar
     */
    @CacheEvict(value = "participantes", key = "#participante.getIdParticipante()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.ParticipanteErrorEliminar.class)
    public void eliminar(Participante participante) throws ParticipanteErrorEliminar {
        try {
            em.remove(em.contains(participante) ? participante : em.merge(participante));
        } catch (Exception e) {
            throw new ParticipanteErrorEliminar();
        }
    }
    
    public List<Participante> listadoParticipantes() { //Devuelve los participantes del sistema
        List<Participante> participantes = new ArrayList();
        List<Participante> lista = em.createQuery("Select p from Participante p").getResultList();

        for (Participante participante : lista) {
            participantes.add(participante);
        }
        return Collections.unmodifiableList(participantes);
    }
    
    //Dado el ID del deporte, devuelve los participantes (equipos o deportistas) de dicho deporte
    public List<Participante> participantesPorDeporte(int id){
        List<Participante> participantes = new ArrayList();
        List<Participante> lista = em.createQuery("Select p from Participante p WHERE p.deporte.idDeporte = ?1")
                .setParameter(1, id)
                .getResultList();
        
        for (Participante participante : lista) {
            participantes.add(participante);
        }
        return Collections.unmodifiableList(participantes);
    }
    
    //Dado un deporte, eliminamos los participantes que lo contengan
    /**
     *
     * @param deporte
     */
    @CacheEvict(value = "participantes", key = "#participante.getIdParticipante()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.ParticipanteErrorEliminar.class)
    public void eliminarPorDeporte(Deporte deporte) throws ParticipanteErrorEliminar {
        List<Participante> participantes = em.createQuery("Select p from Participante p WHERE p.deporte = ?1")
                .setParameter(1, deporte)
                .getResultList();

        for (Participante participante : participantes) {
            try {
                em.remove(em.contains(participante) ? participante : em.merge(participante));
            } catch (Exception e) {
                throw new ParticipanteErrorEliminar();
            }
        }
    }
}

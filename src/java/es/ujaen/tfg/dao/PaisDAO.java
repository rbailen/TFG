package es.ujaen.tfg.dao;

import es.ujaen.tfg.modelos.Pais;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Component
public class PaisDAO {
    
    @PersistenceContext
    private EntityManager em;

    public PaisDAO() {

    }
    
     public List<Pais> listadoPaises() { //Devuelve los paises del sistema
        List<Pais> paises = new ArrayList();
        List<Pais> lista = em.createQuery("Select p from Pais p").getResultList();

        for (Pais pais : lista) {
            paises.add(pais);
        }
        return Collections.unmodifiableList(paises);
     }
     
}

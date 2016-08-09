package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.PaisDAO;
import es.ujaen.tfg.modelos.Pais;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioPais {
    
    @Autowired
    private PaisDAO paisDAO;
    
    public ServicioPais(){
        
    }
    
    public List<Pais> listadoPaises() {
        return paisDAO.listadoPaises();
    }
}

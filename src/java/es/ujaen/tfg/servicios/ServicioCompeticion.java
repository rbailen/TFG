package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompeticionDAO;
import es.ujaen.tfg.dao.CompraDAO;
import es.ujaen.tfg.dao.EntradaDAO;
import es.ujaen.tfg.dao.EventoDAO;
import es.ujaen.tfg.excepciones.CompeticionNoEncontrada;
import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioCompeticion {
    
    @Autowired
    private CompeticionDAO competicionDAO;
    
    @Autowired
    private EventoDAO eventoDAO;
    
    @Autowired
    private EntradaDAO entradaDAO;
    
    @Autowired
    private CompraDAO compraDAO;
    
    public ServicioCompeticion(){
        
    }
    
    public List<Competicion> listadoCompeticiones() {
        return competicionDAO.listadoCompeticiones();
    }
    
    public Competicion buscaCompeticion(int id){
        return competicionDAO.buscar(id);
    }
    
    public void altaCompeticion(Competicion competicion){
        competicionDAO.insertar(competicion);
    }
    
    public void editaCompeticion(Competicion competicion){
        competicionDAO.actualizar(competicion);
    }
    
    public void bajaCompeticion(int id){
        Competicion competicion = competicionDAO.buscar(id);
        
        if(competicion == null){
            throw new CompeticionNoEncontrada();
        }
        
        /* Para borrar una competición, en primer lugar tenemos que eliminar los eventos asociados
        a la misma y para eliminar dichos eventos, tenemos que borrar previamente las entradas de los mismos*/
        
        //Buscamos los eventos asociados a la competición
        List<Evento> eventosCompeticion = eventoDAO.listadoEventosCompeticion(competicion.getIdCompeticion());
        
        //Eliminamos las compras de las entradas de dichos eventos
        for (Evento evento : eventosCompeticion) {

            //Entradas de los eventos
            List<Entrada> entradas = entradaDAO.listadoEntradasEvento(evento.getIdEvento());
            
            Compra compra = new Compra();
            for (Entrada entrada : entradas) {
                //Buscamos la compra de la primera entrada, ya que todas ellas pertenecen al mismo evento
                compra = compraDAO.compraEntrada(entrada.getIdEntrada());
            }
            if (compra != null) {
                //Eliminamos la compra de la entrada
                compraDAO.eliminar(compra);
            }
            
            //Para cada uno de esos eventos, eliminamos sus entradas
            entradaDAO.eliminarEntradasEvento(evento);
        }
        
        //Una vez eliminadas las entradas del evento, eliminamos los eventos que contengan esa competición
        eventoDAO.eliminarPorCompeticion(competicion);
        
        //Finalmente eliminamos la competición
        competicionDAO.eliminar(competicion);
    }
    
    //Devuelve las competiciones de un deporte
    public List<Competicion> competicionesDeporte(int id){
        return competicionDAO.competicionesPorDeporte(id);
    }
}

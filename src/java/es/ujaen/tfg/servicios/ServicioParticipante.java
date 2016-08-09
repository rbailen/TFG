package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompraDAO;
import es.ujaen.tfg.dao.EntradaDAO;
import es.ujaen.tfg.dao.EventoDAO;
import es.ujaen.tfg.dao.ParticipanteDAO;
import es.ujaen.tfg.excepciones.ParticipanteNoEncontrado;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import es.ujaen.tfg.modelos.Participante;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioParticipante {
    
    @Autowired
    private ParticipanteDAO participanteDAO;
    
    @Autowired
    private EventoDAO eventoDAO;
    
    @Autowired
    private EntradaDAO entradaDAO;
    
    @Autowired
    private CompraDAO compraDAO;
    
    public ServicioParticipante(){
        
    }
    
    public List<Participante> listadoParticipantes() {
        return participanteDAO.listadoParticipantes();
    }
    
    public Participante buscaParticipante(int id){
        return participanteDAO.buscar(id);
    }
    
    public void altaParticipante(Participante participante){
        participanteDAO.insertar(participante);
    }
    
    public void editaParticipante(Participante participante){
        participanteDAO.actualizar(participante);
    }
    
    public void bajaParticipante(int id){
        Participante participante = participanteDAO.buscar(id);
        
        if(participante == null){
            throw new ParticipanteNoEncontrado();
        }
        
        /* Hay que eliminar los eventos en los que participa dicho participante. Previamente se eliminan las entradas
        a dichos eventos*/
        
        List<Evento> eventosQueParticipa = eventoDAO.listadoEventosParticipante(participante.getIdParticipante());
        
        for(Evento evento: eventosQueParticipa){
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
            
            //Eliminamos las entradas de los eventos
            entradaDAO.eliminarEntradasEvento(evento);
            
            //Eliminamos los eventos
            eventoDAO.eliminar(evento);
        }
        
        //Finalmente, eliminamos el participante
        participanteDAO.eliminar(participante);
    }
    
    //Devuelve los participante de un deporte (equipos o deportistas)
    public List<Participante> participantesDeporte(int id){
        return participanteDAO.participantesPorDeporte(id);
    }
}

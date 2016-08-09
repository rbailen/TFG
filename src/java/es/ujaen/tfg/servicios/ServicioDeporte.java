package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompeticionDAO;
import es.ujaen.tfg.dao.DeporteDAO;
import es.ujaen.tfg.dao.ParticipanteDAO;
import es.ujaen.tfg.excepciones.DeporteNoEncontrado;
import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Participante;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioDeporte {
    
    @Autowired
    private DeporteDAO deporteDAO;
    
    @Autowired
    private CompeticionDAO competicionDAO;
    
    @Autowired
    private ParticipanteDAO participanteDAO;
    
    @Autowired
    private ServicioCompeticion servicioCompeticion;
    
    public ServicioDeporte(){
        
    }
    
    public List<Deporte> deportes(){
        return deporteDAO.listadoDeportes();
    }
    
    public Deporte buscarDeporte(int id){
        return deporteDAO.buscar(id);
    }
    
    public void altaDeporte(Deporte deporte){
        deporteDAO.insertar(deporte);
    }
    
    public void editaDeporte(Deporte deporte){
        deporteDAO.actualizar(deporte);
    }
    
    public void bajaDeporte(int id){
        Deporte deporte = deporteDAO.buscar(id);
        
        if(deporte == null){
            throw new DeporteNoEncontrado();
        }
        
        /* Para borrar un deporte, en primer lugar tenemos que eliminar las competiciones y los participantes
        del mismo*/

        /* Dado un deporte, buscamos las competiciones del mismo e invocamos al servio de competiciones para
        que se encargue de eliminar todo lo relacionado con la competición*/
        List<Competicion> competicionesDeporte = competicionDAO.competicionesPorDeporte(id);
        for(Competicion competicion: competicionesDeporte){
            servicioCompeticion.bajaCompeticion(competicion.getIdCompeticion());
        }
        
        //Eliminamos los participantes del deporte
        participanteDAO.eliminarPorDeporte(deporte);
        
        //Finalmente eliminamos el deporte
        deporteDAO.eliminar(deporte);
    }
    
    //Devuelve los deportes que contienen entradas a la venta y no están compradas
    public List<Deporte> deportesEntradas() {
        return deporteDAO.deportesEntradas();
    }
    
    //Dado el ID de un participante, devuelve el deporte en el que participa
    public Deporte deporteParticipante(int idParticipante){
        Participante participante = participanteDAO.buscar(idParticipante);
        return participante.getDeporte();
    }
}

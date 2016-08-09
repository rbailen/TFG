package es.ujaen.tfg.editor;

import es.ujaen.tfg.modelos.Participante;
import es.ujaen.tfg.servicios.ServicioParticipante;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class ParticipanteEditor extends PropertyEditorSupport{
    
    @Autowired
    private final ServicioParticipante servicio;
    
    public ParticipanteEditor(ServicioParticipante servicio) {
        this.servicio = servicio;
    }
    
    @Override
    public void setAsText(String text){
        Participante participante = servicio.buscaParticipante(Integer.parseInt(text));
        setValue(participante);
    }
    
}

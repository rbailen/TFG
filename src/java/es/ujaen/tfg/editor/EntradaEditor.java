package es.ujaen.tfg.editor;

import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.servicios.ServicioEntrada;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class EntradaEditor extends PropertyEditorSupport{
    
    @Autowired
    private final ServicioEntrada servicio;
    
    public EntradaEditor(ServicioEntrada servicio) {
        this.servicio = servicio;
    }
    
    @Override
    public void setAsText(String text){
        Entrada entrada = servicio.buscarEntrada(Integer.parseInt(text));
        setValue(entrada);
    }
    
}

package es.ujaen.tfg.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "deportes")
public class Deporte implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idDeporte;
    @NotEmpty @Size(min = 3, max = 20)
    private String nombre;
    @NotEmpty @Size(min = 3, max = 15)
    private String denominacionParticipante;
    @NotEmpty @Size(min = 3, max = 15)
    private String denominacionEvento;
    
    public Deporte(){
        
    }

    public Deporte(int idDeporte, String nombre, String denominacionParticipante, String denominacionEvento) {
        this.idDeporte = idDeporte;
        this.nombre = nombre;
        this.denominacionParticipante = denominacionParticipante;
        this.denominacionEvento = denominacionEvento;
    }

   /**
     * @return the idDeporte
     */
    public int getIdDeporte() {
        return idDeporte;
    }

    /**
     * @param idDeporte the id to set
     */
    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the denominacionParticipante
     */
    public String getDenominacionParticipante() {
        return denominacionParticipante;
    }

    /**
     * @param denominacionParticipante the denominacionParticipante to set
     */
    public void setDenominacionParticipante(String denominacionParticipante) {
        this.denominacionParticipante = denominacionParticipante;
    }

    /**
     * @return the denominacionEvento
     */
    public String getDenominacionEvento() {
        return denominacionEvento;
    }

    /**
     * @param denominacionEvento the denominacionEvento to set
     */
    public void setDenominacionEvento(String denominacionEvento) {
        this.denominacionEvento = denominacionEvento;
    }

    
}

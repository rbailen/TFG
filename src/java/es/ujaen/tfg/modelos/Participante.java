package es.ujaen.tfg.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "participantes")
public class Participante implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idParticipante;
    @NotEmpty @Size(min = 3, max = 30)
    private String nombre;
    
    @OneToOne
    @JoinColumn(name = "idDeporte")
    private Deporte deporte;
    
    public Participante(){
        
    }

    public Participante(int idParticipante, String nombre, Deporte deporte) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.deporte = deporte;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        
        if(!(object instanceof Participante)){
           return false; 
        }
        
        return this.idParticipante ==((Participante)object).idParticipante;
    }

    @Override
    public int hashCode() {
        return new Long(idParticipante).hashCode();
    }
    
    /**
     * @return the idParticipante
     */
    public int getIdParticipante() {
        return idParticipante;
    }

    /**
     * @param idParticipante the id to set
     */
    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
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
     * @return the deporte
     */
    public Deporte getDeporte() {
        return deporte;
    }

    /**
     * @param deporte the deporte to set
     */
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
    
    
}

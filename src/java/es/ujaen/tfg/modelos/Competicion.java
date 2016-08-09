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
@Table(name = "competiciones")
public class Competicion implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idCompeticion;
    @NotEmpty @Size(min = 3, max = 30)
    private String nombre; 
    
    @OneToOne
    @JoinColumn(name = "idDeporte")
    private Deporte deporte;
    
    public Competicion(){
        
    }

    public Competicion(int idCompeticion, String nombre, Deporte deporte) {
        this.idCompeticion = idCompeticion;
        this.nombre = nombre;
        this.deporte = deporte;
    }

    /**
     * @return the idCompeticion
     */
    public int getIdCompeticion() {
        return idCompeticion;
    }

    /**
     * @param idCompeticion the id to set
     */
    public void setIdCompeticion(int idCompeticion) {
        this.idCompeticion = idCompeticion;
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

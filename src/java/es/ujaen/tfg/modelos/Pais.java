package es.ujaen.tfg.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paises")
public class Pais implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idPais;
    
    private String iso;
    private String nombre;
    
    public Pais(){
        
    }
    
    public Pais(int idPais, String iso, String nombre){
        this.idPais = idPais;
        this.iso = iso;
        this.nombre = nombre;
    }

    /**
     * @return the idPais
     */
    public int getIdPais() {
        return idPais;
    }

    /**
     * @param idPais the idPais to set
     */
    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    /**
     * @return the iso
     */
    public String getIso() {
        return iso;
    }

    /**
     * @param iso the iso to set
     */
    public void setIso(String iso) {
        this.iso = iso;
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
    
    
}

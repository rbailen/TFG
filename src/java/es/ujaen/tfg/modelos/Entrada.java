package es.ujaen.tfg.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "entradas")
public class Entrada implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idEntrada;
    @NotEmpty @Size(min = 3, max = 150)
    private String descripcion;
    @Min(1)
    private double precio;
    private boolean comprada;
    
    @OneToOne
    @JoinColumn(name = "idEvento")
    private Evento evento;
   
    public Entrada(){
        
    }
    
    public Entrada(String descripcion, double precio, boolean comprada, Evento evento) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.comprada = comprada;
        this.evento = evento;
    }

    public Entrada(int idEntrada, String descripcion, double precio, boolean comprada, Evento evento) {
        this.idEntrada = idEntrada;
        this.descripcion = descripcion;
        this.precio = precio;
        this.comprada = comprada;
        this.evento = evento;
    }

    /**
     * @return the id
     */
    public int getIdEntrada() {
        return idEntrada;
    }

    /**
     * @param idEntrada the id to set
     */
    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    /**
     * @return the evento
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * @return the comprada
     */
    public boolean isComprada() {
        return comprada;
    }

    /**
     * @param comprada the comprada to set
     */
    public void setComprada(boolean comprada) {
        this.comprada = comprada;
    }
    
    
}

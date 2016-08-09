package es.ujaen.tfg.modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "compras")
public class Compra implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idCompra;
    
    private double total;
    
    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    @OneToMany (fetch = FetchType.EAGER)
    private List<Entrada> entradas;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompra;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    
    public Compra(){
        fechaCompra = new Date();
    }

    public Compra(int idCompra, double total, Usuario usuario, List<Entrada> entradas, Date fechaCompra, Date fechaPago) {
        this.idCompra = idCompra;
        this.total = total;
        this.usuario = usuario;
        this.entradas = entradas;
        this.fechaCompra = fechaCompra;
        this.fechaPago = fechaPago;
    }

    /**
     * @return the idCompra
     */
    public int getIdCompra() {
        return idCompra;
    }

    /**
     * @param idCompra the idCompra to set
     */
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the entradas
     */
    public List<Entrada> getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    /**
     * @return the fechaCompra
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }

    /**
     * @param fechaCompra the fechaCompra to set
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * @return the fechaPago
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    
}

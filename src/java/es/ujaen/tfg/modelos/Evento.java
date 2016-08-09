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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "eventos")
public class Evento implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idEvento;
    @NotEmpty @Size(min = 5, max = 50)
    private String nombre;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @NotEmpty
    private String hora;
    @NotEmpty @Size(min = 3, max = 30)
    private String lugar;
    @NotEmpty @Size(min = 3, max = 30)
    private String ciudad;
    private boolean vendida; /* Indica si las entradas de dicho evento están puestas a la venta */
    @OneToOne
    @JoinColumn(name = "idPais")
    private Pais pais;
    
    @OneToOne
    @JoinColumn(name = "idDeporte")
    private Deporte deporte;
    
    @OneToOne
    @JoinColumn(name = "idCompeticion")
    private Competicion competicion;
    
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name="participa",
    joinColumns=@JoinColumn(name="idEvento"),
    inverseJoinColumns=@JoinColumn(name="idParticipante"))
    private List<Participante> participantes;
    
    public Evento(){
        fecha = new Date();
    }
    
    public Evento(int idEvento, String nombre, String hora, Date fecha, String lugar, String ciudad, boolean vendida, Pais pais, Deporte deporte, Competicion competicion, List<Participante> participantes) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.ciudad = ciudad;
        this.vendida = vendida;
        this.pais = pais;
        this.deporte = deporte;
        this.competicion = competicion;
        this.participantes = participantes;
    }
    
    /**
     * @return the id
     */
    public int getIdEvento() {
        return idEvento;
    }

    /**
     * @param idEvento the id to set
     */
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    /**
     * @return the vendida
     */
    public boolean isVendida() {
        return vendida;
    }

    /**
     * @param vendida the vendida to set
     */
    public void setVendida(boolean vendida) {
        this.vendida = vendida;
    }
    
    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
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

    /**
     * @return the competicion
     */
    public Competicion getCompeticion() {
        return competicion;
    }

    /**
     * @param competicion the competicion to set
     */
    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    /**
     * @return the participantes
     */
    public List<Participante> getParticipantes() {
        return participantes;
    }

    /**
     * @param participantes the participantes to set
     */
    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    
}

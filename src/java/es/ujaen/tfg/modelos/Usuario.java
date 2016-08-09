package es.ujaen.tfg.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "usuarios", uniqueConstraints={@UniqueConstraint(columnNames={"usuario"})})
public class Usuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idUsuario;
    @NotEmpty @Size(min = 5, max = 30)
    private String nombre;
    @NotEmpty @Email
    private String correo;
    @NotEmpty @Size(min = 2, max = 25)
    private  String ciudad;
    @NotEmpty @Size(min = 5)
    private String usuario;
    @NotEmpty @Size(min = 5)
    private String clave;
    @AssertTrue(message = "Tiene que aceptar las condiciones")
    private boolean edad;
    @NotEmpty
    private String rol;
    
    public Usuario(){
        this.rol = "ROLE_USER";
    }

    //Dar de alta un usuario con rol admin o rol user
    public Usuario(int idUsuario, String nombre, String correo, String ciudad, String usuario, String clave, boolean edad, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.ciudad = ciudad;
        this.usuario = usuario;
        this.clave = clave;
        this.edad = edad;
        this.rol = rol;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the id to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario= idUsuario;
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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    
    /**
     * @return the edad
     */
    public boolean isEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(boolean edad) {
        this.edad = edad;
    }
    
    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
    
}


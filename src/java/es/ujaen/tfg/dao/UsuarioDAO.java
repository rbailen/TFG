package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.UsuarioErrorActualizar;
import es.ujaen.tfg.excepciones.UsuarioErrorEliminar;
import es.ujaen.tfg.excepciones.UsuarioErrorInsertar;
import es.ujaen.tfg.modelos.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Component
public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    public UsuarioDAO() {

    }

    @Cacheable(value = "usuarios", key = "#idUsuario")
    public Usuario buscar(int idUsuario) {
        return em.find(Usuario.class, idUsuario);
    }
    
    public Usuario buscarPorUsuario(String usuario) {
        List<Usuario> buscado = em.createQuery("Select u from Usuario u WHERE u.usuario = ?1").setParameter(1, usuario).getResultList();

        if (!buscado.isEmpty()) {
            for (Usuario user : buscado) {
                return user;
            }
        }

        return null;
    }

    /**
     *
     * @param usuario
     * @throws UsuarioErrorInsertar
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.UsuarioErrorInsertar.class)
    public void insertar(Usuario usuario) throws UsuarioErrorInsertar {
        try {
            em.persist(usuario);
        } catch (Exception e) {
            throw new UsuarioErrorInsertar();
        }
    }

    /**
     *
     * @param usuario
     * @throws UsuarioErrorActualizar
     */
    @CacheEvict(value = "usuarios", key = "#usuario.getIdUsuario()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.UsuarioErrorActualizar.class)
    public void actualizar(Usuario usuario) throws UsuarioErrorActualizar {
        try {
            em.merge(usuario);
        } catch (Exception e) {
            throw new UsuarioErrorActualizar();
        }
    }

    /**
     *
     * @param usuario
     * @throws UsuarioErrorEliminar
     */
    @CacheEvict(value = "usuarios", key = "#usuario.getIdUsuario()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.UsuarioErrorEliminar.class)
    public void eliminar(Usuario usuario) throws UsuarioErrorEliminar {
        try {
            em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
        } catch (Exception e) {
            throw new UsuarioErrorEliminar();
        }
    }

    public List<Usuario> listadoUsuarios() { //Devuelve los usuarios del sistema
        List<Usuario> usuarios = new ArrayList();
        List<Usuario> lista = em.createQuery("Select u from Usuario u").getResultList();

        for (Usuario usuario : lista) {
            usuarios.add(usuario);
        }
        return Collections.unmodifiableList(usuarios);
    }
    
}

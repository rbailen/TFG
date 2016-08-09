package es.ujaen.tfg.dao;

import es.ujaen.tfg.excepciones.CompraErrorActualizar;
import es.ujaen.tfg.excepciones.CompraErrorEliminar;
import es.ujaen.tfg.excepciones.CompraErrorInsertar;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Component
public class CompraDAO {
    
    @PersistenceContext
    private EntityManager em;

    public CompraDAO() {

    }
    
    @Cacheable(value = "compras", key = "#idCompra")
    public Compra buscar(int idCompra) {
        return em.find(Compra.class, idCompra);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.CompraErrorInsertar.class)
    public void insertar(Compra compra) throws CompraErrorInsertar {
        try {
            em.persist(compra);
        } catch (Exception e) {
            throw new CompraErrorInsertar();
        }
    }
    
     @CacheEvict(value = "compras", key = "#compra.getIdCompra()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.DeporteErrorActualizar.class)
    public void actualizar(Compra compra) throws CompraErrorActualizar {
        try {
            em.merge(compra);
        } catch (Exception e) {
            throw new CompraErrorActualizar();
        }
    }
    
    @CacheEvict(value = "compras", key = "#compra.getIdCompra()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = es.ujaen.tfg.excepciones.CompraErrorEliminar.class)
    public void eliminar(Compra compra) throws CompraErrorEliminar {
        try {
            em.remove(em.contains(compra) ? compra : em.merge(compra));
        } catch (Exception e) {
            throw new CompraErrorEliminar();
        }
    }
    
    public List<Compra> listadoCompras() { //Devuelve los deportes del sistema
        List<Compra> compras = new ArrayList();
        List<Compra> lista = em.createQuery("Select c from Compra c").getResultList();

        for (Compra compra : lista) {
            compras.add(compra);
        }
        return compras;
    }
    
    //Dado el ID de un cliente, devuelve las compras que ha realizado
    public List<Compra> comprasRealizadasCliente(int id) {
        List<Compra> compras = new ArrayList();
        List<Compra> lista = em.createQuery("Select c from Compra c WHERE c.usuario.idUsuario = ?1")
                .setParameter(1, id)
                .getResultList();

        for (Compra compra: lista) {
            compras.add(compra);
        }
        return Collections.unmodifiableList(compras);
    }
    
    //Dado el ID de una entrada, devuelve la compra a la que pertenece la misma
    public Compra compraEntrada(int idEntrada) {
        
        Query q1 = em.createQuery("Select c from Compra c JOIN c.entradas e WHERE e.idEntrada = ?1").setParameter(1, idEntrada);
        List<Compra> compras = q1.getResultList();
        
        if (!compras.isEmpty()) {
            for (Compra compra : compras) {
                return compra;
            }
        }

        return null;
    }
    
    //Dado el ID de una compra, devuelve las entradas de la misma
    public List<Entrada> entradasCompra(int idCompra) {
        
        List<Compra> buscada = em.createQuery("Select c from Compra c JOIN c.entradas e WHERE c.idCompra = ?1")
                .setParameter(1, idCompra)
                .getResultList();

        if (!buscada.isEmpty()) {
            for (Compra compra : buscada) {
                return compra.getEntradas();
            }
        }

        return null;
    }
    
}
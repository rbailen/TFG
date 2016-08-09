package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompraDAO;
import es.ujaen.tfg.excepciones.CompraNoEncontrada;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioCompra {
    
    @Autowired
    private CompraDAO compraDAO;
    
    public ServicioCompra(){
        
    }
    
     public List<Compra> listadoCompras() {
        return compraDAO.listadoCompras();
    }
    
    public Compra buscarCompra(int id){
        return compraDAO.buscar(id);
    }
    
    public void altaCompra(Compra compra){
        compraDAO.insertar(compra);
    }
    
    public void editaCompra(Compra compra){
        compraDAO.actualizar(compra);
    }
    
    public void bajaCompra(int id){
        Compra compra = compraDAO.buscar(id);
        
        if(compra== null){
            throw new CompraNoEncontrada();
        }
        
        compraDAO.eliminar(compra);
    }

    //Dado el ID de un cliente, devuelve las compras que ha realizado
    public List<Compra> comprasRealizadasCliente(int id){
        return compraDAO.comprasRealizadasCliente(id);
    }
    
    //Dado el ID de una entrada, devuelve la compra a la que pertenece la misma
    public Compra compraEntrada(int idEntrada){
        return compraDAO.compraEntrada(idEntrada);
    }
    
    //Dado el ID de una compra, devuelve las entradas de la misma
    public List<Entrada> entradasCompra(int idCompra) {
        return compraDAO.entradasCompra(idCompra);
    }
    
    //Dado el ID de una compra, devuelve el evento al que pertenece la misma
    public Evento obtenerEvento(int idCompra) {
        
        /* Obtenemos las entradas de la compra para devolver el evento al que pertenecen */
        List<Entrada> entradasCompra = compraDAO.entradasCompra(idCompra);
        
        for(Entrada entrada: entradasCompra){
            return entrada.getEvento();
        }
        return null;
    }
}

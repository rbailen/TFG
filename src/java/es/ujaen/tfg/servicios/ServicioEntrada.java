package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompraDAO;
import es.ujaen.tfg.dao.EntradaDAO;
import es.ujaen.tfg.excepciones.EntradaNoEncontrada;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioEntrada {
    
    @Autowired
    private EntradaDAO entradaDAO;
    
    @Autowired
    private CompraDAO compraDAO;
    
    public ServicioEntrada(){
        
    }
    
    public List<Entrada> entradas(){
        return entradaDAO.listadoEntradas();
    }
    
    public Entrada buscarEntrada(int id){
        return entradaDAO.buscar(id);
    }
    
    public void altaEntrada(Entrada entrada){
        entradaDAO.insertar(entrada);
    }
    
    public void editaEntrada(Entrada entrada){
        entradaDAO.actualizar(entrada);
    }
    
    public void bajaEntrada(int id){
        Entrada entrada = entradaDAO.buscar(id);
        
        if(entrada == null){
            throw new EntradaNoEncontrada();
        }
        
        //Eliminamos la compra de la entrada
        Compra compra = compraDAO.compraEntrada(entrada.getIdEntrada());
        
        if (compra != null) {
            compraDAO.eliminar(compra);
        }

        //Finalmente eliminamos la entrada
        entradaDAO.eliminar(entrada);
    }
    
    //Dado el ID de un evento, devuelve las entradas de ese evento que no están aún compradas
    public List<Entrada> entradasEvento(int idEvento){
        return entradaDAO.entradasEvento(idEvento);
    }
    
    //Dado el ID de un evento, devuelve el número de entradas de ese evento
    public Long numEntradasEvento(int idEvento){
        return entradaDAO.numEntradasEvento(idEvento);
    }
    
    //Dado el ID de un evento, devuelve las entradas de ese evento que están compradas
    public List<Entrada> entradasEventoCompradas(int idEvento){
        return entradaDAO.entradasEventoCompradas(idEvento);
    }
    
    //Dado el ID de un evento, devuelve las entradas de ese evento
    public List<Entrada> listadoEntradasEvento(int idEvento){
        return entradaDAO.listadoEntradasEvento(idEvento);
    }
    
    //Listado de entradas que no están compradas
    public List<Entrada> listadoEntradasCompradas(){
        return entradaDAO.listadoEntradasCompradas();
    }
}

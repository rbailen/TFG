package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompraDAO;
import es.ujaen.tfg.dao.EntradaDAO;
import es.ujaen.tfg.dao.EventoDAO;
import es.ujaen.tfg.excepciones.EventoNoEncontrado;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioEvento {

    @Autowired
    private EventoDAO eventoDAO;
    
    @Autowired
    private EntradaDAO entradaDAO;
    
    @Autowired
    private CompraDAO compraDAO;

    public ServicioEvento() {

    }

    public List<Evento> listadoEventos() {
        return eventoDAO.listadoEventos();
    }

    public Evento buscarEvento(int id) {
        return eventoDAO.buscar(id);
    }

    public void altaEvento(Evento evento) {
        eventoDAO.insertar(evento);
    }
    
    public void editaEvento(Evento evento){
        eventoDAO.actualizar(evento);
    }

    public void bajaEvento(int id) {
        Evento evento = eventoDAO.buscar(id);

        if (evento == null) {
            throw new EventoNoEncontrado();
        }
        
        List<Entrada> entradas = entradaDAO.entradasEventoCompradas(evento.getIdEvento());
        
        //Eliminamos la compra de entradas del evento
        for (Entrada entrada : entradas) {
            Compra compra = compraDAO.compraEntrada(entrada.getIdEntrada());
            
            if (compra != null) {
                compraDAO.eliminar(compra);
            }
        }
        
        //Eliminamos las entradas del evento
        entradaDAO.eliminarEntradasEvento(evento);

        //Finalmente eliminamos el evento
        eventoDAO.eliminar(evento);
    }
    
    /* Dado un participante, devuelve los eventos en los que participa */
    public List<Evento> listadoEventosParticipante(int id){
        return eventoDAO.listadoEventosParticipante(id);
    }
    
    /* Dado un participante, devuelve los eventos en los que participa cuyas entradas se 
       encuentran a la venta y no est�n compradas */
    public List<Evento> eventosParticipante(int id){
        List<Evento> eventos = new ArrayList();
        List<Evento> eventosEntradaVenta = eventoDAO.eventosParticipante(id);
        boolean comprada = false;
        
        for (Evento evento : eventosEntradaVenta) {
            List<Entrada> entradas = entradaDAO.entradasEvento(evento.getIdEvento());
            for (Entrada entrada : entradas) {
                if (entrada.isComprada() == false) {
                    comprada = true;
                }
            }
            if (comprada == true) {
                eventos.add(evento);
            }
            comprada=false;
        }
        
        return eventos;
    }
    
    /* Dado el id de una competici�n, devuelve la lista de eventos de dicha  competici�n */
    public List<Evento> listadoEventosCompeticion(int id){
        return eventoDAO.listadoEventosCompeticion(id);
    }
    
    /* Dado el id de una competici�n, devuelve la lista de eventos de dicha competici�n cuyas 
       entradas se encuentran a a venta y no est�n compradas */
    public List<Evento> eventosCompeticion(int id){
        List<Evento> eventos = new ArrayList();
        List<Evento> eventosEntradaVenta = eventoDAO.eventosCompeticion(id);
        boolean comprada = false;
        
        for (Evento evento : eventosEntradaVenta) {
            List<Entrada> entradas = entradaDAO.entradasEvento(evento.getIdEvento());
            for (Entrada entrada : entradas) {
                if (entrada.isComprada() == false) {
                    comprada = true;
                }
            }
            if (comprada == true) {
                eventos.add(evento);
            }
            comprada=false;
        }
        
        return eventos;
    }
    
    /* Dado el id de un deporte, devuelve la lista de eventos de dicho deporte cuyas entradas 
       se encuentran a la venta y no est�n compradas */
    public List<Evento> eventosDeporte(int id) {

        List<Evento> eventos = new ArrayList();
        List<Evento> eventosEntradaVenta = eventoDAO.eventosDeporte(id);
        boolean comprada = false;

        for (Evento evento : eventosEntradaVenta) {
            List<Entrada> entradas = entradaDAO.entradasEvento(evento.getIdEvento());
            for (Entrada entrada : entradas) {
                if (entrada.isComprada() == false) {
                    comprada = true;
                }
            }
            if (comprada == true) {
                eventos.add(evento);
            }
            comprada=false;
        }

        return eventos;
    }

    //Devuelve los eventos del sistema que contienen entradas y no est�n a�n a la venta
    public List<Evento> eventosEntradasNoPuestasEnVenta() {
        List<Evento> eventos = eventoDAO.eventosEntradasNoPuestasEnVenta();
        List<Evento> devueltos = new ArrayList();
        Long num;
        
        for(Evento evento:eventos){
            num=entradaDAO.numEntradasEvento(evento.getIdEvento());
            
            //Si el evento tiene entradas lo a�ado
            if(num!=0){
                devueltos.add(evento);
            }
        }
        
        return devueltos;
    }
    
    //Devuelve los eventos del sistema cuyas entradas est�n a la venta
    public List<Evento> eventosEntradasPuestasEnVenta() {
        return eventoDAO.eventosEntradasPuestasEnVenta();
    }
    
    /* Dado un criterio de b�squeda, devuelve los eventos que coinciden con �l y aquellos que contienen entradas 
    que no est�n compradas */
    public List<Evento> buscarEventos(String criterio){
        //Eventos que coinciden con el criterio de b�squeda
        List<Evento> eventos = eventoDAO.buscarEventos(criterio);
        List<Evento> devueltos = new ArrayList();
        
        //De los eventos anteriores hay que devolver aquellos cuyas entradas est�n a la venta y a�n no est�n compradas
        for (Evento evento : eventos) {
            if (evento.isVendida() == true) {
                List<Entrada> entradas = entradaDAO.entradasEvento(evento.getIdEvento());
                for (Entrada entrada : entradas) {
                    if (entrada.isComprada() == false) {
                        devueltos.add(evento);
                        return devueltos;
                    }
                }
            }
        }
        return null;
    }
}

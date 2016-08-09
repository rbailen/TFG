package es.ujaen.tfg.controladores;

import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import es.ujaen.tfg.servicios.ServicioEntrada;
import es.ujaen.tfg.servicios.ServicioEvento;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/entradas")
@Component
public class ControladorEntradas {
    
    @Autowired
    private ServicioEntrada servicioEntrada;
    
    @Autowired
    private ServicioEvento servicioEvento;
    
    private ControladorEntradas(){
        
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
    
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/listadoEntradas"}, method = RequestMethod.GET)
    public String listadoEntradas(ModelMap model) {
        List<Entrada> entradas = servicioEntrada.entradas();
        model.addAttribute("entradas", entradas);
        
        List<Entrada> entradasCompradas = servicioEntrada.listadoEntradasCompradas();
        model.addAttribute("entradasCompradas", entradasCompradas);
        
        List<Evento> eventos = servicioEvento.eventosEntradasNoPuestasEnVenta();
        model.addAttribute("eventos", eventos);

        return "admin/entradas/listado";
    }
    
    @RequestMapping(value = "/altaEntrada", method = RequestMethod.GET)
    public String altaEntradaForm(ModelMap model, HttpServletRequest request) {
        model.addAttribute("entrada", new Entrada());
        
        //Devuelve el listado de eventos posibles para una entrada
        List<Evento> eventos = servicioEvento.listadoEventos();
        
        if (eventos.isEmpty()) {
            //Añadimos en la vista que tenemos que introducir el evento
            request.getSession().setAttribute("mensaje6", "Para agregar una entrada, debe previamente disponer de eventos");
            return "redirect:/eventos/altaEvento";
            
        } else {
            
            model.addAttribute("eventos", eventos);
             
            return "admin/entradas/alta";
        }
    }

    @RequestMapping(value = "/altaEntrada", method = RequestMethod.POST)
    public String altaEntrada(@ModelAttribute("entrada") @Valid Entrada entrada, BindingResult result, ModelMap model) {
        String view = "redirect:listadoEntradas"; //default view
        
        //Validamos el evento asociado a la entrada
        if (entrada.getEvento().getIdEvento()== 0) {
            result.rejectValue("evento", "error.evento.camporequerido");
        }

        if (!result.hasErrors()) {
            servicioEntrada.altaEntrada(entrada);
            model.clear();
            
        } else {
            //Devuelve el listado de eventos posibles para una entrada
            List<Evento> eventos = servicioEvento.listadoEventos();
            model.addAttribute("eventos", eventos);
            
            view = "admin/entradas/alta";
        }
        
        return view;
    }
    
    @RequestMapping(value = "/borraEntrada/{id}", method = RequestMethod.GET)
    public String borraEntrada(@PathVariable(value = "id") Integer id, ModelMap model) {
        servicioEntrada.bajaEntrada(id);
            
        model.clear();
        return "redirect:/entradas/listadoEntradas";
    }

    @RequestMapping(value = "/editaEntrada/{id}", method = RequestMethod.GET)
    public String editaFormEntrada(@PathVariable(value = "id") Integer id, ModelMap model) {
        Entrada entrada = servicioEntrada.buscarEntrada(id);

        if ((entrada != null)&&(entrada.isComprada()==false)) {
            model.addAttribute("entrada", entrada);

            //Devuelve el listado de eventos posibles para una entrada
            List<Evento> eventos = servicioEvento.listadoEventos();
            model.addAttribute("eventos", eventos);

            return "admin/entradas/edita";
        } else {
            return listadoEntradas(model);
        }
    }
    
    @RequestMapping(value = "/editaEntrada/{id}", method = RequestMethod.POST)
    public String editaEntrada(@ModelAttribute("entrada") @Valid Entrada entrada, BindingResult result, ModelMap model) {
        String view = "redirect:/entradas/listadoEntradas";
        
        if (!result.hasErrors()) {
            servicioEntrada.editaEntrada(entrada);
            model.clear();
            
        } else {
            //Devuelve el listado de eventos posibles para una entrada
            List<Evento> eventos = servicioEvento.listadoEventos();
            model.addAttribute("eventos", eventos);
            
            view = "admin/entradas/edita";
        }
        
        return view;
    }
     
    @RequestMapping(value = "/venta", method = RequestMethod.GET)
    public String activarVentaEntradasForm(ModelMap model, HttpServletRequest request) {
        List<Evento> eventos = servicioEvento.eventosEntradasNoPuestasEnVenta();
        
        if (!eventos.isEmpty()) {
            model.addAttribute("entrada", new Entrada());
            model.addAttribute("eventos", eventos);
            return "admin/entradas/ventaEntradas";
        }
        
        return listadoEntradas(model);
    }
    
    @RequestMapping(value = "/venta", method = RequestMethod.POST)
    public String activarVentaEntradas(@ModelAttribute("entrada") Entrada entrada, ModelMap model, BindingResult result) {
        
        if (entrada.getEvento().getIdEvento() == 0) {
            result.rejectValue("evento", "error.evento.camporequerido");
            List<Evento> eventos = servicioEvento.eventosEntradasNoPuestasEnVenta();
            model.addAttribute("eventos", eventos);
            return "admin/entradas/ventaEntradas";
        }
        
        /* Una vez se han editado las entradas se ponen a la venta */
        Evento evento = servicioEvento.buscarEvento(entrada.getEvento().getIdEvento());
        evento.setVendida(true);
        servicioEvento.editaEvento(evento);
        
        model.clear();
        return "redirect:/entradas/listadoEntradas"; 
    }
    
}

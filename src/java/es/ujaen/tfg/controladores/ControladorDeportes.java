package es.ujaen.tfg.controladores;

import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Evento;
import es.ujaen.tfg.modelos.Participante;
import es.ujaen.tfg.servicios.ServicioDeporte;
import es.ujaen.tfg.servicios.ServicioEvento;
import es.ujaen.tfg.servicios.ServicioParticipante;
import java.util.ArrayList;
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
@RequestMapping("/deportes")
@Component
public class ControladorDeportes {
    
    @Autowired
    private ServicioDeporte servicio;
    
    @Autowired
    private ServicioEvento servicioEvento;
    
    @Autowired
    private ServicioParticipante servicioParticipante;
    
    public ControladorDeportes(){
        
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
    
    @RequestMapping(value = {"", "/listadoDeportes"}, method = RequestMethod.GET)
    public String listadoDeportes(ModelMap model) {
        List<Deporte> deportes = servicio.deportes();
        model.addAttribute("deportes", deportes);
        return "admin/deportes/listado";
    }

    @RequestMapping(value = "/altaDeporte", method = RequestMethod.GET)
    public String altaDeporteForm(ModelMap model) {
        model.addAttribute("deporte", new Deporte());
        return "admin/deportes/alta";
    }

    @RequestMapping(value = "/altaDeporte", method = RequestMethod.POST)
    public String altaDeporte(@ModelAttribute("deporte") @Valid Deporte deporte, BindingResult result, ModelMap model, HttpServletRequest request) {
        String view = "redirect:listadoDeportes"; //default view
        
        if (!result.hasErrors()) {
            servicio.altaDeporte(deporte);
           
            //Eliminamos los mensajes que hay en deportes
            request.getSession().setAttribute("mensaje","");
            request.getSession().setAttribute("mensaje2","");
            request.getSession().setAttribute("mensaje3","");
            model.clear();
            
        } else {
            view = "admin/deportes/alta";
        }
        return view;
    }
    
    @RequestMapping(value = "/borraDeporte/{id}", method = RequestMethod.GET)
    public String borraDeporte(@PathVariable(value = "id") Integer id, ModelMap model) {
        servicio.bajaDeporte(id);
        model.clear();
        return "redirect:/deportes/listadoDeportes";
    }

    @RequestMapping(value = "/editaDeporte/{id}", method = RequestMethod.GET)
    public String editaFormDeporte(@PathVariable(value = "id") Integer id, ModelMap model) {
        Deporte deporte = servicio.buscarDeporte(id);
        
        if (deporte != null) {
            model.addAttribute("deporte", deporte);
            return "admin/deportes/edita";
        } else {
            return listadoDeportes(model);
        }
    }

    @RequestMapping(value = "/editaDeporte/{id}", method = RequestMethod.POST)
    public String editaDeporte(@ModelAttribute("deporte") @Valid Deporte deporte, BindingResult result, ModelMap model) {
        String view = "redirect:/deportes/listadoDeportes";
        
        if (!result.hasErrors()) {
            servicio.editaDeporte(deporte);
            model.clear();
        } else {
            view = "admin/deportes/edita"; 
        }
        return view;
    }
    
    /* ---------- MÉTODOS DEL CLIENTE ---------- */
    
    @RequestMapping(value = {"", "/listado"}, method = RequestMethod.GET)
    public String listadoDeportesCliente(ModelMap model) {
        List<Deporte> deportes = servicio.deportesEntradas();
        model.addAttribute("deportes", deportes);
        return "clientes/deportes/listado";
    }
    
    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = {"/verDeporte/{id}"}, method = RequestMethod.GET)
    public String verDeporte(@PathVariable(value = "id") Integer id, ModelMap model) {
        Deporte deporte = servicio.buscarDeporte(id);
        
        if (deporte != null) {
            List<Evento> eventos = servicioEvento.buscarEventos(deporte.getNombre());
            if (eventos != null) {
                for (Evento evento : eventos) {
                    model.addAttribute("deporte", evento.getDeporte());
                    return "clientes/deportes/visualiza";
                }
            }
        }
        
        return "redirect:/deportes/listado";
    }
    
    /**
     *
     * @param id
     * @param denom
     * @param model
     * @return
     */
    @RequestMapping(value = {"/ver{denominacion}/{id}"}, method = RequestMethod.GET)
    public String verCategoria(@PathVariable(value = "id") Integer id, @PathVariable(value = "denominacion") String denom, ModelMap model) {
        Deporte deporte = servicio.buscarDeporte(id);
        List<Deporte> deportes = servicio.deportes();

        if (deporte != null) {
            List<Evento> eventos = servicioEvento.buscarEventos(deporte.getNombre());
            if (eventos != null) {
                for (Deporte sport : deportes) {
                    if ((sport.getDenominacionParticipante().equals(denom)) && (sport.getDenominacionParticipante().equals(deporte.getDenominacionParticipante()))) {
                        model.addAttribute("deporte", deporte);

                        //Devuelve el listado de participantes de un deporte
                        List<Participante> participantes = servicioParticipante.participantesDeporte(id);
                        model.addAttribute("participantes", participantes);

                        return "clientes/deportes/categorias";
                    }
                }
            }
        }

        return "redirect:/deportes/listado";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = {"/verCompeticiones/{id}"}, method = RequestMethod.GET)
    public String verCompeticiones(@PathVariable(value = "id") Integer id, ModelMap model) {
        Deporte deporte = servicio.buscarDeporte(id);
        
        if (deporte != null) {
            List<Evento> eventos = servicioEvento.buscarEventos(deporte.getNombre());
            if (eventos != null) {
                for (Evento evento : eventos) {
                    List<Competicion> comp = new ArrayList();
                    comp.add(evento.getCompeticion());
                    
                    model.addAttribute("deporte", deporte);
                    model.addAttribute("competiciones", comp);
                    return "clientes/deportes/competiciones";
                }
            }
        }
        return "redirect:/deportes/listado";
        
    }

}

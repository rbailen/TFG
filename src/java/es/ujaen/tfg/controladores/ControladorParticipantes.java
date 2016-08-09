package es.ujaen.tfg.controladores;

import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Participante;
import es.ujaen.tfg.servicios.ServicioDeporte;
import es.ujaen.tfg.servicios.ServicioParticipante;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/participantes")
public class ControladorParticipantes {
    
    @Autowired
    private ServicioParticipante servicio;
    
    @Autowired
    private ServicioDeporte servicioDeportes;
     
    public ControladorParticipantes(){
        
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
    
     /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/listadoParticipantes"}, method = RequestMethod.GET)
    public String listadoParticipantes(ModelMap model) {
        List<Participante> participantes = servicio.listadoParticipantes();
        model.addAttribute("participantes", participantes);
        return "admin/participantes/listado";
    }
    
    @RequestMapping(value = "/altaParticipante", method = RequestMethod.GET)
    public String altaParticipanteForm(ModelMap model, HttpServletRequest request) {
        model.addAttribute("participante", new Participante());

        //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
        List<Deporte> deportes = servicioDeportes.deportes();
        
        //Si no hay deportes hacemos un redirect para dar de alta primero un deporte
        if (deportes.isEmpty()) {
            //Añadimos en la vista que tenemos que introducir el deporte
            request.getSession().setAttribute("mensaje2","Para agregar participantes, debe previamente disponer de deportes");
            return "redirect:/deportes/altaDeporte";
        
        //Si hay deportes, podemos dar de alta nuevos participantes
        } else { 
            model.addAttribute("deportes", deportes);
            return "admin/participantes/alta";
        }
    }

    @RequestMapping(value = "/altaParticipante", method = RequestMethod.POST)
    public String altaParticipante(@ModelAttribute("participante") @Valid Participante participante, BindingResult result, ModelMap model, HttpServletRequest request) {
        String view = "redirect:listadoParticipantes"; //default view

        //Validamos el deporte asociado al participante
        if (participante.getDeporte().getIdDeporte() == 0) {
            result.rejectValue("deporte", "error.deporte.camporequerido");
        }
        
        if (!result.hasErrors()) {
            servicio.altaParticipante(participante);
            //Eliminamos el mensaje que hay en participantes
            request.getSession().setAttribute("mensaje5", "");
            model.clear();
            
        } else {
            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al participante
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);
            
            view = "admin/participantes/alta";
        }
        
        return view;
    }
    
    @RequestMapping(value = "/borraParticipante/{id}", method = RequestMethod.GET)
    public String borraParticipante(@PathVariable(value = "id") Integer id, ModelMap model) {
        servicio.bajaParticipante(id);
        model.clear();
        return "redirect:/participantes/listadoParticipantes";
    }

    @RequestMapping(value = "/editaParticipante/{id}", method = RequestMethod.GET)
    public String editaFormParticipante(@PathVariable(value = "id") Integer id, ModelMap model) {
        Participante participante = servicio.buscaParticipante(id);

        if (participante != null) {
            model.addAttribute("participante", participante);

            //Devuelve el listado de deportes para editarlos
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);

            return "admin/participantes/edita";
        } else {
            return listadoParticipantes(model);
        }
    }

    @RequestMapping(value = "/editaParticipante/{id}", method = RequestMethod.POST)
    public String editaParticipante(@ModelAttribute("participante") @Valid Participante participante, BindingResult result, ModelMap model) {
        String view = "redirect:/participantes/listadoParticipantes";
       
        if (!result.hasErrors()) {
            servicio.editaParticipante(participante);
            model.clear();
        } else {
            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al participante
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);
            
            view = "admin/participantes/edita"; //ask for correct data
        }
        return view;
    }
    
}

package es.ujaen.tfg.controladores;

import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.servicios.ServicioCompeticion;
import es.ujaen.tfg.servicios.ServicioDeporte;
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
@RequestMapping("/competiciones")
public class ControladorCompeticiones {
    
    @Autowired
    private ServicioCompeticion servicio;
    
    @Autowired
    private ServicioDeporte servicioDeportes;
    
    public ControladorCompeticiones(){
        
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
    
     /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/listadoCompeticiones"}, method = RequestMethod.GET)
    public String listadoCompeticiones(ModelMap model) {
        List<Competicion> competiciones = servicio.listadoCompeticiones();
        model.addAttribute("competiciones", competiciones);
        return "admin/competiciones/listado";
    }

    @RequestMapping(value = "/altaCompeticion", method = RequestMethod.GET)
    public String altaCompeticionForm(ModelMap model, HttpServletRequest request) {
        model.addAttribute("competicion", new Competicion());
        
        //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
        List<Deporte> deportes = servicioDeportes.deportes();
        
        //Si no hay deportes hacemos un redirect para dar de alta primero un deporte
        if (deportes.isEmpty()) {
            //Añadimos en la vista que tenemos que introducir el deporte
            request.getSession().setAttribute("mensaje","Para agregar competiciones, debe previamente disponer de deportes");
            return "redirect:/deportes/altaDeporte";
        
        //Si hay deportes, podemos dar de alta nuevos participantes
        } else {
            model.addAttribute("deportes", deportes);
            return "admin/competiciones/alta";
        }
    }

    @RequestMapping(value = "/altaCompeticion", method = RequestMethod.POST)
    public String altaCompeticion(@ModelAttribute("competicion") @Valid Competicion competicion, BindingResult result, ModelMap model, HttpServletRequest request) {
        String view = "redirect:listadoCompeticiones"; //default view

        //Validamos el deporte de la competición
        if (competicion.getDeporte().getIdDeporte() == 0) {
            result.rejectValue("deporte", "error.deporte.camporequerido");
        }
        
        if (!result.hasErrors()) {
            servicio.altaCompeticion(competicion);
            //Eliminamos los mensajes que hay en competiciones
            request.getSession().setAttribute("mensaje4", "");
            model.clear();
            
        } else {
            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);
            
            view = "admin/competiciones/alta";
        }
        
        return view;
    }
    
    @RequestMapping(value = "/borraCompeticion/{id}", method = RequestMethod.GET)
    public String borraCompeticion(@PathVariable(value = "id") Integer id, ModelMap model) {
        servicio.bajaCompeticion(id);
        model.clear();
        return "redirect:/competiciones/listadoCompeticiones";
    }

    @RequestMapping(value = "/editaCompeticion/{id}", method = RequestMethod.GET)
    public String editaFormCompeticion(@PathVariable(value = "id") Integer id, ModelMap model) {
        Competicion competicion = servicio.buscaCompeticion(id);

        if (competicion != null) {
            model.addAttribute("competicion", competicion);

            //Devuelve el listado de deportes para editarlos
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);

            return "admin/competiciones/edita";
        } else {
            return listadoCompeticiones(model);
        }
    }

    @RequestMapping(value = "/editaCompeticion/{id}", method = RequestMethod.POST)
    public String editaCompeticion(@ModelAttribute("competicion") @Valid Competicion competicion, BindingResult result, ModelMap model) {
        String view = "redirect:/competiciones/listadoCompeticiones";
        
        if (!result.hasErrors()) {
            servicio.editaCompeticion(competicion);
            model.clear();
        } else {
            
            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);
            
            view = "admin/competiciones/edita";
        }
        return view;
    }

}

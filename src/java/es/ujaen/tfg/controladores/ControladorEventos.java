package es.ujaen.tfg.controladores;

import es.ujaen.tfg.editor.ParticipanteEditor;
import es.ujaen.tfg.modelos.Competicion;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Evento;
import es.ujaen.tfg.modelos.Pais;
import es.ujaen.tfg.modelos.Participante;
import es.ujaen.tfg.servicios.ServicioCompeticion;
import es.ujaen.tfg.servicios.ServicioDeporte;
import es.ujaen.tfg.servicios.ServicioEntrada;
import es.ujaen.tfg.servicios.ServicioEvento;
import es.ujaen.tfg.servicios.ServicioPais;
import es.ujaen.tfg.servicios.ServicioParticipante;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/eventos")
@Component
public class ControladorEventos {
    
    @Autowired
    private ServicioEvento servicio;
    
    @Autowired
    private ServicioDeporte servicioDeportes;
    
    @Autowired
    private ServicioCompeticion servicioCompeticion;
    
    @Autowired
    private ServicioParticipante servicioParticipante;
    
    @Autowired
    private ServicioPais servicioPais;
    
    @Autowired
    private ServicioEntrada servicioEntrada;
    
    private ControladorEventos(){
        
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /* Participantes */
        binder.registerCustomEditor(Participante.class, new ParticipanteEditor(this.servicioParticipante));
        
        /* Fecha */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
    
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/listadoEventos"}, method = RequestMethod.GET)
    public String listadoEventos(ModelMap model) {
        List<Evento> eventos = servicio.listadoEventos();
        model.addAttribute("eventos", eventos);
        
        List<Evento> eventosEntradasPuestasVenta = servicio.eventosEntradasPuestasEnVenta();
        model.addAttribute("eventosEntradasPuestasVenta", eventosEntradasPuestasVenta);
        
        return "admin/eventos/listado";
    }
    
    @RequestMapping(value = "/altaEvento", method = RequestMethod.GET)
    public String altaEventoForm(ModelMap model, HttpServletRequest request) {
        
        Evento evento = new Evento();
        model.addAttribute("evento", evento);
        
        //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
        List<Deporte> deportes = servicioDeportes.deportes();
        
        //Devuelve el listado de competiciones para seleccionarlos al introducir el deporte perteneciente al evento
        List<Competicion> competiciones = servicioCompeticion.listadoCompeticiones();
        
        //Devuelve el listado de participantes para seleccionarlos al introducir el deporte perteneciente al evento
        List<Participante> participantes = servicioParticipante.listadoParticipantes();
       
        //Devuelve el listado de paises posibles para un evento
        List<Pais> paises = servicioPais.listadoPaises();
        
        if (deportes.isEmpty()) {
            //Añadimos en la vista que tenemos que introducir el deporte
            request.getSession().setAttribute("mensaje3", "Para agregar un evento, debe previamente disponer de deportes");
            return "redirect:/deportes/altaDeporte";
        } else if (competiciones.isEmpty()) {
             //Añadimos en la vista que tenemos que introducir competiciones
            request.getSession().setAttribute("mensaje4", "Para agregar un evento, debe previamente disponer de competiciones");
            return "redirect:/competiciones/altaCompeticion";
        } else if (participantes.isEmpty()) {
             //Añadimos en la vista que tenemos que introducir participantes
            request.getSession().setAttribute("mensaje5", "Para agregar un evento, debe previamente disponer de participantes");
            return "redirect:/participantes/altaParticipante";
        } else {
            
            model.addAttribute("deportes", deportes);
            model.addAttribute("competiciones", competiciones);
            model.addAttribute("participantes", participantes);
            model.addAttribute("paises", paises);
             
        //evento.preparaDatosFormEvento(model);
            return "admin/eventos/alta";
        }
    }

    @RequestMapping(value = "/altaEvento", method = RequestMethod.POST)
    public String altaEvento(@RequestParam int numEntradas, @ModelAttribute("evento") @Valid Evento evento, BindingResult result, ModelMap model, HttpServletRequest request) {
        
        /* Al crear el evento, si contiene entradas redireccionamos al listado de entradas 
        para poder editar las mismas si fuera conveniente. Si no contiene entradas conducimos
        al administrador al listado de eventos */
        String view;
        
        if (numEntradas == 0) {
            view = "redirect:/eventos/listadoEventos"; 
        }else{
            view = "redirect:/entradas/listadoEntradas"; 
        }
        
        //Validamos la fecha del evento
        if (evento.getFecha()==null) {
            result.rejectValue("fecha", "error.fecha.camporequerido");
        }
        
        //Validamos el país del evento
        if (evento.getPais().getIdPais() == 0) {
            result.rejectValue("pais", "error.pais.camporequerido");
        }

        //Validamos el deporte del evento
        if (evento.getDeporte().getIdDeporte() == 0) {
            result.rejectValue("deporte", "error.deporte.camporequerido");
        }
        
        //Validamos la competición del evento
        if (evento.getCompeticion().getIdCompeticion() == 0) {
            result.rejectValue("competicion", "error.competicion.camporequerido");
        }
        
        //Validamos los participantes del evento
        if((evento.getParticipantes()==null)||(evento.getParticipantes().size()<2)){
            result.rejectValue("participantes", "error.participantes.camporequerido");
        }

        if (!result.hasErrors()) {
            /*Al crear el evento, las entradas no se ponen a la venta, para poder editarlas si es necesario */
            evento.setVendida(false);
            
            servicio.altaEvento(evento);
            
            //Eliminamos los mensajes que hay en eventos
            request.getSession().setAttribute("mensaje6","");
            
            //Creamos las entradas indicadas en el evento
            for (int i = 1; i <= numEntradas; i++) {
                Entrada entrada = new Entrada("Entrada" + " " + i + " " + "para evento:" + " " + evento.getNombre(), 20, false, evento);
                servicioEntrada.altaEntrada(entrada);
            }
      
            model.clear();
        } else {
            
            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);

            //Devuelve el listado de competiciones para seleccionarlos al introducir el deporte perteneciente al evento
            List<Competicion> competiciones = servicioCompeticion.listadoCompeticiones();
            model.addAttribute("competiciones", competiciones);

            //Devuelve el listado de participantes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Participante> participantes = servicioParticipante.listadoParticipantes();
            model.addAttribute("participantes", participantes);

            //Devuelve el listado de paises posibles para un evento
            List<Pais> paises = servicioPais.listadoPaises();
            model.addAttribute("paises", paises);

            view = "admin/eventos/alta";
        }
        
        return view;
    }
    
    @RequestMapping(value = "/borraEvento/{id}", method = RequestMethod.GET)
    public String borraEvento(@PathVariable(value = "id") Integer id, ModelMap model) {
        servicio.bajaEvento(id);
        model.clear();
        return "redirect:/eventos/listadoEventos";
    }

    @RequestMapping(value = "/editaEvento/{id}", method = RequestMethod.GET)
    public String editaFormEvento(@PathVariable(value = "id") Integer id, ModelMap model) {
        Evento evento = servicio.buscarEvento(id);

        if ((evento != null)&&(evento.isVendida()==false)) {
            model.addAttribute("evento", evento);

            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);

            //Devuelve el listado de competiciones para seleccionarlos al introducir el deporte perteneciente al evento
            List<Competicion> competiciones = servicioCompeticion.listadoCompeticiones();
            model.addAttribute("competiciones", competiciones);

            //Devuelve el listado de participantes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Participante> participantes = servicioParticipante.listadoParticipantes();
            model.addAttribute("participantes", participantes);

            //Devuelve el listado de paises posibles para un evento
            List<Pais> paises = servicioPais.listadoPaises();
            model.addAttribute("paises", paises);

            return "admin/eventos/edita";
        } else {
            return listadoEventos(model);
        }
    }
    
    @RequestMapping(value = "/editaEvento/{id}", method = RequestMethod.POST)
    public String editaEvento(@ModelAttribute("evento") @Valid Evento evento, BindingResult result, ModelMap model) {
        String view = "redirect:/eventos/listadoEventos";
        
        //Validamos la fecha del evento
        if (evento.getFecha()==null) {
            result.rejectValue("fecha", "error.fecha.camporequerido");
        }
        
        //Validamos los participantes del evento
        if(evento.getParticipantes()==null){
            result.rejectValue("participantes", "error.participantes.camporequerido");
        }

        if (!result.hasErrors()) {
            
            //Actualizar el evento
            servicio.editaEvento(evento);
            
            //Actualizar las entradas del evento y la descripción
            List<Entrada> entradas = servicioEntrada.entradasEvento(evento.getIdEvento());
            
            for(Entrada entrada: entradas){
                entrada.setDescripcion("Entrada" + " " + entrada.getIdEntrada() + " " + "para evento:" + " " + evento.getNombre());
                servicioEntrada.editaEntrada(entrada);
            }
            
            model.clear();
        } else {

            //Devuelve el listado de deportes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Deporte> deportes = servicioDeportes.deportes();
            model.addAttribute("deportes", deportes);

            //Devuelve el listado de competiciones para seleccionarlos al introducir el deporte perteneciente al evento
            List<Competicion> competiciones = servicioCompeticion.listadoCompeticiones();
            model.addAttribute("competiciones", competiciones);

            //Devuelve el listado de participantes para seleccionarlos al introducir el deporte perteneciente al evento
            List<Participante> participantes = servicioParticipante.listadoParticipantes();
            model.addAttribute("participantes", participantes);

            //Devuelve el listado de paises posibles para un evento
            List<Pais> paises = servicioPais.listadoPaises();
            model.addAttribute("paises", paises);

            view = "admin/eventos/edita";
        }
        
        return view;
    }
    
    @RequestMapping(value = {"/visualizaEvento/{id}"}, method = RequestMethod.GET)
    public String visualizaEvento(@PathVariable(value = "id") Integer id, ModelMap model) {
        model.addAttribute("evento", servicio.buscarEvento(id));
        model.addAttribute("numEntradas", servicioEntrada.numEntradasEvento(id));
        return "admin/eventos/visualiza";
    }
    
    /* ---------- MÉTODOS DEL CLIENTE ---------- */
    
    @RequestMapping(value = {"/verEventosParticipante/{id}"}, method = RequestMethod.GET)
    public String verEventosParticipante(@PathVariable(value = "id") Integer id, ModelMap model) {
        Participante participante = servicioParticipante.buscaParticipante(id);

        if (participante != null) {
            List<Evento> eventos = servicio.buscarEventos(participante.getNombre());
            if (eventos != null) {
                model.addAttribute("eventosParticipante", servicio.eventosParticipante(id));
                model.addAttribute("participanteEvento", participante);
                model.addAttribute("deporte", servicioDeportes.deporteParticipante(id));
                return "clientes/eventos/eventosParticipante";
            }
        }
        
        return "redirect:/deportes/listado";
    }
    
    @RequestMapping(value = {"/verEventosCompeticion/{id}"}, method = RequestMethod.GET)
    public String verEventosCompeticion(@PathVariable(value = "id") Integer id, ModelMap model) {
        Competicion competicion = servicioCompeticion.buscaCompeticion(id);

        if (competicion != null) {
            List<Evento> eventos = servicio.buscarEventos(competicion.getNombre());
            if (eventos != null) {
                model.addAttribute("eventosCompeticion", servicio.eventosCompeticion(id));
                model.addAttribute("competicionEvento", competicion);
                return "clientes/eventos/eventosCompeticion";
            }
        }

        return "redirect:/deportes/listado";
    }
    
    @RequestMapping(value = {"/verEventosDeporte/{id}"}, method = RequestMethod.GET)
    public String verEventosDeporte(@PathVariable(value = "id") Integer id, ModelMap model) {
        Deporte deporte = servicioDeportes.buscarDeporte(id);

        if (deporte != null) {
            List<Evento> eventos = servicio.buscarEventos(deporte.getNombre());
            if (eventos != null) {
                model.addAttribute("eventosDeporte", servicio.eventosDeporte(deporte.getIdDeporte()));
                model.addAttribute("deporte", deporte);
                return "clientes/eventos/eventosDeporte";
            }
        }
        
        return "redirect:/deportes/listado";

    }
    
    @RequestMapping(value = {"", "/buscarEventos"}, method = RequestMethod.POST)
    public String buscarEventos(ModelMap model, HttpServletRequest request) {
        String criterio = request.getParameter("criterio");

        if (!criterio.equals("")) {
            List<Evento> eventos = servicio.buscarEventos(criterio);
            model.addAttribute("eventos", eventos);
            return "clientes/eventos/busqueda";
        } else {
            model.addAttribute("criterio", "Introduzca un criterio de búsqueda");
            return "clientes/eventos/busqueda";
        }
    }
    
}

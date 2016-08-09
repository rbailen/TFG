package es.ujaen.tfg.controladores;

import es.ujaen.tfg.editor.EntradaEditor;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Usuario;
import es.ujaen.tfg.servicios.ServicioCompra;
import es.ujaen.tfg.servicios.ServicioDeporte;
import es.ujaen.tfg.servicios.ServicioEntrada;
import es.ujaen.tfg.servicios.ServicioUsuario;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
@RequestMapping("/compras")
@Component
public class ControladorCompras {
    
    @Autowired
    private ServicioCompra servicio;
    
    @Autowired
    private ServicioEntrada servicioEntrada;
    
    @Autowired
    private ServicioUsuario servicioUsuario;
    
    @Autowired
    private ServicioDeporte servicioDeporte;
    
    public ControladorCompras(){
        
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Entrada.class, new EntradaEditor(this.servicioEntrada));
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
     /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/listadoCompras"}, method = RequestMethod.GET)
    public String listadoCompras(ModelMap model) {
        List<Compra> compras= servicio.listadoCompras();
        model.addAttribute("compras", compras);
        return "admin/compras/listado";
    }
    
    @RequestMapping(value = {"/visualizaCompra/{id}"}, method = RequestMethod.GET)
    public String visualizaCompra(@PathVariable(value = "id") Integer id, ModelMap model) {
        Compra compra = servicio.buscarCompra(id);

        if (compra != null) {
            model.addAttribute("compra", servicio.buscarCompra(id));

            List<Entrada> entradasCompra = servicio.entradasCompra(id);
            model.addAttribute("entradasCompra", entradasCompra);

            return "admin/compras/visualiza";
        } else {
            return listadoCompras(model);
        }
    }

    /* ---------- MÉTODOS DEL CLIENTE ---------- */
    
    @RequestMapping(value = {"/altaCompra/{id}"}, method = RequestMethod.GET)
    public String altaFormCompra(@PathVariable(value = "id") Integer id, ModelMap model) {
        model.addAttribute("entradas", servicioEntrada.entradasEvento(id));
        model.addAttribute("compra", new Compra());
        
        return "clientes/entradas/listado";
    }

    @RequestMapping(value = "/altaCompra/{id}", method = RequestMethod.POST)
    public String altaCompra(@PathVariable(value = "id") Integer id, @ModelAttribute("compra") @Valid Compra compra, ModelMap model, BindingResult result) {

        //Validamos que la compra contenga entradas
        if(compra.getEntradas()==null){
            result.rejectValue("entradas", "error.entradas.camporequerido");
            model.addAttribute("entradas", servicioEntrada.entradasEvento(id));
            return "clientes/entradas/listado";
        }
        
        //Devuelve el usuario que está logueado y que compra las entradas (usuario)
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usuario = userDetails.getUsername();
        
        Usuario user = servicioUsuario.buscarPorUsuario(usuario);
        compra.setUsuario(user);
        
        //Suma del precio de las entradas
        double total=0;
        
        for(Entrada entrada: compra.getEntradas()){
            //Calculamos el precio de las entradas
            total+=entrada.getPrecio();
            //Indicamos que las entradas han sido compradas
            entrada.setComprada(true);
            servicioEntrada.editaEntrada(entrada);
        }
        compra.setTotal(total);
        
        servicio.altaCompra(compra);
        
        model.clear();
        
        return "redirect:/compras/verCompra/" + compra.getIdCompra();
    }
    
    @RequestMapping(value = {"/verCompra/{id}"}, method = RequestMethod.GET)
    public String verCompra(@PathVariable(value = "id") Integer id, ModelMap model, @ModelAttribute("user") Usuario user) {

        int idBuscado = 0;

        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();

        if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }

        Usuario usuario = servicioUsuario.buscarUsuario(idBuscado);
        Compra compra = servicio.buscarCompra(id);
        List<Entrada> entradasCompra = servicio.entradasCompra(id);

        /* Si el usuario que está logueado es el mismo que el que ha realizado la 
        compra y no está ya pagada puede confirmarla */
        
        if (usuario.getUsuario().equals(compra.getUsuario().getUsuario()) &&(compra.getFechaPago()==null)) {
            model.addAttribute("compra", compra);
            model.addAttribute("entradasCompra", entradasCompra);
            return "clientes/compras/confirmacion";
        }else{
            return "forbidden";
        }
    }
    
    @RequestMapping(value = {"/historico/{id}"}, method = RequestMethod.GET)
    public String comprasRealizadasCliente(@PathVariable(value = "id") Integer id, ModelMap model, @ModelAttribute("user") Usuario user) {
        
        int idBuscado=0;
        
        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();

        if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }
        
        Usuario usuario = servicioUsuario.buscarUsuario(idBuscado);
        
        model.addAttribute("compras", servicio.comprasRealizadasCliente(usuario.getIdUsuario()));
        
        return "clientes/compras/historico";
    }
    
    @RequestMapping(value = {"/visualiza/{id}"}, method = RequestMethod.GET)
    public String visualizaCompraCliente(@PathVariable(value = "id") Integer id, ModelMap model, @ModelAttribute("user") Usuario user) {
        
        int idBuscado=0;
        
        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();

        if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }
        
        Usuario usuario = servicioUsuario.buscarUsuario(idBuscado);
        Compra compra = servicio.buscarCompra(id);
        
        if (usuario.getUsuario().equals(compra.getUsuario().getUsuario())) {
            model.addAttribute("compra", compra);

            List<Entrada> entradasCompra = servicio.entradasCompra(compra.getIdCompra());
            model.addAttribute("entradasCompra", entradasCompra);
            return "clientes/compras/visualiza";
        } else {
            return "forbidden";
        }
    }
    
    @RequestMapping(value = "/borraCompra/{id}", method = RequestMethod.GET)
    public String borraCompra(@PathVariable(value = "id") Integer id, ModelMap model) {

        /* Obtenemos el evento de la compra*/
        //Evento evento = servicio.obtenerEvento(id);
        
        //Al borrar la compra, hay que volver a poner en venta las entradas de la misma
        List<Entrada> entradas = servicio.entradasCompra(id);
        
        for(Entrada entrada: entradas){
            entrada.setComprada(false);
            servicioEntrada.editaEntrada(entrada);
        }
        
        servicio.bajaCompra(id);
        
        model.clear();
        //return "redirect:/compras/altaCompra/"+evento.getIdEvento();
        return "redirect:/inicio";
    }
    
    /* ------ PRUEBAS DE PAYPAL -----*/
    
    @RequestMapping(value = {"", "/formularioPrueba"}, method = RequestMethod.GET)
    public String formularioPruebaForm(ModelMap model) {
        return "clientes/pruebas/formularioPrueba";
    }
    
    @RequestMapping(value = {"/confirmacionPaypalTest"}, method = RequestMethod.POST)
    public String confirmacionPaypalTest(@RequestParam int invoice, ModelMap model) {
        
         Compra compra = servicio.buscarCompra(invoice);
         Date fechaPago = new Date();
         compra.setFechaPago(fechaPago);
         servicio.editaCompra(compra);
     
        return "redirect:/compras/confirmacionPago/"+compra.getIdCompra();
    }
    
    @RequestMapping(value = {"/confirmacionPago/{id}"}, method = RequestMethod.GET)
    public String confirmacionPago(@PathVariable(value = "id") Integer id, ModelMap model, @ModelAttribute("user") Usuario user) {

        int idBuscado = 0;

        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();

        if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }

        Usuario usuario = servicioUsuario.buscarUsuario(idBuscado);
        Compra compra = servicio.buscarCompra(id);

        if (compra != null) {

            //4 días para imprimir las entradas
            int day = 4;
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(compra.getFechaPago());
            calendarDate.add(Calendar.DATE, day);

            if (usuario.getUsuario().equals(compra.getUsuario().getUsuario())) {
                Date comparacion = new Date();
                if (calendarDate.getTime().after(comparacion)) {

                    //Pasamos a la vista la compra y las entradas pertenecientes a ésta
                    model.addAttribute("compra", compra);

                    List<Entrada> entradasCompra = servicio.entradasCompra(compra.getIdCompra());
                    model.addAttribute("entradasCompra", entradasCompra);

                    model.addAttribute("imprimir", "Dispone de 4 días desde la fecha de pago para imprimir las entradas");

                    //Carrusel
                    List<Deporte> deportes = servicioDeporte.deportesEntradas();
                    model.addAttribute("deportes", deportes);

                    return "clientes/pruebas/confirmacionPago";
                }
            } else {
                return "forbidden";
            }
            return "redirect:/deportes/listado";
        } else {
            return "redirect:/deportes/listado";
        }
    }
    
    /* ------ FIN PRUEBAS DE PAYPAL -----*/
    
    @RequestMapping(value = {"/imprimir/{id}"}, method = RequestMethod.GET)
    public String imprimirEntradas(@PathVariable(value = "id") Integer id, ModelMap model, @ModelAttribute("user") Usuario user) {

        int idBuscado = 0;

        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();

        if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }

        Usuario usuario = servicioUsuario.buscarUsuario(idBuscado);
        Compra compra = servicio.buscarCompra(id);

        if (compra != null) {

            //4 días para imprimir las entradas
            int day = 4;
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(compra.getFechaPago());
            calendarDate.add(Calendar.DATE, day);

            if (usuario.getUsuario().equals(compra.getUsuario().getUsuario())) {
                Date comparacion = new Date();
                if (calendarDate.getTime().after(comparacion)) {

                    //Pasamos a la vista la compra y las entradas pertenecientes a ésta
                    model.addAttribute("compra", compra);

                    List<Entrada> entradasCompra = servicio.entradasCompra(compra.getIdCompra());
                    model.addAttribute("entradasCompra", entradasCompra);

                    model.addAttribute("imprimir", "Dispone de 4 días desde la fecha de pago para imprimir las entradas");

                    return "clientes/compras/impresion";
                }
            } else {
                return "forbidden";
            }
            
            model.addAttribute("compra", compra);
            model.addAttribute("mensaje", "Ya no puede imprimir las entradas para este evento");
            return "clientes/compras/impresion";
            
        } else {
            return "redirect:/deportes/listado";
        }
    }
          
}

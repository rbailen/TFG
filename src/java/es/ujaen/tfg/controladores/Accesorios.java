package es.ujaen.tfg.controladores;

import es.ujaen.tfg.modelos.Deporte;
import es.ujaen.tfg.modelos.Usuario;
import es.ujaen.tfg.servicios.ServicioDeporte;
import es.ujaen.tfg.servicios.ServicioUsuario;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class Accesorios {
    
    @Autowired
    ServicioUsuario servicio;
    
    @Autowired
    ServicioDeporte servicioDeporte;

    //Login administrador
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String inicioAdmin(ModelMap model) {
        String usuario;
        
        //Devuelve un usuario autentificado (usuario)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            usuario = "desconocido"; 
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            usuario = userDetails.getUsername();
        }
        
        Usuario user = servicio.buscarPorUsuario(usuario);
        model.addAttribute("user", user);
        
        return "admin/menu";

    }
    
    //Login usuario
    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicioUsuario(ModelMap model) {
        String usuario;
        
        //Devuelve un usuario autentificado (usuario)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            usuario = "desconocido"; 
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            usuario = userDetails.getUsername();
        }
        
        Usuario user = servicio.buscarPorUsuario(usuario);
        model.addAttribute("user", user);
        
        //Carrrusel de deportes
        List<Deporte> carousel = servicioDeporte.deportesEntradas();
        model.addAttribute("deportes", carousel);
        
        return "clientes/menu";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("cliente", new Usuario());
        return "index";

    }

    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        return login(model);

    }
    
    @RequestMapping(value={ "/", "/logout"}, method = RequestMethod.GET)
    public String logout(ModelMap model) {
        model.addAttribute("cliente", new Usuario());
        return "index";

    }
    
    @RequestMapping(value = "/registro", method = RequestMethod.POST)
    public String registroUsuario(@ModelAttribute("cliente") @Valid Usuario cliente, BindingResult result, ModelMap model, HttpServletRequest request) {
        String view = "redirect:login"; 
        
        //Buscamos al usuario introducido para ver si ya existe alguno con el mismo nombre de usuario
        if (cliente.getUsuario().length() != 0) {
            Usuario buscado = servicio.buscarPorUsuario(cliente.getUsuario());

            if (buscado != null) {
                result.rejectValue("usuario", "error.cliente.usuarioexistente");
            }
        }
        
        if (!result.hasErrors()) {
            servicio.altaUsuario(cliente);
            model.clear();
            request.getSession().setAttribute("mensaje","Se ha registrado correctamente");
        } else {
            view = "index";
        }
        return view;
    }

}

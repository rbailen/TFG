package es.ujaen.tfg.controladores;

import es.ujaen.tfg.modelos.Usuario;
import es.ujaen.tfg.servicios.ServicioUsuario;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
@RequestMapping("/usuarios")
@Component
public class ControladorUsuarios {
    
    @Autowired
    private ServicioUsuario servicio;
    
    public ControladorUsuarios(){
        
    }
    
    /* ---------- MÉTODOS DEL ADMINISTRADOR ---------- */
    
     /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"", "/listadoClientes"}, method = RequestMethod.GET)
    public String listadoUsuarios(ModelMap model) {
        List<Usuario> clientes = servicio.listadoUsuarios();
        model.addAttribute("clientes", clientes);
        return "admin/usuarios/listado";
    }

    @RequestMapping(value = "/altaCliente", method = RequestMethod.GET)
    public String altaUsuarioForm(ModelMap model) {
        model.addAttribute("cliente", new Usuario());
        return "admin/usuarios/alta";
    }

    @RequestMapping(value = "/altaCliente", method = RequestMethod.POST)
    public String altaUsuario(@ModelAttribute("cliente") @Valid Usuario cliente, BindingResult result, ModelMap model) {
        String view = "redirect:listadoClientes";
        
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
        } else {
            view = "admin/usuarios/alta";
        }
        return view;
    }
    
    @RequestMapping(value = "/editaCliente/{id}", method = RequestMethod.GET)
    public String editaFormCliente(@PathVariable(value = "id") Integer id, ModelMap model) {
        Usuario usuario = servicio.buscarUsuario(id);
        
        if (usuario != null) {
            model.addAttribute("cliente", usuario);
            return "admin/usuarios/edita";
        } else {
            return listadoUsuarios(model);
        }
    }
    
    @RequestMapping(value = "/editaCliente/{id}", method = RequestMethod.POST)
    public String editaCliente(@ModelAttribute("cliente") @Valid Usuario cliente, BindingResult result, ModelMap model, @ModelAttribute("user") Usuario user) {
        String view = "redirect:/usuarios/listadoClientes";
        
        //Buscamos al usuario introducido para ver si ya existe alguno con el mismo nombre de usuario
        if (cliente.getUsuario().length() != 0) {
            Usuario buscado = servicio.buscarPorUsuario(cliente.getUsuario());

            if ((cliente.getIdUsuario()!=buscado.getIdUsuario()) && (buscado != null)) {
                result.rejectValue("usuario", "error.cliente.usuarioexistente");
            }
        }
        
        if (!result.hasErrors()) {
            //Agrego el rol, ya que no es posible editarlo
            Usuario actual = servicio.buscarUsuario(cliente.getIdUsuario());
            cliente.setRol(actual.getRol());
            
            servicio.editaUsuario(cliente);
            model.clear();
        } else {
            view = "admin/usuarios/edita";
        }
        return view;
    }
    
     /* ---------- MÉTODOS DEL CLIENTE ---------- */
    
    /**
     *
     * @param id
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = {"/visualizaCliente/{id}"}, method = RequestMethod.GET)
    public String visualizaCliente(@PathVariable(value = "id") Integer id, ModelMap model,  @ModelAttribute("user") Usuario user) {
        int idBuscado=0;
        
        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();
        
        if (rol.contains("ROLE_ADMIN")) {
            idBuscado = id;
        } else if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }
        
        Usuario usuario = servicio.buscarUsuario(idBuscado);
        
        //Se actualiza la sesión si editamos el nombre de un cliente
        model.addAttribute("user", usuario);
        
        //model.addAttribute("cliente", usuario);
        return "clientes/usuarios/visualiza";
    }
    
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editarForm(@PathVariable(value = "id") Integer id, ModelMap model, @ModelAttribute("user") Usuario user) {
        int idBuscado=0;
        
        //Busca el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();
        
        if (rol.contains("ROLE_ADMIN")) {
            idBuscado = id;
        } else if (rol.contains("ROLE_USER")) {
            idBuscado = user.getIdUsuario();
        }
        
        Usuario usuario = servicio.buscarUsuario(idBuscado);
        
        model.addAttribute("cliente", usuario);
        return "clientes/usuarios/edita";
    }
    
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.POST)
    public String editar(@ModelAttribute("cliente") @Valid Usuario usuario, BindingResult result, ModelMap model) {
        String view = "redirect:/usuarios/visualizaCliente/"+usuario.getIdUsuario();
        
        //Buscamos al usuario introducido para ver si ya existe alguno con el mismo nombre de usuario
//        if (usuario.getUsuario().length() != 0) {
//            Usuario buscado = servicio.buscarPorUsuario(usuario.getUsuario());
//
//            if (buscado != null) {
//                result.rejectValue("usuario", "error.cliente.usuarioexistente");
//            }
//        }
           
        if (!result.hasErrors()) {
            //Agrego rol y edad, ya que no es posible editarlos
            Usuario actual = servicio.buscarUsuario(usuario.getIdUsuario());
            usuario.setRol(actual.getRol());
            usuario.setEdad(actual.isEdad());

            servicio.editaUsuario(usuario);

            model.clear();
        } else {
            view = "clientes/usuarios/edita";
        }
        return view;
    }
    
     /* ---------- MÉTODOS DE AMBOS ---------- */

    @RequestMapping(value = "/borraCliente/{id}", method = RequestMethod.GET)
    public String borraUsuario(@PathVariable(value = "id") Integer id, ModelMap model, HttpServletRequest request) {

        //Devuelve un usuario logueado
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usuario = userDetails.getUsername();

        Usuario logueado = servicio.buscarPorUsuario(usuario);

        servicio.bajaUsuario(id);
        model.clear();

        /*Si el usuario logueado se elimina a sí mismo lo redireccionamos al login. Si elimima
         a otro usuario, siendo administrador, redireccionamos al listado de usuarios*/
        if (logueado.getIdUsuario() == id) {
            request.getSession().setAttribute("mensaje","");
            return "redirect:/";
        } else {
            return "redirect:/usuarios/listadoClientes";
        }
    }
    
}

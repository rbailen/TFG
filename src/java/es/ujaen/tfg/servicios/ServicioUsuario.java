package es.ujaen.tfg.servicios;

import es.ujaen.tfg.dao.CompraDAO;
import es.ujaen.tfg.dao.EntradaDAO;
import es.ujaen.tfg.dao.UsuarioDAO;
import es.ujaen.tfg.excepciones.UsuarioNoEncontrado;
import es.ujaen.tfg.modelos.Compra;
import es.ujaen.tfg.modelos.Entrada;
import es.ujaen.tfg.modelos.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioUsuario {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    private CompraDAO compraDAO;
    
    @Autowired
    private EntradaDAO entradaDAO;
    
    public ServicioUsuario(){
        
    }
    
    public List<Usuario> listadoUsuarios() {
        return usuarioDAO.listadoUsuarios();
    }
    
    public Usuario buscarUsuario(int id){
        return usuarioDAO.buscar(id);
    }
    
    public Usuario buscarPorUsuario(String usuario){
        return usuarioDAO.buscarPorUsuario(usuario);
    }
    
    public void altaUsuario(Usuario usuario){
        usuarioDAO.insertar(usuario);
    }
    
    public void editaUsuario(Usuario usuario){
        usuarioDAO.actualizar(usuario);
    }
    
    public void bajaUsuario(int id){
        Usuario usuario = usuarioDAO.buscar(id);
        
        if(usuario == null){
            throw new UsuarioNoEncontrado();
        }
        
        //Eliminamos las compras del usuario
        List<Compra> compras = compraDAO.comprasRealizadasCliente(usuario.getIdUsuario());
        for (Compra compra : compras) {
            //Poner las entradas de la compra como no compradas
            List<Entrada> entradas = compra.getEntradas();
            for (Entrada entrada : entradas) {
                entrada.setComprada(false);
                entradaDAO.actualizar(entrada);
            }

            //Se eliminan las compras
            compraDAO.eliminar(compra);
        }
        
        //Finalmente eliminamos el usuario
        usuarioDAO.eliminar(usuario);
    }

    
}

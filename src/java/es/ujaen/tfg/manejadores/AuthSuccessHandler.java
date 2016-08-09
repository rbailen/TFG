package es.ujaen.tfg.manejadores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        
        //Obtiene el rol del usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().toString();

        String targetUrl = "";
        if(rol.contains("ROLE_ADMIN")) {
            targetUrl = "/main";
        } else if(rol.contains("ROLE_USER")) {
            targetUrl = "/inicio";
        }
        return targetUrl;
    }
}
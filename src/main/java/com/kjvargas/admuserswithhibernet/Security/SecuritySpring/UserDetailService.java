package com.kjvargas.admuserswithhibernet.Security.SecuritySpring;

import com.kjvargas.admuserswithhibernet.Security.Entities.UsuarioSecurity;
import com.kjvargas.admuserswithhibernet.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    
    @Autowired
    private UsuarioService usuarioRolService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioSecurity usuario = this.usuarioRolService.findByIdEmailLoad(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        } else {
            return usuario;
        }


    }
}

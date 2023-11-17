package com.kjvargas.admuserswithhibernet.Services.Usuario;

import com.kjvargas.admuserswithhibernet.Entities.Usuario.Rol;
import com.kjvargas.admuserswithhibernet.Repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public List<Rol> findAllRoles() {
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty()) {
            throw new RuntimeException("No hay roles registrados");
        }
        return roles;
    }

    public List<Rol> findRolByUserId(Integer id) {
        List<Rol> roles = this.rolRepository.findRolByUserId(id);
        if(roles.isEmpty())
            throw new RuntimeException("No hay roles");
        return roles;
    }
}

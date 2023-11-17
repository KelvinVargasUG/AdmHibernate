package com.kjvargas.admuserswithhibernet;

import com.kjvargas.admuserswithhibernet.Entities.Usuario.Rol;
import com.kjvargas.admuserswithhibernet.Entities.Usuario.Usuario;
import com.kjvargas.admuserswithhibernet.Repositories.RolRepository;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@Component
public class InitMethod {

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Value("${user-email-admin}")
    private String emailAdmin;

    @Value("${user-password-admin}")
    private String passwordAdmin;

    @Value("${user-name-admin}")
    private String nameUser;

    @Value("${user-lastname-admin}")
    private String lastnameUser;

    @PostConstruct
    public void createAdminUser() {
        try {
            createInitRol();
            Usuario usuario = usuarioRepository.findByEmail(emailAdmin);
            if (usuario == null) {
                log.info("Creando usuario administrador");
                String passwordEncrypt = new BCryptPasswordEncoder().encode(passwordAdmin);
                Rol rol = rolRepository.findRolByNombre("Rol_Admin");
                usuario = new Usuario();
                usuario.setEmail(emailAdmin);
                usuario.setEstado("A");
                usuario.setPassword(passwordEncrypt);
                usuario.setNombre(nameUser);
                usuario.setApellido(lastnameUser);
                usuario.setRoles(Collections.singletonList(rol));
                usuarioRepository.save(usuario);
                log.info("Usuario administrador creado");
            }
            log.info("Usuario administrador ya existe");
        } catch (Exception e) {
            log.error("Error al crear usuario administrador");
            e.printStackTrace();
        }
    }

    public void createInitRol() {
        Rol rol;
        rol = rolRepository.findRolByNombre("Rol_Admin");
        if (rol == null) {
            log.info("Creando rol administrador");
            rol = new Rol();
            rol.setNombre("Rol_Admin");
            rol.setEstado("A");
            rol.setDescripcion("Rol de administrador puede crear y eliminar usuarios");
            rolRepository.save(rol);
            log.info("Rol administrador creado");
        }

        rol = rolRepository.findRolByNombre("Rol_User");
        if (rol == null) {
            log.info("Creando rol usuario");
            rol = new Rol();
            rol.setNombre("Rol_User");
            rol.setEstado("A");
            rol.setDescripcion("Rol con limitados permisos");
            rolRepository.save(rol);
            log.info("Rol usuario creado");
        }
        log.info("Roles Iniciales ya existen");
    }
}

package com.kjvargas.admuserswithhibernet.Services.Usuario;

import com.kjvargas.admuserswithhibernet.Entities.Usuario.Rol;
import com.kjvargas.admuserswithhibernet.Entities.Usuario.Usuario;
import com.kjvargas.admuserswithhibernet.Repositories.RolRepository;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRepository;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRolRepository;
import com.kjvargas.admuserswithhibernet.Security.Entities.UsuarioSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

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

    public List<Usuario> findAllUser() {
        List<Usuario> usuarios = usuarioRepository.findAllUsers();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios registrados");
        }
        usuarios.forEach(usuario -> {
            usuario.setPassword(null);
        });
        return usuarios;
    }

    public Usuario findByIdUser(Long id) {
        Usuario usuario = usuarioRepository.findUsersById(id);
        if (usuario == null) {
            throw new RuntimeException("No se encontro el usuario");
        }
        return usuario;
    }

    public int deleteUser(long id) {
        int deleteRow = this.usuarioRepository.deleteUser(id);
        if (deleteRow == 0) {
            throw new RuntimeException("No se pudo eliminar el usuario");
        }
        return deleteRow;
    }

    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("El usuario no existe");
        }
        return usuario;
    }

    public UsuarioSecurity findByIdEmailLoad(String email) {
        Usuario usuario = findByEmail(email);
        if (usuario.getEstado() == null) {
            throw new RuntimeException("El usuario no esta disponible");
        }
        UsuarioSecurity usuarioSecurity = new UsuarioSecurity();
        usuarioSecurity.setEmail(usuario.getEmail());
        usuarioSecurity.setPassword(usuario.getPassword());
        usuarioSecurity.setRoles(usuario.getRoles());
        return usuarioSecurity;
    }

    public int habilitarUsuario(Long id) {
        int rowUpdate = usuarioRepository.habilitarUsuario(id);
        if (rowUpdate == 0) {
            throw new RuntimeException("No se pudo habilitar el usuario");
        }
        return rowUpdate;
    }
}

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

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;


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

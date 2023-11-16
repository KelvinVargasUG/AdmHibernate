package com.kjvargas.admuserswithhibernet.Services.Usuario;

import com.kjvargas.admuserswithhibernet.Entitys.Usuario.Rol;
import com.kjvargas.admuserswithhibernet.Entitys.Usuario.Usuario;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRepository;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    public List<Usuario> findAllUser() {
        List<Usuario> usuarios = usuarioRepository.find_all_users();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios registrados");
        }
        return usuarios;
    }

    public Usuario findByIdUser(Long id) {
        Usuario usuario = usuarioRepository.find_users_by_id(id);
        if (usuario == null) {
            throw new RuntimeException("No se encontro el usuario");
        }
        return usuario;
    }

    public Usuario createUser(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("El email no puede ser nulo o vacio");
        }

        Rol rol = new Rol();
        rol.setId(2L);
        usuario.setRoles(Collections.singletonList(rol));
        return usuarioRepository.save(usuario);

    }

    public Usuario updateUser(Usuario usuario, Long id) {
        Usuario usuarioByid = findByIdUser(usuario.getId());
        if (usuarioByid.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        if(usuario.getId() != null){
            usuarioByid.setId(usuario.getId());
        }
        if(usuario.getNombre() != null){
            usuarioByid.setNombre(usuario.getNombre());
        }
        if(usuario.getApellido() != null){
            usuarioByid.setApellido(usuario.getApellido());
        }
        if(usuario.getEmail() != null){
            usuarioByid.setEmail(usuario.getEmail());
        }
        if(usuario.getPassword() != null){
            usuarioByid.setPassword(usuario.getPassword());
        }
        if(usuario.getEstado() != null){
            usuarioByid.setEstado(usuario.getEstado());
        }

        return this.usuarioRepository.save(usuario);
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

    public int updateRolUser(Integer id_rol, Integer id_user) {
        this.findByIdUser(Long.valueOf(id_user));
        int rowUpdate = usuarioRolRepository.updateRolUser(id_rol, id_user);
        if (rowUpdate == 0) {
            throw new RuntimeException("No se pudo actualizar el rol del usuario");
        }
        return rowUpdate;
    }

    public int habilitarUsuario(Long id) {
        int rowUpdate = usuarioRepository.habilitar_usuario(id);
        if (rowUpdate == 0) {
            throw new RuntimeException("No se pudo habilitar el usuario");
        }
        return rowUpdate;
    }
}

package com.kjvargas.admuserswithhibernet.Services.Usuario;

import com.kjvargas.admuserswithhibernet.Entities.Usuario.Rol;
import com.kjvargas.admuserswithhibernet.Entities.Usuario.Usuario;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRepository;
import com.kjvargas.admuserswithhibernet.Repositories.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioRolService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    @Autowired
    UsuarioService usuarioService;

    public int updateRolUser(Integer id_rol, Integer id_user) {
        this.usuarioService.findByIdUser(Long.valueOf(id_user));
        int rowUpdate = usuarioRolRepository.updateRolUser(id_rol, id_user);
        if (rowUpdate == 0) {
            throw new RuntimeException("No se pudo actualizar el rol del usuario");
        }
        return rowUpdate;
    }

    public Usuario createUser(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("El email no puede ser nulo o vacio");
        }
        String passwordEncrypt = this.bCryptPasswordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncrypt);
        Rol rol = new Rol();
        rol.setId(2L);
        usuario.setRoles(Collections.singletonList(rol));
        return usuarioRepository.save(usuario);

    }

    public Usuario updateUser(Usuario usuario, Long id) {
        Usuario usuarioByid = this.usuarioService.findByIdUser(usuario.getId());
        if (usuarioByid.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        if (usuario.getId() != null) {
            usuarioByid.setId(usuario.getId());
        }
        if (usuario.getNombre() != null) {
            usuarioByid.setNombre(usuario.getNombre());
        }
        if (usuario.getApellido() != null) {
            usuarioByid.setApellido(usuario.getApellido());
        }
        if (usuario.getEmail() != null) {
            usuarioByid.setEmail(usuario.getEmail());
        }
        if (usuario.getPassword() != null) {
            String passwordEncrypt = this.bCryptPasswordEncoder.encode(usuario.getPassword());
            usuarioByid.setPassword(passwordEncrypt);

        }
        if (usuario.getEstado() != null) {
            usuarioByid.setEstado(usuario.getEstado());
        }

        return this.usuarioRepository.save(usuario);
    }


}
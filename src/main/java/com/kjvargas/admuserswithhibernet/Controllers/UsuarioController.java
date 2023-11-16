package com.kjvargas.admuserswithhibernet.Controllers;

import com.kjvargas.admuserswithhibernet.Entitys.ApiError;
//import com.kjvargas.admuserswithhibernet.Services.Usuario.UsuarioRolService;
import com.kjvargas.admuserswithhibernet.Entitys.Usuario.Usuario;
import com.kjvargas.admuserswithhibernet.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /*
       @Autowired
       private UsuarioRolService usuarioRolService;
   */
    @PostMapping
    //@PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> createUser(@Valid @RequestBody Usuario usuario) {
        try {
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, "El password es requerido"));
            }
            usuario.setEstado("A");
            return ResponseEntity.ok(usuarioService.createUser(usuario));
        } catch (Exception e) {
            Usuario emailExist = usuarioService.findByEmail(usuario.getEmail());
            if (emailExist != null) {
                if (emailExist.getEstado() == null) {
                    this.usuarioService.habilitarUsuario(emailExist.getId());
                    return ResponseEntity.ok(ApiError.fromMessage(HttpStatus.OK, "El email ya existe, se habilitó el usuario"));
                }
                return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, "El correo electrónico ya existe"));
            }
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }


    @GetMapping
    //@PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findAllUser() {
        try {
            return ResponseEntity.ok(usuarioService.findAllUser());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findByIdUser(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.findByIdUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasAuthority('Rol_Admin') or hasAuthority('Rol_User')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody Usuario usuario, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.updateUser(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }


    @GetMapping("/comprobar_exit_email")
   // @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> comprobarExistenciaEmail(@Valid @RequestParam("email") String email) {
        try {
            return ResponseEntity.ok(usuarioService.findByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PutMapping("/{idUser}/update_rol/{idRol}")
   // @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> updateRol(@Valid @PathVariable Long idRol, @PathVariable Long idUser) {
        try {
            return ResponseEntity.ok(usuarioService.updateRolUser(idRol, idUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiError.fromMessage(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

}

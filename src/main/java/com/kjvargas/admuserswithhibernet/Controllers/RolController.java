package com.kjvargas.admuserswithhibernet.Controllers;

//import com.kjvargas.admuserswithhibernet.Services.Usuario.RolService;
//import com.kjvargas.admuserswithhibernet.Services.Usuario.UsuarioRolService;
import com.kjvargas.admuserswithhibernet.Entitys.ApiResponse;
import com.kjvargas.admuserswithhibernet.Services.Usuario.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;
/*
    @Autowired
    private UsuarioRolService usuarioRolService;
*/

    @GetMapping
   // @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findAllRol() {
        try {
            return ResponseEntity.ok(rolService.findAllRoles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fromMessage(HttpStatus.BAD_REQUEST,e.getMessage()));
        }
    }
  /*
    @GetMapping("/no_asignados/user/{id}")
    @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findByUserUnassignedRoles(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.usuarioRolService.findByUserUnassignedRoles(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('Rol_Admin')")
    public ResponseEntity<?> findRolByUserId(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.usuarioRolService.findRolByUserId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

 */
}

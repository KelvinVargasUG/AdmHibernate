package com.kjvargas.admuserswithhibernet.Repositories;

import com.kjvargas.admuserswithhibernet.Entities.Usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    @Query(value = "SELECT r FROM UsuarioRol ur " +
            "INNER JOIN Rol r ON r.id = ur.idRol " +
            "WHERE ur.estado IS NOT NULL and ur.idUsuario = :idUser")
    List<Rol> findRolByUserId(@Param("idUser") Integer id);

    Rol findRolByNombre(String nombre);
}

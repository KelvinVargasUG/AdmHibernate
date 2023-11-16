package com.kjvargas.admuserswithhibernet.Repositories;

import com.kjvargas.admuserswithhibernet.Entitys.Usuario.Usuario;
import com.kjvargas.admuserswithhibernet.Entitys.Usuario.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    @Modifying
    @Query(value ="UPDATE UsuarioRol u SET u.idRol = :rol WHERE u.idUsuario = :user")
    int updateRolUser(@Param("rol") Integer idRol, @Param("user") Integer idUser);

}

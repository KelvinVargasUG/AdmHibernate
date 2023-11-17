package com.kjvargas.admuserswithhibernet.Repositories;

import com.kjvargas.admuserswithhibernet.Entities.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario u WHERE u.estado IS NOT NULL " +
                    "ORDER BY u.id_usuario DESC", nativeQuery = true)
    List<Usuario> findAllUsers();

    @Query(value = "SELECT u FROM Usuario u " +
            "       WHERE u.estado IS NOT NULL and u.id = :id ")
    Usuario findUsersById(@Param("id") Long id);


    @Query(value = "SELECT u from Usuario u where u.email = :email")
    Usuario findByEmail(@Param("email") String email);

    @Modifying
    @Query(value ="UPDATE Usuario u SET u.estado = 'A' WHERE u.id = :id")
    int habilitarUsuario(@Param("id") Long id);

    @Modifying
    @Query(value ="UPDATE Usuario u SET u.estado = null WHERE u.id = :id")
    int deleteUser(@Param("id") long id);
}

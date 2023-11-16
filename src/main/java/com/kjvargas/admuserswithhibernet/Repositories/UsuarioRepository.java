package com.kjvargas.admuserswithhibernet.Repositories;

import com.kjvargas.admuserswithhibernet.Entitys.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario u WHERE u.estado IS NOT NULL " +
                    "ORDER BY u.id_usuario DESC", nativeQuery = true)
    List<Usuario> find_all_users();

    @Query(value = "SELECT u FROM Usuario u " +
            "       WHERE u.id = :id ")
    Usuario find_users_by_id(@Param("id") Long id);


    @Query(value = "SELECT u from Usuario u where u.email = :email")
    Usuario findByEmail(@Param("email") String email);

    @Query(value =" UPDATE usuario u SET u.estado = 'A' WHERE u.id_usuario = :id",
            nativeQuery = true)
    void habilitar_usuario(@Param("id") Long id);

    @Modifying
    @Query(value ="UPDATE Usuario u SET u.estado = null WHERE u.id = :id")
    int deleteUser(@Param("id") long id);

    @Query(value ="UPDATE UsuarioRol u SET u.idRol = :idRol WHERE u.idUsuario = :idUser")
    Usuario updateRolUser(@Param("idRol") Long idRol, @Param("idUser") Long idUser);
}

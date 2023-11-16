package com.kjvargas.admuserswithhibernet.Entitys.Usuario;

import com.kjvargas.admuserswithhibernet.Entitys.CamposObligatorios;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "usuario_rol")
public class UsuarioRol extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "id_usuario")
    private Integer idUsuario;

}

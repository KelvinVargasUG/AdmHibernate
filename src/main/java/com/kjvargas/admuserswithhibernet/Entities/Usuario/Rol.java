package com.kjvargas.admuserswithhibernet.Entities.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kjvargas.admuserswithhibernet.Entities.CamposObligatorios;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rol", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Rol extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "descripcion")
    private String descripcion;

}
package com.capgeminiTp.sva.models.entities.producto;


import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// javaX validetion @NotBlack, @Min() en la parte de atributos
// y @Valid cuando se pida un valir desde el front

@Entity
@Table(name = "productosBase")
@Getter @Setter
public class ProductoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name = "categoria", columnDefinition = "VARCHAR(100)")
    private String categoria; // Pueden ser Ropa, accesorio celular, etc

    @Column(name = "imagen", columnDefinition = "VARCHAR(500)")
    private String imagen;

    @Column(name = "nombre", columnDefinition = "VARCHAR(500)")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(500)")
    private String descripcion;

    @Column(name = "tiempo_fabricacion", columnDefinition = "INTEGER")
    private int tiempoFabricacion;

    @Column(name = "precio_base", columnDefinition = "INTEGER")
    private int precio_Base;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_g_id", referencedColumnName = "id")
    private Usuario gestor;

    @Column(name = "fechaSubida", columnDefinition = "DATE")
    private LocalDate fechaSubida;

    @Column(name = "estado")
    @Enumerated(value = EnumType.STRING)
    private Estado estado;

    @JsonIgnore // El jsonIgnore es para que no se genere un bucle de recursividad. En el json no se pone la lista de posiblesPersonalizaciones
    @OneToMany(mappedBy = "producto",cascade = {CascadeType.ALL})
    private List<PosiblePersonalizacion> personalizacion = new ArrayList<PosiblePersonalizacion>(); // Personalizaciones predefinidas por los usuarios gestores

    // constructor

    public ProductoBase(){}

    public ProductoBase(String nombre, String descripcion, int tiempoFabricacion, int precio_Base) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tiempoFabricacion = tiempoFabricacion;
        this.precio_Base = precio_Base;
    }

    // Metodos

    public Boolean esUnaPosiblePersonalizacion(PosiblePersonalizacion busqueda) { return personalizacion.indexOf(personalizacion) != -1; }


}

package com.capgeminiTp.sva.abm;

import com.capgeminiTp.sva.abm.DTO.ModificarPBDTO;
import com.capgeminiTp.sva.abm.DTO.ProductoBaseDTO;
import com.capgeminiTp.sva.abm.repositories.PosiblePersonalizacionRepositorie;
import com.capgeminiTp.sva.abm.repositories.ProductoBaseRepositorie;
import com.capgeminiTp.sva.abm.repositories.UsuarioRepositorie;
import com.capgeminiTp.sva.models.entities.producto.*;
import com.capgeminiTp.sva.models.entities.usuario.TipoUsuario;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/productosbase")
public class ProductoBaseController {

    @Autowired
    @Qualifier("jpa")
    ProductoBaseRepositorie repo;

    @Autowired
    @Qualifier("jpa")
    PosiblePersonalizacionRepositorie pprepo;

    @Autowired
    @Qualifier("jpa")
    UsuarioRepositorie repou;

    @GetMapping(path = {"","/"})
    public List<ProductoBase> mostrarTodosLosProductos(){
        return repo.findAll();
    }

    @PostMapping("/publicar/{user_id}") // analiza si es GESTOR
    public ProductoBase darDeAltaProductoBase(@RequestBody ProductoBase producto,
                                              @PathVariable(name = "user_id") Long u_id){
        ProductoBase prd = producto;
        Usuario gestor = repou.findById(u_id).get();
        if(gestor.getTipo() == TipoUsuario.GESTOR){
            prd.setGestor(repou.findById(u_id).get());
            prd.setEstado(Estado.PUBLICADO);
            prd.setFechaSubida(LocalDate.now());
            return repo.save(prd);
        }

        return prd; // QUIERO QUE ME DEVUELVA UN ERROR
    }

    @PostMapping("/{pb_id}/eliminar")
    public ProductoBase borrarProductoP(@PathVariable(name = "pb_id") Long p_id){
        ProductoBase base = repo.findById(p_id).get();
        base.setEstado(Estado.DESACTIVADO);
        return repo.save(base);
    }

    @DeleteMapping("/{pb_id}/posiblespersonalizaciones/{posib_id}/borrar")
    public ProductoBase borrarPersonalizacion(@PathVariable(name = "pb_id") Long pb_id,
                                                       @PathVariable(name = "posib_id") Long posib_id){
        repo.deleteById(posib_id);
        ProductoBase base = repo.findById(pb_id).get();
        return repo.findById(pb_id).get();
    }

    @PostMapping("/{productobase_id}/modificar") // ESTA MAL
    public ProductoBase modificar(@RequestBody ModificarPBDTO dto,
                                      @PathVariable(name = "productobase_id") Long pb_id){
        ProductoBase prd = repo.findById(pb_id).get();
        if(dto.getNombre() != null){
            prd.setNombre(dto.getNombre());
        }
        if(dto.getPrecio_Base() != 0){
            prd.setPrecio_Base(dto.getPrecio_Base());
        }
        if(dto.getCategoria() != null){
            prd.setCategoria(dto.getCategoria());
        }
        if(dto.getDescripcion() != null){
            prd.setDescripcion(dto.getDescripcion());
        }
        if(dto.getTiempoFabricacion() != 0){
            prd.setTiempoFabricacion(dto.getTiempoFabricacion());
        }
        if(dto.getEstado() != null){
            prd.setEstado(dto.getEstado());
        }
        if(dto.getImagen() != null){
            prd.setImagen(dto.getImagen());
        }
        return repo.save(prd);
    }

    @PostMapping("/{productobase_id}/posiblesPersonalizaciones/crear")
    public PosiblePersonalizacion crearPosiblePersonalizacion(@RequestBody ProductoBaseDTO dtopp,
                                                              @PathVariable(name = "productobase_id") Long pb_id){

        PosiblePersonalizacion posiblePersonalizacion = new PosiblePersonalizacion();
        AreaPersonalizacion areaNueva = new AreaPersonalizacion(dtopp.getArea(), dtopp.getArea_description());
        TipoPersonalizacion tipoPersonalizacion = new TipoPersonalizacion(dtopp.getTipo());

        posiblePersonalizacion.setArea(areaNueva);
        posiblePersonalizacion.setTipo(tipoPersonalizacion);
        ProductoBase productoBase = repo.findById(pb_id).get();
        posiblePersonalizacion.setProducto(productoBase);

        productoBase.getPersonalizacion().add(posiblePersonalizacion);
        pprepo.save(posiblePersonalizacion);

        return posiblePersonalizacion;
        // falta relacionarlo con el gestor

    }

    @GetMapping("/{productobase_id}/posiblesPersonalizaciones")
    public List<PosiblePersonalizacion> verTodasLasPPdeUnPB(@PathVariable(name = "productobase_id") Long pb_id){
        ProductoBase productoBase = repo.findById(pb_id).get();
        return productoBase.getPersonalizacion();
    }




}

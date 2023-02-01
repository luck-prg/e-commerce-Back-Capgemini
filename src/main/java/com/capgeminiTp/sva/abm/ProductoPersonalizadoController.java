package com.capgeminiTp.sva.abm;

import com.capgeminiTp.sva.abm.DTO.PPaltaDTO;
import com.capgeminiTp.sva.abm.DTO.PersonalizacionDTO;
import com.capgeminiTp.sva.abm.repositories.*;
import com.capgeminiTp.sva.models.entities.producto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productospersonalizados")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProductoPersonalizadoController {

    @Autowired
    @Qualifier("jpa")
    ProductoPersonalizadoRepositorie pprepo;

    @Autowired
    @Qualifier("jpa")
    PersonalizacionRepositorie prepo;

    @Autowired
    @Qualifier("jpa")
    ProductoBaseRepositorie pbrepo;

    @Autowired
    @Qualifier("jpa")
    PosiblePersonalizacionRepositorie ppersorepo;

    @Autowired
    @Qualifier("jpa")
    UsuarioRepositorie repou;

    @Autowired
    @Qualifier("jpa")
    CategoriaRepositorie crepo;

    @GetMapping("{pp_id}/personalizaciones")
    public List<Personalizacion> verPersonalizaciones(@PathVariable(name = "pp_id") Long p_id){
        return pprepo.findById(p_id).get().getPersonalizacion();
    }

    @DeleteMapping("{pp_id}/personalizaciones/{perso_id}/borrar")
    public ProductoPersonalizado borrarPersonalizacion(@PathVariable(name = "pp_id") Long p_id,
                                                       @PathVariable(name = "perso_id") Long perso_id){
        prepo.deleteById(perso_id);
        ProductoPersonalizado personalizado = pprepo.findById(p_id).get();
        return pprepo.findById(p_id).get();
    }

    @PostMapping("{pp_id}/modificar")
    public ProductoPersonalizado modificarProducto(@PathVariable(name = "pp_id") Long p_id,
                                                   @RequestBody ProductoPersonalizado producto){

        ProductoPersonalizado prd = pprepo.findById(p_id).get();
        if(producto.getNombre() != null){
            prd.setNombre(producto.getNombre());
        }
        if(producto.getCategoria() != null){
            prd.setCategoria(producto.getCategoria());
        }
        if(producto.getEstado() != null){
            prd.setEstado(producto.getEstado());
        }
        return pprepo.save(prd);
    }

    @PostMapping("{pp_id}/eliminar")
    public ProductoPersonalizado borrarProductoP(@PathVariable(name = "pp_id") Long p_id){
        ProductoPersonalizado personalizado = pprepo.findById(p_id).get();
        personalizado.setEstado(Estado.DESACTIVADO);
        return pprepo.save(personalizado);
    }


    @GetMapping(path = {"/",""})
    public Page<ProductoPersonalizado> verTodosLosProductosP(@RequestParam(name = "categoria",required = false)
                                                             String categoria, Pageable page){
        if(categoria == null){
            return pprepo.findAll(page);
        }else {
            Categoria categoriaB =crepo.findByCategoria(categoria).get();
            return pprepo.findByCategoria(categoriaB,page);
        }
    }

    @PostMapping("publicar/{user_id}")
    public ProductoPersonalizado darDeAltaProductoPersonalizado(@RequestBody PPaltaDTO altaDTO,
                                                                @PathVariable(name = "user_id") Long u_id){
        ProductoPersonalizado pp = new ProductoPersonalizado();
        if(crepo.findByCategoria(altaDTO.getCategoria()).isPresent()){
            pp.setCategoria(crepo.findByCategoria(altaDTO.getCategoria()).get());
        }else{
            Categoria categoria = new Categoria(altaDTO.getCategoria());
            pp.setCategoria(categoria);
            crepo.save(categoria);
        }
        pp.setVendedor(repou.findById(u_id).get());
        pp.setProducto_Base(pbrepo.findById(altaDTO.getProductobase_id()).get());
        pp.setEstado(Estado.PUBLICADO);
        pprepo.save(pp);
        return pp;
    }

    @PostMapping("{pp_id}/agregar")
    public Personalizacion agregarPersonalizacion(@RequestBody PersonalizacionDTO dto,
                                                  @PathVariable(name = "pp_id") Long p_id){
        ProductoPersonalizado producto = pprepo.findById(p_id).get();
        Optional<PosiblePersonalizacion> posiblePersonalizacion = ppersorepo.findById(dto.getPosiblepersonalizacion_id());
        //&& producto.getProducto_Base().esUnaPosiblePersonalizacion(posiblePersonalizacion.get())
        if(posiblePersonalizacion.isPresent()){
            Personalizacion personalizacion = new Personalizacion(posiblePersonalizacion.get(),dto.getContenido(), dto.getNombre(), dto.getPrecio_p());
            personalizacion.setProductoP(producto);
            producto.getPersonalizacion().add(personalizacion);
            prepo.save(personalizacion);
            return personalizacion;
        }else {
            try {
                throw new IllegalAccessException("ID incorrecto o No pertenece al producto base");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

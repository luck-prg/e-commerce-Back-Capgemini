package com.capgeminiTp.sva.abm;

import com.capgeminiTp.sva.abm.DTO.DTOagregarItem;
import com.capgeminiTp.sva.abm.DTO.DTOrelizarCompra;
import com.capgeminiTp.sva.abm.repositories.*;
import com.capgeminiTp.sva.models.entities.Tienda;
import com.capgeminiTp.sva.models.entities.carrito.Carrito;
import com.capgeminiTp.sva.models.entities.carrito.EstadoCarrito;
import com.capgeminiTp.sva.models.entities.carrito.Item;
import com.capgeminiTp.sva.models.entities.producto.ProductoPersonalizado;
import com.capgeminiTp.sva.models.entities.MetodoDePago;
import com.capgeminiTp.sva.models.entities.usuario.EstadoCuenta;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class GeneralController {

    @Autowired
    @Qualifier("jpa")
    MetodoPagoRepositorie m_p_repo;

    @Autowired
    @Qualifier("jpa")
    UsuarioRepositorie repou;

    @Autowired
    @Qualifier("jpa")
    CarritoRepositorie repocart;

    @Autowired
    @Qualifier("jpa")
    TiendaRepositorie repotienda;

    @Autowired
    @Qualifier("jpa")
    ItemrRepositorie repoItem;

    @Autowired
    @Qualifier("jpa")
    ProductoPersonalizadoRepositorie repopp;


    // --------------------------------------------------------------- Carritos

    @DeleteMapping("/carritos/{cart_id}/items/{item_id}/eliminar")
    public Carrito eliminarItem(@PathVariable(name = "cart_id") Long c_id,
                              @PathVariable(name = "item_id") Long i_id){
        Carrito carrito = repocart.findById(c_id).get();
        if(carrito.getEstado() == EstadoCarrito.FINALIZADO) {
            return carrito;
        }
        carrito.getProductos().remove(repoItem.findById(i_id).get());
        repoItem.findById(i_id).get();
        return repocart.findById(c_id).get();
    }

    @PostMapping("/carrito/agregarproducto")
    public Item agregarItem(@RequestBody DTOagregarItem itemNuevo){
        ProductoPersonalizado pp = repopp.findById(itemNuevo.getProducto_id()).get();
        Tienda tienda = repotienda.findByVendedor(pp.getVendedor());
        Item item = new Item(pp,itemNuevo.getCantidad());

        Usuario comprador = new Usuario("anonimo","123","anonimo");

        if(itemNuevo.getComprador_id() == null){
            comprador.setEstado(EstadoCuenta.ACTIVADA);
            Carrito carritoNuevo = new Carrito(EstadoCarrito.ABIERTO,comprador,tienda);
            item.setCarrito(carritoNuevo);
            return repoItem.save(item);
        }

        Optional<Usuario> posibleComprador = repou.findById(itemNuevo.getComprador_id());
        comprador = posibleComprador.get();
        Optional<Carrito> carrito = repocart.findByCompradorAndTiendaVendedor(comprador,tienda);

        if(carrito.isPresent()){
            item.setCarrito(carrito.get());
        }else{
            Carrito carritoNuevo = new Carrito(EstadoCarrito.ABIERTO,comprador,tienda);
            carritoNuevo.setFechaApertura(LocalDate.now());
            item.setCarrito(carritoNuevo);
        }
        return repoItem.save(item);
    }

    @PostMapping("/usuarios/{user_id}/carritos/{carrito_id}/pagar")
    public Carrito realizarCompra(@PathVariable(name = "user_id") Long u_id,
                                  @PathVariable(name = "carrito_id") Long c_id,
                                  @RequestBody DTOrelizarCompra compra){
        Usuario usuario = repou.findById(u_id).get();
        if(usuario.getUser() == "anonimo") {
            usuario.setNombre(compra.getNombre());
            usuario.setPassword(compra.getPassword());
            usuario.setUser(compra.getUser());
            repou.save(usuario);
        }
        Carrito carrito = repocart.findById(c_id).get();
        carrito.setEstado(EstadoCarrito.FINALIZADO);
        return repocart.save(carrito);
    }


    @GetMapping("/usuarios/{user_id}/carritos")
    public Page<Carrito> verCarritosDe(@PathVariable(name = "user_id") Long u_id, Pageable page){
        Usuario comprador = repou.findById(u_id).get();
        return repocart.findByComprador(comprador,page);
    }

    @GetMapping("/carritos/{carrito_id}/productos")
    public List<Item> verProductosDe(@PathVariable(name = "user_id") Long u_id, @PathVariable(name = "carrito_id") Long cart_id, Pageable page){
        Carrito carrito = repocart.findById(cart_id).get();
        return carrito.getProductos();
    }


    /*
    @PostMapping("/carrito/agregarproducto")
    public Item agregar(@RequestBody DTOagregarItem itemNuevo){
        ProductoPersonalizado pp = repopp.findById(itemNuevo.getProducto_id()).get();
        Item item = new Item(pp,itemNuevo.getCantidad());
        Usuario comprador = new Usuario("anonimo","123","anonimo");
        Carrito carritoNuevo = new Carrito(EstadoCarrito.ABIERTO,comprador);
        item.setCarrito(carritoNuevo);
        return repoItem.save(item);
    } */

    // ---------------------- Metodos de pago -------------------------


    @PostMapping("/metodos_de_pago/agregar")
    public MetodoDePago crear(@RequestBody MetodoDePago metodoDePago){
        MetodoDePago nuevoMetodo = new MetodoDePago();
        nuevoMetodo.setMetodo(metodoDePago.getMetodo());
        return m_p_repo.save(nuevoMetodo);
    }

    // ----------------------------------------------------------- ESTA MAL
    @PostMapping("/usuario/{user_id}/tienda/metodos_de_pago/agregar")
    public Tienda agregar(@RequestBody MetodoDePago metodoDePago,
                          @PathVariable(name = "user_id") Long u_id){
        if(m_p_repo.findByMetodo(metodoDePago.getMetodo()).isPresent()){
            MetodoDePago nuevoMetodo = m_p_repo.findByMetodo(metodoDePago.getMetodo()).get();
            Tienda tienda = repotienda.findByVendedor(repou.findById(u_id).get());
            tienda.agregarMetodo(nuevoMetodo);
            return repotienda.save(tienda);
        }else {
            MetodoDePago nuevoMetodo = metodoDePago;
            Tienda tienda = repotienda.findByVendedor(repou.findById(u_id).get());
            tienda.agregarMetodo(nuevoMetodo);
            return repotienda.save(tienda);
        }
    }

    /*
    @GetMapping("/usuario/{user_id}/metodos_de_pago")
    public List<MetodoDePago> vermetodosdepago(@PathVariable(name = "user_id") Long u_id){
        return repou.findById(u_id).get().getMetodospago();
    } */



}

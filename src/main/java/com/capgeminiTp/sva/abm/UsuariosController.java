package com.capgeminiTp.sva.abm;

import com.capgeminiTp.sva.abm.DTO.DTOLogin;
import com.capgeminiTp.sva.abm.DTO.DTOSession;
import com.capgeminiTp.sva.abm.DTO.RegistrarUsuarioDTO;
import com.capgeminiTp.sva.abm.repositories.ProductoPersonalizadoRepositorie;
import com.capgeminiTp.sva.abm.repositories.TiendaRepositorie;
import com.capgeminiTp.sva.abm.repositories.UsuarioRepositorie;
import com.capgeminiTp.sva.models.entities.Tienda;
import com.capgeminiTp.sva.models.entities.usuario.EstadoCuenta;
import com.capgeminiTp.sva.models.entities.usuario.TipoUsuario;
import com.capgeminiTp.sva.models.entities.usuario.Usuario;
import com.capgeminiTp.sva.models.entities.producto.ProductoPersonalizado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UsuariosController {

    @Autowired
    @Qualifier("jpa")
    UsuarioRepositorie repou;

    @Autowired
    @Qualifier("jpa")
    ProductoPersonalizadoRepositorie pprepo;

    @Autowired
    @Qualifier("jpa")
    TiendaRepositorie repotienda;

    @GetMapping("/{user_id}/productospersonalizados")
    public Page<ProductoPersonalizado> verSusProductosP(@PathVariable(name = "user_id") Long userid,Pageable page){
        Usuario user = repou.findById(userid).get();
        return pprepo.findByVendedor(user,page);
    }

    @GetMapping("/vendedores")
    public Page<Usuario> vendedoresPorPagina(@RequestParam(name = "nombre",required = false)
                                                 String nombre, Pageable page){
        if(nombre == null){
            Page<Usuario> paginas = repou.findByTipo(TipoUsuario.VENDEDOR,page);
            return paginas;
        }else {
            return repou.findByTipoAndNombre(TipoUsuario.VENDEDOR,nombre,page);
        }
    }

    @PostMapping("/registrar")
    public Usuario crearUsuario(@RequestBody RegistrarUsuarioDTO user){

        // Validar si hay otro usuario con ese nombre

        Usuario nuevoUser = new Usuario(user.getUser(),user.getPassword(),user.getNombre());
        nuevoUser.setEstado(EstadoCuenta.ACTIVADA);
        nuevoUser.setTipo(user.getTipousuario());
        if(user.getTipousuario() == TipoUsuario.VENDEDOR){
            Tienda nuevaTienda = new Tienda(nuevoUser);
            repotienda.save(nuevaTienda);
        }else {
            repou.save(nuevoUser);
        }
        return nuevoUser;
    }

    @PostMapping("{usr_id}/delete") // baja
    public Usuario borrarUsuario(@PathVariable(name = "usr_id") Long id){
        Usuario usuario = repou.findById(id).get();
        usuario.setEstado(EstadoCuenta.DESCATIVADA);
        repou.save(usuario);
        return usuario;
    }

    @PostMapping("/{usr_id}/modificarperfil") // ESTA MAL
    public Usuario modificarUsuario(@RequestBody Usuario usuario,
                                    @PathVariable(name = "usr_id") Long userid){
        Optional<Usuario> usr = repou.findById(userid);
        usr.get().setNombre(usuario.getNombre());
        usr.get().setPassword(usuario.getPassword());
        usr.get().setEstado(usuario.getEstado());
        usr.get().setUser(usuario.getUser());
        repou.save(usr.get());
        return (repou.findById(userid).get());
    }

    @PostMapping("/login")
    public DTOSession validarUsuario(@RequestBody DTOLogin dto) {
        Optional<Usuario> user = repou.findByUser(dto.getUser());
        DTOSession session = new DTOSession();
        if (user.isPresent()) {
            Usuario usr = user.get();
            session.setUser(usr.getUser());
            if (usr.getPassword().equals(dto.getPassword())) {
                session.setName(usr.getNombre());
                session.setUser_id(usr.getId());
                session.setPassword(usr.getPassword());
                session.setTipo(usr.getTipo());
                session.setOpCode("OK");
            }else{
                session.setOpCode("CONTRASEÑO O USUARIO INCORRECTOS");
            }
        } else {
            session.setOpCode("CONTRASEÑO O USUARIO INCORRECTOS");
        }
        return session;
    }


}

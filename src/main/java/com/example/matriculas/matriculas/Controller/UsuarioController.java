package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Modelo.Usuario;
import com.example.matriculas.matriculas.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /*@ApiOperation(value = "Retorna el listado de todas las usuario")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Usuario> listaUsuario = usuarioRepository.findByDeleted(false);
            response.setResponse(listaUsuario);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Retorna el matricula filtrando idUsuario")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idUsuario}")
    public ResponseObjeto GetById(@PathVariable("idUsuario") Integer idUsuario) {
        response = new ResponseObjeto();

        try {
            Usuario usuario = usuarioRepository.findByIdUsuarioInAndDeletedIn(idUsuario, false);
            response.setResponse(usuario);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Agrega una nueva usuario")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Usuario usuarioObj) {
        response = new ResponseObjeto();

        try {
            if (usuarioObj != null) {
                response.setRequest(usuarioObj);

                usuarioObj.setUpdatedBy(usuarioObj.getCreatedBy());
                usuarioObj.setCreationDate(currentDate);
                usuarioObj.setUpdatedDate(currentDate);
                usuarioObj.setDeleted(false);

                usuarioRepository.save(usuarioObj);
                response.setResponse(usuarioObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Modifica la información de una usuario")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idUsuario}")
    public ResponseObjeto Update(@PathVariable("idUsuario") Integer idUsuario, @RequestBody Usuario usuarioObj) {
        response = new ResponseObjeto();
        Usuario usuario;
        currentDate = new Date();

        try {
            if (usuarioObj != null) {
                usuarioObj.setIdUsuario(idUsuario);
                response.setRequest(usuarioObj);

                usuario = usuarioRepository.findByIdUsuarioInAndDeletedIn(idUsuario, false);

                if (usuario != null)

                {

                    usuario.setIdUsuario(usuarioObj.getIdUsuario());
                    usuario.setNombre(usuarioObj.getNombre());
                    usuario.setClave(usuarioObj.getClave());
                    usuario.setDeleted(false);
                    usuario.setCreationDate(usuarioObj.getCreationDate());
                    usuario.setUpdatedBy(usuarioObj.getUpdatedBy());
                    usuario.setUpdatedDate(currentDate);
                    usuario.setCreatedBy(usuarioObj.getCreatedBy());


                    usuarioRepository.save(usuario);
                    response.setResponse(usuarioObj);

                } else {
                    throw new Exception(Constante.itemNotFound);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

  /*  *//*@ApiOperation(value = "Elimina la información de una usuario")*//*
    @RequestMapping(method = RequestMethod.DELETE, value = "/{idUsuario}")
    public ResponseObjeto Delete(@PathVariable("idUsuario") Integer idUsuario) {

        ResponseObjeto response = new ResponseObjeto();
        Usuario usuarioStored;
        try {
            response.setRequest(idUsuario);
            usuarioStored = usuarioRepository.findOne(idUsuario);

            if (usuarioStored != null) {
                usuarioStored.setDeleted(true);
                usuarioStored.setUpdatedDate(new Date());
                usuarioRepository.save(usuarioStored);
                response.setResponse(Constante.itemDeleted);
            } else {
                throw new Exception(Constante.itemNotFound);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }
        return response;
    }*/
}

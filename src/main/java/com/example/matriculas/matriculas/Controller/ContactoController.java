package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.Contacto;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/contacto")
public class ContactoController {

    @Autowired
    ContactoRepository contactoRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /* @ApiOperation(value = "Retorna el listado de todas las contacto")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Contacto> listaContacto = contactoRepository.findByDeleted(false);
            response.setResponse(listaContacto);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Obtiene un alumno filtrándolo por el parámetro idContacto")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idContacto}")
    public ResponseObjeto GetById(@PathVariable("idContacto") Integer idContacto) {
        response = new ResponseObjeto();

        try {
            Contacto contacto = contactoRepository.findByIdContactoInAndDeletedIn(idContacto, false);
            response.setResponse(contacto);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Agrega una nueva contacto")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Contacto contactoObj) {
        response = new ResponseObjeto();

        try {
            if (contactoObj != null) {
                response.setRequest(contactoObj);

                contactoObj.setUpdatedBy(contactoObj.getCreatedBy());
                contactoObj.setCreationDate(currentDate);
                contactoObj.setUpdatedDate(currentDate);
                contactoObj.setDeleted(false);

                contactoRepository.save(contactoObj);
                response.setResponse(contactoObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Modifica la información de un contacto")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idContacto}")
    public ResponseObjeto Update(@PathVariable("idContacto") Integer idContacto, @RequestBody Contacto contactoObj) {
        response = new ResponseObjeto();
        Contacto contacto;
        currentDate = new Date();

        try {
            if (contactoObj != null) {
                contactoObj.setIdContacto(idContacto);
                response.setRequest(contactoObj);

                contacto = contactoRepository.findByIdContactoInAndDeletedIn(idContacto, false);

                if (contacto != null)

                {

                    contacto.setIdContacto(contactoObj.getIdContacto());
                    contacto.setNombre(contactoObj.getNombre());
                    contacto.setDescripcion(contactoObj.getDescripcion());
                    contacto.setPersona(contactoObj.getPersona());
                    contacto.setTipo(contactoObj.getTipo());
                    contacto.setDeleted(false);
                    contacto.setCreationDate(contactoObj.getCreationDate());
                    contacto.setUpdatedBy(contactoObj.getUpdatedBy());
                    contacto.setUpdatedDate(currentDate);
                    contacto.setCreatedBy(contactoObj.getCreatedBy());


                    contactoRepository.save(contacto);

                    response.setResponse(contactoObj);
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

 /*   @RequestMapping(method = RequestMethod.DELETE, value = "/{idContacto}")
    public ResponseObjeto Delete(@PathVariable("idContacto") Integer idContacto) {

        ResponseObjeto response = new ResponseObjeto();
        Contacto contactoStored;
        try {
            response.setRequest(idContacto);
            contactoStored = contactoRepository.findOne(idContacto);

            if (contactoStored != null) {
                contactoStored.setDeleted(true);
                contactoStored.setUpdatedDate(new Date());
                contactoRepository.save(contactoStored);
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

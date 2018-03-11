package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.Matricula;
import com.example.matriculas.matriculas.Modelo.Persona;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.MatriculaRepository;
import com.example.matriculas.matriculas.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/persona")
public class PersonaController {

    @Autowired
    PersonaRepository personaRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /*@ApiOperation(value = "Retorna el listado de todas las personas")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Persona> listaPersona = personaRepository.findByDeleted(false);
            response.setResponse(listaPersona);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Retorna el matricula filtrando idPersona")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idPersona}")
    public ResponseObjeto GetById(@PathVariable("idPersona") Integer idPersona) {
        response = new ResponseObjeto();

        try {
            Persona persona = personaRepository.findByIdPersonaInAndDeletedIn(idPersona, false);
            response.setResponse(persona);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Agrega una nueva persona")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Persona personaObj) {
        response = new ResponseObjeto();

        try {
            if (personaObj != null) {
                response.setRequest(personaObj);

                personaObj.setUpdatedBy(personaObj.getCreatedBy());
                personaObj.setCreationDate(currentDate);
                personaObj.setUpdatedDate(currentDate);
                personaObj.setDeleted(false);

                personaRepository.save(personaObj);
                response.setResponse(personaObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Modifica la información de una persona")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idPersona}")
    public ResponseObjeto Update(@PathVariable("idPersona") Integer idPersona, @RequestBody Persona personaObj) {
        response = new ResponseObjeto();
        Persona persona;
        currentDate = new Date();

        try {
            if (personaObj != null) {
                personaObj.setIdPersona(idPersona);
                response.setRequest(personaObj);

                persona = personaRepository.findByIdPersonaInAndDeletedIn(idPersona, false);

                if (persona != null)

                {

                    persona.setIdPersona(personaObj.getIdPersona());
                    persona.setCedula(personaObj.getCedula());
                    persona.setApellido(personaObj.getApellido());
                    persona.setEdad(personaObj.getEdad());
                    persona.setSexo(personaObj.getSexo());
                    persona.setDeleted(false);
                    persona.setCreationDate(personaObj.getCreationDate());
                    persona.setUpdatedBy(personaObj.getUpdatedBy());
                    persona.setUpdatedDate(currentDate);
                    persona.setCreatedBy(personaObj.getCreatedBy());


                    personaRepository.save(persona);

                    response.setResponse(personaObj);
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

 /*   @RequestMapping(method = RequestMethod.DELETE, value = "/{idPersona}")
    public ResponseObjeto Delete(@PathVariable("idPersona") Integer idPersona) {

        ResponseObjeto response = new ResponseObjeto();
        Persona personaStored;
        try {
            response.setRequest(idPersona);
            personaStored = personaRepository.findOne(idPersona);

            if (personaStored != null) {
                personaStored.setDeleted(true);
                personaStored.setUpdatedDate(new Date());
                personaRepository.save(personaStored);
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

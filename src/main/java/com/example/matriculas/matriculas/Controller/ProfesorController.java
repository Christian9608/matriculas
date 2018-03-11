package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.Profesor;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/profesor")
public class ProfesorController {

    @Autowired
    ProfesorRepository profesorRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /*@ApiOperation(value = "Retorna el listado de todas las profe")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Profesor> listaProfesor = profesorRepository.findByDeleted(false);
            response.setResponse(listaProfesor);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Retorna el matricula filtrando idProfesor")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idProfesor}")
    public ResponseObjeto GetById(@PathVariable("idProfesor") Integer idProfesor) {
        response = new ResponseObjeto();

        try {
            Profesor profesor = profesorRepository.findByIdProfesorInAndDeletedIn(idProfesor, false);
            response.setResponse(profesor);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Agrega una nueva profesor")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Profesor profesorObj) {
        response = new ResponseObjeto();

        try {
            if (profesorObj != null) {
                response.setRequest(profesorObj);

                profesorObj.setUpdatedBy(profesorObj.getCreatedBy());
                profesorObj.setCreationDate(currentDate);
                profesorObj.setUpdatedDate(currentDate);
                profesorObj.setDeleted(false);

                profesorRepository.save(profesorObj);
                response.setResponse(profesorObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Modifica la información de una profesor")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idProfesor}")
    public ResponseObjeto Update(@PathVariable("idProfesor") Integer idProfesor, @RequestBody Profesor profesorObj) {
        response = new ResponseObjeto();
        Profesor profesor;
        currentDate = new Date();

        try {
            if (profesorObj != null) {
                profesorObj.setIdProfesor(idProfesor);
                response.setRequest(profesorObj);

                profesor = profesorRepository.findByIdProfesorInAndDeletedIn(idProfesor, false);

                if (profesor != null)

                {

                    profesor.setIdProfesor(profesorObj.getIdProfesor());
                    profesor.setEspecialidad(profesorObj.getEspecialidad());
                    profesor.setDeleted(false);
                    profesor.setCreationDate(profesorObj.getCreationDate());
                    profesor.setUpdatedBy(profesorObj.getUpdatedBy());
                    profesor.setUpdatedDate(currentDate);
                    profesor.setCreatedBy(profesorObj.getCreatedBy());


                    profesorRepository.save(profesor);

                    response.setResponse(profesorObj);
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

   /* *//*@ApiOperation(value = "Elimina la información de una profesor")*//*
    @RequestMapping(method = RequestMethod.DELETE, value = "/{idProfesor}")
    public ResponseObjeto Delete(@PathVariable("idProfesor") Integer idProfesor) {

        ResponseObjeto response = new ResponseObjeto();
        Profesor profesorStored;
        try {
            response.setRequest(idProfesor);
            profesorStored = profesorRepository.findOne(idProfesor);

            if (profesorStored != null) {
                profesorStored.setDeleted(true);
                profesorStored.setUpdatedDate(new Date());
                profesorRepository.save(profesorStored);
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

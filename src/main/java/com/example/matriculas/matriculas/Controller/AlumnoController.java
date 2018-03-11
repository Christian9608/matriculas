package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Alumno;
import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/alumno")
public class AlumnoController {

    @Autowired
    AlumnoRepository alumnoRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /* @ApiOperation(value = "Retorna el listado de todos los alumnos")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Alumno> listaAlumno = alumnoRepository.findByDeleted(false);
            response.setResponse(listaAlumno);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Obtiene un alumno filtrándolo por el parámetro idAlumno")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idAlumno}")
    public ResponseObjeto GetById(@PathVariable("idAula") Integer idAlumno) {
        response = new ResponseObjeto();

        try {
            Alumno alumno = alumnoRepository.findByIdAlumnoInAndDeletedIn(idAlumno, false);
            response.setResponse(alumno);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Agrega una nueva alumno")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Alumno alumnoObj) {
        response = new ResponseObjeto();

        try {
            if (alumnoObj != null) {
                response.setRequest(alumnoObj);

                alumnoObj.setUpdatedBy(alumnoObj.getCreatedBy());
                alumnoObj.setCreationDate(currentDate);
                alumnoObj.setUpdatedDate(currentDate);
                alumnoObj.setDeleted(false);

                alumnoRepository.save(alumnoObj);
                response.setResponse(alumnoObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Modifica la información de un alumno")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idAlumno}")
    public ResponseObjeto Update(@PathVariable("idAlumno") Integer idAlumno, @RequestBody Alumno alumnoObj) {
        response = new ResponseObjeto();
        Alumno alumno;
        currentDate = new Date();

        try {
            if (alumnoObj != null) {
                alumnoObj.setIdAlumno(idAlumno);
                response.setRequest(alumnoObj);

                alumno = alumnoRepository.findByIdAlumnoInAndDeletedIn(idAlumno, false);

                if (alumno != null)

                {
                    alumno.setIdAlumno(alumnoObj.getIdAlumno());
                    alumno.setBeca(alumnoObj.getBeca());
                    alumno.setCarrera(alumnoObj.getCarrera());
                    alumno.setDeleted(false);
                    alumno.setCreationDate(alumnoObj.getCreationDate());
                    alumno.setUpdatedBy(alumnoObj.getUpdatedBy());
                    alumno.setUpdatedDate(currentDate);
                    alumno.setCreatedBy(alumnoObj.getCreatedBy());


                    alumnoRepository.save(alumno);

                    response.setResponse(alumnoObj);
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

    /*@RequestMapping(method = RequestMethod.DELETE, value = "/{idAlumno}")
    public ResponseObjeto Delete(@PathVariable("idAlumno") Integer idAlumno) {

        ResponseObjeto response = new ResponseObjeto();
        Alumno alumnoStored;
        try {
            response.setRequest(idAlumno);
            alumnoStored = alumnoRepository.findOne(idAlumno);

            if (alumnoStored != null) {
                alumnoStored.setDeleted(true);
                alumnoStored.setUpdatedDate(new Date());
                alumnoRepository.save(alumnoStored);
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

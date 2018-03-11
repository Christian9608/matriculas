package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.Matricula;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/matricula")
public class MatriculaController {

    @Autowired
    MatriculaRepository matriculaRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /*@ApiOperation(value = "Retorna el listado de todas las matriculas")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Matricula> listaMatricula = matriculaRepository.findByDeleted(false);
            response.setResponse(listaMatricula);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Retorna el matricula filtrando idMatricula")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idMatricula}")
    public ResponseObjeto GetById(@PathVariable("idMatricula") Integer idMatricula) {
        response = new ResponseObjeto();

        try {
            Matricula matricula = matriculaRepository.findByIdMatriculaInAndDeletedIn(idMatricula, false);
            response.setResponse(matricula);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Agrega una nueva matricula")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Matricula matriculaObj) {
        response = new ResponseObjeto();

        try {
            if (matriculaObj != null) {
                response.setRequest(matriculaObj);

                matriculaObj.setUpdatedBy(matriculaObj.getCreatedBy());
                matriculaObj.setCreationDate(currentDate);
                matriculaObj.setUpdatedDate(currentDate);
                matriculaObj.setDeleted(false);

                matriculaRepository.save(matriculaObj);
                response.setResponse(matriculaObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }


    /*@ApiOperation(value = "Modifica la información de una matricula")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idMatricula}")
    public ResponseObjeto Update(@PathVariable("idMatricula") Integer idMatricula, @RequestBody Matricula matriculaObj) {
        response = new ResponseObjeto();
        Matricula matricula;
        currentDate = new Date();

        try {
            if (matriculaObj != null) {
                matriculaObj.setIdMatricula(idMatricula);
                response.setRequest(matriculaObj);

                matricula = matriculaRepository.findByIdMatriculaInAndDeletedIn(idMatricula, false);

                if (matricula != null)

                {

                    matricula.setIdMatricula(matriculaObj.getIdMatricula());
                    matricula.setUsuario(matriculaObj.getUsuario());
                    matricula.setAlumno(matriculaObj.getAlumno());
                    matricula.setFecha(matriculaObj.getFecha());
                    matricula.setMonto(matriculaObj.getMonto());
                    matricula.setTotal(matriculaObj.getTotal());
                    matricula.setDeleted(false);
                    matricula.setCreationDate(matriculaObj.getCreationDate());
                    matricula.setUpdatedBy(matriculaObj.getUpdatedBy());
                    matricula.setUpdatedDate(currentDate);
                    matricula.setCreatedBy(matriculaObj.getCreatedBy());


                    matriculaRepository.save(matricula);

                    response.setResponse(matriculaObj);
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

   /* @RequestMapping(method = RequestMethod.DELETE, value = "/{idMatricula}")
    public ResponseObjeto Delete(@PathVariable("idMatricula") Integer idMatricula) {

        ResponseObjeto response = new ResponseObjeto();
        Matricula matriculaStored;
        try {
            response.setRequest(idMatricula);
            matriculaStored = matriculaRepository.findOne(idMatricula);

            if (matriculaStored != null) {
                matriculaStored.setDeleted(true);
                matriculaStored.setUpdatedDate(new Date());
                matriculaRepository.save(matriculaStored);
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

package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.DetalleMatricula;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.DetalleMatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/detalle-matricula")
public class DetalleMatriculaController {

    @Autowired
    DetalleMatriculaRepository detalleMatriculaRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /* @ApiOperation(value = "Retorna el listado de todas las detalle matricula")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<DetalleMatricula> listaDetalleMatricula = detalleMatriculaRepository.findByDeleted(false);
            response.setResponse(listaDetalleMatricula);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Obtiene un alumno filtrándolo por el parámetro idDetalle")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idDetalleMatricula}")
    public ResponseObjeto GetById(@PathVariable("idDetalleMatricula") Integer idDetalleMatricula) {
        response = new ResponseObjeto();

        try {
            DetalleMatricula detalleMatricula = detalleMatriculaRepository.findByIdDetalleMatriculaInAndDeletedIn(idDetalleMatricula, false);
            response.setResponse(detalleMatricula);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Agrega una nueva detalle matricula")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody DetalleMatricula detalleObj) {
        response = new ResponseObjeto();

        try {
            if (detalleObj != null) {
                response.setRequest(detalleObj);

                detalleObj.setUpdatedBy(detalleObj.getCreatedBy());
                detalleObj.setCreationDate(currentDate);
                detalleObj.setUpdatedDate(currentDate);
                detalleObj.setDeleted(false);

                detalleMatriculaRepository.save(detalleObj);
                response.setResponse(detalleObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Modifica la información de un contacto")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idDetalleMatricula}")
    public ResponseObjeto Update(@PathVariable("idDetalleMatricula") Integer idDetalleMatricula, @RequestBody DetalleMatricula detalleObj) {
        response = new ResponseObjeto();
        DetalleMatricula detalleMatricula;
        currentDate = new Date();

        try {
            if (detalleObj != null) {
                detalleObj.setIdDetalleMatricula(idDetalleMatricula);
                response.setRequest(detalleObj);

                detalleMatricula = detalleMatriculaRepository.findByIdDetalleMatriculaInAndDeletedIn(idDetalleMatricula, false);

                if (detalleMatricula != null)

                {

                    detalleMatricula.setIdDetalleMatricula(detalleObj.getIdDetalleMatricula());
                    detalleMatricula.setMateria(detalleObj.getMateria());
                    detalleMatricula.setMatricula(detalleObj.getMatricula());
                    detalleMatricula.setDeleted(false);
                    detalleMatricula.setCreationDate(detalleObj.getCreationDate());
                    detalleMatricula.setUpdatedBy(detalleObj.getUpdatedBy());
                    detalleMatricula.setUpdatedDate(currentDate);
                    detalleMatricula.setCreatedBy(detalleObj.getCreatedBy());


                    detalleMatriculaRepository.save(detalleMatricula);

                    response.setResponse(detalleObj);
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

    /*@RequestMapping(method = RequestMethod.DELETE, value = "/{idDetalleMatricula}")
    public ResponseObjeto Delete(@PathVariable("idDetalleMatricula") Integer idDetalleMatricula) {

        ResponseObjeto response = new ResponseObjeto();
        DetalleMatricula detalleStored;
        try {
            response.setRequest(idDetalleMatricula);
            detalleStored = detalleMatriculaRepository.findOne(idDetalleMatricula);

            if (detalleStored != null) {
                detalleStored.setDeleted(true);
                detalleStored.setUpdatedDate(new Date());
                detalleMatriculaRepository.save(detalleStored);
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

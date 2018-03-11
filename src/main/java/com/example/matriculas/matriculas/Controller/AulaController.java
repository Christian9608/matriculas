package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Aula;
import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/aula")
public class AulaController {
    @Autowired
    AulaRepository aulaRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /* @ApiOperation(value = "Retorna el listado de todas las aulas")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Aula> listaAula = aulaRepository.findByDeleted(false);
            response.setResponse(listaAula);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Obtiene un alumno filtrándolo por el parámetro idAula")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idAula}")
    public ResponseObjeto GetById(@PathVariable("idAula") Integer idAula) {
        response = new ResponseObjeto();

        try {
            Aula aula = aulaRepository.findByIdAulaInAndDeletedIn(idAula, false);
            response.setResponse(aula);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Agrega una nueva aula")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Aula aulaObj) {
        response = new ResponseObjeto();

        try {
            if (aulaObj != null) {
                response.setRequest(aulaObj);

                aulaObj.setUpdatedBy(aulaObj.getCreatedBy());
                aulaObj.setCreationDate(currentDate);
                aulaObj.setUpdatedDate(currentDate);
                aulaObj.setDeleted(false);

                aulaRepository.save(aulaObj);
                response.setResponse(aulaObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Modifica la información de un aulas")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idAula}")
    public ResponseObjeto Update(@PathVariable("idAula") Integer idAula, @RequestBody Aula aulaObj) {
        response = new ResponseObjeto();
        Aula aula;
        currentDate = new Date();

        try {
            if (aulaObj != null) {
                aulaObj.setIdAula(idAula);
                response.setRequest(aulaObj);

                aula = aulaRepository.findByIdAulaInAndDeletedIn(idAula, false);

                if (aula != null)

                {
                    aula.setIdAula(aulaObj.getIdAula());
                    aula.setTipo(aulaObj.getTipo());
                    aula.setArea(aulaObj.getArea());
                    aula.setNumeroAula(aulaObj.getNumeroAula());
                    aula.setDeleted(false);
                    aula.setCreationDate(aulaObj.getCreationDate());
                    aula.setUpdatedBy(aulaObj.getUpdatedBy());
                    aula.setUpdatedDate(currentDate);
                    aula.setCreatedBy(aulaObj.getCreatedBy());


                    aulaRepository.save(aula);

                    response.setResponse(aulaObj);
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

    /*@RequestMapping(method = RequestMethod.DELETE, value = "/{idAula}")
    public ResponseObjeto Delete(@PathVariable("idAula") Integer idAula) {

        ResponseObjeto response = new ResponseObjeto();
        Aula aulaStored;
        try {
            response.setRequest(idAula);
            aulaStored = aulaRepository.findOne(idAula);

            if (aulaStored != null) {
                aulaStored.setDeleted(true);
                aulaStored.setUpdatedDate(new Date());
                aulaRepository.save(aulaStored);
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

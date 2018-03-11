package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.Materia;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/materia")
public class MateriaController {

    @Autowired
    MateriaRepository materiaRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /* @ApiOperation(value = "Retorna el listado de todas las materias")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Materia> listaMateria = materiaRepository.findByDeleted(false);
            response.setResponse(listaMateria);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Obtiene un alumno filtrándolo por el parámetro idMateria")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idMateria}")
    public ResponseObjeto GetById(@PathVariable("idMateria") Integer idMateria) {
        response = new ResponseObjeto();

        try {
            Materia materia = materiaRepository.findByIdMateriaInAndDeletedIn(idMateria, false);
            response.setResponse(materia);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Agrega una nueva materia")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Materia materiaObj) {
        response = new ResponseObjeto();

        try {
            if (materiaObj != null) {
                response.setRequest(materiaObj);

                materiaObj.setUpdatedBy(materiaObj.getCreatedBy());
                materiaObj.setCreationDate(currentDate);
                materiaObj.setUpdatedDate(currentDate);
                materiaObj.setDeleted(false);

                materiaRepository.save(materiaObj);
                response.setResponse(materiaObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Modifica la información de una materia")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idMateria}")
    public ResponseObjeto Update(@PathVariable("idMateria") Integer idMateria, @RequestBody Materia materiaObj) {
        response = new ResponseObjeto();
        Materia materia;
        currentDate = new Date();

        try {
            if (materiaObj != null) {
                materiaObj.setIdMateria(idMateria);
                response.setRequest(materiaObj);

                materia = materiaRepository.findByIdMateriaInAndDeletedIn(idMateria, false);

                if (materia != null)

                {

                    materia.setIdMateria(materiaObj.getIdMateria());
                    materia.setNombre(materiaObj.getNombre());
                    materia.setCodigo(materiaObj.getCodigo());
                    materia.setAula(materiaObj.getAula());
                    materia.setCarrera(materiaObj.getCarrera());
                    materia.setCosto(materiaObj.getCosto());
                    materia.setCreditos(materiaObj.getCreditos());
                    materia.setDeleted(false);
                    materia.setCreationDate(materiaObj.getCreationDate());
                    materia.setUpdatedBy(materiaObj.getUpdatedBy());
                    materia.setUpdatedDate(currentDate);
                    materia.setCreatedBy(materiaObj.getCreatedBy());


                    materiaRepository.save(materia);

                    response.setResponse(materiaObj);
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

  /*  @RequestMapping(method = RequestMethod.DELETE, value = "/{idMateria}")
    public ResponseObjeto Delete(@PathVariable("idMateria") Integer idMateria) {

        ResponseObjeto response = new ResponseObjeto();
        Materia materiaStored;
        try {
            response.setRequest(idMateria);
            materiaStored = materiaRepository.findOne(idMateria);

            if (materiaStored != null) {
                materiaStored.setDeleted(true);
                materiaStored.setUpdatedDate(new Date());
                materiaRepository.save(materiaStored);
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

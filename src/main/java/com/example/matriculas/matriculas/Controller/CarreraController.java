package com.example.matriculas.matriculas.Controller;

import com.example.matriculas.matriculas.Modelo.Carrera;
import com.example.matriculas.matriculas.Modelo.Constante;
import com.example.matriculas.matriculas.Modelo.ResponseObjeto;
import com.example.matriculas.matriculas.Repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/carrera")
public class CarreraController {


    @Autowired
    CarreraRepository carreraRepository;

    private ResponseObjeto response;
    private Date currentDate;


    /* @ApiOperation(value = "Retorna el listado de todas las carreras")*/
    @RequestMapping(method = RequestMethod.GET)
    public ResponseObjeto GetAll() {
        response = new ResponseObjeto();

        try {
            List<Carrera> listaCarrera = carreraRepository.findByDeleted(false);
            response.setResponse(listaCarrera);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Obtiene un alumno filtrándolo por el parámetro idCarrera")*/
    @RequestMapping(method = RequestMethod.GET, value = "/{idCarrera}")
    public ResponseObjeto GetById(@PathVariable("idCarrera") Integer idCarrera) {
        response = new ResponseObjeto();

        try {
            Carrera carrera = carreraRepository.findByIdCarreraInAndDeletedIn(idCarrera, false);
            response.setResponse(carrera);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Agrega una nueva carrera")*/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseObjeto Create(@RequestBody Carrera carreraObj) {
        response = new ResponseObjeto();

        try {
            if (carreraObj != null) {
                response.setRequest(carreraObj);

                carreraObj.setUpdatedBy(carreraObj.getCreatedBy());
                carreraObj.setCreationDate(currentDate);
                carreraObj.setUpdatedDate(currentDate);
                carreraObj.setDeleted(false);

                carreraRepository.save(carreraObj);
                response.setResponse(carreraObj);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(Constante.badRequest);
        }

        return response;
    }

    /*@ApiOperation(value = "Modifica la información de un carreras")*/
    @RequestMapping(method = RequestMethod.PUT, value = "/{idCarrera}")
    public ResponseObjeto Update(@PathVariable("idCarrera") Integer idCarrera, @RequestBody Carrera carreraObj) {
        response = new ResponseObjeto();
        Carrera carrera;
        currentDate = new Date();

        try {
            if (carreraObj != null) {
                carreraObj.setIdCarrera(idCarrera);
                response.setRequest(carreraObj);

                carrera = carreraRepository.findByIdCarreraInAndDeletedIn(idCarrera, false);

                if (carrera != null)

                {

                    carrera.setIdCarrera(carreraObj.getIdCarrera());
                    carrera.setCodigo(carreraObj.getCodigo());
                    carrera.setNombre(carreraObj.getNombre());
                    carrera.setTotalCreditos(carreraObj.getTotalCreditos());
                    carrera.setDeleted(false);
                    carrera.setCreationDate(carreraObj.getCreationDate());
                    carrera.setUpdatedBy(carreraObj.getUpdatedBy());
                    carrera.setUpdatedDate(currentDate);
                    carrera.setCreatedBy(carreraObj.getCreatedBy());


                    carreraRepository.save(carrera);

                    response.setResponse(carreraObj);
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

   /* @RequestMapping(method = RequestMethod.DELETE, value = "/{idCarrera}")
    public ResponseObjeto Delete(@PathVariable("idCarrera") Integer idCarrera) {

        ResponseObjeto response = new ResponseObjeto();
        Carrera carreraStored;
        try {
            response.setRequest(idCarrera);
            carreraStored = carreraRepository.findOne(idCarrera);

            if (carreraStored != null) {
                carreraStored.setDeleted(true);
                carreraStored.setUpdatedDate(new Date());
                carreraRepository.save(carreraStored);
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

package com.estu.petify.petifystorefront.controllers;


import com.estu.petify.petifycore.model.advertising.AdvertiseModel;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/advertise")
public class AdvertisingPageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisingPageController.class);

    @Autowired
    private PetifyAdvertiseService petifyAdvertiseService;

    @PostMapping(value = "/{username}", consumes = {"application/json"})
    public ResponseEntity<AdvertiseModel> advertise(@PathVariable final String username, @Valid @RequestBody final AdvertiseDTO advertiseDTO){
        AdvertiseModel newAdvertise = petifyAdvertiseService.advertise(username, advertiseDTO);
        return new ResponseEntity<>(newAdvertise, HttpStatus.OK);
    }

    @PutMapping(value = "/update-advertise/{id}", consumes = {"application/json"})
    public ResponseEntity<AdvertiseModel> updateAdvert(@PathVariable final String id, @Valid @RequestBody final AdvertiseDTO advertiseDTO){
        AdvertiseModel advertiseModel = petifyAdvertiseService.getAdvertDetail(id);
        AdvertiseModel updatedAdvertise = petifyAdvertiseService.updateAdvertise(advertiseDTO, advertiseModel);
        if (Objects.isNull(updatedAdvertise)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(updatedAdvertise, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<AdvertiseModel>> getAllAdverts(){
        List<AdvertiseModel> adverts = petifyAdvertiseService.getAllAdverts();
        if (CollectionUtils.isEmpty(adverts)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(adverts, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<AdvertiseModel> getAdvertiseDetail(@PathVariable final String id){
        AdvertiseModel advert = petifyAdvertiseService.getAdvertDetail(id);
        if (Objects.isNull(advert)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(advert, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<String> removeAdvert(@PathVariable final String id){
        petifyAdvertiseService.removeAdvertiseById(id);
        return new ResponseEntity<>("Advertise with id= " + id + " removed successfully.", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

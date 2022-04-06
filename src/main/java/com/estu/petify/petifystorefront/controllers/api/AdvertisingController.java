package com.estu.petify.petifystorefront.controllers.api;


import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import com.estu.petify.petifyfacades.dto.UpdateAdvertiseDTO;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/advertise")
@RequiredArgsConstructor
@Slf4j
public class AdvertisingController extends CustomAbstractController {

    private final PetifyAdvertiseService petifyAdvertiseService;


    @GetMapping("all")
    public ResponseEntity<List<AdvertiseModel>> getAllAdverts(){

        final List<AdvertiseModel> adverts = petifyAdvertiseService.getAllAdverts();

        return new ResponseEntity<>(adverts, HttpStatus.OK);
    }


    @PostMapping("/add-advertise")
    public ResponseEntity<AdvertiseModel> advertise(@Valid @RequestBody final AdvertiseDTO advertiseDTO){

        final AdvertiseModel newAdvertise = petifyAdvertiseService.advertise(advertiseDTO);

        return new ResponseEntity<>(newAdvertise, HttpStatus.OK);
    }


    @GetMapping("/detail")
    public ResponseEntity<AdvertiseModel> advertiseDetail(@RequestParam final String id){

        final AdvertiseModel advert = petifyAdvertiseService.getAdvertDetail(id);
        if (Objects.isNull(advert)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(advert, HttpStatus.OK);
    }


    @PostMapping(value = "/update-advertise")
    public ResponseEntity<AdvertiseModel> updateAdvertise(@Valid @RequestBody final UpdateAdvertiseDTO updateAdvertiseDTO){

        final AdvertiseModel updatedAdvertise = petifyAdvertiseService.updateAdvertise(updateAdvertiseDTO);

        return new ResponseEntity<>(updatedAdvertise, HttpStatus.OK);
    }


    @DeleteMapping("/remove")
    public ResponseEntity<String> removeAdvertise(@RequestParam final String id){

        petifyAdvertiseService.removeAdvertiseById(id);

        return new ResponseEntity<>("Advertise with id= " + id + " removed successfully.", HttpStatus.OK);
    }

}

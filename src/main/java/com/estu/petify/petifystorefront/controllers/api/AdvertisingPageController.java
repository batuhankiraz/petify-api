package com.estu.petify.petifystorefront.controllers.api;


import com.estu.petify.petifycore.model.AdvertiseModel;
import com.estu.petify.petifycore.service.PetifyAdvertiseService;
import com.estu.petify.petifyfacades.dto.AdvertiseDTO;
import com.estu.petify.petifystorefront.controllers.CustomAbstractController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/advertise")
@AllArgsConstructor
@Slf4j
public class AdvertisingPageController extends CustomAbstractController {

    private final PetifyAdvertiseService petifyAdvertiseService;

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<AdvertiseModel> advertise(@Valid @RequestBody final AdvertiseDTO advertiseDTO){
        AdvertiseModel newAdvertise = petifyAdvertiseService.advertise(advertiseDTO);
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

}

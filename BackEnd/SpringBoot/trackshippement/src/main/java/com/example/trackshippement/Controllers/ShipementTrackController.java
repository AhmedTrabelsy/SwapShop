package com.example.trackshippement.Controllers;

import Requests.SetPositionRequest;
import Requests.UpdatePositionRequest;
import com.example.trackshippement.Entities.ShippementTrack;
import com.example.trackshippement.Repositories.ShipementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShipementTrackController {
    @Autowired
    ShipementRepository shipementRepository;

    @GetMapping("/positions")
    public List<ShippementTrack> getShippmentTracks() {
        return shipementRepository.findAll();
    }
    @PostMapping("/positions")
    public void setShipementPosition(@Valid SetPositionRequest request) {
        ShippementTrack shippementTrak = new ShippementTrack();
        shippementTrak.setUserId(request.getUserId());
        shippementTrak.setProductId(request.getProductId());
        shippementTrak.setLon(request.getLongi());
        shippementTrak.setLat(request.getLat());
        shippementTrak.setIsDelivered(request.getIsDelivered());
        shipementRepository.save(shippementTrak);
    }
    @PutMapping("/positions/{id}")
    public void updateShipementPosition(@PathVariable Long id, @Valid UpdatePositionRequest request) {
        ShippementTrack shippementTrak = shipementRepository.findById(id).orElseThrow();
        if (request.getUserId() != null) {
            shippementTrak.setUserId(request.getUserId());
        }
        if (request.getProductId() != null) {
            shippementTrak.setProductId(request.getProductId());
        }
        if (request.getLongi() != null) {
            shippementTrak.setLon(request.getLongi());
        }
        if (request.getLat() != null) {
            shippementTrak.setLat(request.getLat());
        }
        if (request.getIsDelivered() != null) {
            shippementTrak.setIsDelivered(request.getIsDelivered());
        }
        shipementRepository.save(shippementTrak);
    }
}


package com.example.trackshippement.Repositories;

import com.example.trackshippement.Entities.ShippementTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ShipementRepository extends JpaRepository<ShippementTrack, Long> {

}

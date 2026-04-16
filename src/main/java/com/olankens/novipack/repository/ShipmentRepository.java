package com.olankens.novipack.repository;

import com.olankens.novipack.entity.Shipment;
import com.olankens.novipack.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByShipmentStatus(ShipmentStatus shipmentStatus);

    List<Shipment> findByOriginContainingIgnoreCaseOrDestinationContainingIgnoreCase(String origin, String destination);
}

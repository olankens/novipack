package com.olankens.novipack.controller;

import com.olankens.novipack.dto.DashboardStats;
import com.olankens.novipack.entity.Shipment;
import com.olankens.novipack.entity.ShipmentEvent;
import com.olankens.novipack.entity.ShipmentStatus;
import com.olankens.novipack.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<Shipment> getAllShipments(
            @RequestParam(required = false) ShipmentStatus status,
            @RequestParam(required = false) String search
    ) {
        if (status != null) return shipmentService.findByStatus(status);
        if (search != null && !search.isBlank()) return shipmentService.search(search);
        return shipmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        return shipmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<Shipment> getByTrackingNumber(@PathVariable String trackingNumber) {
        return shipmentService.findByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@Valid @RequestBody Shipment shipment) {
        Shipment created = shipmentService.create(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(@PathVariable Long id, @RequestBody ShipmentStatus status) {
        return shipmentService.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/events")
    public ResponseEntity<Shipment> addEvent(@PathVariable Long id, @Valid @RequestBody ShipmentEvent shipmentEvent) {
        return shipmentService.addShipmentEvent(id, shipmentEvent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Shipment> deleteShipment(@PathVariable Long id) {
        if (shipmentService.findById(id).isPresent()) return ResponseEntity.notFound().build();
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public DashboardStats getStats() {
        return new DashboardStats(
                shipmentService.count(),
                shipmentService.countByStatus(ShipmentStatus.CREATED),
                shipmentService.countByStatus(ShipmentStatus.PICKED_UP),
                shipmentService.countByStatus(ShipmentStatus.IN_TRANSIT),
                shipmentService.countByStatus(ShipmentStatus.OUT_FOR_DELIVERY),
                shipmentService.countByStatus(ShipmentStatus.DELIVERED)
        );
    }
}

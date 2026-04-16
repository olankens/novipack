package com.olankens.novipack.service;

import com.olankens.novipack.entity.Shipment;
import com.olankens.novipack.entity.ShipmentEvent;
import com.olankens.novipack.entity.ShipmentStatus;
import com.olankens.novipack.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> findById(Long id) {
        return shipmentRepository.findById(id);
    }

    public Optional<Shipment> findByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber);
    }

    public List<Shipment> findByStatus(ShipmentStatus shipmentStatus) {
        return shipmentRepository.findByShipmentStatus(shipmentStatus);
    }

    public List<Shipment> search(String query) {
        return shipmentRepository.findByOriginContainingIgnoreCaseOrDestinationContainingIgnoreCase(query, query);
    }

    @Transactional
    public Shipment create(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Optional<Shipment> updateStatus(Long id, ShipmentStatus shipmentStatus) {
        return shipmentRepository.findById(id).map(shipment -> {
            shipment.setShipmentStatus(shipmentStatus);
            return shipmentRepository.save(shipment);
        });
    }

    @Transactional
    public Optional<Shipment> addShipmentEvent(Long id, ShipmentEvent shipmentEvent) {
        return shipmentRepository.findById(id).map(shipment -> {
            shipment.addShipmentEvent(shipmentEvent);
            return shipmentRepository.save(shipment);
        });
    }

    @Transactional
    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    public long count() {
        return shipmentRepository.count();
    }

    public long countByStatus(ShipmentStatus shipmentStatus) {
        return shipmentRepository.findByShipmentStatus(shipmentStatus).size();
    }
}

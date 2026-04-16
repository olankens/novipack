package com.olankens.novipack;

import com.olankens.novipack.entity.Shipment;
import com.olankens.novipack.entity.ShipmentEvent;
import com.olankens.novipack.entity.ShipmentStatus;
import com.olankens.novipack.repository.ShipmentRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final ShipmentRepository shipmentRepository;

    public DataLoader(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public void run(String @NonNull ... args) {
        if (shipmentRepository.count() == 0) {
            setShipmentNumber1();
            setShipmentNumber2();
            setShipmentNumber3();
            setShipmentNumber4();
        }
    }

    private void setShipmentNumber1() {
        Shipment s = new Shipment("TF-2026-001", "New Work, NY", "Los Angeles, LA", LocalDateTime.now().plusDays(3));
        s.setShipmentStatus(ShipmentStatus.IN_TRANSIT);
        s.addShipmentEvent(new ShipmentEvent("New Work, NY", "Package picked up by sender", LocalDateTime.now().minusDays(2)));
        s.addShipmentEvent(new ShipmentEvent("Philadelphia, PA", "Package arrived at sorting facility", LocalDateTime.now().minusDays(1)));
        s.addShipmentEvent(new ShipmentEvent("Columbus, OH", "Package in transit to next facility", LocalDateTime.now().minusHours(6)));
        shipmentRepository.save(s);
    }

    private void setShipmentNumber2() {
        Shipment s = new Shipment("TF-2026-002", "Chicago, IL", "Miami, FL", LocalDateTime.now().minusDays(3));
        s.setShipmentStatus(ShipmentStatus.DELIVERED);
        s.addShipmentEvent(new ShipmentEvent("Chicago, IL", "Package picked up", LocalDateTime.now().minusDays(5)));
        s.addShipmentEvent(new ShipmentEvent("Atlanta, GA", "Package at regional hub", LocalDateTime.now().minusDays(3)));
        s.addShipmentEvent(new ShipmentEvent("Miami, FL", "Package delivered to recipient", LocalDateTime.now().minusDays(1)));
        shipmentRepository.save(s);
    }

    private void setShipmentNumber3() {
        Shipment s = new Shipment("TF-2026-003", "Seattle, WA", "Denver, CO", LocalDateTime.now().plusDays(5));
        s.setShipmentStatus(ShipmentStatus.CREATED);
        s.addShipmentEvent(new ShipmentEvent("Seattle, WA", "Shipment label created", LocalDateTime.now()));
        shipmentRepository.save(s);
    }

    private void setShipmentNumber4() {
        Shipment s = new Shipment("TF-2026-004", "Boston, MA", "Washington, DC", LocalDateTime.now());
        s.setShipmentStatus(ShipmentStatus.OUT_FOR_DELIVERY);
        s.addShipmentEvent(new ShipmentEvent("Boston, MA", "Package picked up", LocalDateTime.now().minusDays(2)));
        s.addShipmentEvent(new ShipmentEvent("Washington, DC", "Package arrived at local facility", LocalDateTime.now().minusHours(4)));
        s.addShipmentEvent(new ShipmentEvent("Washington, DC", "Out for delivery", LocalDateTime.now().minusHours(1)));
        shipmentRepository.save(s);
    }
}

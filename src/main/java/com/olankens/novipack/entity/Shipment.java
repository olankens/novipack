package com.olankens.novipack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The tracking number is required")
    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @NotBlank(message = "The origin is required")
    private String origin;

    @NotBlank(message = "The destination is required")
    private String destination;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus = ShipmentStatus.CREATED;

    private LocalDateTime estimatedDelivery;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("timestamp desc")
    private List<ShipmentEvent> shipmentEvents = new ArrayList<>();

    public Shipment() {
    }

    public Shipment(String trackingNumber, String origin, String destination, LocalDateTime estimatedDelivery) {
        this.trackingNumber = trackingNumber;
        this.origin = origin;
        this.destination = destination;
        this.estimatedDelivery = estimatedDelivery;
    }

    public void addShipmentEvent(ShipmentEvent shipmentEvent) {
        shipmentEvents.add(shipmentEvent);
        shipmentEvent.setShipment(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ShipmentEvent> getShipmentEvents() {
        return shipmentEvents;
    }

    public void setShipmentEvents(List<ShipmentEvent> shipmentEvents) {
        this.shipmentEvents = shipmentEvents;
    }
}

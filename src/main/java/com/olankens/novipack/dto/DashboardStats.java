package com.olankens.novipack.dto;

public record DashboardStats(
        long totalShipments,
        long created,
        long pickedUp,
        long inTransit,
        long outForDelivery,
        long delivered
) {
}

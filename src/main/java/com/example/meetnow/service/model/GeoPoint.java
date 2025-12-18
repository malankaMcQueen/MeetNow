package com.example.meetnow.service.model;

import lombok.Value;

public record GeoPoint (Double latitude, Double longitude) {
    // todo check function
    public static double distanceKm(GeoPoint a, GeoPoint b) {
        final double R = 6371.0; // радиус Земли в км

        double lat1 = Math.toRadians(a.latitude());
        double lat2 = Math.toRadians(b.latitude());
        double dLat = Math.toRadians(b.latitude() - a.latitude());
        double dLon = Math.toRadians(b.longitude() - a.longitude());

        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
        return R * c;
    }

}

package com.project.localization;

/**
 * Localization DTO class
 */
public class LocalizationDTO {
    private Double longitude;
    private Double latitude;

    public LocalizationDTO() {
    }

    public LocalizationDTO(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}

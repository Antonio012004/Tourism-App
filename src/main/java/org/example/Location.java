package org.example;

public class Location {
    String county;
    Integer sirutaCode;
    String locality;
    String adminUnit;
    String address;
    String latitude;
    String longitude;

    private Location(Builder builder) {
        this.county = builder.county;
        this.sirutaCode = builder.sirutaCode;
        this.locality = builder.locality;
        this.adminUnit = builder.adminUnit;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public static class Builder {
        private String county;
        private Integer sirutaCode;
        private String locality;
        private String adminUnit;
        private String address;
        private String latitude;
        private String longitude;

        public Builder(String county, Integer sirutaCode) {
            this.county = county;
            this.sirutaCode = sirutaCode;
        }

        public Builder setLocality(String locality) {
            this.locality = locality;
            return this;
        }

        public Builder setAdminUnit(String adminUnit) {
            this.adminUnit = adminUnit;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLatitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(String longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }
}

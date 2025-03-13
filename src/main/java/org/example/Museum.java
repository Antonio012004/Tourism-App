package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Museum implements Subject{
    private String name;
    private long code;
    private long supervisorCode;
    private Location location;
    private Person manager;
    private Integer foundingYear;
    private String phoneNumber;
    private String fax;
    private String email;
    private String url;
    private String profile;
    private List<Observer> guides;

    private Museum(Builder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.supervisorCode = builder.supervisorCode;
        this.location = builder.location;
        this.manager = builder.manager;
        this.foundingYear = builder.foundingYear;
        this.phoneNumber = builder.phoneNumber;
        this.fax = builder.fax;
        this.email = builder.email;
        this.url = builder.url;
        this.profile = builder.profile;
        this.guides = new ArrayList<>();
    }

    public long getCode() {
        return code;
    }

    public void attachObserver(Observer observer) {
        guides.add(observer);
    }
    public void detachObserver(Observer observer) {
        guides.remove(observer);
    }
    public void notifyObservers(String message, String name, long code, BufferedWriter bw) throws IOException {
        for (Observer guide : guides) {
            guide.update(message, name, code, bw);
        }
    }
    public static class Builder {
        private String name;
        private long code;
        private long supervisorCode;
        private Location location;
        private Person manager;
        private Integer foundingYear;
        private String phoneNumber;
        private String fax;
        private String email;
        private String url;
        private String profile;

        public Builder(String name, long code, long supervisorCode, Location location) {
            this.name = name;
            this.code = code;
            this.supervisorCode = supervisorCode;
            this.location = location;
        }
        public Builder setManager(Person manager) {
            this.manager = manager;
            return this;
        }

        public Builder setFoundingYear(Integer foundingYear) {
            this.foundingYear = foundingYear;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setFax(String fax) {
            this.fax = fax;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public Museum build() {
            return new Museum(this);
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return code + ": " + name;
    }
}

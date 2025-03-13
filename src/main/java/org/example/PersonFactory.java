package org.example;

public class PersonFactory {
    public static Person createPerson(String surname, String name, String role, String type) {
        switch (type) {
            case "profesor":
                return new Professor(surname, name, role);
            case "student":
                return new Student(surname, name, role);
            case "manager":
                return new Person(surname, name, role);
            default:
                return null;
        }
    }
}

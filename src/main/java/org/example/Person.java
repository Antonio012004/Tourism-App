package org.example;

public class Person {
    String surname;
    String name;
    String role;
    int age;
    String email;

    public Person(String surname, String name, String role) {
        this.surname = surname;
        this.name = name;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

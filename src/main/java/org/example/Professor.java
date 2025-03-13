package org.example;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Observable;

public class Professor extends Person implements Observer{
    int experience;
    String school;
    public Professor(String surname, String name, String role) {
        super(surname, name, role);
    }

    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public String toString() {
        return "surname=" + getSurname() +
                ", name=" + getName() +
                ", role=" + getRole() +
                ", age=" + getAge() +
                ", email=" + getEmail() +
                ", school=" + getSchool() +
                ", experience=" + getExperience();
    }

    public void update(String message, String name, long code, BufferedWriter bw) throws IOException {
        bw.write( "To: " + this.email + " ## Message: " + name + " (" + code + ") " + message + "\n");
    }
}

package org.example;

import java.util.*;

class Group {
    private List<Person> members;
    private Professor guide;
    private Integer museumCode;
    private String timetable;

    public Group(Integer museumCode, String timetable) {
        this.members = new ArrayList<>();
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.guide = null;
    }

    public void addGuide(Professor guide) {
        this.guide = guide;
    }

    public Professor getGuide() {
        return guide;
    }

    public String getTimetable() {
        return timetable;
    }

    public Integer getMuseumCode() {
        return museumCode;
    }

    public void addMember(Person member) {
        members.add(member);
    }

    public void removeMember(Person member) {
        members.remove(member);
    }

    public List<Person> getMembers() {
        return members;
    }

    public void resetGuide() {
        this.guide = null;
    }

    public Person findMember(String surname, String name) {
        for (Person member : members) {
            if (member.getName().equals(name) && member.getSurname().equals(surname)) {
                return member;
            }
        }
        return null;
    }

}
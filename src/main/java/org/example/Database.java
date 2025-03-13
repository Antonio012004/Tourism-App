package org.example;

import java.util.HashSet;
import java.util.Set;

public class Database {
    private static Database dataBaseinst;
    private Set<Museum> museums;
    private Set<Group> groups;

    private Database() {
        museums = new HashSet<>();
        groups = new HashSet<>();
    }

    public static Database getInstance() {
        if(dataBaseinst == null) {
            return new Database();
        }
        return dataBaseinst;
    }
    public void addMuseum(Museum museum) {
        museums.add(museum);
    }

    public void addMuseums(Set<Museum> museums) {
        museums.addAll(museums);
    }

    public void addGroups (Set<Group> groups) {
        this.groups.addAll(groups);
    }

    public void addGroup (Group group) {
        groups.add(group);
    }

    public Group findGroup(long museumCode, String timetable) {
        for (Group group : groups) {
            long code = group.getMuseumCode().longValue();
            if (code == museumCode && group.getTimetable().equals(timetable)) {
                return group;
            }
        }
        return null;
    }

    public Museum findMuseum(long museumCode) {
        for (Museum museum : museums) {
            if (museum.getCode() == museumCode) {
                return museum;
            }
        }
        return null;
    }
}

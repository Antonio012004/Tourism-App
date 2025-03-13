package org.example;

import java.io.*;
import java.util.*;

public class FunctionManager {
    public static void processMuseums(String path, Database database) throws IOException {
        String inputPath = path + ".in";
        String outputPath = path + ".out";
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                try {
                    String[] tokens = line.split("\\|");
                    long museumCode = Long.parseLong(tokens[1]);
                    String museumName = tokens[2];
                    String region = tokens[3];
                    String locality = tokens[4];
                    String adminUnit = tokens[5];
                    String address = tokens[6];
                    Integer postalCode = Integer.parseInt(tokens[7]);
                    String phoneNumber = tokens[8];
                    String fax = tokens[9];
                    Integer foundingYear = tokens[10].isEmpty() ? null : Integer.parseInt(tokens[10]);
                    String url = tokens[11];
                    String email = tokens[12];
                    String manager = tokens[13];
                    Person managerPerson = null;
                    if (!manager.isEmpty()) {
                        String[] parts = manager.split(" ");
                        if (parts.length >= 2) {
                            managerPerson = new Person(parts[0], parts[1], "manager");
                        }
                    }
                    long supervisorCode = tokens[14].isEmpty() ? 0L : Long.parseLong(tokens[14]);
                    String profile = tokens[15];
                    Integer sirutaCode = tokens[16].isEmpty() ? null : Integer.parseInt(tokens[16]);
                    String latitude = tokens[18];
                    String longitude = tokens[19];

                    // Build Location with conditional setters
                    Location.Builder locationBuilder = new Location.Builder(region, sirutaCode);
                    if (!locality.isEmpty()) locationBuilder.setLocality(locality);
                    if (!adminUnit.isEmpty()) locationBuilder.setAdminUnit(adminUnit);
                    if (!address.isEmpty()) locationBuilder.setAddress(address);
                    if (!latitude.isEmpty()) locationBuilder.setLatitude(latitude);
                    if (!longitude.isEmpty()) locationBuilder.setLongitude(longitude);
                    Location location = locationBuilder.build();

                    // Build Museum with conditional setters
                    Museum.Builder museumBuilder = new Museum.Builder(museumName, museumCode, supervisorCode, location);
                    if (foundingYear != null) museumBuilder.setFoundingYear(foundingYear);
                    if (!phoneNumber.isEmpty()) museumBuilder.setPhoneNumber(phoneNumber);
                    if (!fax.isEmpty()) museumBuilder.setFax(fax);
                    if (!email.isEmpty()) museumBuilder.setEmail(email);
                    if (!url.isEmpty()) museumBuilder.setUrl(url);
                    if (managerPerson != null) museumBuilder.setManager(managerPerson);
                    if (!profile.isEmpty()) museumBuilder.setProfile(profile);

                    Museum museum = museumBuilder.build();
                    database.addMuseum(museum);
                    bw.write(museum.toString() + "\n");
                } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                    bw.write("Exception: Data is broken. ## (" + line + ")\n");
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public static void processGroups(String path, Database database) throws IOException {
        String inputPath = path + ".in";
        String outputPath = path + ".out";
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\|");
                String command = tokens[0];
                long museumCode = Long.parseLong(tokens[9]);
                String timetable = tokens[10];
                Group group = database.findGroup(museumCode, timetable);
                Museum museum = database.findMuseum(museumCode);
                Person person = createPersonFromTokens(tokens);
                try {
                    switch (command) {
                        case "ADD MEMBER":
                            manageAddMember(group, person, tokens, bw);
                            break;
                        case "FIND MEMBER":
                            manageFindMember(group, person, tokens, bw);
                            break;
                        case "REMOVE MEMBER":
                            manageRemoveMember(group, person, tokens, bw);
                            break;
                        case "ADD GUIDE":
                           manageAddGuide(database, group, person, museum,timetable, museumCode, bw);
                            break;
                        case "FIND GUIDE":
                            manageFindGuide(group, person, tokens, bw);
                            break;
                        case "REMOVE GUIDE":
                            manageRemoveGuide(group, person, museum, tokens, bw);
                            break;
                        default:
                            System.out.println("Comanda necunoscuta!");
                    }
                } catch (GroupThresholdException | GuideTypeException | GuideExistsException |
                         PersonNotExistsException | IOException | GroupNotExistsException e) {
                    bw.write(tokens[9] + " ## " + tokens[10] + " ## " + e.getMessage());
                }
            }
        }
    }
    public static void processEvents(String museumsPath, String groupsPath, String eventsPath, Database database) throws IOException, GuideExistsException {
        processMuseums(museumsPath, database);
        processGroups(groupsPath, database);
        String eventsPathIn = eventsPath + ".in";
        String eventsPathOut = eventsPath + ".out";
        try (BufferedReader br = new BufferedReader(new FileReader(eventsPathIn));
             BufferedWriter bw = new BufferedWriter(new FileWriter(eventsPathOut))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\|");
                long museumCode = Long.parseLong(tokens[1]);
                Museum museum = database.findMuseum(museumCode);
                String message = tokens[2];
                museum.notifyObservers(message, museum.getName(), museumCode, bw);
            }
        }
    }

    private static Person createPersonFromTokens(String[] tokens) {
        String surname = tokens[1];
        String name = tokens[2];
        String type = tokens[3];
        int age = Integer.parseInt(tokens[4]);
        String email = null;
        if(!tokens[5].isEmpty()) {
            email = tokens[5];
        }
        String school = tokens[6];
        int experience = Integer.parseInt(tokens[7]);
        String role = tokens[8];

        Person person = PersonFactory.createPerson(surname, name, role, type);
        person.setAge(age);
        person.setEmail(email);

        if (person instanceof Student student) {
            student.setSchool(school);
            student.setStudyYear(experience);
        } else {
            Professor professor = (Professor) person;
            professor.setSchool(school);
            professor.setExperience(experience);
        }
        return person;
    }

    private static void manageAddMember(Group group, Person person ,String[] tokens, BufferedWriter bw) throws IOException, GroupNotExistsException, GroupThresholdException {
        if(group == null) {
            throw new GroupNotExistsException("new member: " + person  + ")\n");
        } else if (group.getMembers().size() >= 10) {
            throw new GroupThresholdException("new member: " + person  + ")\n");
        } else{
            if (person instanceof Student) {
                Student student = (Student) person;
                group.addMember(student);
                bw.write(tokens[9] + " ## " + tokens[10] + " ## new member: " + student + "\n");
            } else {
                Professor professor = (Professor) person;
                group.addMember(professor);
                bw.write(tokens[9] + " ## " + tokens[10] + " ## new member: " + professor + "\n");
            }
        }
    }

    private static void manageFindMember(Group group, Person person, String[] tokens, BufferedWriter bw) throws IOException, GroupNotExistsException {
        if(group == null) {
            throw new GroupNotExistsException("find member: " + person  + ")\n");
        } else {
            String found = "member not exists: ";
            Person wantedPerson = group.findMember(person.getSurname(), person.getName());
            if(wantedPerson != null) {
                found = "member found: ";
            }
            bw.write(tokens[9] + " ## " + tokens[10] + " ## " + found + person + "\n");
        }
    }

    private static void manageRemoveMember(Group group, Person person, String[] tokens, BufferedWriter bw) throws IOException, GroupNotExistsException, PersonNotExistsException {
        if(group == null) {
            throw new GroupNotExistsException("removed member: " + person  + ")\n");
        } else {
            Person member = group.findMember(tokens[1], tokens[2]);
            if(member == null) {
                throw new PersonNotExistsException( person  + ")\n");
            } else {
                group.removeMember(member);
                bw.write(tokens[9] + " ## " + tokens[10] + " ## removed member: " + member + "\n");
            }
        }
    }

    private static void manageAddGuide(Database database, Group group, Person person, Museum museum, String timetable, long museumCode, BufferedWriter bw) throws IOException, GuideExistsException, GroupNotExistsException, GuideTypeException {
        if(group == null) {

            Integer code = (int) museumCode;
            group = new Group(code, timetable);
            database.addGroup(group);
        }
        if (person instanceof Student) {
            throw new GuideTypeException("new guide: " + person  + ")\n");
        } else {
            if(group.getGuide() != null) {
                Professor guide = group.getGuide();
                throw new GuideExistsException("new guide: " + guide + ")\n");
            }
            else {
                Professor professor = (Professor) person;
                group.addGuide(professor);
                if(museum != null) {
                    museum.attachObserver(professor);
                }
                bw.write(museumCode + " ## " + timetable + " ## new guide: " + professor + "\n");
            }
        }
    }

    private static void manageFindGuide(Group group, Person person, String[] tokens, BufferedWriter bw) throws IOException, GroupNotExistsException, GuideTypeException {
        if(person instanceof Student) {
            throw new GuideTypeException("new guide: " + person  + ")\n");
        } else {
            if(group == null) {
                throw new GroupNotExistsException("find guide: " + person  + ")\n");
            } else {
                Professor professor = (Professor) person;
                Professor guide = group.getGuide();
                String found = "guide not exists: ";
                if(guide.getName().equals(professor.getName()) && guide.getSurname().equals(professor.getSurname())) {
                    found = "guide found: ";
                }
                bw.write(tokens[9] + " ## " + tokens[10] + " ## " + found + professor + "\n");
            }
        }
    }

    private static void manageRemoveGuide(Group group, Person person, Museum museum, String[] tokens, BufferedWriter bw) throws IOException, GroupNotExistsException {
        if(group == null) {
            throw new GroupNotExistsException("removed guide: " + person  + ")\n");
        } else {
            bw.write(tokens[9] + " ## " + tokens[10] + " ## removed guide: " + group.getGuide() + "\n");
            if(museum != null) {
                museum.detachObserver(group.getGuide());
            }
            group.resetGuide();
        }
    }
}

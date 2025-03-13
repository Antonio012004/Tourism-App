package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String path = args[0];
        Database database = Database.getInstance();
        FunctionManager manager = new FunctionManager();
        try {
            if(args.length == 2) {
                switch (path) {
                    case "museums":
                        FunctionManager.processMuseums(args[1], database);
                        break;
                    case "groups":
                        FunctionManager.processGroups(args[1], database);
                        break;
                    default:
                        System.out.println("Invalid path");
                }
            }
            else {
                FunctionManager.processEvents(args[1], args[2], args[3], database);
            }
        } catch (IOException | GuideExistsException e) {
            System.out.println("Eroare " + e.getMessage());
        }
    }
}
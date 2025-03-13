package org.example;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Subject {
    public void attachObserver(Observer o);
    public void detachObserver(Observer o);
    public void notifyObservers(String message, String name, long code, BufferedWriter bw) throws IOException;
}

package org.example;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Observer {
    void update(String message, String name, long code, BufferedWriter bw) throws IOException;
}

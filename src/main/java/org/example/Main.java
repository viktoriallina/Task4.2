package org.example;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DummyException, IOException {
        Connection connection = null;
        Session session = null;
        String filePath = args[0];
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> list = reader.lines().toList();
        reader.close();
        try {
            connection = new ConnectionImpl();
            session = connection.createSession(true);
            Destination destination = session.createDestination("myQueue");
            Producer producer = session.createProducer(destination);
            do {
                for (String s : list) {
                    producer.send(s);
                    Thread.sleep(2000);
                }
            } while (!new Scanner(System.in).nextLine().equals("stop"));
        } catch (DummyException exception) {
            throw new DummyException();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (session != null)
                session.close();
            if (connection != null)
                connection.close();
        }
    }
}
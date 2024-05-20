package org.example;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

public class Main {
    public static void main(String[] args) throws DummyException {
        Connection connection = null;
        Session session = null;
        for (String arg : args) {
            System.out.println(arg);
        }
        String[] array = {"Четыре", "Пять", "Шесть"};
        try {
            connection = new ConnectionImpl();
            session = connection.createSession(true);
            Destination destination = session.createDestination("myQueue");
            Producer producer = session.createProducer(destination);
            for (int i = 0; i < array.length; i++) {
                producer.send(array[i]);
                Thread.sleep(2000);
            }
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
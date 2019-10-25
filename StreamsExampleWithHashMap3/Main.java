package com.company;

import javafx.concurrent.Worker;

import java.awt.*;
import java.security.Key;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Workers worker1 = new Workers("John", 20, true);
        Workers worker2 = new Workers("Alexandr", 14, false);
        Workers worker3 = new Workers("Ivan", 35, false);
        Workers worker4 = new Workers("Dan", 15, false);
        Workers worker5 = new Workers("Andrey", 25, false);
        Workers worker6 = new Workers("Inness", 40, false);
        Workers worker7 = new Workers("Viktor", 50, true);

        Workers workers = new Workers();
        workers.addWorkerToList(worker1);
        workers.addWorkerToList(worker2);
        workers.addWorkerToList(worker3);
        workers.addWorkerToList(worker4);
        workers.addWorkerToList(worker5);
        workers.addWorkerToList(worker6);
        workers.addWorkerToList(worker7);
        for (Workers worker : workers.getWorkers()) {
            System.out.println(worker.toString());
        }
        // getting of a simple random number.
        int simpleNumber = workers.getSimpleNumberFrom3To107();

        Map <Integer, Workers> resultMap = workers.getWorkers().stream().
                filter(s -> s.getAge() > 15).
                filter(s -> s.getHasCriminalRecordOrParkingFine() == false).
                collect(Collectors.toMap(w->w.getAge()*simpleNumber,w->w));

        System.out.println(resultMap);

    }
}
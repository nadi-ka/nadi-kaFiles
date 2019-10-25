package com.company;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;

public class Printer {
    private static LinkedBlockingDeque<String> printingQueue = new LinkedBlockingDeque();

    public static void main(String[] args) throws InterruptedException {
        int countOfEmployees = 20;
        int countOfBosses = 3;
        for (int i = 0; i < countOfEmployees; i++) {
            new Thread(new Employee(i + 1)).start();
        }
        for (int i = 0; i < countOfBosses; i++) {
            new Thread(new Boss(i + 1)).start();
        }

        //Let's consider this main Thread as a printer Thread now;
        //It'll print messages from the printingQueue;

        int numDocumentsProcessed = 0;

        //Here we check the number of printed documents to decide, when the program is done;

        while (numDocumentsProcessed < (countOfBosses + countOfEmployees)) {
            try {
                //pop a document from queue and print it.
                String document = printingQueue.pop();
                System.out.println("Printing: " + document);
                numDocumentsProcessed++;
                //Sleep a little bit(printer's working);
                Thread.sleep(200);
            } catch (NoSuchElementException ex) {
                //Here we expected NoSuchElementException, is there's no one document in the queue;
                //Simply Thread'll sleep a Little Bit;
                ex.printStackTrace();
                Thread.sleep(300);
            }
        }

    }

    public static class Employee implements Runnable {
        private int id;

        public Employee(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                //Everybody'll come randomly to be more fair;

                Thread.sleep(ThreadLocalRandom.current().nextInt(300, 3000));

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.printf("Employee %s wants to print his documents. \n", id);
            printingQueue.addLast(String.format("Documents of employee %s are ready. \n", id));
        }
    }

    public static class Boss implements Runnable {
        private int id;

        public Boss(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                //Bosses come randomly as well. They could come immediately;
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 2000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.printf("Big Boss %s wants to print his documents! \n", id);
            printingQueue.addFirst(String.format("Documents of Big Boss %s are ready!!!\n", id));
        }
    }
}

package com.company;

import java.util.concurrent.Semaphore;

public class Parking {
    //If parking place is busy - boolean true, free - boolean false;
    private static final boolean[] PARKING_PLACES = new boolean[5];
    //flag 'true' means that acquire() method will give permits in order;
    private static final Semaphore SEMAPHORE = new Semaphore(5, true);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 7; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }
    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            System.out.printf("The car number %s drive up to the parking. \n", carNumber);
            try {
                SEMAPHORE.acquire();
                int parkingNumber = -1;
                synchronized (PARKING_PLACES) {
                    for (int i = 0; i < 5; i++) {
                        if (!PARKING_PLACES[i]) {
                            PARKING_PLACES[i] = true;
                            parkingNumber = i;
                            System.out.printf("The car number %s has successfully parked at the place number %s.\n",
                                    carNumber, i+1);
                            break;
                        }
                    }
                }
                Thread.sleep(5000);
                synchronized (PARKING_PLACES) {
                    PARKING_PLACES[parkingNumber] = false;
                }
                SEMAPHORE.release();
                System.out.printf("The car number %s left the parking.\n", carNumber);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

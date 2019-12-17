package by.trjava.task07.util;

import by.trjava.exception.ObjectIsNotExistException;

public class PointProximityDetector {

    public static int comparePoints(Point point1, Point point2) throws ObjectIsNotExistException {
        if (point1 == null || point2 == null) {
            throw new ObjectIsNotExistException("Exception in Class by.trjava.task07.util.PointProximityDetector, " +
                    "Method comparePoints(). One ore both points have null value!");
        }
        double distanceOfTheFirstPointFromOrigin;
        double distanceOfTheSecondPointFromOrigin;
        distanceOfTheFirstPointFromOrigin = Math.sqrt(Math.pow(point1.getCoordinate1(), 2) +
                Math.pow(point1.getCoordinate2(), 2));
        distanceOfTheSecondPointFromOrigin = Math.sqrt(Math.pow(point2.getCoordinate1(), 2) +
                Math.pow(point2.getCoordinate2(), 2));
        if (distanceOfTheFirstPointFromOrigin < distanceOfTheSecondPointFromOrigin) {
            return 1;
        } else if (distanceOfTheFirstPointFromOrigin > distanceOfTheSecondPointFromOrigin) {
            return -1;
        } else {
            return 0;
        }
    }

    public static String getNearestPoint(Point point1, Point point2) throws ObjectIsNotExistException {
        int isTheNearest = comparePoints(point1, point2);
        String message = "The nearest is: ";
        if (isTheNearest == 1) {
            return message + point1.toString();
        } else if (isTheNearest == -1) {
            return message + point2.toString();
        } else {
            return message + "equals";
        }
    }
}

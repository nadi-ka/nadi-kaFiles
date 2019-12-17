package by.trjava.task07.util;

public class Point {
    private double coordinate1;
    private double coordinate2;

    public Point() {
    }

    public Point(double coordinate1, double coordinate2) {
        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;
    }

    public void setCoordinate1(double coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public void setCoordinate2(double coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public double getCoordinate1() {
        return coordinate1;
    }

    public double getCoordinate2() {
        return coordinate2;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Point point = (Point) object;
        return (coordinate1 == point.coordinate1) && (coordinate2 == point.coordinate2);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        long isDoubleToLong1 = Double.doubleToLongBits(coordinate1);
        int coordinateAsInteger1 = (int) (isDoubleToLong1 - (isDoubleToLong1 >>> 32));
        long isDoubleToLong2 = Double.doubleToLongBits(coordinate2);
        int coordinateAsInteger2 = (int) (isDoubleToLong2 - (isDoubleToLong2 >>> 32));

        result = prime * result + coordinateAsInteger1;
        result = prime * result + coordinateAsInteger2;
        return result;
    }

    @Override
    public String toString() {
        return "Point [" + coordinate1 + ", " + coordinate2 + "];";
    }
}

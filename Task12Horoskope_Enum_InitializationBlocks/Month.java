package com.company;

public enum Month {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4),
    MAY(5), JUNE(6), JULY(7), AUGUST(8),
    SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

    private int numberOfMonth;

    Month(int numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }
    public int getNumberOfMonth(){
        return numberOfMonth;
    }
    public static Month getNumberOfMonthAndReturnAsEnam (int numberOfMonth){
        for (Month element: values()){
            if (element.getNumberOfMonth() == numberOfMonth){
                return element;
            }
        }
        return null;
    }
}

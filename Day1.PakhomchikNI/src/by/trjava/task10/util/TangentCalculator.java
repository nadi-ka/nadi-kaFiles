package by.trjava.task10.util;

import by.trjava.exception.IllegalValuesException;

import java.util.TreeMap;

public class TangentCalculator {

    public static TreeMap<Integer, Double> getValueOnSegment(int start, int finish, int step) throws
            IllegalValuesException {
        if (start > finish || step < 0) {
            throw new IllegalValuesException("Exception in Class be.trjava.task10.util.TangentCalculator," +
                    "Method - getValueOnSegment(). If value of 'step' is positive, value 'start' should be less," +
                    "then value 'finish'.");
        }
        if (start < 0 || finish > 360) {
            throw new IllegalValuesException("Exception in Class be.trjava.task10.util.TangentCalculator," +
                    "Method - getValueOnSegment(). Angle should be from 0 to 360 in degrees!.");
        }
        TreeMap<Integer, Double> tgValues = new TreeMap<>();
        double value;
        for (int i = start; i <= finish; i += step) {
            // value of tg(90) and tg(270) isn't defined and aims to infinity;
            int angle90 = 90;
            int angle270 = 270;
            if (i == 90) {
                tgValues.put(i, Double.POSITIVE_INFINITY);
                if (step == 0) {
                    break;
                }
            } else if (i == 270) {
                tgValues.put(i, Double.NEGATIVE_INFINITY);
                if (step == 0) {
                    break;
                }
            } else {
                value = Math.tan(Math.toRadians(i));
                double valueRound = Math.rint(value * 100) / 100;
                tgValues.put(i, valueRound);
                if (step == 0) {
                    break;
                }
            }
        }
        return tgValues;
    }

}

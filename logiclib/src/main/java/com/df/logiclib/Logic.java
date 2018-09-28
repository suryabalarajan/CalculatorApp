package com.df.logiclib;

public class Logic {

    public double computeArithmeticCal(double val1, double val2, String action){
        double result = 0;

        if(action.equalsIgnoreCase("addition")){

            result = val1 + val2;

        } else if(action.equalsIgnoreCase("subtraction")){

            result = val1 - val2;

        } else if(action.equalsIgnoreCase("multiplication")){

            result = val1 * val2;

        } else if(action.equalsIgnoreCase("division")){

            result = val1 / val2;
        }

        return result;
    }

    public double computeTrigonometricCal(double val, String action) {
        double result = 0;

        if(action.equalsIgnoreCase("Sine")) {

            result = Math.sin(Math.toRadians(val));

        } else if(action.equalsIgnoreCase("Cosine")) {

            result = Math.cos(Math.toRadians(val));

        } else if(action.equalsIgnoreCase("Tangent")) {

            result = Math.tan(Math.toRadians(val));
        }

        return result;
    }


}

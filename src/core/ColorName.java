package core;

/**
 * Engine for translating Color object to String
 * @version 1.0
 * @see java.awt.Color
 */
public class ColorName {
    /**
     * Generate color name from awt.Color object parsed to string
     * @param object parsed to string awt.Color object
     * @return color name (starting with uppercase character)
     */
    public static String getColorName(String object) {
        switch(object) {
            case "java.awt.Color[r=0,g=0,b=255]":
                return "Blue";
            case "java.awt.Color[r=255,g=0,b=0]":
                return "Red";
            case "java.awt.Color[r=255,g=255,b=0]":
                return "Yellow";
            case "java.awt.Color[r=0,g=255,b=0]":
                return "Green";
            case "java.awt.Color[r=255,g=200,b=0]":
                return "Orange";
            case "java.awt.Color[r=255,g=175,b=175]":
                return "Pink";
            case "java.awt.Color[r=255,g=0,b=255]":
                return "Magenta";
            case "java.awt.Color[r=0,g=255,b=255]":
                return "Cyan";
            default:
                return "Blue";
        }
    }
}

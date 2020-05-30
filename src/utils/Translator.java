package utils;

public class Translator {
    public static int flatten(int x_Coordinate, int y_Coordinate, int number_of_Rectangle_X_Axis) {
        return x_Coordinate + y_Coordinate * number_of_Rectangle_X_Axis;
    }
}

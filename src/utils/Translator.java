package utils;

public class Translator {
    public static int flatten(int x_Coordinate, int y_Coordinate) {
        int return_Coordinate = x_Coordinate + y_Coordinate * consts.NUMBER_OF_RECTANGLE_X_AXIS;
        return return_Coordinate;
    }
}

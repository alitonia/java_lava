package utils.front_end_logic;

import utils.consts;

import static utils.consts.*;

public class Translator {
    public static int translate(String status){
        if (status == SEQUENTIAL){
            return SEQUENTIAL_MODE ;
        }
        else if (status == BINARY){
            return BINARY_MODE;
        }
        else if (status == A_Star){
            return A_STAR_MODE;
        }
        else return -1;
    }
}

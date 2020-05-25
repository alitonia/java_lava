package utils;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Random_Color {
    private final int status;
    private final double probability;


    public Random_Color(int status, double probability) {
        this.status = status;
        this.probability = probability;
    }


    public static int get_Random(Random_Color... statuses) {
        List<Integer> choice_List = new ArrayList<>();
        for (Random_Color random_status : statuses) {
            int number_of_elements = (int) Math.floor(random_status.getProbability() * 100);
            for (int i = 0; i < number_of_elements; i++) {
                choice_List.add(random_status.getStatus());
            }
        }
        int choice = ThreadLocalRandom.current().nextInt(choice_List.size());
        return choice_List.get(choice);
    }


    @Getter
    public int getStatus() {
        return status;
    }

    public double getProbability() {
        return probability;
    }
}

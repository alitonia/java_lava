package utils.backend_logic;

public class Constants {
    // Only keep for the shake of integration
    // TODO: Delete upon finishing A*

    public class Sequential_Status {
        public static final int NORMAL_STATUS = -3;
        public static final int CHOOSING_STATUS = 0;
        public static final int NOT_STATUS = 1;
        public static final int FOUND_VALUE_STATUS = 2;
    }

    public class Binary_Search_Status {
        public static final int RANGE_STATUS = 0;
        public static final int MID_STATUS = 1;
        public static final int NOT_MID_STATUS = 2;
        public static final int DELETE_STATUS = 3;
        public static final int FOUND_VALUE_STATUS = 4;
    }
}

package utils.backend_logic;

public class Mini_testing {
    public static void main(String[] args) {
        // TODO code application logic here
        int[] a = {0, 1, 2, 3, 4, 5, 6};
        Queue q = new Queue();
        q = SearchingUtils.Binary_Search(6, a, 0, a.length - 1);
        //q = SearchingUtils.Sequential_Search(6, a);
        for (int i = 0; i < q.getList().size(); i++) {
            System.out.println(q.getList().get(i).getStatus() + " " + q.getList().get(i).getIndex());
        }
    }
}

package day07;

public class 选择法排序 {
    static int[] i = {5, 2, -1, 1, 6, 7, 10};

    public static void main(String[] args) {
        zhijiesort();
        for (int q = 0; q < i.length; q++) {
            System.out.print("\t" + i[q]);
        }
    }

    public static void zhijiesort() {
        for (int j = 0; j < i.length - 1; j++) {
            int min = j;
            for (int k = j + 1; k < i.length; k++) {
                if (i[k] < i[min])
                    min = k;
            }
            if (min != j) {
                i[j] = i[j] ^ i[min];
                i[min] = i[j] ^ i[min];
                i[j] = i[j] ^ i[min];
            }
        }



    }
}
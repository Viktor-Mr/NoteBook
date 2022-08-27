package day07;

public class 矩阵的使用 {
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6,4}, {7, 8, 9,10}};
        System.out.println(a[0][3]);

        System.out.println("变化前的矩阵:");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++)
                System.out.print("\t" + a[i][j]);
            System.out.println();
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a[i].length; j++) {

                a[i][j] = a[i][j] ^ a[j][i];
                a[j][i] = a[i][j] ^ a[j][i];
                a[i][j] = a[i][j] ^ a[j][i];
            }
        }


        System.out.println("变化后的矩阵:");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++)
                System.out.print("\t" + a[i][j]);
            System.out.println();
        }

        int b[][] = new int[5][];
         b[0]  = new int[5];
         b[1]  = new int[4];
         b[2]  = new int[3];
        System.out.println(b[1][3]);
    }
}

package day07;

public class class_stu {
    public static void main(String[] args){
        int [][]a = {{12,15,13,121},{19,16,15},{112,25,220}};
        int min = a[0][0];
        int max = a[0][0];
        for(int i=0; i<a.length; i++){
            for(int j=0; j<a[i].length; j++){
                if(a[i][j] > max )
                    max =a[i][j];
                if(a[i][j] < min)
                    min = a[i][j];
            }
        }
        System.out.println("最高分是:" + max);
        System.out.println("最低分是:" + min);
    }
}

package day07;

import java.util.Arrays;

public class Array {
    static int[] arr ={3,2,6,9,1,7};
    public static void main(String[] args){
        int[] arr ={3,2,6,9,1,7};
        System.out.println(Arrays.toString(arr));
        System.out.println(arr[0]);

        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));
        System.out.println(arr[0]);

        int a[] ={1,2,3};
        int b[] ={1,3,2};
        System.out.println(Arrays.equals(a,b));

        int []array = new int[5];
        System.out.println(Arrays.toString(array));
        Arrays.fill(array,1);
        System.out.println(Arrays.toString(array));

        //返回索引值 即下标  返回 -（第一个大于目标值的元素下标+1）
        //  如果不存在大于的数  返回  - （长度+1）
        System.out.println(Arrays.binarySearch(a,6600));

        //拷贝可以选择长度
        int []dd ={8,5,6,11};
        int []bb = Arrays.copyOf(dd,dd.length+1);
        System.out.println(Arrays.toString(bb));

    }
}

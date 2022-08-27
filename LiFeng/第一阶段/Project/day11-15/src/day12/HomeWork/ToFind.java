package day12.HomeWork;

public class ToFind {
    public static void main(String[] args){
        String toFind = "This is a string of characters ， Num has 2510";

        char []toTest = toFind.toCharArray();

        int countA=0,counta=0,count1 =0;

        for(int i = 0; i<toTest.length; i++){
            if(toTest[i] >= 'a' && toTest[i]<='z')
                counta++;
            if(toTest[i] >= '0' && toTest[i]<= '9')
                count1++;
            if(toTest[i] >= 'A' && toTest[i]<='Z')
                countA++;
        }
        System.out.println("字符串中有小写字符" + counta);
        System.out.println("字符串中有大写字符" + countA);
        System.out.println("字符串中有数字字符" + count1);
    }
}

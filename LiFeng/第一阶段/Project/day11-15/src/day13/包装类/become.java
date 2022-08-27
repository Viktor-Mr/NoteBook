package day13.包装类;

public class become {
    public static void main(String[] args){
        System.out.println("-----int ---->String-----");
        System.out.println("----------方式1-------");
        int number = 100;
        String s1 = "" + number;
        System.out.println("s1:" + s1);
        System.out.println("----------方式2--------");
        String s2 = String.valueOf(number);
        System.out.println("s2 " + s2);
        System.out.println("----------方式3--------");
        // int -> Integer -> String
        Integer I = new Integer(number);
        String s3 = I.toString();
        System.out.println("s3 " + s3);
        System.out.println("----------方式4--------");
        String s4 = Integer.toHexString(number);
        System.out.println("s4 " + s4);

        System.out.println("-----------String ------> int -----------");
        System.out.println("----------方式1---------");
        String s = "100";
        int y1 = Integer.parseInt(s);
        System.out.println("y1 = " + y1);
        System.out.println("----------方式2---------");

        //String -> Integer ->int
        Integer y2 = new Integer(s);
        int i = y2.intValue();
        System.out.println("i = " + i);

    }
}

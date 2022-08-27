package day13.HomeWork;

public class W4 {
    public static void main(String[] args){
        String s = "寻寻觅觅,冷冷清清,凄凄惨惨戚戚.";
        String regex = "(.)\\1(.)\\2,(.)\\3(.)\\4,(.)\\5(.)\\6(.)\\7.";
        System.out.println(s.matches(regex));
    }
}

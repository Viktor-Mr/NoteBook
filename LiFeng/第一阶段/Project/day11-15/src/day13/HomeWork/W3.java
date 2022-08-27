package day13.HomeWork;


public class W3 {
    public static void main(String[] args){
        String s = "我我我123是514是7667是是中85678中中国658国人人人人人457人人人人";
        String regex = "\\d+";
        s = s.replaceAll(regex,"").replaceAll("(.)\\1+","$1");
        System.out.println(s);


    }
}

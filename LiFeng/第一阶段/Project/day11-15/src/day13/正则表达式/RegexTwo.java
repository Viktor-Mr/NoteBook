package day13.正则表达式;

public class RegexTwo {
    public static void main(String [] args){
        String birthday = "1998-01";
        String regex = "(199\\d|20[01]\\d|202[01])-((0?[1-9])|1[0-2])";
        System.out.println(birthday.matches(regex));

        String 	str2  = "我我我98765987爱爱爱爱kujyjjhkh中中中中中HGHV H国国国89709";
        String regex2  = "\\w+| ";
        str2  = str2.replaceAll(regex2,"");
        str2  = str2.replaceAll("(.)\\1+","$1");
        System.out.println(str2);
    }
}

package day12.HomeWork;

public class toEnter {
    public static void main(String[] args){
        String useName = " Admin ";
        String usePassWord = "123";
        if(useName.trim().equalsIgnoreCase("admin")) {
            if (usePassWord.trim().equals("123"))
                System.out.println("登录成功");
            else
                System.out.println("用户的密码错误");
        }
        else
            System.out.println("录入的用户名错误");
    }
}

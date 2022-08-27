package day14;

public class one {
    static void f() throws Exception{
        throw new Exception("TEST");
    }
    public static void main(String[] args) {
        try {
            f();
        }catch(Exception e) {
            System.out.println("catch Exception");
        }
    }
}

class Pre_Exception extends Exception{

}

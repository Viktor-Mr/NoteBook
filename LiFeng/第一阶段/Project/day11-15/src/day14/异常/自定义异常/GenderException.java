package day14.异常.自定义异常;

public class GenderException extends  Exception{
    public GenderException(String msg) {
        super(msg); //super
    }
}

package day14.异常.自定义异常;

public class AgeException extends  Exception {
    public AgeException(String msg){
        super(msg);
    }
}

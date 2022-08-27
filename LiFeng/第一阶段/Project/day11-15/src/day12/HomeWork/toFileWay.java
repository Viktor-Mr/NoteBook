package day12.HomeWork;

public class toFileWay {
    public static void main(String[] args){
        String s ="d:/document/data/hello.jpg";
        int  t = s.lastIndexOf('/');
        String aimOne= s.substring(t+1);
        System.out.println("文件名（包含后缀）"+ aimOne);

        int  ti = s.lastIndexOf('.');
        String aimTwo = s.substring(ti+1);
        System.out.println("文件名的后缀 " + aimTwo);
    }
}

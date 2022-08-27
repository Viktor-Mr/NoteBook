package day13.HomeWork;

public class W7 {
    public static void main(String[] args){
        String aim = "大大江东去6874浪淘尽jhbkj千古风流人物#$-故垒西边7g$人道是5  三国周郎赤壁。";
        String regex = "\\w+|[#$。-]|\\s";
        String new_str = aim.replaceAll(regex," "); //替换为空格
        new_str = new_str.replaceAll("( )\\1+"," ");

        String []arr  = new_str.split("\\s+");
        for (int i = 0; i <arr.length ; i++) {
            System.out.println(arr[i]);
        }
    }
}

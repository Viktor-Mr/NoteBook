package day16.File文件;

import java.io.File;

public class FileUse {
    public static void main(String[] args) {
    fileDome();
    }
    public  static void fileDome(){
        File file = new File("e:\\ϵͳ������Ŀ����ʦ �м�\\���ĵ�");
        File []files =  file.listFiles();
        int count = 0;
        for (File fi: files){
            if(fi.isFile()){
                System.out.println(fi.getName());
                count++;
            }
        }
        System.out.println(count);
        System.out.println(System.getProperty("os.name"));
    }
}

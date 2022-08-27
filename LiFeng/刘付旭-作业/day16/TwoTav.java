package day16.HomeWord;

import java.io.File;

public class TwoTav {
    public static void main(String[] args) {
        Dome("f:\\");
    }
    public  static void Dome(String name){

        File file = new File(name);
        File []files =  file.listFiles();
     //   System.out.println(files.length);
   if(files!=null){

       for (File fi: files){
           if(fi.isDirectory())
           {
               System.out.println(fi.getAbsolutePath());
               Dome(fi.getAbsolutePath());
           }
           if(fi.isFile())
           {
               System.out.println(fi.getAbsolutePath());
           }
       }
   }


    }
}

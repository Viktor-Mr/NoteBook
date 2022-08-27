package day16.File文件;

/*
  File方法:
  public boolean createNewFile() :创建文件如果存在这样的文件就不创建了
  public boolean mkdir()创建文件夹， 如果文件夹存在了就不创建了,如果父文件夹不存在,则不会创建
  public boolean mkdirs()创建多 层文件夹如果父文件夹不存在就自动帮你创建出来
 */

import java.io.File;

public class FileUse02 {
    public static void main(String[] args) {
    createDir();
    createDirs();
    }

    //创建单一目录
    public static  void createDir(){
        File  file = new File("e:\\aa");
        System.out.println(file.mkdir()); //c创建目录（文件夹）make directory
    }
    public static  void createDirs(){
        File  file = new File("e:\\aa\\ww");
        System.out.println(file.mkdir()); //c创建目录（文件夹）make directory
    }
}

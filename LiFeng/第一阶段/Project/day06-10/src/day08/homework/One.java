package day08.homework;

public class One {
    public static void main(String[] args){
        OneUse form = new OneUse(10,5);
        System.out.println(form.leng* form.wide);
    }
}

class OneUse{
    int leng;
    int wide;
    public OneUse(int leng,int wide){
        this.leng= leng;
        this.wide = wide;
    }
}



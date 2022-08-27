package day09;

public class UseClassArray {
    public static void  main(String[] args){
        Moblie[] mp = new Moblie[3];
        mp[0] =  new Moblie("苹果","10s",9000,"150*150",1200);
        mp[1] =  new Moblie("三星","n7",6000,"190*190",1500);
        mp[2] =  new Moblie("华为","p40",5000,"200*200",1600);
        System.out.println("\t\t手机信息");
        System.out.println("品牌\t型号\t价格\t大小\t像素");
        System.out.println("-----------------------------------------------------");
        for(int i=0; i<mp.length; i++){
            System.out.println(mp[i].getBrand()+"\t"+mp[i].getType()+"\t"+mp[i].getPrice()+"\t"+mp[i].getSize()+"\t\t"+mp[i].getPx());
        }
        System.out.println("------------------------------------------------------");
        mp[2].network();
        mp[1].playGame();
    }

}

class Moblie{
    private String brand;
    private String type;
    private int  price;
    private String  size;
    private int   px;

    public void call(){
        System.out.println("使用" + this.brand + this.type + "的手机打电话。");
    }
    public void network(){
        System.out.println("使用" + this.brand + this.type + "的手机上网。");
    }
    public void playGame(){
        System.out.println("使用" + this.brand + this.type + "的手机玩游戏。");
    }
    public Moblie(){}

    public Moblie(String brand,String type,int price, String size, int px){
        this.brand =brand;
        this.type = type;
        this.price = price;
        this.size = size;
        this.px = px;
    }


    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public int getPx() {
        return px;
    }
    public void setPx(int px) {
        this.px = px;
    }
}
package 代理模式;

/*   我     vpn    上网   */



interface Network{
    void surfing();
}
class OuerIp implements Network{
    @Override
    public void surfing() {
        System.out.println("-----上网 -----");
    }
}

class Vpn implements Network{
    Network network;

    public Vpn(Network network) {
        this.network = network;
    }

    public void  use(){
        System.out.println("-----------------------------------------");
    }

    @Override
    public void surfing() {
       use();
        network.surfing();
        use();
        System.out.println("测试  后  测试");
    }
}

public class testtwo {
    public static void main(String[] args) {
        Network net = new Vpn(new OuerIp());
        net.surfing();

    }
}

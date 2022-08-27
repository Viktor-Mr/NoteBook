package day12.HomeWork;

public class ToFindHello {
    public static void main(String[] args){
        String s = "asdq56wfhellohelloer8tyhellofbhelloy7jthellofyjunkjkhellort345hello"; // 01  12 23
        String a = "hello";

       String []testS = s.split("hello");
       System.out.println("字符串中存在" + (testS.length) + "个hello。");




        int count = 0;
        for (int i=0; i<s.length()-a.length()+1; i++){
            if(s.charAt(i) == a.charAt(0)){
                if(s.substring(i,i+a.length()).equals(a)){
                    count++;
                }
            }
        }
        System.out.println(count);

//
//
//        count = 0;
//        for (int i = 0; i <s.length() ; i++) {
//            if(s.charAt(i)==a.charAt(0)){
//
//                for (int j = 0; j < a.length()&&i+j<s.length(); j++) {
//                    if(j+1==a.length()){
//                        count++;
//                    }
//                    if(s.charAt(i+j)!=a.charAt(j)){
//                        break;
//                    }
//
//                }
//            }
//        }
//        System.out.println(count);
//

    }
}

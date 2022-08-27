package day13.HomeWork;

import org.omg.CORBA.ARG_IN;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class W1 {
    public static void main(String[] args){
        String s  = "91aa27bbb46cccc38ddddd50";
        String regex = "[a-zA-Z]+";
        String newS = s.replaceAll(regex," ");

        regex = "[0-9]{1,2}";
        Pattern p = Pattern.compile((regex));
        Matcher m = p.matcher(newS);
        int []nums = new int [5];
        for(int i=0;m.find();i++){
            nums[i] = Integer.parseInt(m.group());
        }
        Arrays.sort(nums);
        for (int i = 0; i <nums.length ; i++) {
            System.out.print( nums[i]+ "\t" );
        }

    }

}

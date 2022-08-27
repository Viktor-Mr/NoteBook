package day07.homework;

public class computer {
    public static <com> void main(String[] args){
        int [][]com = {{225,660,348,605,0,0,0,0},{727,353,818,625,0,0,0,0},{322,249,565,458,0,0,0,0}};
        int upMax = -999999,upMin=999999,upSum=0;
        double upAver=0;
        for(int i=0; i< com.length;i++){
            int sum = 0,min=com[i][0],max=com[i][0];  //min max 初始为第一家公司
            int aver=0;
            for(int j=0; j< 4; j++){
                if(min>com[i][j])
                    min = com[i][j];
                if(max<com[i][j])
                    max = com[i][j];
                sum += com[i][j];
            }
            com[i][4] = sum;
            com[i][5] = sum /4;
            com[i][6] = max;
            com[i][7] = min;

            if(upMin > min)
                upMin  = min;
            if(upMax < max)
                upMax  = max;
           upSum += sum;
        }
        System.out.println(com[0][7]);
        upAver = upSum*1.0/3;
        System.out.println("\t子公司\t第一季度\t第二季度\t第三季度\t第四季度\t年销售额\t平均销售额\t最高销售额\t最低销售额");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        for (int i=0; i<com.length;i++){
            System.out.print("\t公司" + (i+1));
            for(int j=0; j<com[i].length;j++){
                System.out.print("\t\t\t"+com[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("总公司年销售总额为" + upSum);
        System.out.println("平均每个子公司的年销售额度为" + upAver);
        System.out.println("所有子公司中最高季度销售额为" + upMax);
        System.out.println("所有子公司中最低季度销售额为" + upMin);
    }
}

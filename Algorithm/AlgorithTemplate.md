# 1. 质因数

## 质数合数 | Prime & Composite

- 质数（prime number）又称素数，有无限个。一个大于1的自然数，除了1和它本身外，不能被其他自然数（质数）整除，换句话说就是该数除了1和它本身以外不再有其他的因数；否则称为合数。
- 根据算术基本定理，每一个比1大的整数，要么本身是一个质数，要么可以写成一系列质数的乘积；而且如果不考虑这些质数在乘积中的顺序，那么写出来的形式是唯一的。最小的质数是2。
- 合数，数学用语，英文名为Composite number，指自然数中除了能被1和本身整除外，还能被其他的数整除（不包括0)的数。与之相对的是质数（因数只有1和它本身，如2,3,5,7,11,13等等，也称素数）,而1既不属于质数也不属于合数。最小的合数是4。
- 分类合数的一种方法为计算其质因数的个数。一个有两个质因数的合数称为半质数，有三个质因数的合数则称为楔形数。



## 质因数分解

首先质数(素数)是指大于1的，除了1和它本身没有其他因数的整数
1. 从2开始遍历，找到一个数i可以整除n，输出i作为质因数，未找到执行步骤3
2. 执行 n /= i 再继续执行步骤1
3. 执行i++，当i==n时终止遍历



分解质因数 代码

```c++
#include <iostream>
using namespace std;
int main()
{
    int n;//输入的正整数
    cin >> n; 
    cout << "将" << n << "分解质因数得 " ;
    for(int i =2;i<=n;i++)
    {
        while(n%i == 0)
        {
            cout<<i<<" ";
            n /= i;
        }
    }
}
```



优化代码： 循环 从  2 ~  (i*i<n)

```java
public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    Long n =  sc.nextLong();
    Long ans = 1L;

    for (Long i = 2L; i * i <= n; i++) {
        int s = 0;
        while( n % i == 0){
            s++;
            n /= i;
            System.out.println(ans);
        }
    }
    System.out.println(n);
}
```



例题：

https://www.acwing.com/problem/content/3494/

















# 2.数据结构

## 2.1栈

利用栈的**后进先出**的特点可以模拟实现递归算法的效果







<font color='red'>**算数表达式**</font>

**输入样例：**

```
(2+2)*(1+1)
```

**输出样例：**

```
8
```

```java
public class Main {
    //创建两个栈用于维护 数字 以及 运算符号
    static Stack <Integer>num = new Stack();
    static Stack <Character>op = new Stack();

    //创建一个 Map 用于维护符号的优先级
    static HashMap<Character,Integer> pr = new HashMap<Character,Integer>() {{{put('+',1);put('-',1);put('*',2);put('/',2);}}};

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        for (int i = 0; i < s.length(); i++) {

            //判断是否为数字
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                int x = 0,j=i;
                //读取多为数字
                while(j < s.length() && s.charAt(j) >= '0' && s.charAt(j) <= '9') {

                    x = x * 10 + s.charAt(j++)-'0';
                }
                i = j - 1;
                num.push(x);
            }

            else if (s.charAt(i) == '(') {
                op.push('('); // 将左括号存入字符栈栈顶
            }else if (s.charAt(i) == ')'){
                //如果栈顶不等于左括号，一直计算下去；
                while ( op.peek() != '(' ) eval();
                op.pop();// 将左括号弹出栈顶
            }
            //判断是否为 + - * /
            else{
                while (op.size()!=0 && op.peek() != '(' &&  pr.get(op.peek()) >= pr.get(s.charAt(i))) eval();
                op.push(s.charAt(i));
            }
        }
        while (op.size() != 0) eval();
        System.out.println(num.peek());
    }
    static void eval() {
        int b = num.pop();
        int a = num.pop();
        int c = op.pop();
        int x;
        if (c == '+') x = a + b;
        else if (c == '-') x = a - b;
        else if (c == '*') x = a * b;
        else x = a / b;
        num.push(x);
    }
}
```









# 3.动态规划

![](http://mk-images.tagao.top/img/202204081956998.png?imageslim)

## 3.1背包DP 

<font color='red'>**背包问题也叫选择问题 ： 有限制选择问题**</font>



### 1.1 01背包问题

有 N件物品和一个容量是 V 的背包。每件物品只能使用一次。

第 i件物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。

接下来有 N行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。

**输出格式**

输出一个整数，表示最大价值。

**数据范围**

0<N,V≤1000
0<vi,wi≤1000

**输入样例**

```mark
4 5
1 2
2 4
3 4
4 5
```

**输出样例：**

```markdown
8
```



> 分析

![](http://mk-images.tagao.top/img/202204081957460.png?imageslim)



```java
public class _Case02_01完全背包问题 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int v = sc.nextInt();

        int[] vi = new int[n + 1];
        int[] wi = new int[n + 1];
        int[][] f = new int[n + 1][v + 1];

        for (int i = 1; i <= n; i++) {
            vi[i] = sc.nextInt();
            wi[i] = sc.nextInt();
        }

        // 算法过程，因为f[0][0~m]初始就为0，因此初始化可以省略
        for (int i = 1; i <= n; i++) // 先循环物品
            for (int j = 1; j <= v; j++) // 再循环容量
                f[i][j] = Math.max(f[i - 1][j], j - vi[i] >= 0 ? f[i - 1][j - vi[i]] + wi[i] : 0); // j - vi[i] > 0 才能选第i件物品
            
        
        System.out.println(f[n][v]);

    }
}
```



> 优化

* 考虑到在计算 f 的时候，当计算第 i 行时，只用到了第 i - 1 行的数据，因此可以用滚动数组优化

* 考虑到要用到上一行数据，因此第二层循环应该从大到小进行遍历，这样每次更新用到的就是未被更新的数据

<font color='red'>**考虑循环从大到小的原因 **</font>： 在二维情况下，状态f[i] [j]是由上一轮i - 1的状态得来的，f[i] [j]与f[i - 1] [j]是独立的。而优化到一维后，如果我们还是正序，则有f[较小体积]更新到f[较大体积]，则有可能本应该用第i-1轮的状态却用的是第i轮的状态。



```java
public class _Case01_02背包问题 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int v = sc.nextInt();

        int[] vi = new int[n + 1];
        int[] wi = new int[n + 1];
        int[] f = new int[v + 1];

        for (int i = 1; i <= n; i++) {
            vi[i] = sc.nextInt();
            wi[i] = sc.nextInt();
        }

        // 算法过程，因为f[0][0~m]初始就为0，因此初始化可以省略
        for (int i = 1; i <= n; i++) {// 先循环物品
            for (int j = v; j >= vi[i]; j--) {// 再循环容量
                f[j] = Math.max(f[j], j - vi[i] >= 0 ? f[j - vi[i]] + wi[i] : 0); // j - vi[i] > 0 才能选第i件物品
            }
        }
        System.out.println(f[v]);

    }
}
```



### 1.2 完全背包问题

有 N 种物品和一个容量是 V 的背包，每种物品都有无限件可用。

第 i 种物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。

接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 种物品的体积和价值。

**输出格式**

输出一个整数，表示最大价值。

**数据范围**

0<N,V≤1000
0<vi,wi≤1000

**输入样例**

```
4 5
1 2
2 4
3 4
4 5
```

**输出样例**：

```
10
```



> 分析

![](http://mk-images.tagao.top/img/202204082040670.png?imageslim)





```java
import java.util.Scanner;
public class _Case02_01完全背包问题 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int v = sc.nextInt();

        int[] vi = new int[n + 1];
        int[] wi = new int[n + 1];
        int[][] f = new int[n + 1][v + 1];

        for (int i = 1; i <= n; i++) {
            vi[i] = sc.nextInt();
            wi[i] = sc.nextInt();
        }
        for (int i = 1; i <= n; i++)
            for (int j = 0; j <= v; j++)
                for (int k = 0; k * vi[i] <= j; k++)
                    f[i][j] = Math.max(f[i - 1][j - vi[i] * k] + wi[i] * k, f[i][j] );
        System.out.println(f[n][v]);
    }
}

```



> 优化

![](http://mk-images.tagao.top/img/202204082059801.png?imageslim)



因此代码可以根据 f(i,j)=max(f(i−1,j),f(i,j−v)+w)f(i,j)=max(f(i−1,j),f(i,j−v)+w) 进行优化：

```java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int v = sc.nextInt();

        int[] vi = new int[n + 1];
        int[] wi = new int[n + 1];
        int[][] f = new int[n + 1][v + 1];

        for (int i = 1; i <= n; i++) {
            vi[i] = sc.nextInt();
            wi[i] = sc.nextInt();
        }
        
        for (int i = 1; i <= n; i++)
            for (int j = 0; j <= v; j++)
                f[i][j] = Math.max(f[i - 1][j] ,( j - vi[i] >= 0 ? f[i][j - vi[i]] + wi[i] : 0));
        System.out.println(f[n][v]);
    }
}

```



* 可以将 f 数组优化为一维数组，因为不需要用到上一行数据，要用到本行之前计算出来的户籍，因此第二层循环应该从小到大进行遍历

```java
public class _Case02_03完全背包问题 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int v = sc.nextInt();

        int[] vi = new int[n + 1];
        int[] wi = new int[n + 1];
        int[] f = new int[v + 1];

        for (int i = 1; i <= n; i++) {
            vi[i] = sc.nextInt();
            wi[i] = sc.nextInt();
        }

        for (int i = 1; i <= n; i++)
            for (int j = vi[i]; j <= v; j++)
                f[j] = Math.max(f[j] , f[j - vi[i]] + wi[i] );

        System.out.println(f[v]);
    }
}
```





### 1.3多重背包问题

<font color='red'>**有限制的完全背包问题**</font>



有 N 种物品和一个容量是  V的背包。

第 i 种物品最多有 si 件，每件体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使物品体积总和不超过背包容量，且价值总和最大。输出最大价值。

**输入格式**

第一行两个整数，N,V，用空格隔开，分别表示物品种数和背包容积。

接下来有 N 行，每行三个整数 vi,wi,si，用空格隔开，分别表示第 ii 种物品的体积、价值和数量。

**输出格式**

输出一个整数，表示最大价值。

**数据范围**

0<N,V≤100
0<vi,wi,si≤100

**输入样例**

```
4 5
1 2 3
2 4 1
3 4 3
4 5 2
```

**输出样例**：

```
10
```



> 分析

![](http://mk-images.tagao.top/img/202204082125856.png?imageslim)



> 代码

```java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int v = sc.nextInt();

        int[] vi = new int[n + 1];
        int[] wi = new int[n + 1];
        int[] si = new int[n + 1];
        int[][] f = new int[n + 1][v + 1];

        for (int i = 1; i <= n; i++) {
            vi[i] = sc.nextInt();
            wi[i] = sc.nextInt();
            si[i] = sc.nextInt();
        }
        for (int i = 1; i <= n; i++)
            for (int j = 0; j <= v; j++)
                for (int k = 0; k * vi[i] <= j &&k <=si[i]; k++)
                    f[i][j] = Math.max(f[i - 1][j - vi[i] * k] + wi[i] * k, f[i][j] );

        System.out.println(f[n][v]);

    }
}

```



------

> 优化

分析

* 这一题不能使用类似于完全背包问题的方式进行优化，可以将

   f(i,j)=f(i−1,j−k∗v)+k∗w,k=0,1,…,sf(i,j)=f(i−1,j−k∗v)+k∗w,k=0,1,…,s 展开，如下：



![](http://mk-images.tagao.top/img/202204082251468.png?imageslim)

我们发现 f[i,j−v]f[i,j−v] 中比 f[i,j]f[i,j] 最后多了一项，不能直接得到这两者的关系，所以不能使用类似于完全背包问题的方式进行优化。



**本次的优化方式为二进制优化**，将s件物品进行拆分，然后可以转化成01背包问题



```markdown
例如 s=10
10可以拆分为0、1、2、4、3；
前四个数可以凑出0~7之间的任何数据，加上3可以凑出3~10之间的任何数据，因此这5个数可以凑出0~10内的任何数据；
相当于将10个物品打包成4个物品，打包后的物品的体积和价值分别为单个物品的1、2、4、3倍；
这四个物品都是可选可不选，因此就转化成了01背包问题。

例如 s=200
200可以拆分为0、1、2、4、8、16、32、64、73；
一共8个物品

总结：对于s，可以划分为 log(s) 上取整个单一物品，然后用01背包问题的思路解决即可，时间复杂度：O(N*V*log(s))
```



```java

import java.util.Scanner;

/**
 * @author fu-xiao-liu
 * @Date 2022/4/8 22:53
 */
public class Main {
    static int N = 12000;
    static int[] v = new int[N];
    static int[] w = new int[N];
    static int[] f = new int[N];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int cnt = 0;
        while (n-- > 0){
            int vi = sc.nextInt();
            int wi = sc.nextInt();
            int si = sc.nextInt();

            int k = 1;
            while ( k <= si ){
                cnt++;
                v[cnt] = vi * k;
                w[cnt] = wi * k;
                si -=k;
                k *= 2;
            }
            if (si > 0){
                cnt++;
                v[cnt] = vi * si;
                w[cnt] = wi * si;
            }
        }
        for (int i = 1; i <= cnt; i++) {
            for (int j = m; j >= v[i] ; j--) {
                f[j] = Math.max(f[j] , f[j - v[i]]+ w[i]);
            }
        }
        System.out.println(f[m]);
    }
}
```



### 1.4分组背包问题



![](http://mk-images.tagao.top/img/202204082309147.png?imageslim)

> 分析

![](http://mk-images.tagao.top/img/202204082316096.png?imageslim)



​	



**多重背包问题 是 分组背包问题 的一个特例。**
对于多重背包问题：对于某个物品来说，如果出现 s 次，则可以选择0次，(1次…s次)，我们将(1次…s次)这些情况打包起来形成一组，看成不同的物品(s个)，我们最多从中选1个，因此有 s+1 种可能的情况。

```java
import java.util.Scanner;

public class Main {
    static int N = 110;
    static int [][]vi = new int[N][N];
    static int [][]wi = new int[N][N];
    static int []si = new int[N];

    static int []f = new int[N];

    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        int  n = sc.nextInt();
        int  v = sc.nextInt();
        for (int i = 1; i <= n; i++) {
             si[i] = sc.nextInt();
            for (int j = 1; j <= si[i] ; j++) {
                 vi[i][j] = sc.nextInt();
                 wi[i][j] = sc.nextInt();
            }
        }
        for (int i = 1; i <= n ; i++) {
            for (int j = v; j >= 0 ; j--) {
                for (int k = 1; k <= si[i] ; k++) {
                    if(vi[i][k] <= j)
                    f[j] = Math.max(f[j],f[j - vi[i][k]] + wi[i][k]);
                }
            }
        }
        System.out.println(f[v]);

    }
}
```





## 3.2线性DP

<font color='red'>**DP是一维数组**</font>



### 2.1最长上升子序列

![](http://mk-images.tagao.top/img/202204072237697.png?imageslim)



```java
public class _Case02_01最长上升子序列 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int []f = new int[n+1];
        int []dp = new int[n+1];

        for (int i = 1; i <=n ; i++) f[i] = sc.nextInt();

        int max = 0;
        for (int i = 1; i <= n ; i++) {
            dp[i] = 1;
            for (int j = 1; j < i ; j++)
                if (  f[i] > f[j] && dp[i] <= dp[j]  )
                    dp[i] = dp[j]+1;
            
            max = Math.max(max,dp[i]);
        }
        System.out.println(max);
    }
}
```







### 2.2最长上升子序列II (贪心)

DP数组存放的是不同长度的上升子序列的最小值



![](http://mk-images.tagao.top/img/202204072253588.png?imageslim)

思路：首先数组a中存输入的数（原本的数），开辟一个数组f用来存结果，最终数组f的长度就是最终的答案；假如数组f现在存了数，当到了数组a的第i个位置时，首先判断a[i] > f[cnt] ？ 若是大于则直接将这个数添加到数组f中，即f[++cnt] = a[i];这个操作时显然的。
当a[i] <= f[cnt] 的时,我们就用a[i]去替代数组f中的第一个大于等于a[i]的数，因为在整个过程中我们维护的数组f 是一个递增的数组，所以我们可以用二分查找在 logn 的时间复杂的的情况下直接找到对应的位置，然后替换，即f[l] = a[i]。我们用a[i]去替代f[i]的含义是：以a[i]为最后一个数的严格单调递增序列,这个序列中数的个数为l个。

这样当我们遍历完整个数组a后就可以得到最终的结果。

时间复杂度分析：O(nlogn)



```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * DP数组存放的是不同长度的上升子序列的最小值
 */
public class _Case03_01最长上升子序列II {
    static int len = 0;
    // 定义数组a 用于 存放输入,数组f 用于存放最长上升子序列
    static int []a = new int[ 100000  +1];
    static int []f = new int[ 100000  +1];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().split(" ")[0]);
        String[] s = br.readLine().split(" ");


        for (int i = 1; i <= n; i++) a[i] = Integer.parseInt(s[i-1]);

        f[++len] = a[1];
        for (int i = 2; i <= n ; i++) {
            if( a[i] > f[len] ) f[++len] = a[i];
            else {  //替换掉第一个大于或者等于这个数字的那个数
                int tmp = find(a[i]);
                f[tmp] = a[i];
            }
        }
        System.out.println(len);
    }
    //利用二分查找 第一个大于或者等于这个数字的那个数
    public static int find(int x){
        int l = 1 ;
        int r = len;
        while (l < r) {
            int mid = l + r >> 1;
            if(f[mid] >= x ) r = mid;
            else l = mid +1;
        }
        return l;

    }
}
```





### 2.3最短编辑距离



![](http://mk-images.tagao.top/img/202204081814096.png?imageslim)

```java
public class _Case05_01最短编辑距离 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String a = " "+ sc.next();
        int m = sc.nextInt();
        String b = " "+  sc.next();

        int [][]f = new int[n+1][m+1];

        //初始化假设 a为空 一直增加
        for (int i = 0; i <= m ; i++) f[0][i] = i;
        //初始化假设 b为空 一直删除
        for (int i = 0; i <= n ; i++) f[i][0] = i;
        
        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= m ; j++) {
                f[i][j]  = Math.min(f[i][j - 1] + 1, f[i - 1][j] + 1);
                if ( a.charAt(i) == b.charAt(j)) f[i][j] = Math.min(f[i-1][j-1] , f[i][j] );
                else f[i][j] = Math.min(f[i][j], f[i-1][j-1]+1);
            }
        }
        System.out.println(f[n][m]);
    }
}

```





## 3.3计数DP

计数 DP 是一类 DP，<font color='red'>**强调不重不漏**</font>的一类 DP。

在设计状态上稍微有点毒瘤，且一般与组合数学有关。

<font color='red'>**计算数量**</font>

### 3.1整数划分

一个正整数 n 可以表示成若干个正整数之和，形如：n=n1+n2+…+nkn=n1+n2+…+nk，其中 n1≥n2≥…≥nk,k≥1n1≥n2≥…≥nk,k≥1。

我们将这样的一种表示称为正整数 n 的一种划分。

现在给定一个正整数 n，请你求出 n 共有多少种不同的划分方法。

**输入格式**

共一行，包含一个整数 n。

**输出格式**

共一行，包含一个整数，表示总划分数量。

由于答案可能很大，输出结果请对 109+7109+7 取模。

**数据范围**

1≤n≤1000

**输入样例:**

```
5
```

**输出样例：**

```
7
```

方法一: 利用完全背包问题解决



![](http://mk-images.tagao.top/img/202204081703786.png?imageslim)

```java
public class _Case01_01整数划分 {
    public static final int M = (int) (1e9 + 7);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        //定义DP数组 从前1~i中选，体积为j
        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;//一个数都不选是一种方案

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k * i <= j; k++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - k * i]) % M;
                }
            }
        }
        System.out.println(dp[n][n]);
    }
}
```





背包问题解法-优化

![](http://mk-images.tagao.top/img/202204081820319.png?imageslim)



```java
public class _Case01_02整数划分 {
    public static final int M = (int) (1e9 + 7);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        //定义DP数组 从前1~i中选，的体积最大
        int[] dp = new int[n + 1];
        dp[0] = 1;//一个数都不选是一种方案
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                    dp[j] = (dp[j] + dp[j - i] )% M;
            }
        }
        System.out.println(dp[n]);
    }
}

```





方法二： 计数DP，划分不同的区间



![](http://mk-images.tagao.top/img/202204081810199.png?imageslim)



```java
public class _Case01_03整数划分 {
    public static final int M = (int) (1e9 + 7);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] dp = new int[n + 1][n + 1];

        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - j][j]) % M;
            }
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = ans +  dp[n][i];
            ans %= M;
        }
        System.out.println(ans);
    }
}
```







# 4.贪心
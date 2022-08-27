# 时间复杂度



**计算复杂度的时候，只统计执行次数最多的(n足够大时)那种固定操作的次数。比如某个算法需要执行加法n2次，除法n次，那么就记其复杂度是O(n2)的。**

*如果复杂度是多个n的函数之和，则只关心随n的增长增长得最快的那个函数*

O(n3+n2 ) => O(n3)

O(2n+n3 ) => O(2n)

O(n! + 3n ) => O(n!) 

```c
 常数复杂度：O(1) 时间(操作次数)和问题的规模无关 // 例如在排好序中的数组取出最大最小值
 对数复杂度：O(log(n))
 线性复杂度：O(n)
 多项式复杂度：O(n ) 
 指数复杂度：O(a^n ) 
 阶乘复杂度：O(n! )
```





# 排序

## qsort( )排序

​	在   **4.指针** 一篇中详细讲解了 qsort()库函数的使用方法



## sort()排序

 在 **STL** 一篇中有讲解 sort() 函数的使用方法



##  选择排序

描述：	如果有N个元素需要排序，那么首先从N个元素中找到最小的那个(称为第0小的)放在第0个位子上(和原来的第0个位子上的元素交换位置)，然后再从剩下的N-1个元素中找到最小的放在第1个位子上，然后再从剩下的N-2个元素中找到最小的放在第2个位子上……直到所有的元素都就位。

```c
void SelectionSort(int a[] ,int size)
{
	for( int i = 0; i < size - 1; ++i ){//每次循环后将第i小的元素放好
	int tmpMin = i; 
	//用来记录从第i个到第size-1个元素中，最小的那个元素的下标
	for( int j = i+1; j < size ; ++j) {
	if( a[j] < a[tmpMin] ) 
		tmpMin = j;
	}
	//下面将第i小的元素放在第i个位子上，并将原来占着第i个位子的元素挪到后面
	int tmp = a[i];
	a[i] = a[tmpMin]; 
	a[tmpMin] = tmp; 
	}
}
```



## 插入排序

*将整个数组a分为有序的部分和无序的两个部分。前者在右，后者在左边。*

* 将整个数组a分为有序的部分和无序的两个部分。前者在左边，后者在右边。
* 开始有序的部分只有a[0]，其余都属于无序的部分
* 每次取出无序部分的第一个（最左边）元素，把它加入到有序部分。假设插入到合适位置p, 则原p位置及其后面的有序部分元素，都向右移动一个位子。有序的部分即增加了一个元素。
* 直到无序的部分没有元素

```c
void InsertionSort(int a[] ,int size)
{
	for(int i = 1;i < size; ++i ) { 
	//a[i]是最左的无序元素，每次循环将a[i]放到合适位置
	for(int j = 0; j < i; ++j) 
		if( a[j]>a[i]) { 
		//要把a[i]放到位置j，原下标j到 i-1的元素都往后移一个位子
		int tmp = a[i];
		for(int k = i; k > j; --k)
			a[k] = a[k-1];
		a[j] = tmp;
		break;
		}
    } 
}
```

## 冒泡排序

* 将整个数组a分为有序的部分和无序的两个部分。前者在右，后者在左边。

1. 开始，整个数组都是无序的。有序的部分没有元素。
2. 每次要使得无序部分最大的元素移动到有序部分第一个元素的左边。移动的方法是：依次比较相邻的两个元素，如果前面的比后面的大，就交换他们的位置。这样，大的元素就像水里气泡一样不断往上浮。移动结束有序部分增加了一个元素。
3. 直到无序的部分没有元素

```c
t=n;
for(i=1;i<n;i++){		
	for(j=1;j<t;j++){
		if(a[j].score>a[j+1].score){
			temp = a[j];
			a[j] = a[j+1];
			a[j+1] = temp;	
		} 	
	}
	t--;  //因为最后一个已经排好序了，可以减少一个，以此类推可以继续减少
}
```



**上面3种简单排序算法，都要做 n2量级次数的比较(n是元素个数）！**





# 查找

## 顺序查找 

​	 时间复杂度 O(n)  不推荐



## 二分查找

- 为了防止( L + R)过大溢出： int mid = L + (R - L ) /2;
- **int mid = (L+R)/2; //取查找区间正中元素的下标**

### BinarySeach

​	写一个函数BinarySeach，在包含size个元素的、从小到大排序的int数组a里查找元素p,如果找到，则返回元素下标，如果找不到，则返回-1。要求复杂度O(log(n))

```c
int BinarySearch(int a[],int size,int p)
{
	int L = 0; //查找区间的左端点
	int R = size - 1; //查找区间的右端点
	while( L <= R) { //如果查找区间不为空就继续查找
		int mid = L+(R-L)/2; //取查找区间正中元素的下标
		if( p == a[mid] ) 
			return mid;
		else if( p > a[mid]) 
			L = mid + 1; //设置新的查找区间的左端点
		else 
			R = mid - 1; //设置新的查找区间的右端点
		}
		return -1;
} //复杂度O(log(n))
```





### LowerBound

​	写一个函数LowerBound，在包含size个元素的、从小到大排序的int数组a里查找比给定整数p小的，下标最大的元素。找到则返回其下标，找不到则返回-1。

```c
int LowerBound(int a[],int size,int p) //复杂度O(log(n))
{
	int L = 0; //查找区间的左端点
	int R = size - 1; //查找区间的右端点
	int lastPos = -1; //到目前为止找到的最优解
	while( L <= R) { //如果查找区间不为空就继续查找
		int mid = L+(R-L)/2; //取查找区间正中元素的下标
		if(a[mid]>= p)
			R = mid - 1;
		else {
			lastPos = mid;
			L = mid+1;
			}
	}
	return lastPos; 
}
```



# 例题：

## 二分法求方程的根

```c
问题描述：求下面方程的一个根：f(x) = x3-5x2+10x-80 = 0
若求出的根是a，则要求 |f(a)| <= 10^-6 
解法：对f(x)求导，得f'(x)=3x2-10x+10。由一元二次方程求根公式知方程 f'(x)= 0 无解，因此f'(x)恒大于0。故f(x)是单调递增的。易知 f(0) < 0且f(100)>0,所以区间[0,100]内必然有且只有一个根。由于f(x)在[0,100]内是单调的，所以可以用二分的办法在区间[0,100]中寻找根。
```



```c
#include<cstdio>  #include<cstdlib>  #include<iostream>  #include<cmath>
double EPS = 1E-6;
using namespace std;
double f(double x) {
	return x*x*x - 5*x*x + 10*x - 180;
}
int main(){
	double r=0,l=100,x;
	double mid = r + (l-r)/2;
	int count = 0; 
	while(r<l){
		count++;
		x = f(mid);
		if( fabs(x)< EPS)
			break;
		else if (x < 0)
			r = mid;	
		else 
			l = mid;
		mid = r + (l-r)/2;	
	}
	cout<<mid<<" "<<count;
	system("pause");
	return 0;
}
```



## 寻找指定和的整数对

**输入n (n<= 100,000)个整数，找出其中的两个数，它们之和等于整数m(假定肯定有解)。题中所有整数都能用int表示**

* 解法一 穷举法，双重循环，时间复杂度是 (n^2)  

  *时间复杂度过高，不选择*

  ```c
  for(int i = 0;i < n-1; ++i)
  	for(int j = i + 1; j < n; ++j) 
  		if( a[i]+a[j] == m)
  			break;
  ```



* 解法二 

  1) 将数组排序，复杂度是O(n×log(n))

  2) 对数组中的每个元素a[i],在数组中二分查找m-a[i]，看能否找到。复杂度log(n)，最坏要查找n-2次，所以查找这部分的复杂度也是O(n×log(n))   

  *时间复杂度是O(n×log(n))*

  ```c
  //排序 时间复杂度是 n*log(n)
  
  //查找 时间复杂度是 n*long(n)
  for(0 - (n-3))//最后两个下标为 n-1 n-2的元素无需再进行查找，因为前面都找了不存在,必定是后面两项
      while(二分查找) 
         break;      
  ```

  

* 解法三 

   解法3：

  1) 将数组排序，复杂度是O(n×log(n))

  2) 查找的时候，设置两个变量i和j,i初值是0,j初值是n-1.看a[i]+a[j],如果大于m，就让j 减1，如果小于m,就让i加1，直至a[i]+a[j]=m。

  ​	这种解法总的复杂度是O(n×log(n))的。

```c
int a[] = {2,3,4,5,6,7,110};
int i=0,j=sizeof(a)/sizeof(a[1])-1;
int m = 115;
while(i<j){	
	if (m == a[i] + a[j]){
		cout<<a[i]<<"   "<<a[j];
		break;
	}
	else if(m < a[i]+a[j])
		j--;
	else
		i++;		
}
```





​	
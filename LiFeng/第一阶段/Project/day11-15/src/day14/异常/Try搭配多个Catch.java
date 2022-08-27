package day14.异常;

public class Try搭配多个Catch {
    public static void main(String[] args) {
        int x = 6;
        int arr[] = new int[] { 1, 2, 3, 4 };
        try {
            arr = null;
            System.out.println(arr[0]);
            x = x / 0;
            arr[0] = 5;
            arr[4] = 10;

        } catch (ArithmeticException e) {
            System.out.println("算术异常ArithmeticException");
            //e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("下标越界异常ArrayIndexOutOfBoundsException");
            e.printStackTrace();
        }catch (NullPointerException e) {
            System.out.println("空指针异常NullPointerException");
            e.printStackTrace();
        }catch (NegativeArraySizeException e) {
            System.out.println("数组下标负数异常NegativeArraySizeException");
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("其他异常Exception");
            e.printStackTrace();
        }
    }
}

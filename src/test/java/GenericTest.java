import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ewan on 28/08/2017.
 */
public class GenericTest {
    //java泛型只在编译阶段有效，不会进入运行阶段。编译时正确检验泛型结果后，会将泛型擦除，从而如下例变成相同的基本类型。
    //因此java泛型只有伪泛型，并不能对性能有提升（因为没有进入运行阶段）
    //泛型作用之一：提供编译检查，在编译时检查对List<String>加进去的元素都是String类型。
    //泛型作用之二：代码复用（通配符）
    //把List<Integer>改为List<int>会编译错误，这也说明了java泛型会在编译时把泛型类型擦除为Object
    //-128到127的int装箱结果会被缓存，以提升性能
    //装箱缓存：–128到127的int或short，'\u0000'到'\u007f'间的char，byte，boolean.
    @Test
    public void sameClass() {
        List<String> stringArrayList = new ArrayList<>();
        List<Integer> integerArrayList = new ArrayList<>();

        assertEquals(stringArrayList.getClass(), integerArrayList.getClass());
    }

    @Test
    public void boxingCache1() {
        List<Integer> bigs = Arrays.asList(100,200,300);
        assertTrue(sumInteger(bigs) == sum(bigs));
        assertTrue(sumInteger(bigs) != sumInteger(bigs));
    }

    @Test
    public void boxingCache2() {
        List<Integer> bigs = Arrays.asList(2,3,4);
        assertTrue(sumInteger(bigs) == sum(bigs));
        assertTrue(sumInteger(bigs) != sumInteger(bigs));
    }

    private static int sum(List<Integer> ints) {
        int s = 0;
        for (int n : ints) { s += n; }
        return s;
    }

    private static Integer sumInteger(List<Integer> ints) {
        Integer s = 0;
        for (Integer n : ints) { s += n; }
        return s;
    }

    @Test
    public void wildCard(){
        List<Integer> integerList = new ArrayList<>();

        //下面一句编译失败，因为虽然Integer继承自Number，
        //所以Integer继承自Number，但是List<Integer>并不能隐式转换为List<Number>
        //但是我们需要再写一个count(List<Integer> integerList)方法吗？
        //类型通配符应运而生
        //?也是实参而不是形参，也即是静态的不是动态的，和Number,String等一样都是一种实际类型
        //?是系统定义好的，不像T这样的需要在泛型类或泛型接口中先定义再使用
        //System.out.println(count(integerList));

        System.out.println(countList(integerList));

        //? extends
        integerList.add(1);
        System.out.println(countNumberList(integerList));
        List<Long> longList = new ArrayList<>();
        longList.add(new Long(0));
        System.out.println(countNumberList(longList));

        //?-接口也是extends
        //多个的话写法比C#复杂，需要用T绕一层
        integerList.add(2);
        System.out.println(compare(integerList));

        //上面的写法其实是java泛型方法的写法
        //前面写上<T>才会被识别为泛型方法
        //?extends多个的话约束只能写在<T>里了
    }

    private int count(List<Number> numberList) {
        return numberList.size();
    }

    private int countList(List<?> list) {
        return list.size();
    }

    private int countNumberList(List<? extends Number> list) {
        return list.get(0).intValue() + 1;
    }

    private <T extends Number & Comparable<? super T>> int
                compare(List<? extends T> list){
        return list.get(0).compareTo(list.get(1));
    }

    /**
     * 泛型方法的基本介绍
     * @param tClass 传入的泛型实参
     * @return T 返回值为T类型
     * 说明：
     *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    private <T> T genericMethod(Class<T> tClass)throws InstantiationException ,
            IllegalAccessException{
        T instance = tClass.newInstance();
        return instance;
    }

    @Test
    public void extensibleParamsUsingGeneric() {
        printMsg(1,"dfsd",true,'C');
    }

    private  <T> void printMsg(T... args) {
        for (T t:
             args) {
            System.out.println(t + ",");
        }
    }

    @Test
    public void genericArray() {
        String[] a = new String[10];
        //下面异常
        //List<String>[] b = new ArrayList<String>[10];

        List<?>[] d = new ArrayList<?>[10];

        List<String>[] c = (List<String>[])new ArrayList[10];
        List<String>[] e = new ArrayList[10];

        //为什么new ArrayList<String>[10]不允许？是因为ArrayList<String>和ArrayList<Integer>等其实是同一种类型

        //ArrayList<String> stringLists = new ArrayList<String>[10];  // compiler error, but pretend it's allowed
        //stringLists[0] = new ArrayList<String>();   // OK
        //stringLists[1] = new ArrayList<Integer>();  // ArrayList<String>应该要有String的类型检查
                                                        // 但是因为泛型擦出，在运行时却检查不出来


        //通配符是静态的，理论上可以转换为任意类型，因此也不存在类型检查
        //List<?>[] d = new ArrayList<?>[10];

        //ArrayList没限定类型，因此是允许的。但存在潜在的类型转换错误的风险。
        //List<String>[] c = (List<String>[])new ArrayList[10];
        //List<String>[] e = new ArrayList[10];
    }
}

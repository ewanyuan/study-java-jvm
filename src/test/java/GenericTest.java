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
    //它的最大作用就是提供编译检查，在编译时检查对List<String>加进去的元素都是String类型。
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
}

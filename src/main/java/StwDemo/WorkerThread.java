package StwDemo;

import java.util.HashMap;

/**
 * Created by ewan on 10/08/2017.
 */

/**
 *注意jvm设置了这些参数
 * -Xmx512M -Xms512M (不让内存扩容）
 * -XX:+UserSerialGC (非后台GC，所以可以阻塞工作线程？)
 * -XLoggc:gc.log -XX:+PrintGCDetails
 * -Xmn1M (让byte[]进入老年代，以便用标记清楚算法？）
 * -XX:PretenureSizeThreshold=50（对象超过多大直接在老年代分配，单位字节）
 * -XX:MaxTenuringThreshold=1（让对象可以经过一次Minor GC就进入老年代）
 */
public class WorkerThread extends Thread {
    HashMap<Long, byte[]> map = new HashMap<>();

    @Override
    public void run(){
        try{
            while (true) {
                //大于450M时，清理内存
                if (map.size() * 512 / 1024 / 1024 >= 450) {
                    System.out.println("====准备清理====:" + map.size());
                    map.clear();
                }

                for (int i = 0; i < 1024; i++) {
                    map.put(System.nanoTime(), new byte[512]);
                }

                //不断产生又销毁对象，GC疲于奔命
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
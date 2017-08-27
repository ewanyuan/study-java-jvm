package classLoaderDemo1;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by ewan on 27/08/2017.
 */
public class HelloMain {

    public static void main(String[] args) {
        new Thread(()->{
            while (true) {
                reloadAndGreet();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void reloadAndGreet() {
        String jarName = "Worker.jar";
        try {
            File file = new File(jarName);
            URL url = file.toURL();
            URLClassLoader loader = new URLClassLoader((new URL[]{url}));
            Class theClass = loader.loadClass("Worker");

            IWorker worker = (IWorker) theClass.newInstance();
            worker.doit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

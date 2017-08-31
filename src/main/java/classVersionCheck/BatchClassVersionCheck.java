package classVersionCheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BatchClassVersionCheck {

    public static void main(String[] args) {
        try {
            BatchClassVersionCheck bcvc = new BatchClassVersionCheck();
            HashMap<String,List<String>> versionMap = bcvc.getDirectoryClassVersionInfo(new File("/opt/testClasses"));
            for (String version : versionMap.keySet()){
                System.out.println("version:" + version);
                List<String> list = versionMap.get(version);
                for (String file : list){
                    System.out.println(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //保存文件夹中的class文件版本信息
    //key是版本号
    //value是对应文件的绝对路径
    private HashMap<String,List<String>> classVersionInfoMap;

    /**
     * 获取文件夹中class类的版本信息
     * @param dir
     * @return
     * @throws Exception
     */
    public HashMap<String,List<String>> getDirectoryClassVersionInfo(File dir) throws Exception{
        classVersionInfoMap = new HashMap<String,List<String>>();
        searchClass(dir);
        return classVersionInfoMap;
    }

    /**
     * 递归方法
     * 搜索当前文件夹下的class文件，并计算版本信息，保存在map中
     * 当搜索到文件夹时，递归搜索
     * @param dir
     * @throws Exception
     */
    protected void searchClass(File  dir) throws Exception{
        File[] childFiles = dir.listFiles();
        for (File childFile : childFiles){
            if (childFile.isDirectory()){
                //递归搜索子文件夹
                searchClass(childFile);
            } else{
                if (childFile.getName().toLowerCase().endsWith(".class")){
                    //搜索出class文件
                    //将版本信息记录在map中
                    putVersionInfo(ClassVersion.checkClassVersion(childFile), childFile.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 将版本信息记录在map中
     * @param version
     * @param absolutePath
     */
    private void putVersionInfo(String version,String absolutePath){
        List<String> list = null;
        if (classVersionInfoMap.containsKey(version)){
            list = classVersionInfoMap.get(version);
        } else{
            list = new ArrayList<String>();
        }
        list.add(absolutePath);
        classVersionInfoMap.put(version, list);
    }

}
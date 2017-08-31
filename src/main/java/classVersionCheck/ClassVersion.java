package classVersionCheck;

import java.io.File;
import java.io.FileInputStream;

public class ClassVersion {

    /**
     * 检查class文件的版本号
     * @param classFile
     * @return
     * 返回值为：JDK1.4 JDK1.5 ... 或者unknown
     * @throws Exception
     */
    public static String checkClassVersion(File classFile) throws Exception{
        byte[] data = new byte[8];

        FileInputStream in = new FileInputStream(classFile);
        //前面8个字节CA FE BA BE是固定的。
        //随后4个字节00 00是次版本号
        //再后面的4个字节00 34是JDK的版本号(JDK1.8)。
        in.read(data, 0, 8);
        in.close();

        //计算出class文件的主次版本号
        int minor_version = (((int)data[4])<<8)+data[5];
        int major_version = (((int)data[6])<<8)+data[7];
        return translateVersionToJDK(major_version);
    }

    /**
     * 根据主版本号，转换成JDK版本
     * 48是JDK1.4，49是JDK1.5，依次类推
     * @param major_version
     * @return
     */
    public static String translateVersionToJDK(final int major_version){
        switch(major_version){
            case 48:
                return "JDK1.4";
            case 49:
                return "JDK1.5";
            case 50:
                return "JDK1.6";
            case 51:
                return "JDK1.7";
            case 52:
                return "JDK1.8";
            default:
                return "unknown";
        }
    }

}
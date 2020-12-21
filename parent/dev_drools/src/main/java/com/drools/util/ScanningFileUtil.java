package com.drools.util;

import com.drools.vo.ClassVo;
import com.drools.vo.PropertyVo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class ScanningFileUtil {

    private static ClassLoader classLoader = ScanningFileUtil.class.getClassLoader();//默认使用的类加载器

    private static final String STARATEGY_PATH = "com.drools.entity.clm";//需要扫描的策略包名

    public static List<PropertyVo> getEntityFields(String className) {
        List<PropertyVo> result = new ArrayList<>();
        try{
            Class<?> clz = Class.forName(className);
            // 获取实体类的所有属性，返回Field数组
//            Field[] fields = clz.getDeclaredFields();
//            result = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
            result = FiledUtil.getChName(clz);
        }catch (Exception e){
            log.error("获取属性异常:{}",e);
        }
        return result;
    }

    /**
     * 获取包下所有实现了superStrategy的类并加入list
     */
    public static List<ClassVo> addClass(){
        List<ClassVo> result = new ArrayList<>();
        URL url = classLoader.getResource(STARATEGY_PATH.replace(".", "/"));
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            // 本地自己可见的代码
           File file = findClassLocal(STARATEGY_PATH);
           File[] files = file.listFiles();
            for (File file1 : files) {
                ClassVo vo = new ClassVo();
                String cls = STARATEGY_PATH+"."+file1.getName().replace(".class", "");
                vo.setClassName(cls);
                vo.setName(file1.getName().replace(".class", ""));
                result.add(vo);
            }
        } else if ("jar".equals(protocol)) {
            // 引用jar包的代码
            result = findClassJar(STARATEGY_PATH);
        }
        return result;
    }

    /**
     * 本地查找
     * @param packName
     */
    private static File findClassLocal(final String packName){
        URI url = null ;
        try {
            url = classLoader.getResource(packName.replace(".", "/")).toURI();
        } catch (URISyntaxException e1) {
            throw new RuntimeException("未找到策略资源");
        }

        File file = new File(url);
        file.listFiles(new FileFilter() {

            public boolean accept(File chiFile) {
                if(chiFile.isDirectory()){
                    findClassLocal(packName+"."+chiFile.getName());
                }
                if(chiFile.getName().endsWith(".class")){
                    Class<?> clazz = null;
                    try {
                        clazz = classLoader.loadClass(packName + "." + chiFile.getName().replace(".class", ""));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        return file;
    }

    /**
     * jar包查找
     * @param packName
     */
    private static List<ClassVo> findClassJar(final String packName){
        List<ClassVo> result = new ArrayList<>();

        String pathName = packName.replace(".", "/");
        JarFile jarFile  = null;
        try {
            URL url = classLoader.getResource(pathName);
            JarURLConnection jarURLConnection  = (JarURLConnection )url.openConnection();
            jarFile = jarURLConnection.getJarFile();
        } catch (IOException e) {
            throw new RuntimeException("未找到策略资源");
        }

        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();

            if(jarEntryName.contains(pathName) && !jarEntryName.equals(pathName+"/")){
                //递归遍历子目录
                if(jarEntry.isDirectory()){
                    String clazzName = jarEntry.getName().replace("/", ".");
                    int endIndex = clazzName.lastIndexOf(".");
                    String prefix = null;
                    if (endIndex > 0) {
                        prefix = clazzName.substring(0, endIndex);
                    }
                    findClassJar(prefix);
                }
                if(jarEntry.getName().endsWith(".class")){
                    Class<?> clazz = null;
                    try {
                        ClassVo vo = new ClassVo();
                        vo.setClassName(packName+"."+jarEntry.getName().replace("/", ".").replace(".class", ""));
                        vo.setName(jarEntry.getName().replace("/", ".").replace(".class", ""));
                        result.add(vo);
                        clazz = classLoader.loadClass(jarEntry.getName().replace("/", ".").replace(".class", ""));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return result;
    }
}

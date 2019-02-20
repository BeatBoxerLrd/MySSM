package com.derry.servlet;

import com.derry.annotation.Controller;
import com.derry.annotation.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @Author:LiuRuidong
 * @Description: DispathcerServlet
 * @Date: Created in 14:50 2019/2/18
 * @Modified By:
 */
public class DispacherServlet extends HttpServlet {

    private Properties properties = new Properties();
    private List<String> classNameList = new ArrayList<>();
    private Map<String,Object> iocMap = new HashMap();
    private Map<String,Method> handlerMapping = new HashMap<>();
    private Map<String,Object> controllerMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描用户指定包下的所有类，并将其添加到classNameList中去
        doScanner(properties.getProperty("scanPackage"));

        //3.将扫描到的类，通过反射创建实例，并将其存放于iocMap中去
        doInstance();

        //4.初始化HandlerMapping,将url和method对应上
        initHandlerMapping();

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispather(req,resp);
        } catch (Exception e) {
            //e.printStackTrace();
            resp.getWriter().write("code:500,Server Exception!");
        }

    }

    private void doDispather(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (handlerMapping.isEmpty()){
            return;
        }

        String url = req.getRequestURI();
        String context = req.getContextPath();
        //得到截取之后的url
        url = url.replace(context,"").replaceAll("/+","/");
        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!");
            return;
        }
        //获取到该Url对应的方法
        Method method = handlerMapping.get(url);
        //获取该方法需要的参数列表类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] paramValues = new Object[parameterTypes.length];
        //获取请求对应的参数
        Map<String, String[]> reqMap = req.getParameterMap();
        for (int i = 0; i <parameterTypes.length ; i++) {
            String needParamType = parameterTypes[i].getSimpleName();
            if (needParamType.equals("HttpServletRequest")){
                paramValues[i] = req;
                continue;
            }
            if(needParamType.equals("HttpServletResponse")){
                paramValues[i]=resp;
                continue;
            }
            if (needParamType.equals("String")){
                for (Map.Entry<String, String[]> param:reqMap.entrySet()
                     ) {
                    String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]","").replaceAll(",\\s",",");
                    paramValues[i]=value;
                }
            }
            //反射进行调用
            method.invoke(this.controllerMap.get(url),paramValues);
        }
        
    }

    /**
     * @Author：LiuRuidong
     * @Description:初始化handlerMapping,初始化url及其对应的方法和url及其对应的类，为了反射去调用
     * @Date:2019/2/18 20:25
     * @Param:
     * @return
     */
    private void initHandlerMapping() {
        if(iocMap.isEmpty()){
            return;
        }
        for (Map.Entry<String,Object> entry:iocMap.entrySet()
             ) {
            Class<?extends Object>  clazz = entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(Controller.class)){
                continue;
            }
            String baseURL = "";
            if(clazz.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseURL = requestMapping.value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method:methods
                 ) {
                if (!method.isAnnotationPresent(RequestMapping.class)){
                    continue;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String url = requestMapping.value();
                url = (baseURL+"/"+url).replaceAll("/+","/");
                handlerMapping.put(url,method);
                try {
                    controllerMap.put(url,clazz.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * @Author：LiuRuidong
     * @Description: 该方法时根据类名，利用反射得到其名称和实例，存储在iocMap中
     * @Date:2019/2/18 20:05
     * @Param:
     * @return
     */
    private void doInstance() {
        if(classNameList.isEmpty()){
            return;
        }
        for (String className:classNameList
             ) {
            try {
                Class<?> clazz = Class.forName(className);
                //如果该类上面有controller注解
                if(clazz.isAnnotationPresent(Controller.class)){
                    iocMap.put(toLowerFirstWord(clazz.getSimpleName()),clazz.newInstance());
                }else{
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //
    private String toLowerFirstWord(String string) {
        char[] chars = string.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }

    /**
     * @Author：LiuRuidong
     * @Description: 扫描包将所有的类加载到classNameList中去
     * @Date:2019/2/18 17:57
     * @Param:
     * @return
     */
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File file = new File(url.getFile());
        //返回file路径下的所有文件和文件夹
        File[] listFiles = file.listFiles();
        for (File f: listFiles
             ) {
            if (f.isDirectory()){
                doScanner(scanPackage+"."+f.getName());
            }else{
                //返回com.derry.test.controller.TestController
                String className = scanPackage+"."+f.getName().replace(".class","");
                classNameList.add(className);
            }
        }
    }
    /**
     * @Author：LiuRuidong
     * @Description: 加载文件，将其以流形式加载到properties中去
     * @Date:2019/2/18 17:59
     * @Param:
     * @return
     */
    private void  doLoadConfig(String location){
        //把web.xml中的contextConfigLocation对应value值的文件加载到留里面
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            //System.out.println(inputStream);
            //用Properties文件加载文件里的内容
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关流
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}

package servlet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import servlet.thymeleaf.ViewBaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private Map<String,Object> beanMap = new HashMap<>();

    //解析Xml
    public void init() throws ServletException {
        super.init();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder() ;
            //3.创建Document对象
            Document document = documentBuilder.parse(inputStream);

            //4.获取所有的bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for(int i = 0 ; i<beanNodeList.getLength() ; i++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element)beanNode ;
                    String beanId =  beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class controllerBeanClass = Class.forName(className);
                    Object beanObj = controllerBeanClass.newInstance() ;
                    beanMap.put(beanId , beanObj) ;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public DispatcherServlet(){

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //假設url是 http://localhost:8080/hello.do
        //ServletPath就是 /hello.do
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDoIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDoIndex);

        //依照req取得的servlet去
        Object controllerBean = beanMap.get(servletPath);

        //獲取operate參數
        String operate = req.getParameter("operate");
        if(StringUtils.isEmpty(operate)){ //判斷是否為空
            operate = "index"; //為空則導向index
        }

        try {
            //依operate決定使用哪個method
            Method[] methods = controllerBean.getClass().getDeclaredMethods();
            for (Method method : methods){
                if(operate.equals(method.getName())){
                    if(method != null){
                        //1.統一獲取參數

                        //獲取當前方法參數
                        Parameter[] parameters = method.getParameters();
                        Object[] parameterValues = new Object[parameters.length];
                        for (int i = 0; i< parameters.length; i++){
                            Parameter parameter = parameters[i];
                            String parameterName = parameter.getName();
                            //篩選req,resp與session
                            if("req".equals(parameterName)){
                                parameterValues[i] = req;
                            }else if("resp".equals(parameterName)){
                                parameterValues[i] = resp;
                            }else if("session".equals(parameterName)){
                                parameterValues[i] = req.getSession();
                            }else {
                                //從請求中
                                String parameterValue = req.getParameter(parameter.getName());
                                String typeName = parameter.getType().getName();

                                Object parameterObj = parameterValue ;

                                if(parameterObj!=null) {
                                    if ("java.lang.Integer".equals(typeName)) {
                                        parameterObj = Integer.parseInt(parameterValue);
                                    }
                                }

                                parameterValues[i] = parameterObj;
                            }
                        }
                        //2.controller方法調用
                        method.setAccessible(true);
                        Object methodReturnObj = method.invoke(controllerBean,parameterValues);

                        //3.視圖處理
                        String methodReturnStr = (String) methodReturnObj;
                        if(methodReturnStr.startsWith("redirect")){
                            String redirectStr = methodReturnStr.substring("redirect:".length());
                            resp.sendRedirect(redirectStr);
                        }else {
                            super.processTemplate(methodReturnStr,req,resp);
                        }
                    }
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
// 常见错误： IllegalArgumentException: argument type mismatch
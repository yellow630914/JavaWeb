package servlet;

import factory.BeanFactory;
import factory.ClassPathXmlApplicationContext;
import servlet.thymeleaf.ViewBaseServlet;
import util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;
    //解析Xml
    public void init() throws ServletException {
        super.init();
        //獲取IOC
        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if (beanFactoryObj != null){
            beanFactory = (BeanFactory) beanFactoryObj;
        }else {
            throw new RuntimeException("IOC容器獲取失敗!");
        }
    }


    public DispatcherServlet(){

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //假設url是 http://localhost:8080/hello.do
        //ServletPath就是 /hello.do
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDoIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDoIndex);

        //依照req取得的servlet去
        Object controllerBean = beanFactory.getBean(servletPath);

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
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Dispatcher層錯誤");
        }
    }
}
// 常见错误： IllegalArgumentException: argument type mismatch
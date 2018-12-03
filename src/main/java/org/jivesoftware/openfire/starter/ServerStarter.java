package org.jivesoftware.openfire.starter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * 启动xmppServer
 */
@WebListener
public class ServerStarter implements ServletContextListener {

    private static final String DEFAULT_LIB_DIR = "../lib";
    private static final String DEFAULT_ADMIN_LIB_DIR = "../plugins/admin/webapp/WEB-INF/lib";

    private void start(){
        try {
            final ClassLoader parent = findParentClassLoader();

            File libDir;

            libDir = new File(DEFAULT_LIB_DIR);

            String adminLibDirString = DEFAULT_ADMIN_LIB_DIR;

            File adminLibDir = new File(adminLibDirString);

            ClassLoader loader = new JiveClassLoader(parent, libDir);

            Thread.currentThread().setContextClassLoader(loader);
            Class containerClass = loader.loadClass(
                    "org.jivesoftware.openfire.XMPPServer");
            containerClass.newInstance();
        }catch (Exception e) {
                e.printStackTrace();
            }

    }


    //该方法在ServletContext启动之后被调用，并准备好处理客户端请求
    public void contextInitialized(ServletContextEvent event)  {
        new ServerStarter().start();
    }

    //这个方法在ServletContext 将要关闭的时候调用
    public void contextDestroyed(ServletContextEvent event){

    }

    private ClassLoader findParentClassLoader(){
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null){
            parent = this.getClass().getClassLoader();
            if (parent == null){
                parent = ClassLoader.getSystemClassLoader();
            }
        }

        return parent;
    }
}

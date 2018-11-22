package org.jivesoftware.openfire.Connection.container;

import jdk.internal.util.xml.impl.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class XMPPServer {

    private XMPPServer instance;

    private boolean initialized = false;

    private boolean started = false;

    private File openfireHome;

    public XMPPServer(){
        if (instance != null){
            throw new IllegalStateException("A Server is already running");
        }
        instance = this;

    }

    /**
     * 获取openfire路径，并验证该路径是否正确
     */
    private void locateOpenfire (){
        String jiveConfigName = "conf" + File.separator + "openfire.xml";

        //1. 如果系统中有openfire.xml路径，则从系统中取
        if(openfireHome == null){
            //获取系统变量，这个变量需要用户自己设定
            String homeProperty = System.getProperty("openfireHOme");
            try {
                if (homeProperty != null){
                    openfireHome = verifyHome(homeProperty,jiveConfigName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        //2、如果系统中没有，则查看其子目录中是否有该文件
        if(openfireHome == null){
            //在try中可以自动关闭
            try {
                openfireHome = verifyHome("..",jiveConfigName).getCanonicalFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else{

        }
    }

    /**
     * 检测openfire.xml路径是一个正确的路径
     * @param homeGuess 文件路径
     * @param jiveConfigName 文件名
     * @return
     */
    private File verifyHome(String homeGuess, String jiveConfigName) throws FileNotFoundException {
        File openfireHome = new File(homeGuess);
        //创建文件，并检查文件名是否正确
        File configFile = new File(openfireHome,jiveConfigName);

        //如果文件不存在
        if (!configFile.exists()){
            throw new FileNotFoundException();
        }else {
            try {
                return new File(openfireHome.getCanonicalPath());
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        }
    }
}

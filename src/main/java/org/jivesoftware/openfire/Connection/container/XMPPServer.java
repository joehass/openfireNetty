package org.jivesoftware.openfire.Connection.container;

import org.jivesoftware.Bean.Jive;
import org.jivesoftware.mapper.openfire.mapper.OfIdMapper;
import org.jivesoftware.mapper.openfire.mapper.OfPropertyMapper;
import org.jivesoftware.openfire.Connection.redis.RedisPoolMgr;
import org.jivesoftware.openfire.Connection.util.openfire.TaskEngine;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimerTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-redis.xml")
public class XMPPServer {

    private boolean initialized = false;

    private boolean started = false;

    private File openfireHome;

    private boolean setupMode = true;

    private static final Logger logger = LoggerFactory.getLogger(XMPPServer.class);

    //用户输入该命令则退出
    public static final String EXIT = "exit";

    @Autowired
    private TaskEngine taskEngine;

    private ClassLoader loader;

    @Autowired
    private RedisPoolMgr redisPoolMgr;

    @Autowired
    private OfPropertyMapper ofPropertyMapper;

    @Autowired
    private OfIdMapper ofIdMapper;

    private Map<Class, Module> modules = new LinkedHashMap<>();

    private static final String STARTER_CLASSNAME =
            "org.jivesoftware.openfire.starter.ServerStarter";

    public XMPPServer(){
        start();
    }

    public void start(){
        initialize();

        File pluginDir = new File(openfireHome, "plugins");

        //插件用spring拦截器替代

        //判断是否可以连上数据库
        verifyDataSource();

    }

    /**
     * 加载模块
     */
    private void loadModule(){
        loadModule(ConnectionManagerImpl.class.getName());
    }

    private void loadModule(String module) {
        try {
            Class<Module> modClass = (Class<Module>) loader.loadClass(module);
            Module mod = modClass.newInstance();
            this.modules.put(modClass, mod);
        }
        catch (Exception e) {
            e.printStackTrace();
            //logger.error(LocaleUtils.getLocalizedString("admin.error"), e);
        }
    }

    /**
     * 确保可以访问数据库
     */
    private void verifyDataSource(){
        //如果连不上会自动抛出异常
        ofIdMapper.count();
    }

    private void initialize(){
        Jive jive = locateOpenfire();
        if ("true".equals(jive.getSetup())){
            setupMode = false
            ;
        }

        if (isStandAlone()){
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
            taskEngine.schedule(new Terminator(),1000,1000);
        }

        loader = Thread.currentThread().getContextClassLoader();

        migrateProperty("xmpp.domain");
        migrateProperty("xmpp.fqdn");

        //暂时不启动缓存
        //CacheFactory.initialize();

        //if (isStan)
    }



    /**
     * 用于将缓存中的数据保存到数据库中
     * @param name
     */
    public void migrateProperty(String name){
        String value = redisPoolMgr.get(name);

        ofPropertyMapper.insertProperty(name,value);
    }

    public boolean isStandAlone(){
        boolean standalone;

        try {
            standalone = Class.forName(STARTER_CLASSNAME) != null;
        }
        catch (ClassNotFoundException e) {
            standalone = false;
        }
        return standalone;
    }

    public Jive locateOpenfire(){
        Jive object = new Jive();
        try {
        String xmlPath = "openfire.xml";
        JAXBContext context = JAXBContext.newInstance(xmlPath);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        object = (Jive) unmarshaller.unmarshal(new File(xmlPath));

        return object;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 获取openfire路径，并验证该路径是否正确
     */
//    private void locateOpenfire (){
//        String jiveConfigName = "conf" + File.separator + "openfire.xml";
//
//        Resource is = new FileSystemResource("E:\\Users\\panteng\\Workspaces\\MyEclipse 10\\mySpring\\src\\applicationContext.xml");
//        BeanFactory factory = new XmlBeanFactory((Resource) is);
//        Action bean = (Action) factory.getBean("theAction");
//        System.out.println(bean.execute("Rod"));
//
//        //1. 如果系统中有openfire.xml路径，则从系统中取
//        if(openfireHome == null){
//            //获取系统变量，这个变量需要用户自己设定
//            String homeProperty = System.getProperty("openfireHOme");
//            try {
//                if (homeProperty != null){
//                    openfireHome = verifyHome(homeProperty,jiveConfigName);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //2、如果系统中没有，则查看其子目录中是否有该文件
//        if(openfireHome == null){
//            //在try中可以自动关闭
//            try {
//                openfireHome = verifyHome("..",jiveConfigName).getCanonicalFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        else{
//
//        }
//    }

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

    /**
     * 开启一个线程确保服务器能没有任何问题的关闭
     */
    private class ShutdownHookThread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);

                System.exit(0);
            } catch (InterruptedException e) {
                // Ignore.
            }
        }
    }

    /**
     * 这个定时器主要是为了监控系统控制台上输入的命令
     * m目前只支持退出open fire命令
     */
    private class Terminator extends TimerTask {
        private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        @Override
        public void run() {
            try{
                //准备接受客户端输入
                if (stdin.ready()){
                    if (EXIT.equalsIgnoreCase(stdin.readLine())){
                        System.exit(0);
                    }
                }
            }catch (IOException ioe){
                logger.error("Error reading console input", ioe);
            }

        }
    }
}

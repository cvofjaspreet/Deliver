package jaspreet.deliver.utils;

/**
 * Created by jaspret on 11/10/15.
 */
public interface Config {

    class AppConfig {

       public static boolean debugEnabled = true;

    }

    class XMPPConfig{

        public static int port= 5222;
        public static int socketTimeOut=30000;
    }

}

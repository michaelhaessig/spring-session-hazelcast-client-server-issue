package server;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.UserCodeDeploymentConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * User: Michael Hässig
 * Email: michael.haessig (at) gmail (dot) com
 * Date: 03.02.19
 * Time: 12:30
 */
public class HazelcastServer {

    public static void main(String[] args){

        Config config = new Config();
        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701);

        // enable code deploy
        UserCodeDeploymentConfig distCLConfig = config.getUserCodeDeploymentConfig();
        distCLConfig.setEnabled( true )
                .setClassCacheMode( UserCodeDeploymentConfig.ClassCacheMode.ETERNAL )
                .setProviderMode( UserCodeDeploymentConfig.ProviderMode.LOCAL_AND_CACHED_CLASSES );


        // run instance ( keeps the jvm running )
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
    }
}

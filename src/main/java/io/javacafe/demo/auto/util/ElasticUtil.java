package io.javacafe.demo.auto.util;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticUtil {


    
    /**
     * ES 클라이언트
     */
    private static Client client = null;
    

    
    /**
     * ES Client 객체를 리턴한다.
     * 
     * @return
     */
    public static Client getEsClient() {
        String clusterName = "javacafe-cluster";
        String ip = "127.0.0.1";
        String port = "9300";
   
        return getEsClient(clusterName, ip, port);
    }
    
    
    /**
     * ES Client 객체를 리턴한다.
     * 
     * @param clusterName
     * @param ip
     * @param port
     * @return
     */
    @SuppressWarnings("resource")
    public static Client getEsClient(String clusterName, String ip, String port) {        
        if (null == client) {

            Settings esSettings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .build();      
            
            try {
                client = new PreBuiltTransportClient(esSettings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName(ip), Integer.parseInt(port)));
            
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }      
        }

        return client;
    }
    


    
}

















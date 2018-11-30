package com.netty.rpc.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author xyl
 * @Create 2018-11-29 14:19
 * @Desc 注册中心，zk实现
 **/
@Slf4j
public class ServiceCenter {

    private static final String APPS_PATH = "/apps";
    private static ZooKeeper zk;

    private static void createConnect() throws IOException, InterruptedException, KeeperException {
        if (zk!=null) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String serverCenterAddress = "localhost";

        zk = new ZooKeeper(serverCenterAddress,30000,null);
        countDownLatch.await();

        if (zk.exists(APPS_PATH, false) == null) {  //不存在根节点就创建
            zk.create(APPS_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);     //持久化节点
        }
    }

    public static void register(String serviceName, String address){
        //服务注册
        try {
            createConnect();
            if (zk.exists(APPS_PATH + "/" + serviceName,false) == null) {       //不存在服务节点就创建
                zk.create(APPS_PATH + "/" + serviceName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String path = zk.create(APPS_PATH + "/" + serviceName + "/", address.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);     //临时节点
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public String getService(String serviceName) throws KeeperException, InterruptedException {
        List<String> apps = zk.getChildren(APPS_PATH + serviceName,false);
        if (apps.isEmpty()) {
            return null;
        }
        byte[] data = zk.getData(apps.get(0), false, null);
        return new String(data);
    }

}

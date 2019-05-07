package 五.使用Zookeeper.开源客户端.ZkClient;

import org.I0Itec.zkclient.ZkClient;

/**
 * ZkClient 创建节点/删除节点/读取数据/更新数据/检测节点是否存在
 *
 * @author luohao
 * @date 2019/05/07
 */
public class ZkClient_Demo {

    public static void main(String[] args) {


        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 50000, 5000);

        System.out.println("Zookeeper session established");

        String path = "/zkClient";

        //创建节点
        createNode(zkClient, path);

        //删除节点
        deleteNode(zkClient, path);

        //读取数据
        getChildren(zkClient,path);


    }


    private static void createNode(ZkClient zkClient, String path) {

        zkClient.createPersistent(path + "/c1", true);
    }

    private static void deleteNode(ZkClient zkClient, String path) {
        zkClient.deleteRecursive(path);
    }

    private static void getChildren(ZkClient zkClient, String path) {
        zkClient.getChildren(path);
    }



}

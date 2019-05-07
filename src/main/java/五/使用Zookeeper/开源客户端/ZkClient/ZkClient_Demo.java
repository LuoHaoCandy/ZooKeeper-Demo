package 五.使用Zookeeper.开源客户端.ZkClient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * ZkClient 创建节点/删除节点/读取数据/更新数据/检测节点是否存在
 *
 * @author luohao
 * @date 2019/05/07
 */
public class ZkClient_Demo {

    public static void main(String[] args) throws Exception {


        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 50000, 5000);

        System.out.println("Zookeeper session established");

        String path = "/zkClient";



        //注册节点变化监听事件
        subscribeChildChanges(zkClient,path);

        //注册内容变化监听事件
        subscribeDataChanges(zkClient,path);


        //创建节点
        createNode(zkClient, path);
        Thread.sleep(1000);


        //写数据
        writeNode(zkClient, path);
        Thread.sleep(1000);



        //读取数据
        getChildren(zkClient,path);
        Thread.sleep(1000);



        //删除节点
        deleteNode(zkClient, path);

        Thread.sleep(1000);

    }




    private static void subscribeChildChanges(ZkClient zkClient,String path) {
        //设置监听
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

                System.out.println("parentPath"+parentPath+";currentChilds:"+currentChilds);
            }
        });
    }

    private static void subscribeDataChanges(ZkClient zkClient, String path) {
       zkClient.subscribeDataChanges(path, new IZkDataListener() {
           @Override
           public void handleDataChange(String dataPath, Object data) throws Exception {
               System.out.println("handleDataChange----dataPath:"+dataPath+";data:"+data);
           }

           @Override
           public void handleDataDeleted(String dataPath) throws Exception {
               System.out.println("handleDataDeleted----dataPath:"+dataPath);

           }
       });
    }


    private static void createNode(ZkClient zkClient, String path) {

        System.out.println("createNode");
        zkClient.createPersistent(path + "/c1", true);

    }

    private static void deleteNode(ZkClient zkClient, String path) {
        System.out.println("deleteNode");
        zkClient.delete(path+"/c1");
    }

    private static void getChildren(ZkClient zkClient, String path) {
        zkClient.getChildren(path);

    }

    private static void writeNode(ZkClient zkClient, String path) {
        zkClient.writeData(path,"LuoHao");
    }



}

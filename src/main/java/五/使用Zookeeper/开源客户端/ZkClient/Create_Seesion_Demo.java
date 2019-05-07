package 五.使用Zookeeper.开源客户端.ZkClient;

import org.I0Itec.zkclient.ZkClient;

/**
 * 使用ZkClient创建会话
 *
 * @author luohao
 * @date 2019/05/07
 */
public class Create_Seesion_Demo {

    public static void main(String[] args) {
        ZkClient zkClient=new ZkClient("127.0.0.1:2181",50000,5000 );

        System.out.println("Zookeeper session established");
    }
}

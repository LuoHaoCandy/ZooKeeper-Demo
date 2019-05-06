package 五.使用Zookeeper.Java客户端API使用;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * 同步创建节点
 *
 * @author luohao
 * @date 2019/05/06
 */
public class Create_Sync_Node_Demo implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",5000,new Create_Sync_Node_Demo());

     //   System.out.println(zooKeeper.getState());

        countDownLatch.await();

        String path=zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("Success create znode :"+path);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState()== Event.KeeperState.SyncConnected){

            System.out.println("state:"+watchedEvent.getState());
            System.out.println("watchedEvent is "+watchedEvent);

            countDownLatch.countDown();

        }

    }
}

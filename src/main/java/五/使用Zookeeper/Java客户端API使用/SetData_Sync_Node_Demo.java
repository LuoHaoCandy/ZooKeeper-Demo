package 五.使用Zookeeper.Java客户端API使用;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 同步更新节点
 *
 * @author luohao
 * @date 2019/05/06
 */
public class SetData_Sync_Node_Demo implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new SetData_Sync_Node_Demo());

        String path = zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        countDownLatch.await();
        Stat stat=zooKeeper.setData(path, "123".getBytes(), -1);


        // version CAS 原理
        zooKeeper.setData(path, "456".getBytes(), stat.getVersion());

        zooKeeper.setData(path, "789".getBytes(), stat.getVersion());


    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }
}

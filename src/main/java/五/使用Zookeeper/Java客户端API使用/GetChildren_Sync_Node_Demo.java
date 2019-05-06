package 五.使用Zookeeper.Java客户端API使用;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 同步获取子节点信息
 *
 * @author luohao
 * @date 2019/05/06
 */
public class GetChildren_Sync_Node_Demo implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper = null;


    public static void main(String[] args) throws Exception {

        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildren_Sync_Node_Demo());

        countDownLatch.await();

        //创建节点
        String path = zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        String path1 = zooKeeper.create("/zk-test-ephemeral/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        //对节点进行一次getChildren，由此来注册NodeChildrenChanged
        System.out.println(zooKeeper.getChildren(path, true));

        //节点变更，触发NodeChildrenChanged
        String path2 = zooKeeper.create("/zk-test-ephemeral/c3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        String path3 = zooKeeper.create("/zk-test-ephemeral/c4", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(10000);

    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("watchedEvent is " + watchedEvent);

            countDownLatch.countDown();
            if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                System.out.println("NodeChildrenChanged");
                try {
                    List<String> s = zooKeeper.getChildren(watchedEvent.getPath(), true);
                    System.out.println("children:" + s);
                } catch (Exception e) {

                }
            }
        }
    }
}

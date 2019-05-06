package 五.使用Zookeeper.Java客户端API使用;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 异步获取子节点
 *
 * @author luohao
 * @date 2019/05/06
 */
public class GetChildren_Aysnc_Node_Demo implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {

        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildren_Aysnc_Node_Demo());

        countDownLatch.await();

        //创建节点
        String path = zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        String path1 = zooKeeper.create("/zk-test-ephemeral/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zooKeeper.getChildren(path, true,new IChildrenCallback(),"i am ctx" );

        String path2 = zooKeeper.create("/zk-test-ephemeral/c3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);


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
                    zooKeeper.getChildren(watchedEvent.getPath(), true,new IChildrenCallback(),"i am ctx" );
                } catch (Exception e) {

                }
            }
        }

    }
}

class IChildrenCallback implements AsyncCallback.ChildrenCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {

        System.out.println("rc:"+rc+";path:"+path+";ctx:"+ctx+"children:"+children);

    }
}

package 五.使用Zookeeper.Java客户端API使用;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * 异步创建节点
 *
 * @author luohao
 * @date 2019/05/06
 */
public class Create_Async_Node_Demo implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Create_Async_Node_Demo());

        //   System.out.println(zooKeeper.getState());

        countDownLatch.await();

        zooKeeper.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new MyStringCallback(), "I am context");


        Thread.sleep(10000);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("watchedEvent is "+watchedEvent);

            countDownLatch.countDown();

        }
    }


}

class MyStringCallback implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("rc:" + rc + ";path:" + path + ";ctx" + ctx + ";name" + name);
    }
}

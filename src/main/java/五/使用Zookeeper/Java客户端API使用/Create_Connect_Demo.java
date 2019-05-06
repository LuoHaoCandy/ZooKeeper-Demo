package 五.使用Zookeeper.Java客户端API使用;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建会话
 *
 * @author luohao
 * @date 2019/05/06
 */
public class Create_Connect_Demo implements Watcher {

    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",5000,new Create_Connect_Demo());

        System.out.println(zooKeeper.getState());

        countDownLatch.await();

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();

        }


    }
}

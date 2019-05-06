# ZooKeeper-Demo

> 《从Paxos到Zookeeper》一书实战代码学习记录，通过笔记加Demo的形式完成本书的学习。
开始时间：2019.05.06

   


### 第五章 使用ZooKeeper
  
  #### 5.2 客户端脚本
  
  
   * 5.2.1 创建
    
     create [-s]  [-e]  path  data  acl
     
     -s ,-e 代表节点特性为：顺序节点或者临时节点.
     
   * 5.2.2 读取
   
     * ls 使用ls命令可以列出指定节点下的所有子节点，只能看到下一级节点。  
     * get 使用get命令可获取Zookeeper指定节点的数据内容和属性信息.
     
   * 5.2.3 更新
   
     set  path  data  [version]
     
     使用set命令可以更新指定节点的数据内容，节点的数据是有版本概念的，version可以
     指定本次操作是基于ZNode的哪一个数据版本进行更新的.
     
   * 5.2.4 删除
   
     delete path [version]
     
     要想删除某一个指定节点，该节点下必须没有子节点的存在.
     
     
   #### 5.3 Java客户端API使用
   
   * 5.3.1 创建会话
       
     构造方法：
      
      ```js
     public ZooKeeper(String connectString, int sessionTimeout, Watcher watcher);
     public ZooKeeper(String connectString, int sessionTimeout, Watcher watcher, boolean canBeReadOnly);
     public ZooKeeper(String connectString, int sessionTimeout, Watcher watcher, long sessionId, byte[] sessionPasswd);
     public ZooKeeper(String connectString, int sessionTimeout, Watcher watcher, long sessionId, byte[] sessionPasswd, boolean canBeReadOnly);

     ```
     
     参数说明：
     
     参数名| 说明
     -----|------
     connectString | Zookeeper的服务器列表,由英文逗号分开的host
     sessionTimeOut | 会话超时时间，毫秒单位，在此时间内没有进行有效的心跳检测会话关闭
     watcher | 事件通知处理器
     canBeReadyOnly | 故障时是否希望继续提供读服务
     sessionId & sessionPasswd | 一组确定唯一会话，用来恢复会话
     
     
   * 5.3.2 创建节点
   
     构造方法：
     
     ```js
     public String create(String path, byte[] data, List<ACL> acl, CreateMode createMode);
     public void create(String path, byte[] data, List<ACL> acl, CreateMode createMode, StringCallback cb, Object ctx);
     ```
     
     参数说明：
     
     参数名| 说明
     -----|------
     path | 需要创建数据节点的节点路径
     data | 一个字节数组，节点创建后的初始内容
     acl  | 节点的ACL策略
     createMode | 节点类型
     cb | 注册一个异步回调函数
     ctx | 传递上下文对象，可以在回调方法时使用
     
     



     
     
     
   

     
     
     
    
    
       
       
       
    
    
  
  
        
    

   
  

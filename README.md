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
   
     方法：
     
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
     
     异步返回参数说明：
     
     ```js
      public void processResult(int rc, String path, Object ctx, String name) ;
     ```
     
     参数名| 说明
     -----|------
     rc | 接口响应吗；0-成功 ；-4 客户端和服务端链接已经断开 ； -110 指定节点已经存在 ；-112 会话已过期
     path | 传入的path
     ctx | 传入的ctx
     name | 节点的完整路径
     
     
   * 5.3.3 删除节点
      
      方法：
      
      ````js
      public void delete(final String path, int version);
      public void delete(final String path, int version, VoidCallback cb,Object ctx);
      ````
      
      参数说明：
      
       参数名| 说明
       -----|------
       path | 指定删除的数据节点
       version | 指定操作节点的数据版本
       cb | 注册一个回调函数
       ctx | 用于传递上下文信息的对象
       
       
       `注： 删除节点和后续5.3.5的更新数据接口相似，如果节点下还有子节点需要先删除子节点.`  
      

   * 5.3.4 读取数据
   
     getChildren 方法：
     
     ````js
     public List<String> getChildren(final String path, Watcher watcher);
     public List<String> getChildren(String path, boolean watch);
     public void getChildren(final String path, Watcher watcher, ChildrenCallback cb, Object ctx);
     public void getChildren(String path, boolean watch, ChildrenCallback cb, Object ctx);
     public List<String> getChildren(final String path, Watcher watcher,Stat stat);
     public List<String> getChildren(String path, boolean watch, Stat stat);
     public void getChildren(final String path, Watcher watcher,Children2Callback cb, Object ctx);
     public void getChildren(String path, boolean watch, Children2Callback cb,Object ctx);
     ````
     
     参数说明：
     
     参数名 | 说明
     ------|------- 
     path | 获取子节点的路径
     watcher | 注册的Watcher，在获取节点之后如果发生了变更则会向客户端发送通知
     watch | 是否需要注册一个Watcher
     cb | 注册一个异步回调函数
     ctx | 传递上下文信息
     stat | 节点状态信息
     
     补充说明：
     
     1. 在注册了Watcher之后，当节点发生变化时，会向客户端发送事件通知，但是不包含最新的节点列表，需要再次手动获取；
     
     2. Stat记录节点的状态信息，如创建时的事务Id(cZxid)等，Stat变量在方法执行过程中会被最新的Stat对象替换.
     
     3. Watcher只能使用一次，需要不断注册.
     
     getData 方法：
     
     ````js
     public byte[] getData(final String path, Watcher watcher, Stat stat);
     public byte[] getData(String path, boolean watch, Stat stat);
     public void getData(final String path, Watcher watcher,DataCallback cb, Object ctx);
     public void getData(String path, boolean watch, DataCallback cb, Object ctx);
     
     ````
     
     参数说明：
     
     `和 getChildren 方法用法基本相同，参数说明同上`
     
   * 5.3.5 更新数据
     
     方法：
     
     ````js
     public Stat setData(final String path, byte data[], int version);
     public void setData(final String path, byte data[], int version,StatCallback cb, Object ctx);
    
     ````
     
     参数说明：
     
     参数名 | 说明
     -------| -------
     path | 更新数据的节点路径
     data | 一个字节数组，用来更新内容
     version | 版本号，CAS
     cb | 异步回调函数
     ctx | 上下文信息对象
     
   
   * 5.3.6 检测节点是否存在
     
      方法：
      
      ````js
      public Stat exists(final String path, Watcher watcher);
      public Stat exists(String path, boolean watch);
      public void exists(final String path, Watcher watcher,StatCallback cb, Object ctx);
      public void exists(String path, boolean watch, StatCallback cb, Object ctx);

      ````
      
      参数说明：
      
      `参数意义同上，watcher监听 1、节点被创建；2、节点被删除；3、节点被更新. 无论节点是否存在，都会注册watcher`
      
   
   * 5.3.7 权限控制
    
     方法：
     
     ````js
     addAuthInfo(String scheme,byte[] auth);
     ````
     
     参数说明：
     
     参数名| 说明
     -----|-----
     scheme | 权限控制模式 分为 world，auth，digest，ip，super
     auth | 具体的权限信息
     
     
     `在创建会话之后，调用此方法添加权限信息，之后凡是通过该会话操作都会带上权限信息;
      需要注意在删除节点的时候，删除子节点需要获取权限，而删除节点本身不需要获取权限`
     
     
     
     

      
      
    
   
    

     
     
     
     
     
     
     

     
     
     
     
      
      

     
     
         



     
     
     
   

     
     
     
    
    
       
       
       
    
    
  
  
        
    

   
  

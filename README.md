# ZooKeeper-Demo

> 《从Paxos到Zookeeper》一书实战代码学习记录


### 第五章 使用ZooKeeper
  
  #### 5.2 客户端脚本
  
  
   * 5.2.1 创建
    
     create [-s]  [-e]  path  data  acl
     
     -s ,-e 代表节点特性为：顺序节点或者临时节点。
     
   * 5.2.2 读取
   
     * ls 使用ls命令可以列出指定节点下的所有子节点，只能看到下一级节点。  
     * get 使用get命令可获取Zookeeper指定节点的数据内容和属性信息。
     
   * 5.2.3 更新
   
     set  path  data  [version]
     
     使用set命令可以更新指定节点的数据内容，节点的数据是有版本概念的，version可以
     指定本次操作是基于ZNode的哪一个数据版本进行更新的。
     
   * 5.2.4 删除
   
     delete path [version]
     
     要想删除某一个指定节点，该节点下必须没有子节点的存在
     
     
     
    
    
       
       
       
    
    
  
  
        
    

   
  

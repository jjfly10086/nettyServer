### 1.AbstractRoutingDataSource动态切换数据源，实现主从读写分离
在Spring 2.0.1中引入了AbstractRoutingDataSource, 该类充当了DataSource的路由中介, 能有在运行时, 根据某种key值来动态切换到真正的DataSource上。
具体的实现就是，虚拟的DataSource仅需继承AbstractRoutingDataSource实现determineCurrentLookupKey（）在其中封装数据源的选择逻辑
再利用AOP拦截jpa dao接口，方法名以find开头的均使用从库查询，其余使用主库
### 2.Docker 模拟多库，开启binlog,实现主从复制
### 3.Spring cache
自定义cacheManager和cache实现，增加自定义过期时间
启用：<cache:annotation-driven cache-manager="myCacheManager"/>
# Netty
1. dubbo——》dubbo协议-》netty-》nio-》原生JDK，I/o-》TCP+IP
2. IO模型
	1. BIO 传统同步阻塞
	2. NIO 同步非阻塞 适用于连接数多且时间短  弹幕，聊天
	3. AIO 异步非阻塞 适用于连接数多且时间长  相册，系统层面的并发
3. BIO
	1. BIO服务端代码，详情看Bio代码块
	2. 服务启动，无线程，阻塞在serverSocket.accept
	3. 有访问，无数据，阻塞在inputStream.read(bytes)
4. NIO基础
	1. 由通道，缓存区，选择器构成
	2. 客户端《1=1》缓存区《1=1》管道《1=N》选择器(1个线程多个选择器)《N=1》server服务 
	3. NIO以块进行数据处理，而Bio以流进行数据处理，所有效率高很多
	4. select 是根据 事件来切换到不同的通道上去的
	5. Buffer 就是一个内存块，底层是一个数组
	6. BIO要么是输入或输出流，而Nio的buffer是双向的，需要 flip方法切换
	7. channel也是双向的
5. buffer
	1. buff入门代码，详情看Nio模块
	2. buff内含一些机制，能够追踪和记录缓冲区的状态变化
	3. buff底层定义属性
		```
		private int mark = -1; //标记
		private int position = 0; //位置，下一个读或写的索引，flip方法会将位置到0
		private int limit; //缓冲区的当前终点
		private int capacity; //最大容量，当创建时被定义且不能改变
		```
    4. buff放入得顺序对应得类型，读取也需要为同样得类型，不然会有可能抛出异常或者读取数据有问题
    5. buff可以设置为只读 
    6. MappedByteBuffer可以在内存（堆外内存）中修改，操作系统不需要再拷贝一次,可以修改文件内容
    7. buff可以使用数组，会自动根据顺序填充数据
6. channel
	1. 通道异步，且可同时进行读写操作，代码详情看Nio模块
	2. FileChannel 文件  DatagramChannel udp  ServiceSocketChannel 和 SocketChannel 用于TCP
	3. FileChannel(已自我为基准) read 从通道读取数据放入缓存区 ，write 将缓存区的数据写入通道中

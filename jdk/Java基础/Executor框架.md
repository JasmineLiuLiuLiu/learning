# Executor框架

## 1 简介

### Executor框架的两级调度模型

HotSpot JVM的线程模型中，Java线程（`java.lang.Thread`）被一对一映射为本地OS线程。  
在上层，Java多线程程序将应用分解为若干个任务，然后使用用户级别的调度器（`Executor`框架）将这些任务映射为固定数量的线程；在底层，OS内核将这些线程映射到硬件处理器上。  
应用程序通过`Executor`框架控制上层的调度，OS内核控制下层的调度，下层的调度不受应用程序的控制。

### Executor框架的结构和成员

#### 结构

`Executor`框架主要由三大部分组成：

* 任务 - 包括被执行任务需要实现的接口：`Runnable`和`Callable`接口。
* 任务的执行 - 包括`Executor`，继承`Executor`的`ExecutorService`接口。`ThreadPoolExecutor`和`ScheduledThreadPoolExecutor`类是实现`ExecutorService`接口的两个关键类。
* 异步计算的接口 - 包括接口`Future`和实现`Future`接口的`FutureTask`类。

![executor框架的类与接口](./images/executor框架的类与接口.png "executor框架的类与接口")

* `Executor` -` Executor`框架的基础，将任务的提交与执行分离开来。
* `ThreadPoolExecutor` - 线程池的核心实现类，用来执行被提交的任务。
* `ScheduledThreadPoolExecutor` - 实现类，在给定的延时后运行命令，或者定期执行命令。比`Timer`更灵活，功能更强大。
* `Future` & `FutureTask` - 异步计算的结果。
* `Runnable`和`Callable`接口的实现类 - 可以被`ThreadPoolExecutor`或`ScheduledThreadPoolExecutor`执行。

`Executor`框架使用

![executor框架使用](./images/executor框架使用.png "executor框架使用")

主线程首先创建`Runnable`或者`Callable`接口的任务对象。工具类`Executors`可以把一个`Runnable`对象封装成`Callable`对象。

```java
public static Callable<Object> callable(Runnable task) {...}

public static <T> Callable<T> callable(Runnable task, T result) {...}
```

然后可以把`Runnable对`象直接交给`ExecutorService`执行（`execute(Runnable command)`）；也可以把`Runnable`对象或`Callable`对象提交给`ExecutorService`执行（`submit(Runnable task)` 或`submit(Callable<T> task)`）。  
如果执行的是`submit()`，`ExecutorService`将返回一个`Future`接口的对象（`FutreTask`对象）。由于`FutureTask`实现了`Runnable`，也可以自己创建`FutureTask`，交给`ExecutorService`执行。  
最后，主线程可以执行`FutureTask.get()`来等待任务执行完成，或者`FutureTask.cancel(boolean mayInterruptIfRunning)`来取消任务。



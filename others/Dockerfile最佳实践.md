# Dockerfile最佳实践

## 一般准则和建议

### 容器应该是短暂的

通过Dockerfile构建的镜像所启动的容器应该尽可能短暂（生命周期短）。“短暂”意味着可以停止和销毁容器，并且创建一个新容器并部署好所需的设置和配置工作量应该是极小的。

### 理解构建上下文

当发出一个docker build命令时，当前的工作目录被称为构建上下文。默认情况下，Dockerfile就位于该路径下，当然你也可以使用-f参数来指定不同的位置。无论Dockerfile在什么地方，当前目录中的所有文件内容（含子目录的）都将作为构建上下文发送到守护进程中去。  
在构建的时候包含不需要的文件会导致构建上下文变大以及镜像变大。这会增加构建时间，拉取和推送镜像的时间以及容器的运行时间。  
要查看您的构建环境有多大，请在构建您的系统时查找这样的消息：

```bash
Sending build context to Docker daemon  187.8MB
```

### 使用`stdin`管道的Dockerfile

Docker有能力通过带有本地或远程构建上下文的`stdin`管道化Dockerfile来构建映像。  
通过stdin管道化Dockerfile可以不用把Dockerfile写入磁盘来执行一次性的构建，这也适用于不需要持久化Dockerfile的情况。

#### 使用`stdin`中的Dockerfile构建映像，而不发送构建上下文

```bash
docker build [OPTIONS] -
```

使用此语法从stdin使用Dockerfile构建图像，而不需要将其他文件作为构建上下文发送。 连字符(-)表示路径的位置，并指示Docker从stdin读取构建上下文(只包含Dockerfile)，而不是从目录中读取。

```bash
echo -e 'FROM busybox\nRUN echo "hello world"' | docker build -
```

#### 使用`stdin`中的Dockerfile从本地构建上下文构建

```bash
docker build [OPTIONS] -f- PATH
```

使用此语法使用本地文件系统上的文件构建映像，但是使用来自stdin的Dockerfile。语法使用-f(或——file)选项来指定要使用的Dockerfile，使用连字符(-)作为文件名来指示Docker从stdin读取Dockerfile.

```bash
mkdir example
cd example
touch somefile.txt
docker build -t myimage:latest -f- . <<EOF
FROM busybox
COPY somefile.txt .
RUN cat /somefile.txt
EOF
```

#### 使用`stdin`中的Dockerfile从远程构建上下文构建

```bash
docker build [OPTIONS] -f- PATH
```

用此语法使用来自远程git存储库的文件，使用来自stdin的Dockerfile构建映像。该语法使用-f(或——file)选项来指定要使用的Dockerfile，使用连字符(-)作为文件名来指示Docker从stdin读取Dockerfile。

```bash
docker build -t myimage:latest -f- https://github.com/docker-library/hello-world.git <<EOF
FROM busybox
COPY hello.c .
EOF
```

### 使用`.dockerignore`文件排除一些文件

使用Dockerfile构建镜像时最好是将Dockerfile放置在一个新建的空目录下，然后将构建镜像所需要的文件添加到该目录中。为了提高构建镜像的效率，可以使用`.dockerignore`文件来指定要忽略的文件和目录。

### 用多阶段构建

在Docker 17.05以上版本中，你可以使用多阶段构建来减少所构建镜像的大小。

### 避免安装不必要的包

为了降低复杂性，减少依赖，减小文件大小和构建时间，应该避免安装额外的或者不必要的软件包。例如，不要在数据库镜像中包含一个文本编辑器。

### 一个容器只专注做一件事情，将多个应用解耦

应该保证一个容器只专注做一件事情。将多个应用解耦到不同容器中，保证了容器可以容易地横向扩展和复用。  
例如一个web应用程序可能包含三个独立的容器：网络应用，数据库，和内存缓存，每个容器都是独立的镜像，分开运行。
虽然“每个容器跑一个进程”是一条很好的法则，但这并不是一条硬性的规定。因为有的程序可能会自行产生其他进程，比如芹菜就可以有很多个工作进程，apache更可能为每个请求创建一个进程。  
保持容器尽可能的干净和模块化。如果容器之间相互依赖，可以使用Docker容器网络来确保这些容器可以通信。

### 最小化镜像层数

在旧版本的Docker（17.05甚至更早）中，你必须尽量减少图像中的层数，以确保它们的性能。不过现在的版本已经有了一定的改善了：

* 在1.10以后，只有`RUN`，`COPY`和`ADD`指令会创建层，其他指令会创建临时的中间镜像，不会直接增加构建的镜像大小。
* 如果可以的化，使用多阶段构建，只复制需要的材料的到最终的镜像中去。你可以在构建过程中包含一些工具和debug信息而不用增加最终的镜像大小。

### 对多行参数排序 增加可读性

只要有可能，就将多行参数按字母顺序排序（比如要安装多个包时）。这可以帮助你避免重复包含同一个包，更新包列表时也更容易，也更容易阅读和审查。  
建议在反斜杠符号\之前添加一个空格，可以增加可读性。  
下面是来自`buildpack-dep`镜像的例子：

```bash
RUN apt-get update && apt-get install -y \
  bzr \
  cvs \
  git \
  mercurial \
  subversion \
  && rm -rf /var/lib/apt/lists/*
```

### 利用构建缓存

在镜像的构建过程中，Docker根据Dockerfile指定的顺序执行每个指令。在执行每条指令之前，Docker都会在缓存中查找是否已经存在可重用的镜像，如果有就使用现存的镜像，不再重复创建。当然如果你不想在构建过程中使用缓存，你可以在`docker build`命令中使用`--no-cache=true`选项。  
如果你想在构建的过程中使用了缓存，那么了解什么时候可以什么时候无法找到匹配的镜像就很重要了，基本规则如下：  

* 从一个基础镜像开始（FROM指令指定），下一条指令将和该基础镜像的所有子镜像进行匹配，检查这些子镜像被创建时使用的指令是否和被检查的指令完全一样。如果不是，则缓存失效。
* 在大多数情况下，只需要简单地对比Dockerfile中的指令和子镜像。然而，有些指令需要更多的检查和解释。
* 对于ADD和COPY指令，镜像中对应文件的内容也会被检查，每个文件都会计算出一个校验值。这些文件的修改时间和最后访问时间不会被纳入校验的范围。在缓存的查找过程中，会将这些校验和和已存在镜像中的文件校验值进行对比。如果文件有任何改变，比如内容和元数据，则缓存失效。
* 除了ADD和COPY指令，缓存匹配过程不会查看临时容器中的文件来决定缓存是否匹配。例如，当执行完`RUN apt-get -y update`指令后，容器中一些文件被更新，但Docker不会检查这些文件。这种情况下，只有指令字符串本身被用来匹配缓存。

一旦缓存失效，所有后续的Dockerfile指令都将产生新的镜像，缓存不会被使用。

## Dockerfile指令

### FROM

尽可能使用当前官方仓库作为你构建镜像的基础。推荐使用Alpine镜像，因为它被严格控制并保持最小尺寸（目前小于5 MB），但它仍然是一个完整的发行版。

### LABEL

给镜像添加标签，以帮助按项目组织镜像、记录许可信息、帮助实现自动化或出于其他原因。对于每个标签，添加以LABEL开头并具有一个或多个键-值对的行。Docker 1.10不需要合并到一行命令。

### RUN

为了保持Dockerfile文件的可读性，以及可维护性，建议将长的或复杂的RUN指令用反斜杠`\`分割成多行。

#### APT-GET

总是在同一个RUN语句中结合运行apt-get update和apt-get install。例如:

```bash
RUN apt-get update && apt-get install -y \
    package-bar \
    package-baz \
    package-foo  \
    && rm -rf /var/lib/apt/lists/*
```

将`apt-get update`放在一条单独的`RUN`声明中会导致缓存问题，然后后续的`apt-get install`失败。比如，假设你有一个Dockerfile文件：

```bash
FROM ubuntu:18.04
RUN apt-get update
RUN apt-get install -y curl
```

构建镜像后，所有的层都在Docker的缓存中。假设你后来又修改了其中的`apt-get install`,添加了一个包：`curl`。  
docker发现修改后的`RUN apt-get update`指令和之前的完全一样。所以，`apt-get update`不会执行，而是使用之前的缓存镜像。因为`apt-get update`没有运行，后面的`apt-get install`可能安装的是过时的`curl`和`nginx`版本。  
使用`RUN apt-get update && apt-get install -y`可以确保你的Dockerfiles每次安装的都是包的最新的版本，而且这个过程不需要进一步的编码或人工干预。这项技术叫作cache busting(缓存破坏)。你也可以通过显式地指定一个包的版本号来达到缓存破坏，这就是所谓的固定版本，例如：

```bash
 RUN apt-get update && apt-get install -y \
   package-bar \
   package-baz \
   package-foo=1.3.*
```

固定版本会迫使构建过程检索特定的版本，而不管缓存中有什么。这项技术也可以减少因所需包中未预料到的变化而导致的失败。  
下面是一个RUN指令的示例模板，展示了所有关于`apt-get`的建议。

```bash
 RUN apt-get update && apt-get install -y \
   aufs-tools \
   automake \
   build-essential \
   curl \
   dpkg-sig \
   libcap-dev \
   libsqlite3-dev \
   mercurial \
   reprepro \
   ruby1.9.1 \
   ruby1.9.1-dev \
   s3cmd=1.1.* \
&& rm -rf /var/lib/apt/lists/*
```

其中s3cmd指令指定了一个版本号1.1.*。如果之前的镜像使用的是更旧的版本，指定新的版本会导致`apt-get udpate`缓存失效并确保安装的是新版本。  
另外，清理掉apt缓存`/var/lib/apt/lists`可以减小镜像大小。因为RUN指令的开头为`apt-get udpate`，包缓存总是会在`apt-get install`之前刷新。  
> 注意：官方的Debian和Ubuntu镜像会自动运行`apt-get clean`，所以不需要显式的调用`apt-get clean`。

#### 使用管道

一些RUN命令依赖于使用管道字符(`|`)将一个命令的输出管道到另一个命令的能力，如下面的示例所示:

```bash
RUN wget -O - https://some.site | wc -l > /number
```

Docker使用`/bin/sh -c`解释器执行这些命令，它只计算管道中最后一个操作的退出代码，以确定是否成功。  
在上面的示例中，只要`wc -l`命令成功(即使`wget`命令失败)，这个构建步骤就会成功并生成一个新的映像。  
如果您希望命令因管道中的任何阶段的错误而失败，在前面添加`set -o pipefail &&`。例如:

```bash
RUN set -o pipefail && wget -O - https://some.site | wc -l > /number
```

> 并不是所有的shell都支持-o pipefail选项。
在诸如基于debian的镜像上的`dash shell`这样的情况下，可以考虑使用`RUN`的`exec`形式来显式地选择一个确实支持`pipefail`选项的`shell`。例如:

```bash
RUN ["/bin/bash", "-c", "set -o pipefail && wget -O - https://some.site | wc -l > /number"]
```

### CMD

`CMD`指令应该用于运镜像中包含的软件，以及任何参数。`CMD`几乎总是以`CMD ["executable", "param1", "param2"…]`的形式使用。因此，如果图像是如果创建镜像的目的是为了部署某个服务（比如Apache），你可能会执行类似于CMD["apache2","-DFOREGROUND"]形式的命令。实际上，对于任何基于服务的映像，都推荐使用这种形式的指令。  
多数情况下，CMD都需要一个交互式的shell（bash，Python，perl等），例如`CMD ["perl"，"-de0"]`，或者`CMD ["PHP"，"- a"]`。  
使用这种形式意味着，当你执行类似`docker run -it python`时，你会进入一个准备好的`shell`中。

CMD在极少的情况下才会以`CMD ["param"，"param"]`的形式与`ENTRYPOINT`协同使用，除非你和你的镜像使用者都对`ENTRYPOINT`的工作方式十分熟悉。

### EXPOSE

EXPOSE指令用于指定容器将要监听的端口。因此，你应该为你的应用程序使用常见的端口。  
例如，提供Apache web服务的镜像应该使用`EXPOSE 80`，而提供MongoDB服务的镜像使用`EXPOSE 27017`。

对于外部访问，用户可以在执行`docker run`时使用一个标志来指示如何将指定的端口映射到所选择的端口。

### ENV

为了方便新程序运行，你可以使用`ENV`来为容器中安装的程序更新`PATH`环境变量。例如使用`ENV PATH=/usr/local/nginx/bin:$PATH`来确保`CMD["nginx"]`能正确运行。  
`ENV`指令也可用于为你想要容器化的服务提供必要的环境变量，比如Postgres需要的`PGDATA`。  
最后，ENV也能用于设置常见的版本号，比如下面的示例：

```bash
ENV PG_MAJOR 9.3
ENV PG_VERSION 9.3.4
RUN curl -SL http://example.com/postgres-$PG_VERSION.tar.xz | tar -xJC /usr/src/postgress && …ENV PATH /usr/local/postgres-$PG_MAJOR/bin:$PATH
```

每个`ENV`行都创建一个新的中间层，就像运行命令一样。这意味着，即使您在未来的层中取消了环境变量的设置，它仍然会保存在这个层中，并且它的值可以转储。  
为了防止这种情况发生，并真正取消设置环境变量，可以使用一个带有`shell`命令的`RUN`命令，在单层中设置、使用和取消设置变量。可以用`;`和`&`连接多个命令。如果使用`&`，一但有一个命令失败，docker构建会失败。  
在Linux Dockerfiles中使用`\`作为连续符可以提高可读性。  
还可以将所有命令放入shell脚本中，并用`RUN`命令运行。

### ADD和COPY

虽然`ADD`和`COPY`功能类似，但一般优先使用`COPY`，因为它更透明。`COPY`只支持简单将本地文件拷贝到容器中，而`ADD`有一些并不明显的功能（比如本地tar提取和远程URL支持）。因此，`ADD`的最佳用例是将本地tar文件自动提取到镜像中，例如`ADD rootfs.tar.xz`。  
如果你的Dockerfile有多个步骤需要使用上下文中不同的文件,单独`COPY`每个文件，而不是一次性的`COPY`所有文件，这将保证每个步骤的构建缓存只在特定的文件变化时失效。例如：

```bash
COPY requirements.txt /tmp/
RUN pip install --requirement /tmp/requirements.txt
COPY . /tmp/
```

如果将`COPY. /tmp/`放置在`RUN`指令之前，只要目录中任何一个文件变化，都会导致后续指令的缓存失效。

为了让镜像尽量小，最好不要使用`ADD`指令从远程`URL`获取包，而是使用`curl`和`wget`。这样可以在文件提取完之后删除不再需要的文件,避免了在镜像中额外添加一层。比如尽量避免下面的用法：

```bash
ADD http://example.com/big.tar.xz /usr/src/things/
RUN tar -xJf /usr/src/things/big.tar.xz -C /usr/src/things
RUN make -C /usr/src/things all
```

而是应该使用下面这种方法：

```bash
RUN mkdir -p /usr/src/things \
   && curl -SL http://example.com/big.tar.xz \
   | tar -xJC /usr/src/things \
   && make -C /usr/src/things all
```

对于其他不需要`ADD`的自动提取功能的文件或目录，你应该使用`COPY`。

### ENTRYPOINT

`ENTRYPOINT`的最佳用处是设置镜像的主命令，允许将镜像当成命令本身来运行（用`CMD`提供默认选项）。  

例如，下面的示例镜像提供了命令行工具`s3cmd`：

```bash
ENTRYPOINT ["s3cmd"] 
CMD ["--help"]
 ```

现在直接运行该镜像创建的容器会显示命令帮助：

```bash
docker run s3cmd
```

或者提供正确的参数来执行某个命令：

```bash
docker run s3cmd ls s3://mybucket
```

这样镜像名可以当成命令行工具的引用。`ENTRYPOINT`指令也可以结合一个辅助脚本使用，和前面命令行风格类似，即使启动工具需要不止一个步骤。  
例如，Postgres官方镜像使用下面的脚本作为`ENTRYPOINT`：

```bash
#!/bin/bash 
set -e
 
if [ "$1" = 'postgres' ]; then
   chown -R postgres "$PGDATA" 
 
   if [ -z "$(ls -A "$PGDATA")" ]; then
      gosu postgres initdb
   fi

   exec gosu postgres "$@"
fi

exec "$@"
```

> 把APP设为PID 1  
该脚本使用了Bash的内置命令`exec`，所以最后运行的进程就是容器的PID为1的进程。这样，进程就可以接收到任何发送给容器的Unix信号了。

该辅助脚本被拷贝到容器，并在容器启动时通过`ENTRYPOINT`执行：

```bash
COPY ./docker-entrypoint.sh / 
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["postgres"]
```

该脚本可以让用户用几种不同的方式和Postgres交互:  
你可以很简单地启动Postgres：`$ docker run postgres`  
也可以执行Postgres并传递参数：`$ docker run postgres postgres --help`  
最后，你还可以启动另外一个完全不同的工具，比如Bash：`$ docker run --rm -it postgres bash`

### VOLUME

`VOLUME`指令用于暴露任何数据库存储文件，配置文件，或容器创建的文件和目录。强烈建议使用`VOLUME`来管理镜像中的可变部分和用户可以改变的部分。

### USER

如果某个服务不需要特权执行，建议使用`USER`指令切换到非root用户。先在Dockerfile中使用类似`RUN groupadd -r postgres && useradd --no-log-init -r -g postgres postgres`的指令创建用户和用户组。

> 考虑特定的UID/GID  
在镜像中，用户和用户组每次被分配的UID/GID都是不确定的，下次重新构建镜像时被分配到的UID/GID可能会不一样。如果要依赖确定的UID/GID ，你应该显式地指定一个UID/GID。

> 由于Go对archive/tar包对稀疏文件的处理有一个未解决的错误，尝试在Docker容器中创建一个有非常大的UID的用户可能会导致磁盘耗尽，因为容器层的`/var/log/faillog`会被`NULL(\0)`字符填充。一个解决方法是将`--no-log-init`标志传递给`useradd`。Debian/Ubuntu包装的`adduser`不支持此标志。

避免使用`sudo`，因为它不可预期的`TTY`和信号转发行为可能会导致问题。如果你真的需要和sudo类似的功能（例如，以root权限初始化某个守护进程，但以非root权限执行它），你可以使用`gosu`。  
最后，为了减少层数和复杂度，避免频繁地使用USER来回切换用户。

### WORKDIR

为了清晰性和可靠性，应该总是在`WORKDIR`中使用绝对路径。另外，你应该使用`WORKDIR`来替代类似于`RUN cd ... && do-something`的指令，后者难以阅读，排错和维护。

### ONBUILD

`ONBUILD`命令在当前Dockerfile构建完成后执行。`ONBUILD`在从当前镜像派生的任何子镜像中执行。可以把`ONBUILD`命令看作是父Dockerfile给子Dockerfile的指令。  
docker构建在子Dockerfile中的任何命令之前执行`ONBUILD`命令。  
`ONBUILD`对于构建一个使用`FROM`指定镜像的子镜像很有用。  
使用`ONBUILD`构建的图像应该得到一个单独的标签，例如:`ruby:1.9-onbuild`或`ruby:2.0-onbuild`。
在`ONBUILD`中使用`ADD`或者`COPY`要小心。如果新构建的上下文缺少正在添加的资源，那么“onbuild”的镜像将灾难性地失败。如上面所推荐的，添加一个单独的标签，允许Dockerfile的作者做出选择，从而帮助缓解这个问题。

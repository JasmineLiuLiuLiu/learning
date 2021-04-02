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
为了防止这种情况发生，并真正取消设置环境变量，可以使用一个带有`shell`命令的`RUN`命令，在单个层中设置、使用和取消设置变量。你可以用;和& &。如果你使用第二种方法，其中一个命令失败，docker构建也失败。这通常是个好主意。在Linux Dockerfiles中使用\作为连续符可以提高可读性。您还可以将所有命令放入shell脚本中，并让RUN命令运行该shell脚本。

ADD和COPY
虽然ADD状语从句：COPY功能类似，但一般优先使用COPY。因为它比加入更透明.COPY只支持简单将本地文件拷贝到容器中，而添加有一些并不明显的功能（比如本地焦油提取和远程URL支持）。因此，ADD的最佳用例是将本地tar文件自动提取到镜像中，例如ADD rootfs.tar.xz。

如果你的Dockerfile有多个步骤需要使用上下文中不同的文件。单独COPY每个文件，而不是一次性的COPY所有文件，这将保证每个步骤的构建缓存只在特定的文件变化时失效。例如：

 COPY requirements.txt /tmp/
 
RUN pip install --requirement /tmp/requirements.txt
 
COPY . /tmp/
 

如果将COPY./tmp/放置在RUN指令之前，只要。目录中任何一个文件变化，都会导致后续指令的缓存失效。

为了让镜像尽量小，最好不要使用ADD指令从远程URL获取包，而是使用curl和wget。这样你可以在文件提取完之后删除不再需要的文件来避免在镜像中额外添加一层。比如尽量避免下面的用法：

ADD http://example.com/big.tar.xz /usr/src/things/
 
RUN tar -xJf /usr/src/things/big.tar.xz -C /usr/src/things
 
RUN make -C /usr/src/things all
 
而是应该使用下面这种方法：

RUN mkdir -p /usr/src/things \
 
   && curl -SL http://example.com/big.tar.xz \
 
   | tar -xJC /usr/src/things \
 
   && make -C /usr/src/things all
 

上面使用的管道操作，所以没有中间文件需要删除。对于其他不需要ADD的自动提取功能的文件或目录，你应该使用COPY。

入口点
ENTRYPOINT的最佳用处是设置镜像的主命令，允许将镜像当成命令本身来运行（用CMD提供默认选项）。

例如，下面的示例镜像提供了命令行工具s3cmd：

 ENTRYPOINT ["s3cmd"]
 
CMD ["--help"]
 

现在直接运行该镜像创建的容器会显示命令帮助：

 $ docker run s3cmd
 

或者提供正确的参数来执行某个命令：

 $ docker run s3cmd ls s3://mybucket
 

这样镜像名可以当成命令行的参考.ENTRYPOINT指令也可以结合一个辅助脚本使用，和前面命令行风格类似，即使启动工具需要不止一个步骤。

例如，Postgres官方镜像使用下面的脚本作为ENTRYPOINT：

 #!/bin/bash
 
set -e
 
if [ "$1" = 'postgres' ]; then
 
   chown -R postgres "$PGDATA"
 
 
   if [ -z "$(ls -A "$PGDATA")" ]; then
 
       gosu postgres initdb
 
   fi
 
 
   exec gosu postgres "$@"fi
 
exec "$@"
 

注意：该脚本使用了Bash的内置命令exec，所以最后运行的进程就是容器的PID为1的进程。这样，进程就可以接收到任何发送给容器的Unix信号了。

该辅助脚本被拷贝到容器，并在容器启动时通过ENTRYPOINT执行：

 COPY ./docker-entrypoint.sh /
 
ENTRYPOINT ["/docker-entrypoint.sh"]
 

该脚本可以让用户用几种不同的方式和Postgres交互。你可以很简单地启动Postgres：

$ docker run postgres
 

也可以执行Postgres并传递参数：

$ docker run postgres postgres --help
 

最后，你还可以启动另外一个完全不同的工具，比如Bash：

 $ docker run --rm -it postgres bash
 

体积
VOLUME指令用于暴露任何数据库存储文件，配置文件，或容器创建的文件和目录。强烈建议使用VOLUME来管理镜像中的可变部分和用户可以改变的部分。

用户
如果某个服务不需要特权执行，建议使用USER指令切换到非root用户。先在Dockerfile中使用类似RUN groupadd -r postgres && useradd -r -g postgres postgres的指令创建用户和用户组。

注意：在镜像中，用户和用户组每次被分配的UID / GID都是不确定的，下次重新构建镜像时被分配到的UID / GID可能会不一样。如果要依赖确定的UID / GID ，你应该显示的指定一个UID / GID。

你应该避免使用sudo，因为它不可预期的TTY和信号转发行为可能造成的问题比它能解决的问题还多。如果你真的需要和sudo类似的功能（例如，以root权限初始化某个守护进程，以非root权限执行它），你可以使用gosu。

最后，为了减少层数和复杂度，避免频繁地使用USER来回切换用户。

WORKDIR
为了清晰性和可靠性，你应该总是在WORKDIR中使用绝对路径。另外，你应该使用WORKDIR来替代类似于RUN cd ... && do-something的指令，后者难以阅读，排错和维护。

ONBUILD
格式：ONBUILD <其它指令>。ONBUILD是一个特殊的指令，它后面跟的是其它指令，比如RUN，COPY等，而这些指令，在当前镜像构建时并不会被执行。只有当以前镜像为基础镜像，去构建下一级镜像的时候才会被执行.Dockerfile中的其他指令都是为了定制当前镜像而准备的，唯有ONBUILD是为了帮助别人定制自己而准备的。

假设我们要制作Node.js所写的应用的镜像。我们都知道Node.js使用npm进行包管理，所有依赖，配置，启动信息等会放到package.json文件里。在拿到程序代码后，需要先进行npm install才可以获得所有需要的依赖。然后就可以通过npm start来启动应用。因此，一般来说会这样写写Dockerfile：

FROM node:slim
 
RUN mkdir /app
 
WORKDIR /app
 
COPY ./package.json /app
 
RUN [ "npm", "install" ]
 
COPY . /app/
 
CMD [ "npm", "start" ]
 

把这个Dockerfile放到Node.js项目的根目录，构建好镜像后，就可以直接拿来启动容器运行。但是如果我们还有第二个Node.js项目也差不多呢？好吧，那就再把这个Dockerfile复制到第二个项目里。那如果有第三个项目呢？再复制么？文件的副本越多，版本控制就越困难，让我们继续看这样的场景维护的问题：

如果第一个Node.js项目在开发过程中，发现这个Dockerfile里存在问题，比如敲错字了，或者需要安装额外的包，然后开发人员修复了这个Dockerfile，再次构建，问题解决。第一个项目没问题了，但是第二个项目呢？虽然最初Dockerfile是复制，粘贴自第一个项目的，但是并不会因为第一个项目修复了他们的Dockerfile，而第二个项目的Dockerfile就会被自动修复。

那么我们可不可以做一个基础镜像，然后各个项目使用这个基础镜像呢？这样基础镜像更新，各个项目不用同步Dockerfile的变化，重新构建后就继承了基础镜像的更新？好吧，可以，让我们看看这样的结果。那么上面的这个Dockerfile就会变为：

FROM node:slim
 
RUN mkdir /app
 
WORKDIR /app
 
CMD [ "npm", "start" ]
 

。这里我们把项目相关的构建指令拿出来，放到子项目里去假设这个基础镜像的名字为我的节点的话，各个项目内的自己的Dockerfile就变为：

FROM my-node
 
COPY ./package.json /app
 
RUN [ "npm", "install" ]
 
COPY . /app/
 

基础镜像变化后，各个项目都用这个Dockerfile重新构建镜像，会继承基础镜像的更新。

那么，问题解决了么？没有。准确说，只解决了一半。如果这个Dockerfile里面有些东西需要调整呢？比如npm install都需要加一些参数，那怎么办？这一行RUN是不可能放入基础镜像的，因为涉及到了当前项目的./package.json，难道又要一个个修改么？所以说，这样制作基础镜像，只解决了原来的Dockerfile的前4条指令的变化问题，而后面三条指令的变化则完全没办法处理。

。ONBUILD可以解决这个问题让我们用ONBUILD重新写一下基础镜像的Dockerfile：

FROM node:slim
 
RUN mkdir /app
 
WORKDIR /app
 
ONBUILD COPY ./package.json /app
 
ONBUILD RUN [ "npm", "install" ]
 
ONBUILD COPY . /app/
 
CMD [ "npm", "start" ]
 

这次我们回到原始的Dockerfile，但是这次将项目相关的指令加上ONBUILD，这样在构建基础镜像的时候，这三行并不会被执行然后各个项目的Dockerfile就变成了简单地：

 FROM my-node
 

是的，只有这么一行。当在各个项目目录中，用这个只有一行的Dockerfile构建镜像时，之前基础镜像的那三行ONBUILD就会开始执行，成功的将当前项目的代码复制进镜像，并且针对本项目执行npm install，生成应用镜像。
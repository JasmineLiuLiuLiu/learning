# 使用多阶段构建(version 17.05开始)

在多阶段构建中，你可以在Dockerfile中使用多个`FROM`语句。每条`FROM`指令都可以使用不同的基本镜像，它们都会开始构建新阶段。您可以有选择地将材料从一个阶段复制到另一个阶段，不想要的材料会被留下，不会在最终镜像中。

```dockerFile
FROM golang:1.7.3
WORKDIR /go/src/github.com/alexellis/href-counter/
RUN go get -d -v golang.org/x/net/html  
COPY app.go .
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .

FROM alpine:latest  
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=0 /go/src/github.com/alexellis/href-counter/app . # COPY --from=0将前一阶段的构建工件复制到这个新阶段
CMD ["./app"]
```

## 给阶段取名

```dockerFile
FROM golang:1.7.3 AS builder # 把阶段0命名为builder
WORKDIR /go/src/github.com/alexellis/href-counter/
RUN go get -d -v golang.org/x/net/html  
COPY app.go    .
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .

FROM alpine:latest  
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /go/src/github.com/alexellis/href-counter/app . # 从builder阶段复制
CMD ["./app"]  
```

## 在指定阶段停止

```bash
docker build --target builder -t alexellis2/href-counter:latest .
```

## 使用外部镜像作为“阶段”

当使用多阶段构建时，并不局限于从Dockerfile中先前创建的阶段复制。可以使用`COPY --from`指令从一个单独的镜像复制，可以使用本地镜像的名称、本地可用的标签或Docker注册表上的标签，或者标签ID。Docker客户端会提取镜像并从那里复制材料。语法是：

```dockerFile
COPY --from=nginx:latest /etc/nginx/nginx.conf /nginx.conf
```

## 使用前面的阶段作为新阶段

```dockerFile
FROM alpine:latest as builder
RUN apk --no-cache add build-base

FROM builder as build1
COPY source1.cpp source.cpp
RUN g++ -o /binary source.cpp

FROM builder as build2
COPY source2.cpp source.cpp
RUN g++ -o /binary source.cpp
```

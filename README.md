### Spring Cloud Sleuth with different versions

1. Start zipkin

```bash
$ docker run -d -p 9411:9411 openzipkin/zipkin
```

Make sure the following URL is accessible in a browser

`http://localhost:9411`

2. Start up all the 3 services:

```
8080                  8081                    8082
zuul-proxy -------->  foo-service --------->  bar-service
```

Send request:

```bash
http :8080/foo 'trId:123'
```

Check the console logs:

zuul-proxy
```bash
...
2019-08-12 21:54:05.577  INFO 40702 [zuul-proxy,abc,7e4d5646e0ff0e80,true] --- [http-nio-8080-exec-1] com.example.zuulproxy.AuditFilter        : GET request to http://localhost:8080/foo
...
```

foo-service
```bash
2019-08-12 21:54:05.690  INFO 40695 [foo-service,abc,0a8bce18b1ae03f9,true] --- [http-nio-8081-exec-1] com.example.fooservice.FooController     : foo-service controller called...
```

bar-service
```bash
2019-08-12 21:54:06.309  INFO 40700 [bar-service,abc,34970b21b0f02542,true] --- [http-nio-8082-exec-1] c.e.barservice.BarServiceApplication     : bar-service called...
```

# Next

Make sure the traceId 'abc' is also propagated to zipkin


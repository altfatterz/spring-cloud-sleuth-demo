### Spring Cloud Sleuth with different versions

Start up all the 3 services:

```
8080                  8081                    8082
zuul-proxy -------->  foo-service --------->  bar-service
sleuth 2.0.1          sleuth 2.1.0.M1         sleuth 1.3.5
```

3 log files are created:

```
zuul-proxy-performance.log
foo-performance.log
bar-performance.log
```

Send request:

```bash
http :8080/foo 'trId:123'
```

zuul-proxy-performance.log will contain:
```
18-10-24 Wed 13:34:04.885  INFO [component=zuul-proxy,requestId=test] GET request to http://localhost:8080/foo
```

foo-performance.log will contain:
```
18-10-24 Wed 13:34:05.019  INFO [component=foo-service,requestId=test] foo-service called...
```

bar-performance.log will contain:
```
18-10-24 Wed 13:34:05.215  INFO [component=bar-service,requestId=test] bar-service called...
```

Next steps:

Use the `spring.sleuth.log.slf4j.whitelisted-mdc-keys` instead of extending the `CurrentTraceContext`

Run fatJar
Generate the fatJar from source or download it and simple do the following:

```
java -jar standalone-hystrix-dashboard-{VERSION}-all.jar
```

it should start the dashboard on default port 7979.

Run on background
Starting the application
Generate the fatJar from source or download it and simple do the following:
```
java -jar standalone-hystrix-dashboard-{VERSION}-all.jar start
```
it should start the dashboard on default port 7979 and it will print an UUID.

Stopping the application
After starting it, the startup process will print a UUID that you can use it to stop the application, if you don't remember the UUID you can check the running instances using the following commands:
```
java -jar standalone-hystrix-dashboard-{VERSION}-all.jar list
```
With the UUID you can stop the running instance with the following command:
```
java -jar standalone-hystrix-dashboard-{VERSION}-all.jar stop UUDI
```
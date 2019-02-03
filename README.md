
# Spring Session Hazelcast Client-Server Code deployment issue sample

Sample project for issue [spring-session](https://github.com/spring-projects/spring-session/issues/1319])

## Observed error
The Code deployment configuration is active, verified by disabling it which triggers an error related to the code deployment setting.

I've also deployed the classes provided in the [Integration Tests](https://github.com/spring-projects/spring-session/blob/83cbff5ce2e9e7b29fd4bb4cb05f21a5054dafaf/spring-session-hazelcast/src/integration-test/java/org/springframework/session/hazelcast/HazelcastClientRepositoryITests.java#L74-L82)

Now with the default Spring Security Config i get the following error on login on the hazelcast server:

```
com.hazelcast.nio.serialization.HazelcastSerializationException: java.lang.ClassNotFoundException: Failed to load class org.springframework.security.core.context.SecurityContextImpl from other members.
        at com.hazelcast.internal.serialization.impl.JavaDefaultSerializers$JavaSerializer.read(JavaDefaultSerializers.java:86)
        at com.hazelcast.internal.serialization.impl.JavaDefaultSerializers$JavaSerializer.read(JavaDefaultSerializers.java:75)
        at com.hazelcast.internal.serialization.impl.StreamSerializerAdapter.read(StreamSerializerAdapter.java:48)
        at com.hazelcast.internal.serialization.impl.AbstractSerializationService.toObject(AbstractSerializationService.java:187)
        at com.hazelcast.query.impl.CachedQueryEntry.getValue(CachedQueryEntry.java:75)
        at org.springframework.session.hazelcast.SessionUpdateEntryProcessor.process(SessionUpdateEntryProcessor.java:47)
        at com.hazelcast.map.impl.operation.EntryOperator.process(EntryOperator.java:318)
        at com.hazelcast.map.impl.operation.EntryOperator.operateOnKeyValueInternal(EntryOperator.java:181)
        at com.hazelcast.map.impl.operation.EntryOperator.operateOnKeyValue(EntryOperator.java:170)
        at com.hazelcast.map.impl.operation.EntryOperation$EntryOperationOffload$2.run(EntryOperation.java:343)
        at com.hazelcast.util.executor.CachedExecutorServiceDelegate$Worker.run(CachedExecutorServiceDelegate.java:227)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
        at java.base/java.lang.Thread.run(Thread.java:844)
        at com.hazelcast.util.executor.HazelcastManagedThread.executeRun(HazelcastManagedThread.java:64)
        at com.hazelcast.util.executor.HazelcastManagedThread.run(HazelcastManagedThread.java:80)

```

On the spring boot client app i get earlier errors related to spring security and hazelcast serialization

```
com.hazelcast.nio.serialization.HazelcastSerializationException: java.lang.ClassNotFoundException: Failed to load class org.springframework.security.web.csrf.DefaultCsrfToken from other members.
	at com.hazelcast.internal.serialization.impl.JavaDefaultSerializers$JavaSerializer.read(JavaDefaultSerializers.java:86) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.internal.serialization.impl.JavaDefaultSerializers$JavaSerializer.read(JavaDefaultSerializers.java:75) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.internal.serialization.impl.StreamSerializerAdapter.read(StreamSerializerAdapter.java:48) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.internal.serialization.impl.AbstractSerializationService.toObject(AbstractSerializationService.java:187) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.query.impl.CachedQueryEntry.getValue(CachedQueryEntry.java:75) ~[hazelcast-3.11.1.jar:3.11.1]
	at org.springframework.session.hazelcast.SessionUpdateEntryProcessor.process(SessionUpdateEntryProcessor.java:47) ~[spring-session-hazelcast-2.1.3.RELEASE.jar:2.1.3.RELEASE]
	at com.hazelcast.map.impl.operation.EntryOperator.process(EntryOperator.java:318) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.map.impl.operation.EntryOperator.operateOnKeyValueInternal(EntryOperator.java:181) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.map.impl.operation.EntryOperator.operateOnKeyValue(EntryOperator.java:170) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.map.impl.operation.EntryOperation$EntryOperationOffload$2.run(EntryOperation.java:343) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.util.executor.CachedExecutorServiceDelegate$Worker.run(CachedExecutorServiceDelegate.java:227) ~[hazelcast-3.11.1.jar:3.11.1]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167) [na:1.8.0_152]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641) [na:1.8.0_152]
	at java.lang.Thread.run(Thread.java:844) [na:1.8.0_152]
	at com.hazelcast.util.executor.HazelcastManagedThread.executeRun(HazelcastManagedThread.java:64) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.util.executor.HazelcastManagedThread.run(HazelcastManagedThread.java:80) ~[hazelcast-3.11.1.jar:3.11.1]
	at ------ submitted from ------.(Unknown Source) ~[na:na]
	at com.hazelcast.spi.impl.operationservice.impl.InvocationFuture.resolve(InvocationFuture.java:127) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.AbstractInvocationFuture$1.run(AbstractInvocationFuture.java:250) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.client.impl.protocol.task.AbstractPartitionMessageTask.execute(AbstractPartitionMessageTask.java:78) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.AbstractInvocationFuture.unblock(AbstractInvocationFuture.java:246) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.AbstractInvocationFuture.unblockAll(AbstractInvocationFuture.java:232) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.AbstractInvocationFuture.unblockAll(AbstractInvocationFuture.java:236) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.AbstractInvocationFuture.complete(AbstractInvocationFuture.java:374) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.Invocation.complete(Invocation.java:651) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.Invocation.notifyNormalResponse(Invocation.java:331) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.Invocation.notifyError(Invocation.java:290) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.Invocation.sendResponse(Invocation.java:209) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.Offload$OffloadedOperationResponseHandler.sendResponse(Offload.java:164) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.Operation.sendResponse(Operation.java:456) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.map.impl.operation.EntryOperation$EntryOperationOffload$3.sendResponse(EntryOperation.java:404) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.Operation.sendResponse(Operation.java:456) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.OperationRunnerImpl.handleResponse(OperationRunnerImpl.java:240) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.OperationRunnerImpl.call(OperationRunnerImpl.java:212) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationservice.impl.OperationRunnerImpl.run(OperationRunnerImpl.java:197) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationexecutor.impl.OperationThread.process(OperationThread.java:147) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationexecutor.impl.OperationThread.process(OperationThread.java:125) ~[hazelcast-3.11.1.jar:3.11.1]
	at com.hazelcast.spi.impl.operationexecutor.impl.OperationThread.run(OperationThread.java:110) ~[hazelcast-3.11.1.jar:3.11.1]

```

## Client

Sample Spring Boot App with default Spring Security Config

Controller that is secured by default accessible on "/".

Auto configured form login "/login"


## Server
*Server has to be run standalone with its own classpath*

i provided a maven project with only hazelcast server libs and code deployment enabled in the "server" directory

Start hazelcast server  ( in the server dir )
> mvn exec:java
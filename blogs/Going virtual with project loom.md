![[Pasted image 20230507125726.png]]
Hey there fellow Java developers! You know how Java has always been good at handling multi-threading and concurrency, right? From the early days of platform threads support to the latest improvements in JDK 8, we've had a lot to work with. But let's face it, it hasn't exactly been a walk in the park.

The shared state concurrency model in Java is powerful, but it's not always easy to use. We've had to deal with data races, thread blocking, and all sorts of other issues. I mean, who hasn't spent hours debugging race conditions?

Well, that's where Project Loom comes in. This new feature promises to revolutionize how we handle concurrency in Java. It's going to make our lives so much easier, with a simpler and more efficient way to deal with threads.

With Project Loom, we're getting a new lightweight thread model that's going to change everything. We won't have to worry so much about synchronizing threads or dealing with complex concurrency issues. It's going to be a game-changer, and I can't wait to see what we can do with it.

So, in this blog post, we're going to dive deep into Project Loom and explore all the benefits it has to offer. Get ready to say goodbye to those frustrating race conditions, and hello to a brighter, more efficient future for Java development!

## What is Project Loom?
At its core, Project Loom is all about making concurrency easier and more efficient in Java. It's an OpenJDK project that aims to enable "easy-to-use, high-throughput lightweight concurrency and new programming models on the Java platform." And how does it plan to accomplish this? By introducing new constructs like virtual threads, delimited continuations, and tail-call elimination.

Let's focus on virtual threads for a moment, as they are the key to Project Loom's success. Virtual threads look and feel like ordinary threads to programmers, but they are managed by the Java runtime and are not just thin wrappers over OS threads. Instead, virtual threads are implemented in user space by the Java runtime, which makes creating and blocking them cheap. Plus, Java execution schedulers (thread pools) can be used with virtual threads, and there are no OS-level data structures for the stack.
![[Pasted image 20230507125228.png]]

But the biggest advantage of virtual threads is that they remove the involvement of the OS in the lifecycle of a thread. This means that scalability is no longer bottlenecked by the number of OS-schedulable objects (which is essentially what a thread is). Large-scale JVM applications can now handle millions or even billions of objects without being restricted to just a few thousand OS-schedulable objects. This opens up new possibilities for concurrent programming styles and is the main aim of Project Loom.
![[Pasted image 20230507125303.png]]

But Project Loom is not just about virtual threads. It's also adding a couple of other features called delimited continuations and tail-call elimination. These might sound a bit intimidating, but they're actually pretty cool. Delimited continuations are like little checkpoints that let you pause your program's execution and resume it later from that point. They're really handy for things like coroutines, which are like lightweight threads that cooperate with each other instead of running in parallel.

Tail-call elimination on the other-hand is a clever optimization technique that can make your code faster and more efficient. When you write recursive functions, you run the risk of eating up a lot of memory with each call because of the added stack frames. But with tail-call elimination, the compiler can optimize your code so that it reuses the same stack frame, avoiding memory issues and making your code run more quickly. It's a pretty niche feature, but it's one of the goals of Project Loom nonetheless.

## See it in action


## So is the hype justified?

While virtual threads are a great feature, it's important to keep in mind that they are not a silver bullet solution to all concurrency problems. Web servers like Jetty have already been using NIO connectors to keep open a large number of connections with just a few threads, and in real-world scenarios, the problem isn't just handling a large number of connections, but also performing I/O work such as calling databases, working with the file system, and executing REST calls.

Project Loom shines in this area by providing non-blocking code without having to resort to the somewhat unintuitive async programming model, but it's important to remember that there is no free lunch. Scaling up to a million private threads in real-life scenarios requires a deep understanding of the underlying mechanisms and is not something that can be achieved automatically without proper knowledge.

So, should you get on board the Project Loom hype train? If you're a Java developer working on high-throughput, concurrent applications with significant I/O workloads, then absolutely. But, as with any new technology, it's important to approach it with a critical eye and not get carried away with the hype. Take the time to learn the underlying mechanisms and decide whether it's the right solution for your specific use case.
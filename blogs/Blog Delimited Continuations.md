**Delimited Continuations and Virtual Threads: A Deep Dive**

Hey there, Java aficionados! Remember our last chat about Project Loom and how it's set to reshape the concurrency landscape in Java? Well, hold onto your hats, because today we're diving deeper into the world of delimited continuations and virtual threads. If you thought Project Loom was all about lightweight threads, you're in for a treat!

**Introduction**

Imagine you're reading a gripping novel, and suddenly you're interrupted. You place a bookmark, attend to the interruption, and then pick up right where you left off. This is the magic of delimited continuations in the world of coding. It's like having a bookmark for your code execution. But how does this magic work? And what's the connection with virtual threads? Let's unravel the mystery.

## How is it working under the hood?

To understand delimited continuations, let's first talk about subroutines and coroutines. Typically, a subroutine has a fixed entry point, and it executes linearly. Think of it as a straight path. On the other hand, a coroutine has multiple entry points with yields. It's like a path with multiple stop points. In essence, it's a state machine.

Now, while many languages have embraced coroutines, Java has introduced them internally for virtual threads. As of now, it's an internal package, so you can't directly use the Continuation API. But here's a sneak peek into its workings based upon an alpha build of Project Loom:

```java
public class ContinuationPlay {

    private static final ContinuationScope MAIN_SCOPE = new ContinuationScope("mainScope");

    public static void main(String[] args) throws Exception {
        System.out.println("main : enter");

        Continuation cont = new Continuation(MAIN_SCOPE, new IOProcessor());
        while (!cont.isDone()) {
            cont.run();
            System.out.println(">> main : mainScope loop");
            Thread.sleep(3000); // Sleep for 3 seconds
        }

        System.out.println("main : exit");
    }
}

class IOProcessor implements Runnable {

    private static final ContinuationScope INNER_SCOPE = new ContinuationScope("innerScope");

    private void processMethod() {
        System.out.println("processMethod : enter");

        int step = 0;

        for (int i = 0; i < 3; i++) {
            step++;
            System.out.println("processMethod : execute step " + step);
            Continuation.yield(INNER_SCOPE);
        }

        System.out.println("processMethod : exit");
    }

    private void execute() {
        System.out.println("execute : enter");

        Continuation cont = new Continuation(INNER_SCOPE, this::processMethod);
        while (!cont.isDone()) {
            cont.run();
            System.out.println(">> execute : innerScope loop");
        }

        System.out.println("execute : exit");
    }

    @Override
    public void run() {
        execute();
    }
}

```

In the above code, we define two scopes: `MAIN_SCOPE` and `INNER_SCOPE`. The `IOProcessor` class has a method `processMethod` that simulates a task with multiple steps. Each step is executed, and then the execution is yielded using `Continuation.yield(INNER_SCOPE)`. This essentially pauses the execution at that point. The `execute` method runs the continuation until it's done, and the main method does the same for the `MAIN_SCOPE`.

Every time we rerun the method, it picks up from where it was paused. Think of it as pausing a movie and playing it again. And yes, we can have nested continuations, which can be yielded, returning the stack to the parent.

The beauty of this mechanism is its application in IO operations. For instance, during a write operation, we can yield the execution, freeing up resources momentarily. Once done, we resume right where we left off.
## Virtual threads using Continuations

Project Loom's introduction of Virtual Threads in Java is transformative. But how do these threads function, especially when paired with continuations?

Imagine you're working with Java's `VirtualThreadPerTaskExecutor`. This executor spawns virtual threads that aren't directly bound to an OS thread. When you submit a task, it's encapsulated within a Continuation. To run this task, a carrier thread (an OS thread) is requisitioned, typically from the common fork-join pool.

The ForkJoinPool then schedules the Continuation on the carrier thread. However, the code isn't immediately executed; it's passed as a lambda reference.

As the Continuation runs, it proceeds until it hits a blocking call, like a socket connection. Recognizing the code's operation on a virtual thread, the system opts for a non-blocking socket connection. Here, `yield` plays its part. The code returns, but the Continuation isn't over. The stack and code are retained within the Continuation.

The prowess of virtual threads is showcased here. The JVM transitions to a non-blocking socket. While achievable using reactive programming, it would've been our duty to handle. With virtual threads, the platform thread is released when the code yields. Each program resumption uses a new stack, based on the stored stack in the Continuation. This elucidates the swift genesis of virtual threads.

Now, let's dive into a hypothetical scenario. Imagine you've got a class named `Test`. You're submitting tasks, and each task creates a new instance of this `Test` class. Within this class, there's a method that uses the `synchronized` keyword. The catch? Due to the use of `synchronized`, the thread becomes pinned to the carrier thread. If your carrier thread pool contains only 5 threads, only five tasks will run concurrently. This means that even if the tasks seem straightforward, they'd take a whopping 800 seconds to complete.

So, what's the solution? Instead of the `synchronized` keyword, consider using locks. Luckily for us is that part of the release of JDK 21 these changes are already made in the JDK (where possible). However, application code that uses `synchronized` will need extra care!

**Conclusion**

Delimited continuations and virtual threads are like two sides of the same coin in the realm of Project Loom. They promise a future where Java developers can handle concurrency with ease, without the complexities of traditional threading models. While we eagerly await the public release of these features, it's clear that the future of Java concurrency is bright and efficient.

Stay tuned, as we'll continue to explore more facets of Project Loom in our upcoming posts. Until then, happy coding!
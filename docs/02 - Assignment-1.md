## Introduction:

You are within a demo project aimed at data handling and processing. The demo application you will be working on is responsible for fetching additional information from an external service, performing a database call, and executing a "complex computation". This is a typical portrayal of how a regular application would work. You will get acquainted with the application by running the test. You will notice that the test fails due to the time duration. The slow test processes a "large" dataset and takes a considerable amount of time. In this assignment, you will improve the application utilizing Project Loom.

---

### Assignment 1.1: Introduction to the Application and Identification of Performance Issues

1. **Launching the Spring Boot Application:**
   - Start by launching the Spring Boot application provided in the workshop repository. This application is set up with a Postgres database which is crucial for data handling within the application. Upon starting the application, it initializes the database and makes it ready for interaction.

2. **Understanding the Components:**
   - **Postgres Database:** This is where your data resides. The Spring Boot application interacts with this database to fetch, update, or store data as needed.
   - **Grafana:** Once your application is running, Grafana comes into play to help visualize the data. It provides a dashboard where you can see various metrics and analytics related to the performance of your application during the load tests.
   - **K6:** K6 is a powerful tool used for load testing. It helps in understanding how your application behaves under load. During this workshop, you will utilize K6 to simulate different load scenarios and observe how your application responds.
   - **Wiremock:** Wiremock is utilized in this setup to simulate some API endpoints. It allows you to test the interaction of your application with other services without having to depend on external systems.

3. **Running Load Tests:**
   - Navigate to the [**k6**](../k6) directory within your project using the console.
       ```bash
       :~$ cd ./k6
       :~$ ./run-load-test.sh
       ```
   - This script triggers the load tests that interact with your application.

4. **Visualizing Load Test Results:**
   - Go to [http://localhost:3000/d/k6/k6-load-testing-results?orgId=1&refresh=5s](http://localhost:3000/d/k6/k6-load-testing-results?orgId=1&refresh=5s) to view the dashboard that Grafana provides. Here you can observe various metrics that change in real-time as the load tests are executed.


5. **Connecting JConsole:**
   - JConsole is a graphical monitoring tool to monitor Java applications. It will be used to observe memory usage, CPU usage, and other performance metrics.
   - To use JConsole, open a terminal or command prompt, type `jconsole` and press Enter. Once JConsole launches, connect to your running Spring Boot application process.
   - Explore the 'Threads' tab to see a live chart of the number of live threads and peak threads, especially during the load tests.

6. **Identifying Performance Bottlenecks:**
   - Now that you have a good understanding of the setup, identify the parts of the code causing delays. Run the available load tests as described before with k6 by starting [`./run-load-test.sh`](../k6/run-load-test.sh). You will notice that the throughput is really low. When running the unit test  one test fails due to the time it takes to complete.
   - Analyze the performance issues. Take note of how the performance metrics on Grafana and JConsole change as you run the k6 load tests.


### Assignment 1.2: Implementation of Project Loom

In this task, you will refactor the `processLargeDataSet` method to leverage Project Loom’s [`newThreadPerTaskExecutor`](https://igm.univ-mlv.fr/~juge/javadoc-19/java.base/java/util/concurrent/Executors.html#newThreadPerTaskExecutor(java.util.concurrent.ThreadFactory)). This will aid you in enhancing the application's performance when dealing with large datasets.

1. **Preparatory Steps:**
   - You will need an `Executor` to manage the tasks. In Project Loom, you can utilize [`newThreadPerTaskExecutor`](https://igm.univ-mlv.fr/~juge/javadoc-19/java.base/java/util/concurrent/Executors.html#newThreadPerTaskExecutor(java.util.concurrent.ThreadFactory)) to create an [executor](https://jenkov.com/tutorials/java-util-concurrent/executorservice.html). Research how to use this method and how to set it up to use platform or virtual threads.
2. **Task Submission:**
   - Consider using a loop to iterate through `inputData` and submit a task for each item to the [executor](https://jenkov.com/tutorials/java-util-concurrent/executorservice.html). How would you submit each task? How would you store the result of each task for later retrieval?
3. **Collecting Results:**
   - After submitting all tasks, how would you collect the results of each task? Think about how you would iterate through the results of each task and collect them into a list.
4. **Shutting Down the Executor:**
   - It’s crucial to shut down the [executor](https://jenkov.com/tutorials/java-util-concurrent/executorservice.html) after the completion of all tasks to release resources. Research how to do this.
5. **Test your Solution:**
   - Re-run the tests and compare the performance of the new code with the old code. What do you notice?
   - Re-run the load tests and observe the performance of the application. How does it compare to the previous run? 
   - Recheck jconsole and Grafana to see how the performance metrics have changed.


# Welcome to the Loom Workshop

Hello and welcome to the/experts Loom Workshop! This is an exciting space where you get to explore and delve into the nuances of Project Loom, which is aimed at making concurrency easier to manage and more scalable within the JVM.

You can find the workshop slides here: [Loom Workshop Slides](https://docs.google.com/presentation/d/1eOhtAWSeEudRnlO17vXWq3k5dN9qan4YOtDBdWaD1W0/edit?usp=sharing)


## Getting Started

Before you dive into the assignments, ensure you've set up your environment correctly. You will find a detailed setup guide in the `docs` directory. Follow the instructions therein to get your system ready for the tasks ahead.

## Project Structure

Here's a quick overview of the repository structure and where to find the essential bits for the workshop:

- [**`compose.yml`**](./compose.yaml): This file contains the configuration for the PostgreSQL database that our Spring Boot application will interact with. Make sure you have Docker installed, and you can easily spin up the database using Docker Compose.

- [**`k6`**](./k6): This directory holds all the scripts and configurations for running load tests on your application using K6. This will be crucial in observing the performance improvements as you refactor the code to utilize virtual threads.

- [**`docs`**](./docs): Head over to this directory to find the assignments and the introductory guide to setting up your environment. The assignments are structured to gradually introduce you to the concepts and practical applications of Project Loom.

- [**`blogs`**](./blogs): For a deeper understanding of Project Loom and the technology behind virtual threads, we've curated some insightful blogs and articles in this directory. Feel free to peruse through them at your leisure.

## Your Journey

Your journey kicks off with baseline tests to understand the application's current performance. As you tackle the assignments, you'll refactor the code to utilize virtual threads for better performance. Yet, you'll face virtual thread-specific challenges, pushing you to find solutions.

During the workshop, you'll run load tests to visually assess performance improvements, using tools like Grafana and JConsole for deeper insights. These tools will highlight your gains and areas for improvement.

As you navigate through these tasks, leveraging online resources to overcome challenges might be necessary. By the end, you'll have a refactored application optimized with virtual threads and a solid grasp of managing the intricacies they bring along.

## Let's Dive In!

Now, with a sense of what lies ahead, it's time to dive into the assignments. Pick your preferred language module (`loom-workshop` or `loom-workshop-java`), follow the setup instructions in the [`docs\Setup.md`](./docs/01%20-%20Setup.md), and embark on this exciting journey of discovery into the world of virtual threads with Project Loom. The assignments are also located in the `docs` directory, and you can find the solutions by checking out the corresponding branch.

Enjoy the workshop, and may the threads be ever in your favor!

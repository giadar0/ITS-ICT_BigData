# Pig

## Prerequisites

This lab references hosts, users, paths and other details that belong to the [Vagrant-provisioned 3 nodes Hadoop cluster](../02-Provision_the_environment/Vagrant/README.md).

The [Vagrant-provisioned 3 nodes Hadoop cluster](../02-Provision_the_environment/Vagrant/README.md) should have been started before executing this lab.

Also, all the cluster services must be started as show in the picture below:  

![Cluster services](./img/3-nodes/cluster_services.png)

It is possible to run this lab in any other environment (i.e.: Hortonworks Sandbox or Cloudera DataFlow) provided some small changes to the elements listed above.


Go to path **ITS-ICT_BigData/labs/02-Provision_the_environment/Vagrant** and connect to node 1.

```console
$ vagrant ssh node1 
[vagrant@node1 ~]$ 
```

Move to this folder within the VM 


```console
[vagrant@node1 ~]$ cd ITS-ICT_BigData/labs/09-Pig/
```

Create the HDFS input folder

```console
[vagrant@node1 09-Pig]$ hadoop fs -mkdir lab9_input
```


Copy the dataset in the right HDFS folder

```console
[vagrant@node1 09-Pig]$ hadoop fs -put ../../datasets/students.csv lab9_input
```

## Case A: search for the maximun GPA

In this scenario, we will use a pig script to search the student with the maximun GPA (Grade Point Average)

### Run the script on local machine using Maven

To run Pig on your local machine please ensure to have:

 - A JDK version 1.8 installed
 - Your JAVA_HOME should be set to your JDK 1.8 home directory
 - JAVA_HOME/bin into the PATH
 - Maven installed and reachable from you PATH

```console
$ mvn exec:java -Dexec.args="-x local script.pig" 
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------< it.sunnyvale.hadoop.labs:code-mapreduce >---------------
[INFO] Building code-mapreduce 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ code-mapreduce ---
log4j:WARN No appenders could be found for logger (org.apache.hadoop.util.Shell).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
2020-09-14 12:26:29,745 [org.apache.pig.Main.main()] INFO  org.apache.pig.Main - Apache Pig version 0.17.0 (r1798794) compiled Jun 14 2017, 23:34:52
2020-09-14 12:26:29,746 [org.apache.pig.Main.main()] INFO  org.apache.pig.Main - Logging error messages to: /Users/denismaggiorotto/Documents/SoxelyNCFS/Sunnyvale/Clienti/ITS-ICT/2019-2021/Cloud/BigData/repos/ITS-ICT_BigData/labs/09-Pig/pig_1600079189713.log
2020-09-14 12:26:29,801 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - user.name is deprecated. Instead, use mapreduce.job.user.name
2020-09-14 12:26:30,468 [org.apache.pig.Main.main()] INFO  org.apache.pig.impl.util.Utils - Default bootup file /Users/denismaggiorotto/.pigbootup not found
2020-09-14 12:26:30,808 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:26:30,850 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.HExecutionEngine - Connecting to hadoop file system at: file:///
2020-09-14 12:26:30,902 [org.apache.pig.Main.main()] INFO  org.apache.pig.PigServer - Pig Script ID for the session: PIG-script.pig-30f39f26-d071-4b25-8040-d2fef0623ea8
2020-09-14 12:26:30,902 [org.apache.pig.Main.main()] WARN  org.apache.pig.PigServer - ATS is disabled since yarn.timeline-service.enabled set to false
2020-09-14 12:26:31,866 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.ScriptState - Pig features used in the script: ORDER_BY,LIMIT
2020-09-14 12:26:32,078 [org.apache.pig.Main.main()] INFO  org.apache.pig.newplan.logical.optimizer.LogicalPlanOptimizer - {RULES_ENABLED=[AddForEach, ColumnMapKeyPrune, ConstantCalculator, GroupByConstParallelSetter, LimitOptimizer, LoadTypeCastInserter, MergeFilter, MergeForEach, NestedLimitOptimizer, PartitionFilterOptimizer, PredicatePushdownOptimizer, PushDownForEachFlatten, PushUpFilter, SplitFilter, StreamTypeCastInserter]}
2020-09-14 12:26:32,231 [org.apache.pig.Main.main()] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:32,319 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MRCompiler - File concatenation threshold: 100 optimistic? false
2020-09-14 12:26:32,384 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.SecondaryKeyOptimizerMR - Using Secondary Key Optimization for MapReduce node scope-14
2020-09-14 12:26:32,391 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size before optimization: 4
2020-09-14 12:26:32,391 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size after optimization: 4
2020-09-14 12:26:32,665 [org.apache.pig.Main.main()] INFO  org.apache.commons.beanutils.FluentPropertyBeanIntrospector - Error when creating PropertyDescriptor for public final void org.apache.commons.configuration2.AbstractConfiguration.setProperty(java.lang.String,java.lang.Object)! Ignoring this property.
2020-09-14 12:26:32,701 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsConfig - Cannot locate configuration: tried hadoop-metrics2-jobtracker.properties,hadoop-metrics2.properties
2020-09-14 12:26:33,010 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - Scheduled Metric snapshot period at 10 second(s).
2020-09-14 12:26:33,011 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system started
2020-09-14 12:26:33,053 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-14 12:26:33,067 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:26:33,067 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-14 12:26:33,069 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.output.compress is deprecated. Instead, use mapreduce.output.fileoutputformat.compress
2020-09-14 12:26:33,085 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-14 12:26:33,103 [org.apache.pig.Main.main()] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-14 12:26:33,326 [org.apache.pig.Main.main()] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-14 12:26:33,326 [org.apache.pig.Main.main()] INFO  org.apache.pig.data.SchemaTupleFrontend - Distributed cache not supported or needed in local mode. Setting key [pig.schematuple.local.dir] with code temp directory: /var/folders/4d/_r283cnd6r5g7qpqgrnt2j1m0000gp/T/1600079193102-0
2020-09-14 12:26:33,392 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-14 12:26:33,406 [JobControl] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:33,451 [JobControl] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.task.id is deprecated. Instead, use mapreduce.task.attempt.id
2020-09-14 12:26:34,284 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-14 12:26:34,313 [JobControl] INFO  org.apache.pig.builtin.PigStorage - Using PigTextInputFormat
2020-09-14 12:26:34,346 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:26:34,346 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-14 12:26:34,369 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-14 12:26:34,551 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-14 12:26:34,801 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_local606298268_0001
2020-09-14 12:26:34,813 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-14 12:26:35,145 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://localhost:8080/
2020-09-14 12:26:35,146 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_local606298268_0001
2020-09-14 12:26:35,146 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset
2020-09-14 12:26:35,146 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset[1,10],dataset[-1,-1] C:  R: 
2020-09-14 12:26:35,146 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter set in config null
2020-09-14 12:26:35,151 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 0% complete
2020-09-14 12:26:35,151 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local606298268_0001]
2020-09-14 12:26:35,164 [Thread-24] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:26:35,190 [Thread-24] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:26:35,192 [Thread-24] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:35,192 [Thread-24] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:35,192 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter is org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigOutputCommitter
2020-09-14 12:26:35,303 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for map tasks
2020-09-14 12:26:35,304 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local606298268_0001_m_000000_0
2020-09-14 12:26:35,337 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:35,337 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:35,347 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:35,348 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:35,354 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Processing split: Number of splits :1
Total Length = 49
Input split[0]:
   Length = 49
   ClassName: org.apache.hadoop.mapreduce.lib.input.FileSplit
   Locations:

-----------------------

2020-09-14 12:26:35,502 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.builtin.PigStorage - Using PigTextInputFormat
2020-09-14 12:26:35,511 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigRecordReader - Current split being processed file:/Users/denismaggiorotto/Documents/SoxelyNCFS/Sunnyvale/Clienti/ITS-ICT/2019-2021/Cloud/BigData/repos/ITS-ICT_BigData/labs/09-Pig/lab9_input/students.csv:0+49
2020-09-14 12:26:35,516 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:35,516 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:35,602 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:35,604 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.data.SchemaTupleBackend - Key [pig.schematuple] was not set... will not generate code.
2020-09-14 12:26:35,612 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigMapOnly$Map - Aliases being processed per job phase (AliasName[line,offset]): M: dataset[1,10],dataset[-1,-1] C:  R: 
2020-09-14 12:26:35,620 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - 
2020-09-14 12:26:35,625 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local606298268_0001_m_000000_0 is done. And is in the process of committing
2020-09-14 12:26:35,723 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - 
2020-09-14 12:26:35,723 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task attempt_local606298268_0001_m_000000_0 is allowed to commit now
2020-09-14 12:26:35,733 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - Saved output of task 'attempt_local606298268_0001_m_000000_0' to file:/tmp/temp-713829570/tmp765344832
2020-09-14 12:26:35,733 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - map
2020-09-14 12:26:35,734 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local606298268_0001_m_000000_0' done.
2020-09-14 12:26:35,742 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local606298268_0001_m_000000_0: Counters: 15
        File System Counters
                FILE: Number of bytes read=588
                FILE: Number of bytes written=413835
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Map input records=5
                Map output records=5
                Input split bytes=485
                Spilled Records=0
                Failed Shuffles=0
                Merged Map outputs=0
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=343932928
        File Input Format Counters 
                Bytes Read=0
        File Output Format Counters 
                Bytes Written=0
2020-09-14 12:26:35,758 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local606298268_0001_m_000000_0
2020-09-14 12:26:35,760 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - map task executor complete.
2020-09-14 12:26:35,988 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 25% complete
2020-09-14 12:26:35,991 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:36,020 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:36,021 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
2020-09-14 12:26:36,021 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
2020-09-14 12:26:36,022 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:36,039 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-14 12:26:36,039 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-14 12:26:36,051 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-14 12:26:36,052 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Using reducer estimator: org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator
2020-09-14 12:26:36,053 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator - BytesPerReducer=1000000000 maxReducers=999 totalInputFileSize=75
2020-09-14 12:26:36,054 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-14 12:26:36,057 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-14 12:26:36,093 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-14 12:26:36,096 [JobControl] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:36,163 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-14 12:26:36,213 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:26:36,213 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-14 12:26:36,213 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-14 12:26:36,437 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-14 12:26:36,512 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_local501787712_0002
2020-09-14 12:26:36,512 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-14 12:26:36,686 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://localhost:8080/
2020-09-14 12:26:36,686 [Thread-55] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter set in config null
2020-09-14 12:26:36,686 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_local501787712_0002
2020-09-14 12:26:36,687 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset_ordered
2020-09-14 12:26:36,687 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:36,694 [Thread-55] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
2020-09-14 12:26:36,694 [Thread-55] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:26:36,809 [Thread-55] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:26:36,809 [Thread-55] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:36,809 [Thread-55] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:36,810 [Thread-55] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter is org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigOutputCommitter
2020-09-14 12:26:36,864 [Thread-55] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for map tasks
2020-09-14 12:26:36,864 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local501787712_0002_m_000000_0
2020-09-14 12:26:36,873 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:36,873 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:36,873 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:36,874 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:36,876 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Processing split: Number of splits :1
Total Length = 75
Input split[0]:
   Length = 75
   ClassName: org.apache.hadoop.mapreduce.lib.input.FileSplit
   Locations:

-----------------------

2020-09-14 12:26:36,995 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigRecordReader - Current split being processed file:/tmp/temp-713829570/tmp765344832/part-m-00000:0+75
2020-09-14 12:26:37,026 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - (EQUATOR) 0 kvi 26214396(104857584)
2020-09-14 12:26:37,027 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - mapreduce.task.io.sort.mb: 100
2020-09-14 12:26:37,027 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - soft limit at 83886080
2020-09-14 12:26:37,027 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufvoid = 104857600
2020-09-14 12:26:37,027 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396; length = 6553600
2020-09-14 12:26:37,036 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-09-14 12:26:37,039 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:37,119 [LocalJobRunner Map Task Executor #0] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:37,122 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigGenericMapReduce$Map - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:37,140 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - 
2020-09-14 12:26:37,140 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Starting flush of map output
2020-09-14 12:26:37,140 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Spilling map output
2020-09-14 12:26:37,140 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufend = 80; bufvoid = 104857600
2020-09-14 12:26:37,140 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396(104857584); kvend = 26214380(104857520); length = 17/6553600
2020-09-14 12:26:37,199 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Finished spill 0
2020-09-14 12:26:37,210 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local501787712_0002_m_000000_0 is done. And is in the process of committing
2020-09-14 12:26:37,212 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - map
2020-09-14 12:26:37,212 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local501787712_0002_m_000000_0' done.
2020-09-14 12:26:37,212 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local501787712_0002_m_000000_0: Counters: 17
        File System Counters
                FILE: Number of bytes read=1110
                FILE: Number of bytes written=844741
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Map input records=5
                Map output records=5
                Map output bytes=80
                Map output materialized bytes=96
                Input split bytes=377
                Combine input records=0
                Spilled Records=5
                Failed Shuffles=0
                Merged Map outputs=0
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=349175808
        File Input Format Counters 
                Bytes Read=0
2020-09-14 12:26:37,268 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local501787712_0002_m_000000_0
2020-09-14 12:26:37,269 [Thread-55] INFO  org.apache.hadoop.mapred.LocalJobRunner - map task executor complete.
2020-09-14 12:26:37,271 [Thread-55] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for reduce tasks
2020-09-14 12:26:37,271 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local501787712_0002_r_000000_0
2020-09-14 12:26:37,285 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:37,285 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:37,286 [pool-6-thread-1] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:37,286 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:37,288 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.ReduceTask - Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@19dbefa0
2020-09-14 12:26:37,301 [pool-6-thread-1] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:37,325 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - MergerManager: memoryLimit=2672505600, maxSingleShuffleLimit=668126400, mergeThreshold=1763853824, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-09-14 12:26:37,328 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - attempt_local501787712_0002_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-09-14 12:26:37,419 [localfetcher#1] INFO  org.apache.hadoop.mapreduce.task.reduce.LocalFetcher - localfetcher#1 about to shuffle output of map attempt_local501787712_0002_m_000000_0 decomp: 92 len: 96 to MEMORY
2020-09-14 12:26:37,421 [localfetcher#1] INFO  org.apache.hadoop.mapreduce.task.reduce.InMemoryMapOutput - Read 92 bytes from map-output for attempt_local501787712_0002_m_000000_0
2020-09-14 12:26:37,431 [localfetcher#1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - closeInMemoryFile -> map-output of size: 92, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->92
2020-09-14 12:26:37,433 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - EventFetcher is interrupted.. Returning
2020-09-14 12:26:37,434 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:37,434 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-09-14 12:26:37,459 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:26:37,459 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 79 bytes
2020-09-14 12:26:37,479 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merged 1 segments, 92 bytes to disk to satisfy reduce memory limit
2020-09-14 12:26:37,480 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 1 files, 96 bytes from disk
2020-09-14 12:26:37,480 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 0 segments, 0 bytes from memory into reduce
2020-09-14 12:26:37,480 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:26:37,481 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 79 bytes
2020-09-14 12:26:37,481 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:37,561 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:37,561 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:37,636 [pool-6-thread-1] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.skip.on is deprecated. Instead, use mapreduce.job.skiprecords
2020-09-14 12:26:37,639 [pool-6-thread-1] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:37,640 [pool-6-thread-1] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:37,649 [pool-6-thread-1] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigMapReduce$Reduce - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:37,699 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 37% complete
2020-09-14 12:26:37,708 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local501787712_0002]
2020-09-14 12:26:37,719 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local501787712_0002_r_000000_0 is done. And is in the process of committing
2020-09-14 12:26:37,721 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:37,721 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Task - Task attempt_local501787712_0002_r_000000_0 is allowed to commit now
2020-09-14 12:26:37,726 [pool-6-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - Saved output of task 'attempt_local501787712_0002_r_000000_0' to file:/tmp/temp-713829570/tmp1730779441
2020-09-14 12:26:37,728 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce > reduce
2020-09-14 12:26:37,728 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local501787712_0002_r_000000_0' done.
2020-09-14 12:26:37,728 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local501787712_0002_r_000000_0: Counters: 24
        File System Counters
                FILE: Number of bytes read=1334
                FILE: Number of bytes written=844899
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Combine input records=0
                Combine output records=0
                Reduce input groups=1
                Reduce shuffle bytes=96
                Reduce input records=5
                Reduce output records=1
                Spilled Records=5
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=73
                Total committed heap usage (bytes)=349175808
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Output Format Counters 
                Bytes Written=0
2020-09-14 12:26:37,854 [pool-6-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local501787712_0002_r_000000_0
2020-09-14 12:26:37,855 [Thread-55] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce task executor complete.
2020-09-14 12:26:38,046 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 50% complete
2020-09-14 12:26:38,048 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:38,049 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:38,049 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:38,059 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-14 12:26:38,059 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-14 12:26:38,060 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-14 12:26:38,216 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-14 12:26:38,219 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-14 12:26:38,251 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-14 12:26:38,253 [JobControl] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:38,317 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-14 12:26:38,361 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:26:38,362 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-14 12:26:38,362 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-14 12:26:38,535 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-14 12:26:38,667 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_local159687042_0003
2020-09-14 12:26:38,667 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-14 12:26:38,883 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://localhost:8080/
2020-09-14 12:26:38,884 [Thread-98] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter set in config null
2020-09-14 12:26:38,884 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_local159687042_0003
2020-09-14 12:26:38,884 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset_ordered
2020-09-14 12:26:38,884 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:38,890 [Thread-98] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
2020-09-14 12:26:38,890 [Thread-98] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:26:39,049 [Thread-98] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:26:39,049 [Thread-98] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:39,049 [Thread-98] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:39,050 [Thread-98] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter is org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigOutputCommitter
2020-09-14 12:26:39,112 [Thread-98] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for map tasks
2020-09-14 12:26:39,112 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local159687042_0003_m_000000_0
2020-09-14 12:26:39,121 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:39,121 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:39,121 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:39,121 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:39,123 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Processing split: Number of splits :1
Total Length = 75
Input split[0]:
   Length = 75
   ClassName: org.apache.hadoop.mapreduce.lib.input.FileSplit
   Locations:

-----------------------

2020-09-14 12:26:39,245 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigRecordReader - Current split being processed file:/tmp/temp-713829570/tmp765344832/part-m-00000:0+75
2020-09-14 12:26:39,307 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - (EQUATOR) 0 kvi 26214396(104857584)
2020-09-14 12:26:39,307 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - mapreduce.task.io.sort.mb: 100
2020-09-14 12:26:39,307 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - soft limit at 83886080
2020-09-14 12:26:39,307 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufvoid = 104857600
2020-09-14 12:26:39,307 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396; length = 6553600
2020-09-14 12:26:39,312 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-09-14 12:26:39,314 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:39,389 [LocalJobRunner Map Task Executor #0] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:39,392 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigGenericMapReduce$Map - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:39,397 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - 
2020-09-14 12:26:39,398 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Starting flush of map output
2020-09-14 12:26:39,398 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Spilling map output
2020-09-14 12:26:39,398 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufend = 90; bufvoid = 104857600
2020-09-14 12:26:39,398 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396(104857584); kvend = 26214380(104857520); length = 17/6553600
2020-09-14 12:26:39,471 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigCombiner$Combine - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:39,475 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Finished spill 0
2020-09-14 12:26:39,496 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local159687042_0003_m_000000_0 is done. And is in the process of committing
2020-09-14 12:26:39,497 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - map
2020-09-14 12:26:39,498 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local159687042_0003_m_000000_0' done.
2020-09-14 12:26:39,498 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local159687042_0003_m_000000_0: Counters: 18
        File System Counters
                FILE: Number of bytes read=1856
                FILE: Number of bytes written=1282348
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Map input records=5
                Map output records=5
                Map output bytes=90
                Map output materialized bytes=30
                Input split bytes=377
                Combine input records=5
                Combine output records=1
                Spilled Records=1
                Failed Shuffles=0
                Merged Map outputs=0
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=454557696
        File Input Format Counters 
                Bytes Read=0
2020-09-14 12:26:39,553 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local159687042_0003_m_000000_0
2020-09-14 12:26:39,553 [Thread-98] INFO  org.apache.hadoop.mapred.LocalJobRunner - map task executor complete.
2020-09-14 12:26:39,554 [Thread-98] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for reduce tasks
2020-09-14 12:26:39,554 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local159687042_0003_r_000000_0
2020-09-14 12:26:39,562 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:39,563 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:39,564 [pool-9-thread-1] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:39,564 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:39,564 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.ReduceTask - Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@57dea8e2
2020-09-14 12:26:39,569 [pool-9-thread-1] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:39,570 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - MergerManager: memoryLimit=2672505600, maxSingleShuffleLimit=668126400, mergeThreshold=1763853824, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-09-14 12:26:39,570 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - attempt_local159687042_0003_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-09-14 12:26:39,572 [localfetcher#2] INFO  org.apache.hadoop.mapreduce.task.reduce.LocalFetcher - localfetcher#2 about to shuffle output of map attempt_local159687042_0003_m_000000_0 decomp: 26 len: 30 to MEMORY
2020-09-14 12:26:39,572 [localfetcher#2] INFO  org.apache.hadoop.mapreduce.task.reduce.InMemoryMapOutput - Read 26 bytes from map-output for attempt_local159687042_0003_m_000000_0
2020-09-14 12:26:39,635 [localfetcher#2] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - closeInMemoryFile -> map-output of size: 26, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->26
2020-09-14 12:26:39,635 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - EventFetcher is interrupted.. Returning
2020-09-14 12:26:39,636 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:39,636 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-09-14 12:26:39,656 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:26:39,656 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 18 bytes
2020-09-14 12:26:39,675 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merged 1 segments, 26 bytes to disk to satisfy reduce memory limit
2020-09-14 12:26:39,676 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 1 files, 30 bytes from disk
2020-09-14 12:26:39,676 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 0 segments, 0 bytes from memory into reduce
2020-09-14 12:26:39,676 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:26:39,676 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 18 bytes
2020-09-14 12:26:39,676 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:39,746 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:39,746 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:39,820 [pool-9-thread-1] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:39,820 [pool-9-thread-1] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:39,821 [pool-9-thread-1] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigMapReduce$Reduce - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:39,822 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local159687042_0003_r_000000_0 is done. And is in the process of committing
2020-09-14 12:26:39,824 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:39,824 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Task - Task attempt_local159687042_0003_r_000000_0 is allowed to commit now
2020-09-14 12:26:39,827 [pool-9-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - Saved output of task 'attempt_local159687042_0003_r_000000_0' to file:/tmp/temp-713829570/tmp-1298487316
2020-09-14 12:26:39,871 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce > reduce
2020-09-14 12:26:39,871 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local159687042_0003_r_000000_0' done.
2020-09-14 12:26:39,871 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local159687042_0003_r_000000_0: Counters: 24
        File System Counters
                FILE: Number of bytes read=1948
                FILE: Number of bytes written=1282409
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Combine input records=0
                Combine output records=0
                Reduce input groups=1
                Reduce shuffle bytes=30
                Reduce input records=1
                Reduce output records=1
                Spilled Records=1
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=454557696
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Output Format Counters 
                Bytes Written=0
2020-09-14 12:26:39,871 [pool-9-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local159687042_0003_r_000000_0
2020-09-14 12:26:39,871 [Thread-98] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce task executor complete.
2020-09-14 12:26:39,895 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 75% complete
2020-09-14 12:26:39,895 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local159687042_0003]
2020-09-14 12:26:40,028 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:40,029 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:40,030 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:40,034 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-14 12:26:40,034 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-14 12:26:40,035 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-14 12:26:40,039 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-14 12:26:40,041 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-14 12:26:40,051 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-14 12:26:40,055 [JobControl] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:40,132 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-14 12:26:40,164 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:26:40,165 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-14 12:26:40,165 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-14 12:26:40,302 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-14 12:26:40,365 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_local467387724_0004
2020-09-14 12:26:40,365 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-14 12:26:40,455 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://localhost:8080/
2020-09-14 12:26:40,455 [Thread-141] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter set in config null
2020-09-14 12:26:40,460 [Thread-141] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
2020-09-14 12:26:40,460 [Thread-141] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:26:40,460 [Thread-141] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:26:40,461 [Thread-141] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:40,461 [Thread-141] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:40,535 [Thread-141] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter is org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigOutputCommitter
2020-09-14 12:26:40,558 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_local467387724_0004
2020-09-14 12:26:40,558 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset_ordered
2020-09-14 12:26:40,558 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:40,574 [Thread-141] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for map tasks
2020-09-14 12:26:40,575 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local467387724_0004_m_000000_0
2020-09-14 12:26:40,581 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:40,581 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:40,683 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:40,683 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:40,684 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Processing split: Number of splits :1
Total Length = 19
Input split[0]:
   Length = 19
   ClassName: org.apache.hadoop.mapreduce.lib.input.FileSplit
   Locations:

-----------------------

2020-09-14 12:26:40,686 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigRecordReader - Current split being processed file:/tmp/temp-713829570/tmp-1298487316/part-r-00000:0+19
2020-09-14 12:26:40,752 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - (EQUATOR) 0 kvi 26214396(104857584)
2020-09-14 12:26:40,752 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - mapreduce.task.io.sort.mb: 100
2020-09-14 12:26:40,752 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - soft limit at 83886080
2020-09-14 12:26:40,752 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufvoid = 104857600
2020-09-14 12:26:40,752 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396; length = 6553600
2020-09-14 12:26:40,753 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-09-14 12:26:40,756 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:40,851 [LocalJobRunner Map Task Executor #0] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:40,852 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigGenericMapReduce$Map - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:40,852 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - 
2020-09-14 12:26:40,852 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Starting flush of map output
2020-09-14 12:26:40,852 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Spilling map output
2020-09-14 12:26:40,852 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufend = 22; bufvoid = 104857600
2020-09-14 12:26:40,853 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396(104857584); kvend = 26214396(104857584); length = 1/6553600
2020-09-14 12:26:40,902 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Finished spill 0
2020-09-14 12:26:40,912 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local467387724_0004_m_000000_0 is done. And is in the process of committing
2020-09-14 12:26:40,913 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - map
2020-09-14 12:26:40,913 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local467387724_0004_m_000000_0' done.
2020-09-14 12:26:40,914 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local467387724_0004_m_000000_0: Counters: 17
        File System Counters
                FILE: Number of bytes read=2416
                FILE: Number of bytes written=1707307
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Map input records=1
                Map output records=1
                Map output bytes=22
                Map output materialized bytes=30
                Input split bytes=379
                Combine input records=0
                Spilled Records=1
                Failed Shuffles=0
                Merged Map outputs=0
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=559939584
        File Input Format Counters 
                Bytes Read=0
2020-09-14 12:26:40,944 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local467387724_0004_m_000000_0
2020-09-14 12:26:40,944 [Thread-141] INFO  org.apache.hadoop.mapred.LocalJobRunner - map task executor complete.
2020-09-14 12:26:40,945 [Thread-141] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for reduce tasks
2020-09-14 12:26:40,945 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local467387724_0004_r_000000_0
2020-09-14 12:26:40,950 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:40,951 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:40,953 [pool-12-thread-1] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:26:40,953 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:26:40,953 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.ReduceTask - Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@31887982
2020-09-14 12:26:40,953 [pool-12-thread-1] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:40,964 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - MergerManager: memoryLimit=2672505600, maxSingleShuffleLimit=668126400, mergeThreshold=1763853824, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-09-14 12:26:41,043 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - attempt_local467387724_0004_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-09-14 12:26:41,044 [localfetcher#3] INFO  org.apache.hadoop.mapreduce.task.reduce.LocalFetcher - localfetcher#3 about to shuffle output of map attempt_local467387724_0004_m_000000_0 decomp: 26 len: 30 to MEMORY
2020-09-14 12:26:41,045 [localfetcher#3] INFO  org.apache.hadoop.mapreduce.task.reduce.InMemoryMapOutput - Read 26 bytes from map-output for attempt_local467387724_0004_m_000000_0
2020-09-14 12:26:41,046 [localfetcher#3] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - closeInMemoryFile -> map-output of size: 26, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->26
2020-09-14 12:26:41,050 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - EventFetcher is interrupted.. Returning
2020-09-14 12:26:41,051 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:41,051 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-09-14 12:26:41,064 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 87% complete
2020-09-14 12:26:41,064 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local467387724_0004]
2020-09-14 12:26:41,070 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:26:41,071 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 18 bytes
2020-09-14 12:26:41,148 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merged 1 segments, 26 bytes to disk to satisfy reduce memory limit
2020-09-14 12:26:41,149 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 1 files, 30 bytes from disk
2020-09-14 12:26:41,149 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 0 segments, 0 bytes from memory into reduce
2020-09-14 12:26:41,149 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:26:41,149 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 18 bytes
2020-09-14 12:26:41,149 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:41,150 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:26:41,150 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:26:41,348 [pool-12-thread-1] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:26:41,348 [pool-12-thread-1] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:41,350 [pool-12-thread-1] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigMapReduce$Reduce - Aliases being processed per job phase (AliasName[line,offset]): M: dataset_ordered[3,18] C:  R: 
2020-09-14 12:26:41,352 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local467387724_0004_r_000000_0 is done. And is in the process of committing
2020-09-14 12:26:41,354 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:26:41,354 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Task - Task attempt_local467387724_0004_r_000000_0 is allowed to commit now
2020-09-14 12:26:41,357 [pool-12-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - Saved output of task 'attempt_local467387724_0004_r_000000_0' to file:/tmp/temp-713829570/tmp481628588
2020-09-14 12:26:41,453 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce > reduce
2020-09-14 12:26:41,453 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local467387724_0004_r_000000_0' done.
2020-09-14 12:26:41,453 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local467387724_0004_r_000000_0: Counters: 24
        File System Counters
                FILE: Number of bytes read=2508
                FILE: Number of bytes written=1707368
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Combine input records=0
                Combine output records=0
                Reduce input groups=1
                Reduce shuffle bytes=30
                Reduce input records=1
                Reduce output records=1
                Spilled Records=1
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=559939584
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Output Format Counters 
                Bytes Written=0
2020-09-14 12:26:41,460 [pool-12-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local467387724_0004_r_000000_0
2020-09-14 12:26:41,461 [Thread-141] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce task executor complete.
2020-09-14 12:26:41,568 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local467387724_0004]
2020-09-14 12:26:41,711 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,713 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,714 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,720 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 100% complete
2020-09-14 12:26:41,724 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.SimplePigStats - Script Statistics: 

HadoopVersion   PigVersion      UserId  StartedAt       FinishedAt      Features
3.0.0   0.17.0  denismaggiorotto        2020-09-14 12:26:33     2020-09-14 12:26:41     ORDER_BY,LIMIT

Success!

Job Stats (time in seconds):
JobId   Maps    Reduces MaxMapTime      MinMapTime      AvgMapTime      MedianMapTime   MaxReduceTime   MinReduceTime   AvgReduceTime   MedianReducetime        Alias   Feature       Outputs
job_local159687042_0003 1       1       n/a     n/a     n/a     n/a     n/a     n/a     n/a     n/a     dataset_ordered ORDER_BY,COMBINER
job_local467387724_0004 1       1       n/a     n/a     n/a     n/a     n/a     n/a     n/a     n/a     dataset_ordered         file:/tmp/temp-713829570/tmp481628588,
job_local501787712_0002 1       1       n/a     n/a     n/a     n/a     n/a     n/a     n/a     n/a     dataset_ordered SAMPLER
job_local606298268_0001 1       0       n/a     n/a     n/a     n/a     0       0       0       0       dataset MAP_ONLY

Input(s):
Successfully read 5 records from: "file:///Users/denismaggiorotto/Documents/SoxelyNCFS/Sunnyvale/Clienti/ITS-ICT/2019-2021/Cloud/BigData/repos/ITS-ICT_BigData/labs/09-Pig/lab9_input/students.csv"

Output(s):
Successfully stored 1 records in: "file:/tmp/temp-713829570/tmp481628588"

Counters:
Total records written : 1
Total bytes written : 0
Spillable Memory Manager spill count : 0
Total bags proactively spilled: 0
Total records proactively spilled: 0

Job DAG:
job_local606298268_0001 ->      job_local501787712_0002,
job_local501787712_0002 ->      job_local159687042_0003,
job_local159687042_0003 ->      job_local467387724_0004,
job_local467387724_0004


2020-09-14 12:26:41,779 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,780 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,781 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,784 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,786 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,787 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,791 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,813 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,814 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,818 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,820 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,821 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:26:41,824 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Success!
2020-09-14 12:26:41,828 [org.apache.pig.Main.main()] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:26:41,904 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:26:41,904 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
(Margherita,30)
2020-09-14 12:26:41,932 [org.apache.pig.Main.main()] INFO  org.apache.pig.Main - Pig script completed in 12 seconds and 894 milliseconds (12894 ms)
```

### Run the script within VM

```console
[vagrant@node1 09-Pig]$ pig script.pig 
20/09/10 23:13:27 INFO pig.ExecTypeProvider: Trying ExecType : LOCAL
20/09/10 23:13:27 INFO pig.ExecTypeProvider: Trying ExecType : MAPREDUCE
20/09/10 23:13:27 INFO pig.ExecTypeProvider: Picked MAPREDUCE as the ExecType
2020-09-10 23:13:27,557 [main] INFO  org.apache.pig.Main - Apache Pig version 0.17.0 (r1797386) compiled Jun 02 2017, 15:41:58
2020-09-10 23:13:27,557 [main] INFO  org.apache.pig.Main - Logging error messages to: /usr/src/git_repo/labs/09-Pig/pig_1599779607555.log
2020-09-10 23:13:28,110 [main] INFO  org.apache.pig.impl.util.Utils - Default bootup file /home/vagrant/.pigbootup not found
2020-09-10 23:13:28,216 [main] INFO  org.apache.pig.backend.hadoop.executionengine.HExecutionEngine - Connecting to hadoop file system at: hdfs://node1.example.com:8020
2020-09-10 23:13:28,856 [main] INFO  org.apache.pig.PigServer - Pig Script ID for the session: PIG-script.pig-224b342d-345e-40d1-a64a-92020de86c4b
2020-09-10 23:13:29,299 [main] INFO  org.apache.hadoop.yarn.client.api.impl.TimelineClientImpl - Timeline service address: node2.example.com:8188
2020-09-10 23:13:29,840 [main] INFO  org.apache.pig.backend.hadoop.PigATSClient - Created ATS Hook
2020-09-10 23:13:31,183 [main] INFO  org.apache.pig.tools.pigstats.ScriptState - Pig features used in the script: ORDER_BY,LIMIT
2020-09-10 23:13:31,304 [main] INFO  org.apache.pig.data.SchemaTupleBackend - Key [pig.schematuple] was not set... will not generate code.
2020-09-10 23:13:31,417 [main] INFO  org.apache.pig.newplan.logical.optimizer.LogicalPlanOptimizer - {RULES_ENABLED=[AddForEach, ColumnMapKeyPrune, ConstantCalculator, GroupByConstParallelSetter, LimitOptimizer, LoadTypeCastInserter, MergeFilter, MergeForEach, NestedLimitOptimizer, PartitionFilterOptimizer, PredicatePushdownOptimizer, PushDownForEachFlatten, PushUpFilter, SplitFilter, StreamTypeCastInserter]}
2020-09-10 23:13:31,560 [main] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 699400192 to monitor. collectionUsageThreshold = 489580128, usageThreshold = 489580128
2020-09-10 23:13:31,749 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MRCompiler - File concatenation threshold: 100 optimistic? false
2020-09-10 23:13:32,017 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.SecondaryKeyOptimizerMR - Using Secondary Key Optimization for MapReduce node scope-14
2020-09-10 23:13:32,062 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size before optimization: 4
2020-09-10 23:13:32,063 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size after optimization: 4
2020-09-10 23:13:32,289 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:13:32,775 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:13:32,906 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-10 23:13:32,910 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-10 23:13:32,942 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - This job cannot be converted run in-process
2020-09-10 23:13:33,386 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/pig-0.17.0-core-h2.jar to DistributedCache through /tmp/temp1767239210/tmp-608831957/pig-0.17.0-core-h2.jar
2020-09-10 23:13:33,471 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/automaton-1.11-8.jar to DistributedCache through /tmp/temp1767239210/tmp1912427586/automaton-1.11-8.jar
2020-09-10 23:13:33,554 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/antlr-runtime-3.4.jar to DistributedCache through /tmp/temp1767239210/tmp-1169895847/antlr-runtime-3.4.jar
2020-09-10 23:13:33,703 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/joda-time-2.9.3.jar to DistributedCache through /tmp/temp1767239210/tmp325619643/joda-time-2.9.3.jar
2020-09-10 23:13:33,732 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-10 23:13:33,812 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-10 23:13:33,812 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-10 23:13:33,812 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Setting key [pig.schematuple.classes] with classes to deserialize []
2020-09-10 23:13:33,967 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-10 23:13:34,049 [JobControl] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:13:34,060 [JobControl] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:13:34,263 [JobControl] INFO  org.apache.hadoop.mapreduce.JobResourceUploader - Disabling Erasure Coding for path: /user/vagrant/.staging/job_1599772615630_0001
2020-09-10 23:13:34,293 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-10 23:13:34,383 [JobControl] INFO  org.apache.pig.builtin.PigStorage - Using PigTextInputFormat
2020-09-10 23:13:34,417 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:13:34,417 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-10 23:13:34,458 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-10 23:13:34,671 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-10 23:13:35,043 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_1599772615630_0001
2020-09-10 23:13:35,046 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-10 23:13:35,331 [JobControl] INFO  org.apache.hadoop.mapred.YARNRunner - Job jar is not present. Not adding any jar to the list of resources.
2020-09-10 23:13:35,463 [JobControl] INFO  org.apache.hadoop.conf.Configuration - found resource resource-types.xml at file:/etc/hadoop/3.1.0.0-78/0/resource-types.xml
2020-09-10 23:13:35,862 [JobControl] INFO  org.apache.hadoop.yarn.client.api.impl.YarnClientImpl - Submitted application application_1599772615630_0001
2020-09-10 23:13:36,207 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://node1.example.com:8088/proxy/application_1599772615630_0001/
2020-09-10 23:13:36,275 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_1599772615630_0001
2020-09-10 23:13:36,275 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset
2020-09-10 23:13:36,275 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset[1,10],dataset[-1,-1] C:  R: 
2020-09-10 23:13:36,494 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 0% complete
2020-09-10 23:13:36,494 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0001]
2020-09-10 23:14:01,929 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 12% complete
2020-09-10 23:14:01,929 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0001]
2020-09-10 23:14:06,955 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 25% complete
2020-09-10 23:14:06,963 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:06,963 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:06,971 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:14:09,177 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:09,177 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:09,200 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:14:09,267 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:09,267 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:09,275 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:14:09,480 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-10 23:14:09,481 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-10 23:14:09,481 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-10 23:14:09,484 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Using reducer estimator: org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator
2020-09-10 23:14:09,557 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator - BytesPerReducer=1000000000 maxReducers=999 totalInputFileSize=75
2020-09-10 23:14:09,558 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-10 23:14:09,558 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - This job cannot be converted run in-process
2020-09-10 23:14:10,286 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/pig-0.17.0-core-h2.jar to DistributedCache through /tmp/temp1767239210/tmp896358724/pig-0.17.0-core-h2.jar
2020-09-10 23:14:10,867 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/automaton-1.11-8.jar to DistributedCache through /tmp/temp1767239210/tmp-207765865/automaton-1.11-8.jar
2020-09-10 23:14:10,943 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/antlr-runtime-3.4.jar to DistributedCache through /tmp/temp1767239210/tmp-1298321465/antlr-runtime-3.4.jar
2020-09-10 23:14:11,035 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/joda-time-2.9.3.jar to DistributedCache through /tmp/temp1767239210/tmp342297925/joda-time-2.9.3.jar
2020-09-10 23:14:11,037 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-10 23:14:11,038 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-10 23:14:11,038 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-10 23:14:11,038 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Setting key [pig.schematuple.classes] with classes to deserialize []
2020-09-10 23:14:11,128 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-10 23:14:11,149 [JobControl] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:11,151 [JobControl] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:11,177 [JobControl] INFO  org.apache.hadoop.mapreduce.JobResourceUploader - Disabling Erasure Coding for path: /user/vagrant/.staging/job_1599772615630_0002
2020-09-10 23:14:11,180 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-10 23:14:11,231 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:14:11,234 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-10 23:14:11,234 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-10 23:14:11,386 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-10 23:14:11,579 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_1599772615630_0002
2020-09-10 23:14:11,579 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-10 23:14:11,584 [JobControl] INFO  org.apache.hadoop.mapred.YARNRunner - Job jar is not present. Not adding any jar to the list of resources.
2020-09-10 23:14:11,893 [JobControl] INFO  org.apache.hadoop.yarn.client.api.impl.YarnClientImpl - Submitted application application_1599772615630_0002
2020-09-10 23:14:11,899 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://node1.example.com:8088/proxy/application_1599772615630_0002/
2020-09-10 23:14:11,899 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_1599772615630_0002
2020-09-10 23:14:11,899 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset_ordered
2020-09-10 23:14:11,899 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset_ordered[3,18] C:  R: 
2020-09-10 23:14:29,116 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 37% complete
2020-09-10 23:14:29,116 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0002]
2020-09-10 23:14:36,194 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 50% complete
2020-09-10 23:14:36,194 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0002]
2020-09-10 23:14:42,223 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:42,224 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:42,230 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:14:42,561 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:42,561 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:42,567 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:14:42,630 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:42,630 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:42,641 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:14:42,727 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-10 23:14:42,728 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-10 23:14:42,728 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-10 23:14:42,728 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-10 23:14:42,728 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - This job cannot be converted run in-process
2020-09-10 23:14:43,192 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/pig-0.17.0-core-h2.jar to DistributedCache through /tmp/temp1767239210/tmp-739366449/pig-0.17.0-core-h2.jar
2020-09-10 23:14:43,354 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/automaton-1.11-8.jar to DistributedCache through /tmp/temp1767239210/tmp922813487/automaton-1.11-8.jar
2020-09-10 23:14:43,439 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/antlr-runtime-3.4.jar to DistributedCache through /tmp/temp1767239210/tmp-1274650947/antlr-runtime-3.4.jar
2020-09-10 23:14:43,637 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/joda-time-2.9.3.jar to DistributedCache through /tmp/temp1767239210/tmp-1247729961/joda-time-2.9.3.jar
2020-09-10 23:14:43,640 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-10 23:14:43,641 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-10 23:14:43,641 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-10 23:14:43,641 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Setting key [pig.schematuple.classes] with classes to deserialize []
2020-09-10 23:14:43,724 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-10 23:14:43,728 [JobControl] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:14:43,728 [JobControl] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:14:43,800 [JobControl] INFO  org.apache.hadoop.mapreduce.JobResourceUploader - Disabling Erasure Coding for path: /user/vagrant/.staging/job_1599772615630_0003
2020-09-10 23:14:43,815 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-10 23:14:43,916 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:14:43,917 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-10 23:14:43,917 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-10 23:14:44,063 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-10 23:14:44,140 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_1599772615630_0003
2020-09-10 23:14:44,140 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-10 23:14:44,145 [JobControl] INFO  org.apache.hadoop.mapred.YARNRunner - Job jar is not present. Not adding any jar to the list of resources.
2020-09-10 23:14:44,411 [JobControl] INFO  org.apache.hadoop.yarn.client.api.impl.YarnClientImpl - Submitted application application_1599772615630_0003
2020-09-10 23:14:44,437 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://node1.example.com:8088/proxy/application_1599772615630_0003/
2020-09-10 23:14:44,442 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_1599772615630_0003
2020-09-10 23:14:44,442 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset_ordered
2020-09-10 23:14:44,442 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset_ordered[3,18] C:  R: 
2020-09-10 23:14:58,586 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 62% complete
2020-09-10 23:14:58,586 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0003]
2020-09-10 23:15:06,614 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 75% complete
2020-09-10 23:15:06,614 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0003]
2020-09-10 23:15:09,627 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:09,627 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:09,631 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:09,757 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:09,757 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:09,760 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:09,792 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:09,793 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:09,801 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:09,847 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-10 23:15:09,847 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-10 23:15:09,848 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-10 23:15:09,848 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-10 23:15:09,848 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - This job cannot be converted run in-process
2020-09-10 23:15:10,000 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/pig-0.17.0-core-h2.jar to DistributedCache through /tmp/temp1767239210/tmp-1253585000/pig-0.17.0-core-h2.jar
2020-09-10 23:15:10,084 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/automaton-1.11-8.jar to DistributedCache through /tmp/temp1767239210/tmp-431983596/automaton-1.11-8.jar
2020-09-10 23:15:10,139 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/antlr-runtime-3.4.jar to DistributedCache through /tmp/temp1767239210/tmp-265330967/antlr-runtime-3.4.jar
2020-09-10 23:15:10,241 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/joda-time-2.9.3.jar to DistributedCache through /tmp/temp1767239210/tmp1852322692/joda-time-2.9.3.jar
2020-09-10 23:15:10,242 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-10 23:15:10,243 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-10 23:15:10,243 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-10 23:15:10,243 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Setting key [pig.schematuple.classes] with classes to deserialize []
2020-09-10 23:15:10,268 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-10 23:15:10,291 [JobControl] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:10,291 [JobControl] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:10,348 [JobControl] INFO  org.apache.hadoop.mapreduce.JobResourceUploader - Disabling Erasure Coding for path: /user/vagrant/.staging/job_1599772615630_0004
2020-09-10 23:15:10,353 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-10 23:15:10,437 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:15:10,437 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-10 23:15:10,437 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-10 23:15:10,577 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-10 23:15:10,668 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_1599772615630_0004
2020-09-10 23:15:10,668 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-10 23:15:10,685 [JobControl] INFO  org.apache.hadoop.mapred.YARNRunner - Job jar is not present. Not adding any jar to the list of resources.
2020-09-10 23:15:11,023 [JobControl] INFO  org.apache.hadoop.yarn.client.api.impl.YarnClientImpl - Submitted application application_1599772615630_0004
2020-09-10 23:15:11,118 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://node1.example.com:8088/proxy/application_1599772615630_0004/
2020-09-10 23:15:11,118 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_1599772615630_0004
2020-09-10 23:15:11,118 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset_ordered
2020-09-10 23:15:11,118 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset_ordered[3,18] C:  R: 
2020-09-10 23:15:25,266 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 87% complete
2020-09-10 23:15:25,266 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0004]
2020-09-10 23:15:33,325 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0004]
2020-09-10 23:15:36,338 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,339 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,344 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,440 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,441 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,446 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,480 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,480 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,486 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,540 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 100% complete
2020-09-10 23:15:36,600 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.SimplePigStats - Script Statistics: 

HadoopVersion   PigVersion      UserId  StartedAt       FinishedAt      Features
3.1.1.3.1.0.0-78        0.17.0  vagrant 2020-09-10 23:13:32     2020-09-10 23:15:36     ORDER_BY,LIMIT

Success!

Job Stats (time in seconds):
JobId   Maps    Reduces MaxMapTime      MinMapTime      AvgMapTime      MedianMapTime   MaxReduceTime   MinReduceTime   AvgReduceTime   MedianReducetimeAlias   Feature Outputs
job_1599772615630_0001  1       0       6       6       6       6       0       0       0       0       dataset MAP_ONLY
job_1599772615630_0002  1       1       4       4       4       4       3       3       3       3       dataset_ordered SAMPLER
job_1599772615630_0003  1       1       3       3       3       3       5       5       5       5       dataset_ordered ORDER_BY,COMBINER
job_1599772615630_0004  1       1       4       4       4       4       3       3       3       3       dataset_ordered         hdfs://node1.example.com:8020/tmp/temp1767239210/tmp2038774277,

Input(s):
Successfully read 5 records (442 bytes) from: "hdfs://node1.example.com:8020/user/vagrant/lab9_input/students.csv"

Output(s):
Successfully stored 1 records (19 bytes) in: "hdfs://node1.example.com:8020/tmp/temp1767239210/tmp2038774277"

Counters:
Total records written : 1
Total bytes written : 19
Spillable Memory Manager spill count : 0
Total bags proactively spilled: 0
Total records proactively spilled: 0

Job DAG:
job_1599772615630_0001  ->      job_1599772615630_0002,
job_1599772615630_0002  ->      job_1599772615630_0003,
job_1599772615630_0003  ->      job_1599772615630_0004,
job_1599772615630_0004


2020-09-10 23:15:36,601 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,601 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,604 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,626 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,627 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,629 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,666 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,666 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,670 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,718 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,718 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,721 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,740 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,740 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,751 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,817 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,817 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,825 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,851 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,851 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,862 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:36,912 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:36,913 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:36,923 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:37,031 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:37,034 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:37,038 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:37,082 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:37,083 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:37,098 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:37,164 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:37,165 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:37,176 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:37,217 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:15:37,218 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:15:37,235 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:15:37,271 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Success!
2020-09-10 23:15:37,274 [main] INFO  org.apache.pig.data.SchemaTupleBackend - Key [pig.schematuple] was not set... will not generate code.
2020-09-10 23:15:37,297 [main] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:15:37,297 [main] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
(Margherita,30)
2020-09-10 23:15:37,586 [main] INFO  org.apache.pig.Main - Pig script completed in 2 minutes, 10 seconds and 214 milliseconds (130214 ms)
```

## Case B: Sum the GPAs of students with the name starts for the same letter 


### Run the script on local machine using Maven

To run Pig on your local machine please ensure to have:

 - A JDK version 1.8 installed
 - Your JAVA_HOME should be set to your JDK 1.8 home directory
 - JAVA_HOME/bin into the PATH
 - Maven installed and reachable from you PATH

```console
$ mvn exec:java -Dexec.args="-x local script_grouping.pig" 
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------< it.sunnyvale.hadoop.labs:code-mapreduce >---------------
[INFO] Building code-mapreduce 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ code-mapreduce ---
log4j:WARN No appenders could be found for logger (org.apache.hadoop.util.Shell).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
2020-09-14 12:46:08,506 [org.apache.pig.Main.main()] INFO  org.apache.pig.Main - Apache Pig version 0.17.0 (r1798794) compiled Jun 14 2017, 23:34:52
2020-09-14 12:46:08,507 [org.apache.pig.Main.main()] INFO  org.apache.pig.Main - Logging error messages to: /Users/denismaggiorotto/Documents/SoxelyNCFS/Sunnyvale/Clienti/ITS-ICT/2019-2021/Cloud/BigData/repos/ITS-ICT_BigData/labs/09-Pig/pig_1600080368484.log
2020-09-14 12:46:08,544 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - user.name is deprecated. Instead, use mapreduce.job.user.name
2020-09-14 12:46:09,113 [org.apache.pig.Main.main()] INFO  org.apache.pig.impl.util.Utils - Default bootup file /Users/denismaggiorotto/.pigbootup not found
2020-09-14 12:46:09,232 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:46:09,234 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.HExecutionEngine - Connecting to hadoop file system at: file:///
2020-09-14 12:46:09,263 [org.apache.pig.Main.main()] INFO  org.apache.pig.PigServer - Pig Script ID for the session: PIG-script_grouping.pig-ce4c50be-6049-454d-bd2b-6bcb2c9b41f0
2020-09-14 12:46:09,263 [org.apache.pig.Main.main()] WARN  org.apache.pig.PigServer - ATS is disabled since yarn.timeline-service.enabled set to false
2020-09-14 12:46:09,961 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.ScriptState - Pig features used in the script: GROUP_BY
2020-09-14 12:46:10,052 [org.apache.pig.Main.main()] INFO  org.apache.pig.newplan.logical.optimizer.LogicalPlanOptimizer - {RULES_ENABLED=[AddForEach, ColumnMapKeyPrune, ConstantCalculator, GroupByConstParallelSetter, LimitOptimizer, LoadTypeCastInserter, MergeFilter, MergeForEach, NestedLimitOptimizer, PartitionFilterOptimizer, PredicatePushdownOptimizer, PushDownForEachFlatten, PushUpFilter, SplitFilter, StreamTypeCastInserter]}
2020-09-14 12:46:10,119 [org.apache.pig.Main.main()] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:46:10,205 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MRCompiler - File concatenation threshold: 100 optimistic? false
2020-09-14 12:46:10,214 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.util.CombinerOptimizerUtil - Choosing to move algebraic foreach to combiner
2020-09-14 12:46:10,243 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size before optimization: 1
2020-09-14 12:46:10,243 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size after optimization: 1
2020-09-14 12:46:10,529 [org.apache.pig.Main.main()] INFO  org.apache.commons.beanutils.FluentPropertyBeanIntrospector - Error when creating PropertyDescriptor for public final void org.apache.commons.configuration2.AbstractConfiguration.setProperty(java.lang.String,java.lang.Object)! Ignoring this property.
2020-09-14 12:46:10,554 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsConfig - Cannot locate configuration: tried hadoop-metrics2-jobtracker.properties,hadoop-metrics2.properties
2020-09-14 12:46:10,985 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - Scheduled Metric snapshot period at 10 second(s).
2020-09-14 12:46:10,985 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system started
2020-09-14 12:46:11,000 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-14 12:46:11,006 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:46:11,006 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-14 12:46:11,008 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.output.compress is deprecated. Instead, use mapreduce.output.fileoutputformat.compress
2020-09-14 12:46:11,042 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-14 12:46:11,043 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Using reducer estimator: org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator
2020-09-14 12:46:11,054 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator - BytesPerReducer=1000000000 maxReducers=999 totalInputFileSize=49
2020-09-14 12:46:11,054 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-14 12:46:11,054 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
2020-09-14 12:46:11,072 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-14 12:46:11,078 [org.apache.pig.Main.main()] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-14 12:46:11,079 [org.apache.pig.Main.main()] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-14 12:46:11,079 [org.apache.pig.Main.main()] INFO  org.apache.pig.data.SchemaTupleFrontend - Distributed cache not supported or needed in local mode. Setting key [pig.schematuple.local.dir] with code temp directory: /var/folders/4d/_r283cnd6r5g7qpqgrnt2j1m0000gp/T/1600080371076-0
2020-09-14 12:46:11,144 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-14 12:46:11,330 [JobControl] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:11,353 [JobControl] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.task.id is deprecated. Instead, use mapreduce.task.attempt.id
2020-09-14 12:46:11,619 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-14 12:46:11,628 [JobControl] INFO  org.apache.pig.builtin.PigStorage - Using PigTextInputFormat
2020-09-14 12:46:11,634 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:46:11,634 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-14 12:46:11,649 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-14 12:46:11,804 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-14 12:46:11,958 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_local91941263_0001
2020-09-14 12:46:11,959 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-14 12:46:12,140 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://localhost:8080/
2020-09-14 12:46:12,141 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_local91941263_0001
2020-09-14 12:46:12,141 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset,group_data,result
2020-09-14 12:46:12,141 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset[1,10],dataset[-1,-1],result[4,9],group_data[3,13] C: result[4,9],group_data[3,13] R: result[4,9]
2020-09-14 12:46:12,144 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter set in config null
2020-09-14 12:46:12,148 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 0% complete
2020-09-14 12:46:12,282 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local91941263_0001]
2020-09-14 12:46:12,161 [Thread-24] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.reduce.tasks is deprecated. Instead, use mapreduce.job.reduces
2020-09-14 12:46:12,282 [Thread-24] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.tracker is deprecated. Instead, use mapreduce.jobtracker.address
2020-09-14 12:46:12,282 [Thread-24] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.job.reduce.markreset.buffer.percent is deprecated. Instead, use mapreduce.reduce.markreset.buffer.percent
2020-09-14 12:46:12,284 [Thread-24] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:46:12,284 [Thread-24] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:46:12,290 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - OutputCommitter is org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigOutputCommitter
2020-09-14 12:46:12,376 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for map tasks
2020-09-14 12:46:12,376 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local91941263_0001_m_000000_0
2020-09-14 12:46:12,404 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:46:12,404 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:46:12,411 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:46:12,411 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:46:12,416 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Processing split: Number of splits :1
Total Length = 49
Input split[0]:
   Length = 49
   ClassName: org.apache.hadoop.mapreduce.lib.input.FileSplit
   Locations:

-----------------------

2020-09-14 12:46:12,565 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.builtin.PigStorage - Using PigTextInputFormat
2020-09-14 12:46:12,569 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigRecordReader - Current split being processed file:/Users/denismaggiorotto/Documents/SoxelyNCFS/Sunnyvale/Clienti/ITS-ICT/2019-2021/Cloud/BigData/repos/ITS-ICT_BigData/labs/09-Pig/lab9_input/students.csv:0+49
2020-09-14 12:46:12,637 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - (EQUATOR) 0 kvi 26214396(104857584)
2020-09-14 12:46:12,637 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - mapreduce.task.io.sort.mb: 100
2020-09-14 12:46:12,637 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - soft limit at 83886080
2020-09-14 12:46:12,637 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufvoid = 104857600
2020-09-14 12:46:12,637 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396; length = 6553600
2020-09-14 12:46:12,641 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Map output collector class = org.apache.hadoop.mapred.MapTask$MapOutputBuffer
2020-09-14 12:46:12,645 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:46:12,854 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.data.SchemaTupleBackend - Key [pig.schematuple] was not set... will not generate code.
2020-09-14 12:46:12,884 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigGenericMapReduce$Map - Aliases being processed per job phase (AliasName[line,offset]): M: dataset[1,10],dataset[-1,-1],result[4,9],group_data[3,13] C: result[4,9],group_data[3,13] R: result[4,9]
2020-09-14 12:46:12,906 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - 
2020-09-14 12:46:12,906 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Starting flush of map output
2020-09-14 12:46:12,906 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Spilling map output
2020-09-14 12:46:12,906 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - bufstart = 0; bufend = 50; bufvoid = 104857600
2020-09-14 12:46:12,941 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - kvstart = 26214396(104857584); kvend = 26214380(104857520); length = 17/6553600
2020-09-14 12:46:13,021 [LocalJobRunner Map Task Executor #0] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigCombiner$Combine - Aliases being processed per job phase (AliasName[line,offset]): M: dataset[1,10],dataset[-1,-1],result[4,9],group_data[3,13] C: result[4,9],group_data[3,13] R: result[4,9]
2020-09-14 12:46:13,028 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.MapTask - Finished spill 0
2020-09-14 12:46:13,053 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local91941263_0001_m_000000_0 is done. And is in the process of committing
2020-09-14 12:46:13,054 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - map
2020-09-14 12:46:13,054 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local91941263_0001_m_000000_0' done.
2020-09-14 12:46:13,271 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local91941263_0001_m_000000_0: Counters: 18
        File System Counters
                FILE: Number of bytes read=588
                FILE: Number of bytes written=445644
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Map input records=5
                Map output records=5
                Map output bytes=50
                Map output materialized bytes=30
                Input split bytes=485
                Combine input records=5
                Combine output records=2
                Spilled Records=2
                Failed Shuffles=0
                Merged Map outputs=0
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=321912832
        File Input Format Counters 
                Bytes Read=0
2020-09-14 12:46:13,271 [LocalJobRunner Map Task Executor #0] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local91941263_0001_m_000000_0
2020-09-14 12:46:13,282 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - map task executor complete.
2020-09-14 12:46:13,285 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - Waiting for reduce tasks
2020-09-14 12:46:13,286 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Starting task: attempt_local91941263_0001_r_000000_0
2020-09-14 12:46:13,286 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 50% complete
2020-09-14 12:46:13,287 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_local91941263_0001]
2020-09-14 12:46:13,298 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:46:13,298 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:46:13,490 [pool-4-thread-1] INFO  org.apache.hadoop.yarn.util.ProcfsBasedProcessTree - ProcfsBasedProcessTree currently is supported only on Linux.
2020-09-14 12:46:13,490 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Task -  Using ResourceCalculatorProcessTree : null
2020-09-14 12:46:13,493 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.ReduceTask - Using ShuffleConsumerPlugin: org.apache.hadoop.mapreduce.task.reduce.Shuffle@1371a186
2020-09-14 12:46:13,494 [pool-4-thread-1] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:13,510 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - MergerManager: memoryLimit=2672505600, maxSingleShuffleLimit=668126400, mergeThreshold=1763853824, ioSortFactor=10, memToMemMergeOutputsThreshold=10
2020-09-14 12:46:13,512 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - attempt_local91941263_0001_r_000000_0 Thread started: EventFetcher for fetching Map Completion Events
2020-09-14 12:46:13,530 [localfetcher#1] INFO  org.apache.hadoop.mapreduce.task.reduce.LocalFetcher - localfetcher#1 about to shuffle output of map attempt_local91941263_0001_m_000000_0 decomp: 26 len: 30 to MEMORY
2020-09-14 12:46:13,532 [localfetcher#1] INFO  org.apache.hadoop.mapreduce.task.reduce.InMemoryMapOutput - Read 26 bytes from map-output for attempt_local91941263_0001_m_000000_0
2020-09-14 12:46:13,533 [localfetcher#1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - closeInMemoryFile -> map-output of size: 26, inMemoryMapOutputs.size() -> 1, commitMemory -> 0, usedMemory ->26
2020-09-14 12:46:13,670 [EventFetcher for fetching Map Completion Events] INFO  org.apache.hadoop.mapreduce.task.reduce.EventFetcher - EventFetcher is interrupted.. Returning
2020-09-14 12:46:13,671 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:46:13,671 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - finalMerge called with 1 in-memory map-outputs and 0 on-disk map-outputs
2020-09-14 12:46:13,697 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:46:13,697 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 20 bytes
2020-09-14 12:46:13,706 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merged 1 segments, 26 bytes to disk to satisfy reduce memory limit
2020-09-14 12:46:13,707 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 1 files, 30 bytes from disk
2020-09-14 12:46:13,707 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.task.reduce.MergeManagerImpl - Merging 0 segments, 0 bytes from memory into reduce
2020-09-14 12:46:13,707 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Merger - Merging 1 sorted segments
2020-09-14 12:46:13,707 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Merger - Down to the last merge-pass, with 1 segments left of total size: 20 bytes
2020-09-14 12:46:13,708 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:46:13,885 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - File Output Committer Algorithm version is 2
2020-09-14 12:46:13,885 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - FileOutputCommitter skip cleanup _temporary folders under output directory:false, ignore cleanup failures: false
2020-09-14 12:46:13,935 [pool-4-thread-1] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.skip.on is deprecated. Instead, use mapreduce.job.skiprecords
2020-09-14 12:46:13,935 [pool-4-thread-1] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 2863661056 to monitor. collectionUsageThreshold = 2496659456, usageThreshold = 2496659456
2020-09-14 12:46:13,936 [pool-4-thread-1] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:46:13,940 [pool-4-thread-1] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigMapReduce$Reduce - Aliases being processed per job phase (AliasName[line,offset]): M: dataset[1,10],dataset[-1,-1],result[4,9],group_data[3,13] C: result[4,9],group_data[3,13] R: result[4,9]
2020-09-14 12:46:13,945 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Task - Task:attempt_local91941263_0001_r_000000_0 is done. And is in the process of committing
2020-09-14 12:46:13,948 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - 1 / 1 copied.
2020-09-14 12:46:14,050 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Task - Task attempt_local91941263_0001_r_000000_0 is allowed to commit now
2020-09-14 12:46:14,053 [pool-4-thread-1] INFO  org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter - Saved output of task 'attempt_local91941263_0001_r_000000_0' to file:/tmp/temp1730037512/tmp-1884803825
2020-09-14 12:46:14,054 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce > reduce
2020-09-14 12:46:14,054 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Task - Task 'attempt_local91941263_0001_r_000000_0' done.
2020-09-14 12:46:14,055 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.Task - Final Counters for attempt_local91941263_0001_r_000000_0: Counters: 24
        File System Counters
                FILE: Number of bytes read=680
                FILE: Number of bytes written=445706
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
        Map-Reduce Framework
                Combine input records=0
                Combine output records=0
                Reduce input groups=2
                Reduce shuffle bytes=30
                Reduce input records=2
                Reduce output records=2
                Spilled Records=2
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=0
                Total committed heap usage (bytes)=321912832
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Output Format Counters 
                Bytes Written=0
2020-09-14 12:46:14,057 [pool-4-thread-1] INFO  org.apache.hadoop.mapred.LocalJobRunner - Finishing task: attempt_local91941263_0001_r_000000_0
2020-09-14 12:46:14,057 [Thread-24] INFO  org.apache.hadoop.mapred.LocalJobRunner - reduce task executor complete.
2020-09-14 12:46:14,221 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:14,229 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:14,230 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.conf.Configuration.deprecation - mapred.map.tasks is deprecated. Instead, use mapreduce.job.maps
2020-09-14 12:46:14,231 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:14,250 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 100% complete
2020-09-14 12:46:14,252 [org.apache.pig.Main.main()] INFO  org.apache.pig.tools.pigstats.mapreduce.SimplePigStats - Script Statistics: 

HadoopVersion   PigVersion      UserId  StartedAt       FinishedAt      Features
3.0.0   0.17.0  denismaggiorotto        2020-09-14 12:46:10     2020-09-14 12:46:14     GROUP_BY

Success!

Job Stats (time in seconds):
JobId   Maps    Reduces MaxMapTime      MinMapTime      AvgMapTime      MedianMapTime   MaxReduceTime   MinReduceTime   AvgReduceTime   MedianReducetime        Alias   Feature Outputs
job_local91941263_0001  1       1       n/a     n/a     n/a     n/a     n/a     n/a     n/a     n/a     dataset,group_data,result       GROUP_BY,COMBINER       file:/tmp/temp1730037512/tmp-1884803825,

Input(s):
Successfully read 5 records from: "file:///Users/denismaggiorotto/Documents/SoxelyNCFS/Sunnyvale/Clienti/ITS-ICT/2019-2021/Cloud/BigData/repos/ITS-ICT_BigData/labs/09-Pig/lab9_input/students.csv"

Output(s):
Successfully stored 2 records in: "file:/tmp/temp1730037512/tmp-1884803825"

Counters:
Total records written : 2
Total bytes written : 0
Spillable Memory Manager spill count : 0
Total bags proactively spilled: 0
Total records proactively spilled: 0

Job DAG:
job_local91941263_0001


2020-09-14 12:46:14,287 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:14,289 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:14,290 [org.apache.pig.Main.main()] WARN  org.apache.hadoop.metrics2.impl.MetricsSystemImpl - JobTracker metrics system already initialized!
2020-09-14 12:46:14,295 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Success!
2020-09-14 12:46:14,297 [org.apache.pig.Main.main()] WARN  org.apache.pig.data.SchemaTupleBackend - SchemaTupleBackend has already been initialized
2020-09-14 12:46:14,337 [org.apache.pig.Main.main()] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-14 12:46:14,337 [org.apache.pig.Main.main()] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
(A,35)
(M,76)
2020-09-14 12:46:14,368 [org.apache.pig.Main.main()] INFO  org.apache.pig.Main - Pig script completed in 6 seconds and 369 milliseconds (6369 ms)

```

### Run the script within VM

```console
[vagrant@node1 09-Pig]$ pig script_grouping.pig 

20/09/10 23:20:24 INFO pig.ExecTypeProvider: Trying ExecType : LOCAL
20/09/10 23:20:24 INFO pig.ExecTypeProvider: Trying ExecType : MAPREDUCE
20/09/10 23:20:24 INFO pig.ExecTypeProvider: Picked MAPREDUCE as the ExecType
2020-09-10 23:20:24,866 [main] INFO  org.apache.pig.Main - Apache Pig version 0.17.0 (r1797386) compiled Jun 02 2017, 15:41:58
2020-09-10 23:20:24,866 [main] INFO  org.apache.pig.Main - Logging error messages to: /usr/src/git_repo/labs/09-Pig/pig_1599780024862.log
2020-09-10 23:20:25,553 [main] INFO  org.apache.pig.impl.util.Utils - Default bootup file /home/vagrant/.pigbootup not found
2020-09-10 23:20:25,706 [main] INFO  org.apache.pig.backend.hadoop.executionengine.HExecutionEngine - Connecting to hadoop file system at: hdfs://node1.example.com:8020
2020-09-10 23:20:26,294 [main] INFO  org.apache.pig.PigServer - Pig Script ID for the session: PIG-script_grouping.pig-b2fee4a7-b04f-4f9e-8774-3c9349533036
2020-09-10 23:20:26,609 [main] INFO  org.apache.hadoop.yarn.client.api.impl.TimelineClientImpl - Timeline service address: node2.example.com:8188
2020-09-10 23:20:27,139 [main] INFO  org.apache.pig.backend.hadoop.PigATSClient - Created ATS Hook
2020-09-10 23:20:28,091 [main] INFO  org.apache.pig.tools.pigstats.ScriptState - Pig features used in the script: GROUP_BY
2020-09-10 23:20:28,115 [main] INFO  org.apache.pig.data.SchemaTupleBackend - Key [pig.schematuple] was not set... will not generate code.
2020-09-10 23:20:28,157 [main] INFO  org.apache.pig.newplan.logical.optimizer.LogicalPlanOptimizer - {RULES_ENABLED=[AddForEach, ColumnMapKeyPrune, ConstantCalculator, GroupByConstParallelSetter, LimitOptimizer, LoadTypeCastInserter, MergeFilter, MergeForEach, NestedLimitOptimizer, PartitionFilterOptimizer, PredicatePushdownOptimizer, PushDownForEachFlatten, PushUpFilter, SplitFilter, StreamTypeCastInserter]}
2020-09-10 23:20:28,242 [main] INFO  org.apache.pig.impl.util.SpillableMemoryManager - Selected heap (PS Old Gen) of size 699400192 to monitor. collectionUsageThreshold = 489580128, usageThreshold = 489580128
2020-09-10 23:20:28,347 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MRCompiler - File concatenation threshold: 100 optimistic? false
2020-09-10 23:20:28,371 [main] INFO  org.apache.pig.backend.hadoop.executionengine.util.CombinerOptimizerUtil - Choosing to move algebraic foreach to combiner
2020-09-10 23:20:28,429 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size before optimization: 1
2020-09-10 23:20:28,429 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MultiQueryOptimizer - MR plan size after optimization: 1
2020-09-10 23:20:28,636 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:28,960 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:29,017 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.MRScriptState - Pig script settings are added to the job
2020-09-10 23:20:29,023 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - mapred.job.reduce.markreset.buffer.percent is not set, set to default 0.3
2020-09-10 23:20:29,075 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Reduce phase detected, estimating # of required reducers.
2020-09-10 23:20:29,076 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Using reducer estimator: org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator
2020-09-10 23:20:29,087 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.InputSizeReducerEstimator - BytesPerReducer=1000000000 maxReducers=999 totalInputFileSize=49
2020-09-10 23:20:29,087 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting Parallelism to 1
2020-09-10 23:20:29,087 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - This job cannot be converted run in-process
2020-09-10 23:20:29,465 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/pig-0.17.0-core-h2.jar to DistributedCache through /tmp/temp1873336036/tmp1975536105/pig-0.17.0-core-h2.jar
2020-09-10 23:20:29,546 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/automaton-1.11-8.jar to DistributedCache through /tmp/temp1873336036/tmp-286896554/automaton-1.11-8.jar
2020-09-10 23:20:29,620 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/antlr-runtime-3.4.jar to DistributedCache through /tmp/temp1873336036/tmp-743619209/antlr-runtime-3.4.jar
2020-09-10 23:20:29,699 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Added jar file:/opt/pig-0.17.0/lib/joda-time-2.9.3.jar to DistributedCache through /tmp/temp1873336036/tmp-496497408/joda-time-2.9.3.jar
2020-09-10 23:20:29,713 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.JobControlCompiler - Setting up single store job
2020-09-10 23:20:29,717 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Key [pig.schematuple] is false, will not generate code.
2020-09-10 23:20:29,718 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Starting process to move generated code to distributed cacche
2020-09-10 23:20:29,718 [main] INFO  org.apache.pig.data.SchemaTupleFrontend - Setting key [pig.schematuple.classes] with classes to deserialize []
2020-09-10 23:20:29,844 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 1 map-reduce job(s) waiting for submission.
2020-09-10 23:20:29,858 [JobControl] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:29,860 [JobControl] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:30,025 [JobControl] INFO  org.apache.hadoop.mapreduce.JobResourceUploader - Disabling Erasure Coding for path: /user/vagrant/.staging/job_1599772615630_0005
2020-09-10 23:20:30,042 [JobControl] WARN  org.apache.hadoop.mapreduce.JobResourceUploader - No job jar file set.  User classes may not be found. See Job or Job#setJar(String).
2020-09-10 23:20:30,091 [JobControl] INFO  org.apache.pig.builtin.PigStorage - Using PigTextInputFormat
2020-09-10 23:20:30,109 [JobControl] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:20:30,110 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
2020-09-10 23:20:30,160 [JobControl] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths (combined) to process : 1
2020-09-10 23:20:30,301 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - number of splits:1
2020-09-10 23:20:30,561 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Submitting tokens for job: job_1599772615630_0005
2020-09-10 23:20:30,565 [JobControl] INFO  org.apache.hadoop.mapreduce.JobSubmitter - Executing with tokens: []
2020-09-10 23:20:30,905 [JobControl] INFO  org.apache.hadoop.mapred.YARNRunner - Job jar is not present. Not adding any jar to the list of resources.
2020-09-10 23:20:31,049 [JobControl] INFO  org.apache.hadoop.conf.Configuration - found resource resource-types.xml at file:/etc/hadoop/3.1.0.0-78/0/resource-types.xml
2020-09-10 23:20:31,410 [JobControl] INFO  org.apache.hadoop.yarn.client.api.impl.YarnClientImpl - Submitted application application_1599772615630_0005
2020-09-10 23:20:31,539 [JobControl] INFO  org.apache.hadoop.mapreduce.Job - The url to track the job: http://node1.example.com:8088/proxy/application_1599772615630_0005/
2020-09-10 23:20:31,541 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - HadoopJobId: job_1599772615630_0005
2020-09-10 23:20:31,541 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Processing aliases dataset,group_data,result
2020-09-10 23:20:31,541 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - detailed locations: M: dataset[1,10],dataset[-1,-1],result[4,9],group_data[3,13] C: result[4,9],group_data[3,13] R: result[4,9]
2020-09-10 23:20:31,585 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 0% complete
2020-09-10 23:20:31,585 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0005]
2020-09-10 23:20:45,697 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 50% complete
2020-09-10 23:20:45,698 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0005]
2020-09-10 23:20:51,730 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Running jobs are [job_1599772615630_0005]
2020-09-10 23:20:56,748 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:56,748 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:56,758 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:20:57,006 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:57,007 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:57,011 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:20:57,078 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:57,078 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:57,092 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:20:57,195 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - 100% complete
2020-09-10 23:20:57,198 [main] INFO  org.apache.pig.tools.pigstats.mapreduce.SimplePigStats - Script Statistics: 

HadoopVersion   PigVersion      UserId  StartedAt       FinishedAt      Features
3.1.1.3.1.0.0-78        0.17.0  vagrant 2020-09-10 23:20:29     2020-09-10 23:20:57     GROUP_BY

Success!

Job Stats (time in seconds):
JobId   Maps    Reduces MaxMapTime      MinMapTime      AvgMapTime      MedianMapTime   MaxReduceTime   MinReduceTime   AvgReduceTime   MedianReducetimeAlias   Feature Outputs
job_1599772615630_0005  1       1       4       4       4       4       3       3       3       3       dataset,group_data,result       GROUP_BY,COMBINER       hdfs://node1.example.com:8020/tmp/temp1873336036/tmp182551647,

Input(s):
Successfully read 5 records (442 bytes) from: "hdfs://node1.example.com:8020/user/vagrant/lab9_input/students.csv"

Output(s):
Successfully stored 2 records (20 bytes) in: "hdfs://node1.example.com:8020/tmp/temp1873336036/tmp182551647"

Counters:
Total records written : 2
Total bytes written : 20
Spillable Memory Manager spill count : 0
Total bags proactively spilled: 0
Total records proactively spilled: 0

Job DAG:
job_1599772615630_0005


2020-09-10 23:20:57,201 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:57,201 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:57,207 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:20:57,237 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:57,237 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:57,243 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:20:57,274 [main] INFO  org.apache.hadoop.yarn.client.RMProxy - Connecting to ResourceManager at node1.example.com/192.168.199.2:8050
2020-09-10 23:20:57,275 [main] INFO  org.apache.hadoop.yarn.client.AHSProxy - Connecting to Application History server at node2.example.com/192.168.199.3:10200
2020-09-10 23:20:57,285 [main] INFO  org.apache.hadoop.mapred.ClientServiceDelegate - Application state is completed. FinalApplicationStatus=SUCCEEDED. Redirecting to job history server
2020-09-10 23:20:57,317 [main] INFO  org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.MapReduceLauncher - Success!
2020-09-10 23:20:57,325 [main] INFO  org.apache.pig.data.SchemaTupleBackend - Key [pig.schematuple] was not set... will not generate code.
2020-09-10 23:20:57,394 [main] INFO  org.apache.hadoop.mapreduce.lib.input.FileInputFormat - Total input files to process : 1
2020-09-10 23:20:57,396 [main] INFO  org.apache.pig.backend.hadoop.executionengine.util.MapRedUtil - Total input paths to process : 1
(A,35)
(M,76)
2020-09-10 23:20:57,628 [main] INFO  org.apache.pig.Main - Pig script completed in 32 seconds and 966 milliseconds (32966 ms)
```

## Run the examples on AWS EMR cluster

As a prerequisite, you should have completed the EMR cluster creation procedure on lab 2, please [refer to](../02-Provision_the_environment/AWS/README.md)

Upload the input dataset into the bucket (change the `--bucket` value with the one representing your bucket)

```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key lab9_input/students.csv --body lab9_input/students.csv
{
  "ETag" : xyz
}
```

Upload both the pig scripts into the bucket (change the `--bucket` value with the one representing your bucket)

```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key script_EMR.pig --body script_EMR.pig
{
  "ETag" : xyz
}
```

```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key script_grouping_EMR.pig --body script_grouping_EMR.pig
{
  "ETag" : xyz
}
```

Delete the output folders in the case you already run this lab (change **its-ict-emr-bucket** with your bucket name)

```console
$ aws s3 rm --recursive s3://its-ict-emr-bucket/lab9_output/
delete: s3://its-ict-emr-bucket/lab9_output/_SUCCESS
delete: s3://its-ict-emr-bucket/lab9_output/part-v004-o000-r-00000
```

```console
$ aws s3 rm --recursive s3://its-ict-emr-bucket/lab9_output_grouping/
delete: s3://its-ict-emr-bucket/lab9_output_grouping/part-v001-o000-r-00000
delete: s3://its-ict-emr-bucket/lab9_output_grouping/_SUCCESS
```

Now run the scripts on EMR cluster (change the `--cluster-id` value with the one representing your cluster and **its-ict-emr-bucket** with your bucket name)

```console
$ aws emr add-steps \
  --cluster-id "j-2NKCDZKNTJ06" \
  --steps Type=Pig,Name="lab9 script",ActionOnFailure=CONTINUE,Args=\[-f,s3://its-ict-emr-bucket/script_EMR.pig,-p,INPUT=s3://its-ict-emr-bucket/lab9_input/students.csv,-p,OUTPUT=s3://its-ict-emr-bucket/lab9_output\]
```

```console
$ aws emr add-steps \
  --cluster-id "j-2NKCDZKNTJ06" \
  --steps Type=Pig,Name="lab9 script grouping",ActionOnFailure=CONTINUE,Args=\[-f,s3://its-ict-emr-bucket/script_grouping_EMR.pig,-p,INPUT=s3://its-ict-emr-bucket/lab9_input/students.csv,-p,OUTPUT=s3://its-ict-emr-bucket/lab9_output_grouping\]
```

Open the AWS console (https://s3.console.aws.amazon.com) and inspect the bucket to see if the output folders have been created and contain the result files.

dataset = LOAD '$INPUT' USING PigStorage(',')
   as (name:chararray, gpa:int);
group_data = GROUP dataset by SUBSTRING(name, 0, 1);
result = foreach group_data generate group, SUM(dataset.gpa);  
store result into '$OUTPUT';
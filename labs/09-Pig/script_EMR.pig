dataset = LOAD '$INPUT' USING PigStorage(',')
   as (name:chararray, gpa:int);
dataset_ordered = ORDER dataset BY gpa DESC;
dataset_ordered_limited = LIMIT dataset_ordered 1;  
store dataset_ordered_limited into '$OUTPUT';
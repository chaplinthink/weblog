#!/bin/sh

#hive -e "CREATE EXTERNAL TABLE weblog(ip string, atime string, url string) PARTITIONED BY (logdate string) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LOCATION '/weblog_cleaned';"

s1=`date --date="$1"  +%s`
s2=`date +%s`
s3=$((($s2-$s1)/3600/24))


for ((i=$s3; i>0; i--))
do
  tmp=`date --date="$i days ago" +%Y_%m_%d`
  weblog_common.sh $tmp
done

   


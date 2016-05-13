#!/bin/sh

#get yesterday format string
#yesterday=`date --date='1 days ago' +%Y_%m_%d`
yesterday=$1

#upload logs to hdfs
hadoop fs -put    access_${yesterday}.log  /weblog

#cleaning data
hadoop jar  cleaned.jar  /weblog/access_${yesterday}.log  /weblog_cleaned/${yesterday}  1>/dev/null


#alter hive table and then add partition to existed table
hive -e "ALTER TABLE weblog ADD PARTITION(logdate='${yesterday}') LOCATION '/weblog_cleaned/${yesterday}';"

#create hive table everyday
hive -e "CREATE TABLE weblog_pv_${yesterday} AS SELECT COUNT(1) AS PV FROM weblog WHERE logdate='${yesterday}';"
hive -e "CREATE TABLE weblog_reguser_${yesterday} AS SELECT COUNT(1) AS REGUSER FROM weblog WHERE logdate='${yesterday}' AND INSTR(url,'member.php?mod=register')>0;"
hive -e "CREATE TABLE weblog_ip_${yesterday} AS SELECT COUNT(DISTINCT ip) AS IP FROM weblog WHERE logdate='${yesterday}';"
hive -e "CREATE TABLE weblog_jumper_${yesterday} AS SELECT COUNT(1) AS jumper FROM (SELECT COUNT(ip) AS times FROM weblog WHERE logdate='${yesterday}' GROUP BY ip HAVING times=1) e;"
hive -e "CREATE TABLE weblog_${yesterday} AS SELECT '${yesterday}', a.pv, b.reguser, c.ip, d.jumper FROM weblog_pv_${yesterday} a JOIN weblog_reguser_${yesterday} b ON 1=1 JOIN weblog_ip_${yesterday} c ON 1=1 JOIN weblog_jumper_${yesterday} d ON 1=1;"

#delete hive tables
hive -e "drop table weblog_pv_${yesterday};"
hive -e "drop table weblog_reguser_${yesterday};"
hive -e "drop table weblog_ip_${yesterday};"
hive -e "drop table weblog_jumper_${yesterday};"

#sqoop export to mysql
sqoop export --connect jdbc:mysql://hadoop:3306/weblog --username root --password admin --table weblog_logs_stat --fields-terminated-by '\001' --export-dir '/hive/weblog_${yesterday}'

#delete hive tables
hive -e "drop table weblog_${yesterday};"

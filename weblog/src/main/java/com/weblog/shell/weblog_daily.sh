#!/bin/sh

yesterday=`date --date='1 days ago' +%Y_%m_%d`
weblog_common.sh $yesterday

# Nameles-logfile

Nameles is the first ever open source solution for detecting invalid traffic (ad fraud).
Repository for computing the Nameles Confidence Score from log files of bid requests.

## Overview

Nameles is an entropy based open source ad fraud detection solution that detects traffic anomalies agnostically across desktop and mobile. It has been proven[[1]](http://www.it.uc3m.es/rcuevas/techreports/entropy_method.pdf) to deliver excellent results in detecting banner, video and app fraud at large scale with minimal Total-Cost-of-Ownership.

Learn more at http://nameles.org

## Getting Started

### Installation

You have two options for installation: 

- Install one of the [releases](https://github.com/Nameles-Org/Nameles-logfile/releases)
- Install from source 

#### Install from Debian Package

From the [releases](https://github.com/Nameles-Org/Nameles-logfile/releases) choose the right package based on which psql version you have. You can check the version first. 

Server version:

    pg_config --version

Client version:

    psql --version

The easiest is to go with 9.6 which is the latest version: 

    # preparation for installing psql
    sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ `lsb_release -cs`-pgdg main" >> /etc/apt/sources.list.d/pgdg.list'
    wget -q https://www.postgresql.org/media/keys/ACCC4CF8.asc -O - | sudo apt-key add -
    sudo apt-get update 
    
    # install psql
    sudo apt-get install postgresql postgresql-contrib postgresql-client

####


From the release tab, select the debian package that suits the PostgreSQL version of your linux distribution and install it:

```bash
sudo dpkg -i nameles-postgresql-9.6_0.1-1_amd64.deb
# It will fail cause the dependencies. Run the next command to install everything
sudo apt-get -f install
```

##### How to upload logs

```bash
nameles-log-migration [-H] -i <IP field> -u <url/domain field> -d <logday> /path/to/log/files/my_log_000*.csv.gz
```

The flag `-H` indicates that the csv log files have a header row with the name of the fields. The rest of the arguments are required and their meaning is the following:
  - `-i` and `-u` the zero-indexed number of the IP and url/domain fields, respectively, in the csv.
  - `-d` the day of the log files.
  - `/path/to/log/files/my_log_000*.csv.gz` list of the compressed log files to upload. You can use a wildcard to match several files, bash will translate it to the full list of matching files.

The script `nameles-log-migration`, when called as above, will create the following tables in the database:
  - **tuples.ip\_ref\_\<logday\>** Table of tuples \<IP, referrer, count\> with the aggregate count of ad-requests for each IP and referrer pair.
  - Temporary tables with the total number of ad-requests and the confidence score for the log day of both, referrers and IPs. These tables will be merged in the stats tables for IPs and referrers.

## How to query the database
The tables in the database can be queried as regular SQL tables. To easily access the Nameles database you can use the `nameles` executable, that is a wrapper to the PostgreSQL client.

Similarly, you can get the statistical thresholds for any day with

```bash
nameles-get-referrer-thresholds [--minimum <MIN_ENTRIES>] <log_day>
```



### Method

Nameles use a three staged approach where:

1) Entropy is computed for each referrer in the dataset

2) The Confidence Score (CS) is calculated for each referrer

3) Based on CS the referrers are grouped into statistically meaningful groups

The grouping in step-3 is done based on how suspicious the referrer is based on its confidence score. The groupings:

- No confidence (highest level of suspicion)
- Low confidence
- Moderate confidence
- High Confidence (no suspicion based on the entropy)

The way the score is calculated and the resulting grouping is affected by the way the system owner sets the rules for scoring. The owner is left with the decision if they want to set the system to have many false positive or many false negatives. The recommendation is for "paranoid" setting, as there is much more inventory in the programmatic market than there is demand. In fact, due to the high perishability of media inventory, it is likely that more than 90% of all inventory never gets sold. Even if just 10% is never getting sold, filtering out 10% would not reduce inventory that is available for buying.  


### Confidence Score

The unique value of Nameles is the Confidence Score (CS) it provides for any referrer, regardless if it's desktop or mobile, app, video or banners. The formal expression to compute CS is as follows:

![
                             /        __ n                          \                
                             |       \         C(x ) log (C(x ) )   |                
                             |       /__ i = 1    i     2    i      |                
               CS(X)  =  100 | 1  -  ------------------------------ |                
                             |             C(X) log (C(X) )         |                
                             \                     2                /                
](CS_formula.png)


### Total-Cost-of-Operation

One of the key considerations in the research and development that led to Nameles was Total-Cost-of-Onwership. This can be split in to two parts; Non-Recurring-Cost (NRC) and Recurring-Cost (RC).

In the case of NRC, we can safely say that it is negligible. In fact, a small startup company could fully deploy and adopt for actual business use, the Nameles system with 1 man-week of work, if not less. In the case of bigger organizations, there tends to be more overhead with introducing anything new, so NRC might be higher depending on the culture and other factors pertaining to the organization in question.

In terms of RC, depending on the database backend, the C codes that compute the NES can process roughly 10 billion rows of bid stream data per 24 hour period with a 16-core 48gb RAM Linux server. Using the same machine and the SQL provided you'll find in this repository, scores can be computed to up to 2 billion rows per day.

### Preparing the logfile

NOTE: You might get error nameles-log-migration unless you have data in both input columns for each sample. Also it does not make any sense to input data where one field is missing as both referrer/domain and IP is needed for score computation. 

### Log File Format

Nameles has been built and tested with various common bid / log file formats, and can be easily setup to work with others. The codes that you find in the repo will work with the following format out-of-the-box:

    118.240.2.92,Yt76Duc954PulBCOCoHwCOBVb64=,Ad-Stir RTB Engine 3.0,jp.ne.ibis.ibispaintx.app,jp.ne.ibis.ibispaintx.app,2016-12-01 14:25:04
    153.159.66.191,pa2kC+53FnhGSn3usjFRi+a+lsY=,Ad-Stir RTB Engine 3.0,com.web_loom.bbs_matome2,com.web_loom.bbs_matome2,2016-12-01 14:37:05
    213.143.48.47,/pzTEIMFcs95GA4EkIJFC8Zqkes=,,com.greenlionsoft.free.madrid,madrid.free.greenlionsoft.com,2016-12-01 14:38:41
    60.138.226.237,FlBh78s6qVs8+JKKDp4IQo4obgM=,Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko,anonymous.xrost.com,,2016-12-01 14:27:21
    175.158.200.140,,be2/1.0,en.softonic.com,en.softonic.com,2016-12-01 14:30:07
    219.100.131.123,DCSPgRVsyW7YOKaZMXKb2xUIO2E=,be2/1.0,chobirich.us,chobirich.us,2016-12-01 14:15:07
    112.153.74.90,vtETnuShjwD0MAQa9S1QfDLYTwM=,spray-can/1.3.3.e,com.cmcm.locker,,2016-12-01 14:44:06
    77.119.128.37,T/aqqCBAr8MuE+kdcMYhHxV7iew=,spray-can/1.3.3.e,com.cleanmaster.mguard,,2016-12-01 14:18:52
    76.198.138,zcqXdwf+7pNrBAK+ksmEEn/G6iI=,YHC/1.0,mg.mail.yahoo.com,,2016-12-01 14:26:27
    107.219.252.241,LF5rUhJzH15Oh9BrtFRRiu7cY+g=,"Mozilla/5.0 (Linux; Android 6.0.1; SM-G900V Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/54.0.2840.85 Mobile Safari/537.36",net.zedge.android,,2016-12-01 14:19:04

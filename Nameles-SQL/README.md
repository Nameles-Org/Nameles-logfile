# Nameles

Scripts and functions to compute the normalized entropy score in PostgreSQL (>=9.4)

First ever open source solution for detecting invalid traffic (ad fraud)

Nameles is an entropy based open source ad fraud detection solution that detects traffic anomalies agnostically across desktop and mobile. It has been proven[1] to deliver excellent results in detecting banner, video and app fraud at large scale with minimal Total-Cost-of-Ownership.


### Install

1. Compile and install the entropy functions in postgres

  ```bash
  cd entropy-postgresql && make && sudo make install && cd ..
  ```
2. Create a database for the [Botlab](http://botlab.io) log files in postgres and create the entropy functions in the new database

  ```bash
  psql -f create_db.sql
  psql -d nameless -f entropy-postgresql/create_functions.sql
  ```
3. Install the python packages [psycopg2](http://initd.org/psycopg) and [tld](https://pypi.python.org/pypi/tld), used by `log_migration.py` to upload compressed log files.

### How to upload logs
```bash
./nameless-log-migration <logday> /path/to/log/files/my_log_000*.csv.gz
```
The script `log_migration.py`, when called as before, will create the following tables in the database:
  - **tuples.ip_ref\_\<logday\>** Table of tuples \<IP, referrer, count\> with the aggregate count of non-concurrent ad-requests with the same IP and referrer.
  - Temporary tables with the total number of ad-requests and normalized entropy score for the log day of both, referrers and IPs. These tables will be merged in the stats tables for IPs and referrers.
  
### How to query the database
The tables in the database can be queried as regular SQL tables. 

### Contributing Team
The core developer of the project is Antonio Pastor (@apastor). Other major contributors include:

Patricia Callejo
Arturo Azcorra
Ruben Cuevas
Angel Cuevas
Matti Parssinen
Amit Phansalkar
Mikko Kotila
And last but not least, the Master himself, RR. Alan Turing of our time.


### References

[1] http://www.it.uc3m.es/rcuevas/techreports/entropy_method.pdf


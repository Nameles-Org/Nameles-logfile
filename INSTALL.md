# nameles-postgresql
Scripts and functions to compute the normalized entropy score in PostgreSQL (>=9.4)

## Install from debian package

Select the debian package that suits the PostgreSQL version of your linux distribution and install it:

```bash
sudo dpkg -i nameles-postgresql-9.5_0.1-1_amd64.deb
# It will fail cause the dependencies. Run the next command to install everything
sudo apt-get -f install
```

## How to upload logs

```bash
nameles-log-migration [-H] -i <IP field> -u <url/domain field> -d <logday> /path/to/log/files/my_log_000*.csv.gz
```

The flag `-H` indicates that the csv log files have a header row with the name of the fields. The rest of the arguments are required and their meaning is the following:
  - `-i` and `-u` the zero-indexed number of the IP and url/domain fields, respectively, in the csv.
  - `-d` the day of the log files.
  - `/path/to/log/files/my_log_000*.csv.gz` list of the compressed log files to upload. You can use a wildcard to match several files, bash will translate it to the full list of matching files.

The script `nameles-log-migration`, when called as above, will create the following tables in the database:
  - **tuples.ip\_ref\_\<logday\>** Table of tuples \<IP, referrer, count\> with the aggregate count of ad-requests for each IP and referrer pair.
  - Temporary tables with the total number of ad-requests and normalized entropy score for the log day of both, referrers and IPs. These tables will be merged in the stats tables for IPs and referrers.

## How to query the database
The tables in the database can be queried as regular SQL tables. To easily access the Nameles database you can use the `nameles` executable, that is a wrapper to the PostgreSQL client.

Similarly, you can get the statistical thresholds for any day with

```bash
nameles-get-referrer-thresholds [--minimum <MIN_ENTRIES>] <log_day>
```

## Building from source in Debian/Ubuntu

1. Install the dependencies. The python package [tld](https://pypi.python.org/pypi/tld) is not available in the Debian repositories, therefore we need to install it with pip for python 3.

  ```bash
  sudo apt-get install postgresql postgresql-server-dev-all gcc make \
                       python3 python3-pip sudo python3-psycopg2 python3-numpy
  sudo pip3 install tld
  ```

2. Compile and install the entropy functions in PostgreSQL

  ```bash
  cd nameles-postgresql
  make
  sudo make install
  cd ..
  ```
3. Create a database for Nameles in PostgreSQL and the entropy functions in the new database.

  ```bash
  sudo -u postgres psql -f /usr/share/nameles/create_db.sql
  sudo -u postgres psql -d nameles -f /usr/share/nameles/create_functions.sql
  ```
4. Create a user in PostgreSQL with the same name than your Linux user with the following script. This script will grant the new user all the privileges in the Nameles database.

  ```bash
  sudo nameles-createuser <username>
  ```

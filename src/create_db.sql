/********** NAMELES - CREATE POSTGRESQL DATABASE **********
 *
 *   author = Antonio Pastor <anpastor@it.uc3m.es>
 *   license = GNU/GPLv3
 *
 ***********************************************************/

CREATE USER nameles CREATEDB password 'nmls#pwd';
SET ROLE nameles;
create database nameles WITH OWNER=nameles TEMPLATE=template0 LC_COLLATE='C';
\connect nameles
SET ROLE nameles;
CREATE SCHEMA IF NOT EXISTS tuples;
CREATE SCHEMA IF NOT EXISTS stats;

ALTER DATABASE nameles set search_path TO public,stats,tuples,lookup;

-- Generic tuples table
CREATE TABLE tuples.ip_ref (
  ip cidr DEFAULT NULL, -- classless inter-domain routing IP type
  referrer text collate "C.UTF-8" DEFAULT NULL,
  cnt int DEFAULT NULL
);

-- Stats tables
CREATE TABLE stats.ip (
  ip cidr NOT NULL PRIMARY KEY
)
WITH (fillfactor=10);

CREATE TABLE stats.referrer (
  referrer text NOT NULL PRIMARY KEY
)
WITH (fillfactor=10);

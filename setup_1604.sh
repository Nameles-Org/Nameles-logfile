#!/bin/bash

sudo apt-get update
sudo apt-get install postgresql postgresql-contrib postgresql-client

# get the right release version
wget https://github.com/Nameles-Org/Nameles-logfile/releases/download/0.1/nameles-postgresql-9.5_0.1-1_amd64.deb

# install (never mind the errors)
sudo dpkg -i nameles-postgresql-9.5_0.1-1_amd64.deb || sudo apt-get -f install

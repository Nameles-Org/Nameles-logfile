#!/bin/bash -x

set -e
# PKGVERSION=$1
PGSQLMAJOR=$1
SRCDIR=$2

make cleanall

# export PGSQLINCLUDEDIR=$SRCDIR/src/include

#make PGSQLINCLUDEDIR=$SRCDIR/src/include

sed -e "s/@PGSQL_MAJOR@/$PGSQLMAJOR/" debian/control.in > debian/control
sed -e "s/@PGSQL_MAJOR@/$PGSQLMAJOR/" debian/prerm.in > debian/prerm

# cp debian/postinst debian/nameless-postgresql-$PGSQLMAJOR.postinst

# dh_make not needed for building only the binary
#dh_make -p nameless-postgresql_$PKGVERSION -c gpl3 --createorig -e anpastor@it.uc3m.es -s -d -n -y

debuild --set-envvar=PGSQLINCLUDEDIR=$SRCDIR/src/include --set-envvar=PGSQLMAJOR=$PGSQLMAJOR -b -us -uc

make cleanall
rm -v debian/control
rm -v debian/prerm

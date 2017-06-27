#!/bin/bash -x

set -e
# PKGVERSION=$1
PGSQLMAJOR=$1

make cleanall

# export PGSQLINCLUDEDIR=$SRCDIR/src/include

#make PGSQLINCLUDEDIR=$SRCDIR/src/include

sed -e "s/@PGSQL_MAJOR@/$PGSQLMAJOR/" debian/dirs.in > debian/dirs
sed -e "s/@PGSQL_MAJOR@/$PGSQLMAJOR/" debian/control.in > debian/control
sed -e "s/@PGSQL_MAJOR@/$PGSQLMAJOR/" debian/postinst.in > debian/postinst
sed -e "s/@PGSQL_MAJOR@/$PGSQLMAJOR/" debian/prerm.in > debian/prerm
cp debian/nameles-postgresql.lintian-overrides debian/nameles-postgresql-$PGSQLMAJOR.lintian-overrides

# cp debian/postinst debian/nameles-postgresql-$PGSQLMAJOR.postinst

# dh_make not needed for building only the binary
#dh_make -p nameles-postgresql_$PKGVERSION -c gpl3 --createorig -e anpastor@it.uc3m.es -s -d -n -y

debuild --set-envvar=PGSQLMAJOR=$PGSQLMAJOR -b -us -uc

make cleanall
rm -v debian/control
rm -v debian/prerm

CC = gcc
PGSQLINCLUDEDIR =$(shell pg_config --includedir-server)
CFLAGS += -I$(PGSQLINCLUDEDIR) -fPIC -lm
LIBDIR = /usr/lib
BINDIR = /usr/bin
SBINDIR = /usr/sbin
SHAREDIR = /usr/share/nameless
LNAME = libentropy-postgresql.so
LSONAME = libentropy-postgresql.so.$(PGSQLMAJOR)
BINNAME = nameless-log-migration
SBINNAME = nameless-createuser
SHARENAME = create_db.sql create_functions.sql test_functions.sql
OBJS = entropy.o

default: $(LNAME) libs

$(LNAME): $(OBJS)
	$(CC) -shared -o $@ $< $(CFLAGS) $(LIBS)

%.o: src/%.c
	$(CC) -c -o $@ $< $(CFLAGS)


.PHONY: install uninstall clean libs

libs:
	if [ ! -d "libs/tld" ]; then pip3 install tld --no-deps -t libs; fi

install:
	cp -t $(DESTDIR)$(LIBDIR) $(LNAME)
	cp -t $(DESTDIR)$(BINDIR) $(BINNAME:%=src/%)
	cp -t $(DESTDIR)$(SBINDIR) $(SBINNAME:%=src/%)
	cp -t $(DESTDIR)$(SHAREDIR) $(SHARENAME:%=src/%)
	cp -R -t $(DESTDIR)$(SHAREDIR) libs

uninstall:
	$(RM) -f $(DESTDIR)$(LIBDIR)/$(LNAME)*
	$(RM) -f $(BINNAME:%=$(DESTDIR)$(BINDIR)/%)
	$(RM) -f $(SBINNAME:%=$(DESTDIR)$(SBINDIR)/%)
	$(RM) -f $(SHARENAME:%=$(DESTDIR)$(SHAREDIR)/%)
	$(RM) -r $(DESTDIR)$(SHAREDIR)/libs
	$(RM) -d $(DESTDIR)$(SHAREDIR) || echo "$(DESTDIR)$(SHAREDIR) not empty, skipping"
	
clean:
	$(RM) $(OBJS)

cleanall: clean
	$(RM) $(LNAME)
#	$(RM) -r libs


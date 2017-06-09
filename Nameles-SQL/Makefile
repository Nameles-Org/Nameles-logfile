CC = gcc -std=c99
PGSQLINCLUDEDIR =$(shell pg_config --includedir-server)
CFLAGS += -I$(PGSQLINCLUDEDIR) -fPIC -lm
PKGLIBDIR =$(shell pg_config --pkglibdir)
BINDIR = /usr/bin
SHAREDIR = /usr/share/nameles
LNAME = libentropy-postgresql.so
LSONAME = libentropy-postgresql.so.$(PGSQLMAJOR)
BINNAME = nameles-log-migration nameles-get-referrer-thresholds nameles
SHARENAME = create_db.sql create_functions.sql test_functions.sql
OBJS = entropy.o

default: $(LNAME)

$(LNAME): $(OBJS)
	$(CC)  $(CPPFLAGS) $(CFLAGS) $(LDFLAGS) -shared -o $@ $< $(LIBS)

%.o: src/%.c
	$(CC)  $(CPPFLAGS) $(CFLAGS) $(LDFLAGS) -c -o $@ $<


.PHONY: install uninstall clean # libs

libs:
	if [ ! -d "libs/tld" ]; then pip3 install tld --no-compile --no-deps -t libs; fi

install:
	cp -t $(DESTDIR)$(PKGLIBDIR) $(LNAME)
	cp -t $(DESTDIR)$(BINDIR) $(BINNAME:%=src/%)
	if [ ! -d "$(DESTDIR)$(SHAREDIR)" ]; then mkdir $(DESTDIR)$(SHAREDIR); fi && \
	cp -t $(DESTDIR)$(SHAREDIR) $(SHARENAME:%=src/%) # && \
	cp -R -t $(DESTDIR)$(SHAREDIR) libs

uninstall:
	$(RM) -f $(DESTDIR)$(PKGLIBDIR)/$(LNAME)*
	$(RM) -f $(BINNAME:%=$(DESTDIR)$(BINDIR)/%)
	$(RM) -f $(SHARENAME:%=$(DESTDIR)$(SHAREDIR)/%)
	$(RM) -rf $(DESTDIR)$(SHAREDIR)/libs
	$(RM) -d $(DESTDIR)$(SHAREDIR) || echo "$(DESTDIR)$(SHAREDIR) not empty, skipping"
	
clean:
	$(RM) $(OBJS)

cleanall: clean
	$(RM) $(LNAME)
#	$(RM) -r libs

print:
	echo $(PKGLIBDIR)
	echo $(PGSQLINCLUDEDIR)


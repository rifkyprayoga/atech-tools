
STARTUP FILES
=============

You have probably notices that there are some files *missing* here. Since a lot of files
were created in /bin directory, it could become very cluttered very soon (especially if using
application on more than one system - USB key).

We added bin/ext directory, where are files are residing from now on. If you need some file from
there you just need to start it from /bin directory, so for example if I would like to run
database (which I know has name db.<ext>) I would run it from bin directory like this:
   ./ext/db.sh

Only files still available in bin are main files for starting application: run_win.cmd,
run_linux.sh,..

Following startup files might be available for you (not every application creates all
of them):
    - db : Running internal database
    - db_init: Initialization of database (be careful with this command it will delete
               your database and recreate it again. Useful for init of external database).
               Will be replaced with db_tool in future.
    - db_tool: Tool for database (not available yet)
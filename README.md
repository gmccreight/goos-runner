### Goos Runner

Run the Goos tests in a Vagrant box

### Installation

First, start with the automated part of the installation

    ./do_automated_part_of_installation_from_scratch

Then setup a temporary port forwarding (which will allow you finish the
openfire XMPP server setup in your web browser)

    vagrant ssh -- -N -L 9090:localhost:9090

Now, on the host machine, open a link to http://localhost:9090/ and complete
the web-based openfire server setup:

Note... TODO... move these docs into the `_configure_openfire.js` file.

* Use English
* Use the default server settings:
    * 9090 and 9091 (using blowfish).  No property encryption key.
* Use the MySQL database
    * connection: `jdbc:mysql://localhost:3306/openfire_dev?rewriteBatchedStatements=true`
    * user: myopenfireuser
    * pass: myopenfirepass
* Set up the administrator account
    * Email: admin@example.com (but login will actually be admin,
      not admin@example.com)
    * Password: adminpass

Ok, this has not been automated yet:

* Log in to the administration console
    * username: admin
    * password: adminpass
* Set up the auction-item-54321 user
    * username: auction-item-54321
    * email: (empty)
    * password: auction
* Create the sniper user
    * username: sniper
    * email: sniper@localhost
    * password: sniper

### Run tests after installation

    vagrant ssh -c "cd /goos/; ./run_tests"

### Debugging (on Mac Yosemite)

On the host machine, install XQuartz.

After installation, and before starting it, enable the test extensions.  XTEST
is required by the end-to-end tests, and enabling the test extensions makes
it available.

    defaults write org.macosforge.xquartz.X11 enable_test_extensions -bool yes

Now, you'll need to run Vagrant so that it proxies X11 to the host machine.

    USE_ACTUAL_X11=1 vagrant reload

in the host, then SSH into Vagrant and run xclock to see that the X11 server
connection is working.

    vagrant ssh -c "xclock"


If that works, then you can run the tests using the host's X11 server.

    vagrant ssh -c "cd /goos/; USE_ACTUAL_X11=1 ./run_tests"

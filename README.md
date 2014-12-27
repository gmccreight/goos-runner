### Goos

Run the Goos code.

### How

cd into the vm folder

run

### Installation

    ./do_everything_from_scratch

then setup a temporary port forwarding (which will allow you finish the setup
in your web browser.

    vagrant ssh -- -N -L 9090:localhost:9090

In the web-based setup

* Use English
* Use the default server settings
    * 9090 and 9091 (using blowfish).  No property encryption key.
* Use the MySQL database
    * jdbc:mysql://localhost:3306/openfire\_dev?rewriteBatchedStatements=true
    * user: myopenfireuser
    * pass: myopenfirepass
* Set up the admin user
    * Email: admin@example.com (but login will actually be admin, not admin@example.com)
    * Password: adminpass
* Create the sniper user
    * name: sniper
    * email: sniper@localhost (this may be important)
    * password: sniper

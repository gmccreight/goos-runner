// This script is used to automate the web-based configuration of the
// Openfire server.

var casper = require('casper').create();

casper.start('http://localhost:9090/', function() {
    // Welcome to Setup
    // * Use English

    // Without the wait, this occasionally fails
    this.wait(1000, function() {
      this.echo(this.getTitle());
      this.click('#jive-setup-save');
    });
});

casper.then(function() {
    // Server Settings
    // * Use the default server settings:
    //     * 9090 and 9091 (using blowfish).  No property encryption key.

    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Database Settings
    // * standard connection (default)

    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Database Settings - Standard Connection
    // * Use the MySQL database
    //     * connection: `jdbc:mysql://localhost:3306/openfire_dev?rewriteBatchedStatements=true`
    //     * user: myopenfireuser
    //     * pass: myopenfirepass

    this.echo(this.getTitle());

    casper.evaluate(function() {
      var selectElem = document.getElementsByName("presets")[0];
      selectElem.children[0].setAttribute("selected", false);
      selectElem.children[1].setAttribute("selected", "selected");

      document.getElementsByName("driver")[0].value = "com.mysql.jdbc.Driver";
      document.getElementsByName("serverURL")[0].value =
       "jdbc:mysql://localhost:3306/openfire_dev?" +
       "rewriteBatchedStatements=true";
      
      document.getElementsByName("username")[0].value = "myopenfireuser";
      document.getElementsByName("password")[0].value = "myopenfirepass";
    });

    this.click('#jive-setup-save');
});

casper.then(function() {
    // Profile Settings
    // * standard profile (default)

    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Administrator Account
    // * Email: admin@example.com (but login will actually be admin,
    //   not admin@example.com)
    // * Password: adminpass

    this.echo(this.getTitle());

    casper.evaluate(function() {
      document.getElementsByName("newPassword")[0].value = "adminpass";
      document.getElementsByName("newPasswordConfirm")[0].value = "adminpass";
    });

    this.click('#jive-setup-save');
});

// At this point, the setup is complete.  Now we need to log in and set up the
// auction item and the sniper user.

casper.thenOpen('http://localhost:9090', function() {
    // Setup Complete!
    // Log in to the administration console
    //     * username: admin
    //     * password: adminpass

    this.echo(this.getTitle());
    casper.evaluate(function() {
      document.getElementsByName("username")[0].value = "admin";
      document.getElementsByName("password")[0].value = "adminpass";
    });
    this.click('input[type=submit]');
});

casper.thenOpen('http://localhost:9090/user-create.jsp', function() {
    // * Set up the auction-item-54321 user
    //     * username: auction-item-54321
    //     * email: (empty)
    //     * password: auction

    this.echo(this.getTitle());
    casper.evaluate(function() {
      document.getElementsByName("username")[0].value = "auction-item-54321";
      document.getElementsByName("password")[0].value = "auction";
      document.getElementsByName("passwordConfirm")[0].value = "auction";
    });
    this.click('input[name=create]');
});

casper.thenOpen('http://localhost:9090/user-create.jsp', function() {
    // * Create the sniper user
    //     * username: sniper
    //     * email: sniper@localhost
    //     * password: sniper

    this.echo(this.getTitle());
    casper.evaluate(function() {
      document.getElementsByName("username")[0].value = "sniper";
      document.getElementsByName("email")[0].value = "sniper@localhost";
      document.getElementsByName("password")[0].value = "sniper";
      document.getElementsByName("passwordConfirm")[0].value = "sniper";
    });
    this.click('input[name=create]');
});

casper.then(function() {
    // Done

    this.echo(this.getTitle());
    this.echo("Openfire configuration done");
});

casper.run();

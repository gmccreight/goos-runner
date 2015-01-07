var casper = require('casper').create();

casper.start('http://localhost:9090/', function() {
    // Welcome to Setup
    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Server Settings
    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Database Settings
    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Database Settings - Standard Connection
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
    this.echo(this.getTitle());
    this.click('#jive-setup-save');
});

casper.then(function() {
    // Administrator Account
    this.echo(this.getTitle());

    casper.evaluate(function() {
      document.getElementsByName("newPassword")[0].value = "adminpass";
      document.getElementsByName("newPasswordConfirm")[0].value = "adminpass";
    });

    this.click('#jive-setup-save');
});

casper.then(function() {
    // Setup Complete
    this.echo(this.getTitle());
});

// At this point, the setup is complete.  Now we need to log in and set up the
// auction item and the sniper user.

casper.run();

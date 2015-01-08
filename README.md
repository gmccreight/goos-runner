### Goos Runner

Run the Goos tests in a Vagrant box

### Installation

Just run the script

    ./do_everything_from_scratch

### Running the tests again

    vagrant ssh -c "cd /goos/; ./run_tests"

### Debugging

#### Debugging the Openfire installation

If the automated Openfire server installation didn't go well, you can 
try it out in your host browser by setting up temporary port forwarding:

    vagrant ssh -- -N -L 9090:localhost:9090

Then you can surf to http://localhost:9090 and do the configuration manually

#### Debugging X11 and Swing (on Mac Yosemite)

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

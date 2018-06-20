# LoginMSG
https://www.spigotmc.org/resources/loginmsg.25986/

Customise login, log out and death messages with a powerful configuration system.

## Development
Get a copy up and running on your computer to test!

### Prerequisites
 - Spigot - get from BuildTools
 - Some IDE (repo includes Intellij project files)
 - JDK 8 / Maven

### Building
Before building, make sure to stop the Spigot server if it is running.

If you're using Intellij, run the `Package` build configuration. This will clean and build LoginMSG. For other systems,
use whatever they supply for building Maven projects.

Now you can start up the Spigot server again to test.

### Deployment

Make sure to follow the [contributing guidelines](.github/CONTRIBUTING.md).

Before developing, make a fork of this repo. Make a branch for what you're doing (following the contributing guidelines),
work your magic, increment the version number (using SemVer) and create a pull request.

If the pull request is approved, Travis will build LoginMSG and create a new release on Github and Spigot. It will
automatically generate change logs from your commits.
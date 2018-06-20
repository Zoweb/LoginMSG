# Contributing
Want to contribute? Cool, thanks! But please be sure that you follow some rules described below:

- [Reporting Issues](#reporting-issues)
  - [Be Verbose](#be-verbose)
  - [Crash reports](#crash-reports)
  - [Issue attachments](#issue-attachments)
- [Requesting Features](#requesting-features)
- [Contributing with Code](#contributing-with-code)
  - [Use latest branch](#use-latest-branch)
  - [Branch names](#branch-names)
  - [Use separate commits](#use-separate-commits)
  - [Use descriptive commit messages](#use-descriptive-commit-messages)
  - [Don't use Java 9 features or above](#dont-use-java-9-features-or-above)
- [Code of Conduct](#code-of-conduct)
  - [Our Pledge](#our-pledge)
  - [Our Standards](#our-standards)
  - [Our Responsibilities](#our-responsibilities)
  - [Scope](#scope)
  - [Enforcement](#enforcement)
  - [Attribution](#attribution)

## Reporting Issues
Thanks! Without user feedback, the authors can't know about problems. But, if you want to be helpful, you need to supply
the authors with detailed information about the problem.

### Be Verbose
- Attach descriptive screenshots demonstrating all the steps you are doing. And it would be better if you highlight the
  problem in a screenshot.
- Attach logs. Remember to remove personal information, though. And make sure it isn't too long!

### Crash reports
LoginMSG will log a "stack trace" when an error occurs. Please include this as an attachment in your issue.

### Issue attachments
Github only accepts screenshots as direct attachments. Please, don't try to cheat Github by changing file extensions,
or posting huge log files via comments body.

Zip your files, then upload them to DropBox or Github Gist and post a link in the issue.

## Requesting Features
- Update LoginMSG first! Don't waste your time requesting a feature that already exists.
- Sure we haven't already implemented it? Describe your suggestion in details. There are a lot of caricatures about
  software design. It would be a disappointment if after implementation you realise it was not the feature you were
  waiting for.
  
## Contributing with code
Before hacking into LoginMSG make sure you are using the latest build.

### Use latest branch
Please do not try to submit patches for old branches! Patches for versions that were obsolete months ago make no sense.

So, before hacking LoginMSG make sure you're using at least the `master` branch. Then fork from it and make a new branch
following the rules in the [branch names section](#branch-names).

### Branch names
We use SmartGit with default settings so you can use that if you want. Just configure Git-Flow as Full.

When creating a branch, make sure you only work on what the branch is for inside it.

- Feature Branch: `feature/[feature name]`
- Hot-Fix Branch: `hotfix/[hotfix-name]`
- Support Branch: `support/[support-name]`

### Use separate commits
Please, use separate commits! Don't do all your changes in one commit, split them up. And commit often - every time you
change something, commit it.

#### Bad Example
```
* add chocolate drip support 🍫
```

#### Good Examples
```
* add chocolate drips 🍫 to login message
* add chocolate drips 🍫 to death message
* add chocolate drips to explosions 💥
```

### Use descriptive commit messages
Commit messages should be as descriptive as possible, but should never exceed 80 characters long.
If you can, try and include a relevant emoji in the commit message to spice it up a little. Never use more than one.

Don't submit commits with the same messages. It's quite difficult to review a list of identical commits.

### Don't use Java 9 features or above
LoginMSG is supposed to run on Java 8, as not many people use newer versions of Java.

## Code Of Conduct
### Our Pledge
In the interest of fostering an open and welcoming environment, we as contributors and maintainers pledge to making
participation in our project and our community a harassment-free experience for everyone, regardless of age, body size,
disability, ethnicity, gender identity and expression, level of experience, nationality, personal appearance, race,
religion, or sexual identity and orientation.

### Our Standards
Examples of behaviour that contribute to creating a positive environment include:
- Using welcoming and inclusive language
- Being respectful of differing viewpoints and experiences
- Gracefully accepting constructive criticism
- Focusing on what is best for the community
- Showing empathy towards other community members

Examples of unacceptable behaviour by participants includes:
- The use of sexualised language or imagery and unwelcome sexual attention or advances
- Trolling, insulting/derogatory comments, and personal or political attacks
- Public or private harassment
- Publishing others' private information, such as physical or electronic address, without explicit permission
- Other conduct which could be reasonably considered inappropriate in a professional setting

### Our Responsibilities

Project maintainers are responsible for clarifying the standards of acceptable behavior and are expected to take
appropriate and fair corrective action in response to any instances of unacceptable behavior.

Project maintainers have the right and responsibility to remove, edit, or reject comments, commits, code, wiki edits,
issues, and other contributions that are not aligned to this Code of Conduct, or to ban temporarily or permanently any
contributor for other behaviors that they deem inappropriate, threatening, offensive, or harmful.

### Scope

This Code of Conduct applies both within project spaces and in public spaces when an individual is representing the
project or its community. Examples of representing a project or community include using an official project e-mail
address, posting via an official social media account, or acting as an appointed representative at an online or offline
event. Representation of a project may be further defined and clarified by project maintainers.

### Enforcement

Instances of abusive, harassing, or otherwise unacceptable behavior may be reported by contacting the project team at
loginmsg@email.cloud.zoweb.me. All complaints will be reviewed and investigated and will result in a response that is
deemed necessary and appropriate to the circumstances. The project team is obligated to maintain confidentiality with
regard to the reporter of an incident. Further details of specific enforcement policies may be posted separately.

Project maintainers who do not follow or enforce the Code of Conduct in good faith may face temporary or permanent
repercussions as determined by other members of the project's leadership.

### Attribution

This Code of Conduct is adapted from the Contributor Covenant, version 1.4,
available at http://contributor-covenant.org/version/1/4
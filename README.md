```
▞▀▖               ▐  ▗          ▛▀▘                       ▌  
▌  ▞▀▖▛▀▖▞▀▖▞▀▖▛▀▖▜▀ ▄ ▛▀▖▞▀▌   ▙▄ ▙▀▖▝▀▖▛▚▀▖▞▀▖▌  ▌▞▀▖▙▀▖▌▗▘
▌ ▖▌ ▌▌ ▌▌ ▖▛▀ ▙▄▘▐ ▖▐ ▌ ▌▚▄▌   ▌  ▌  ▞▀▌▌▐ ▌▛▀ ▐▐▐ ▌ ▌▌  ▛▚ 
▝▀ ▝▀ ▘ ▘▝▀ ▝▀▘▌   ▀ ▀▘▘ ▘▗▄▘   ▘  ▘  ▝▀▘▘▝ ▘▝▀▘ ▘▘ ▝▀ ▘  ▘ ▘
```
Introduction
---
In my nearly 25 years of experience in software development, I faced the same following problems in different companies 
and projects:

1. Code with lack of standards, reuse and complexity.
2. Code duplications due lack of quality checks.
3. Different versions for different environments (Operating systems, Development, Production, etc).
4. Hard-coded and/or non synchronized persistence layer within data models.
5. Hard-coded and/or non synchronized business layer.
6. Poor and/or non-customizable and complex UI.
7. Lots of unused/unecessary libraries in the dependency tree.
8. Critical security flaws/vulnerabilities.
9. Lack of a centralized utilities package.
10. And much more. The list is extensive.

This framework was designed to address the above and make the development process simple. As a result, it ensures 
faster, standardized and reliable way to develop your applications. 

Build Status
---
- Stable version: **3.10.16**
- Development version: **3.10.17**

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fvilarinho_framework&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=fvilarinho_framework)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fvilarinho_framework&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=fvilarinho_framework)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fvilarinho_framework&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=fvilarinho_framework)
[![Build status](https://github.com/fvilarinho/framework/actions/workflows/build.yml/badge.svg)](https://github.com/fvilarinho/framework/actions/workflows/build.yml)
[![Release status](https://github.com/fvilarinho/framework/actions/workflows/release.yml/badge.svg)](https://github.com/fvilarinho/framework/actions/workflows/release.yml)

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=fvilarinho_framework)

Installation
---
The pre-requisites to use this framework are:

For stable version:
1. [Java Development Kit 11](https://openjdk.org/projects/jdk/11/)
2. [Gradle 6.x](https://gradle.org/)

For development version:
1. [Java Development Kit 21](https://openjdk.org/projects/jdk/21/)
2. [Gradle 9.x](https://gradle.org/)

To start using the framework, you should clone the project and then import it into your preferable IDE.
After the cloning, you can choose the version that you want to work.
To use the development version, execute the command below:

`git checkout master`

To use the last stable version, execute the command below:

`git checkout tags/3.10.16 -b 3.10.16`

Or use the IDE to switch between the versions.

License
---
The framework is licensed under the LGPL 3.0. Please read the license file or check the URL [http://www.gnu.org/licenses/lgpl-3.0.html](http://www.gnu.org/licenses/lgpl-3.0.html)

Author
---
My name is Felipe Vilarinho (A.K.A. Vila) and you can reach me accessing my personal website. There you can find my
contact info.

[https://vilanet.sh](https://vilanet.sh)

Contribution
---
If you want to contribute, feel free to join as contributor in GitHub ([https://www.github.com/fvilarinho/framework]([https://www.github.com/fvilarinho/framework))
and then make a pull request. I'll analyze your code and check if it can be incorporated as soon as possible. You can 
also report a bug or request a new feature [here](https://github.com/fvilarinho/framework/issues).

- [Security policy](SECURITY.md)

And that's all! Have Fun!

Best
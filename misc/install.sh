#! /bin/bash
#
# Installs the dependencies necessary for building TimelineAnalyzer.
# Use clone.sh to download the repositories from GitHub when necessary.
#
# Run this directly from the misc directory: ./install.sh
#

# Halyard JAR
mvn install:install-file \
   -Dfile=../timeline-storage-halyard/lib/halyard-common-1.3.jar \
   -DgroupId=com.msd.gin.halyard.common \
   -DartifactId=halyard-common \
   -Dversion=1.3 \
   -Dpackaging=jar \
   -DgeneratePom=true

# sqljet (required by timeline-analyzer-local)
cd ../../sqljet
mvn install -DskipTests

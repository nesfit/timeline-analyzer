#! /bin/bash
#
# Installs the dependencies necessary for building TimelineAnalyzer.
#
# Run this directly from the misc directory: ./install.sh
#

# Halyard JAR
mvn install:install-file \
   -Dfile=../timeline-storage-halyard/lib/halyard-common-3.2.jar \
   -DgroupId=com.msd.gin.halyard.common \
   -DartifactId=halyard-common \
   -Dversion=3.2 \
   -Dpackaging=jar \
   -DgeneratePom=true

# sqljet (required by timeline-analyzer-local)
mvn install:install-file \
   -Dfile=../timeline-analyzer-local/lib/sqljet-1.2.2.4-TARZAN-1.jar \
   -DgroupId=eu.printingin3d.sqljet \
   -DartifactId=sqljet \
   -Dversion=1.2.2.4-TARZAN-1 \
   -Dpackaging=jar \
   -DgeneratePom=true

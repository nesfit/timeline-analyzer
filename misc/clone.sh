#! /bin/bash
#
# Clones or updates the necessary repositories from GitHub.
#
# Run this directly from the misc directory: ./clone.sh
#

REPOS="sqljet rdf4j-vocab-builder rdf4j-class-builder"
GHROOT="git@github.com:radkovo"

cd ../..
for I in $REPOS; do
    if [ -d $I ]; then
        echo "Updating $I"
        cd $I
        git pull
        cd ..
    else
        echo "Cloning $I"
        git clone "$GHROOT/$I.git"
    fi
done

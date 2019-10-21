#! /bin/bash
#
# Clones or updates the necessary repositories from GitHub.
#
# Run this directly from the misc directory: ./clone.sh
#

REPOS="sqljet"
GHROOT="https://github.com/radkovo"

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

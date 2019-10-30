#! /bin/bash
#
# Builds the angular client web application.
#
# Run this directly from the misc directory: ./build_client.sh
#

DEST="../timeline-client-angular"

cd $DEST
rm -rf dist
npm install
ng build --prod

echo "The application should be built in $DEST/dist"

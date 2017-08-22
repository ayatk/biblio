#!/usr/bin/env bash

ROOT_DIR=`git rev-parse --show-toplevel`

VERSION=`grep "def VERSION" $ROOT_DIR/app/build.gradle | sed -E 's/.*"(.+)"$/\1/'`

curl \
  -F "file=@app/build/outputs/apk/biblio-v${VERSION}-nightly-release.apk" \
  -F "token=${DEPLOY_GATE_API_KEY}" \
  -F "message=Build #${CIRCLE_BUILD_NUM}" \
  -F "distribution_key=3f542ebaa365a1893d3467b34eb27c0a77ca67a7" \
  -F "release_note=Build #${CIRCLE_BUILD_NUM}" \
  https://deploygate.com/api/users/ayatk/apps

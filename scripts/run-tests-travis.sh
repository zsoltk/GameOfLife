#!/bin/bash

./gradlew :build \
          :appUnitTests:testDebug \
          :appComponentTests:testDebug \
          :app:connectedAndroidTest \
          :appUnitTests:jacocoTestReport \
          :appUnitTests:coveralls \
          -PtravisCi -PdisablePreDex

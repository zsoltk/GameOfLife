#!/bin/bash

./gradlew :build \
          :appUnitTests:testTest \
          :appComponentTests:testTest \
          :app:connectedAndroidTest \
          :appUnitTests:jacocoTestReport \
          :appUnitTests:coveralls \
          -PtravisCi -PdisablePreDex

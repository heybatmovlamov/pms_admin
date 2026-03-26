#!/bin/bash

# Jar faylının yolu
JAR_PATH="/opt/turpark_core/CORE_PMS_API.jar"

echo "Tətbiq işə düşür..."

nohup java -jar $JAR_PATH > /dev/null 2>&1 &

echo "Tətbiq işə düşdü."
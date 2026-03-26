#!/bin/bash

# Jar faylının yolu
JAR_PATH="/opt/turpark_core/CORE_PMS_API.jar"

# Prosesi jar faylına əsaslanaraq tap
PID=$(ps aux | grep "$JAR_PATH" | grep -v grep | awk '{print $2}')

if [ -z "$PID" ]; then
    echo "Tətbiq tapılmadı, dayandırılacaq proses yoxdur."
else
    echo "Tətbiq dayandırılır... PID: $PID"
    kill -9 $PID
    echo "Tətbiq dayandırıldı."
fi
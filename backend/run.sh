#check if redis not installed then install it
if [ ! -f /usr/bin/redis-server ]; then
    echo "Installing Redis"
    sudo apt-get update
    sudo apt-get install redis-server -y
else
    echo "Redis already installed"
fi

#check java 17 is installed or not
if [ ! -f /usr/bin/java ]; then
    echo "Installing Java 17"
    sudo apt-get update
    sudo apt-get install openjdk-17-jdk -y
else
    echo "Java 17 already installed"
fi

#build and run the application
chmod +x gradlew
./gradlew clean build
./gradlew bootRun
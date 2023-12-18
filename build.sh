original_dir=$(pwd)
echo building jar
cd ./java-movie-fetcher/
mvn clean install
mv ./target/app-1.0.0-SNAPSHOT.jar ../app-1.0.0-SNAPSHOT.jar
echo building C runner
cd "$original_dir"
cd ./c-movie-displayer/c/
make clean
make
mv ./epd ../../epd
cd "$original_dir"
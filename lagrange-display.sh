#!/bin/bash

url=https://cinelagrange.ch/compact

# fetching last updated date to know if image refresh is necessary
# by comparing it with last registered updated date (to timestamps)
curr_updated_date=$(curl https://cinelagrange.ch/feed | jq -r '.date')
curr_updated_date=$(date -d "$curr_updated_date" +%s)

if grep -q '^LAST_UPDATED_DATE=' last-updated-date.txt; then
    prev_updated_date=$(grep '^LAST_UPDATED_DATE=' last-updated-date.txt | cut -d'=' -f2)
    echo "LAST_UPDATED_DATE already exists in last-updated-date.txt: $prev_updated_date"
else
    prev_updated_date=0
    echo "LAST_UPDATED_DATE=$prev_updated_date" > last-updated-date.txt
    echo "LAST_UPDATED_DATE not registered yet, storing default one into last-updated-date.txt"
fi


if [ "$curr_updated_date" -gt "$prev_updated_date" ]; then
    echo "updating last date and refreshing image..."
    chromium --no-sandbox --headless --disable-gpu --screenshot=movies-program.png --window-size=984x1305 --disable-software-rasterizer $url
    convert movies-program.png -rotate 90 movies-program.bmp
    rm -rf movies-program.png
    ./epd movies-program.bmp
else
    echo "nothing to refresh, keep displaying current image."
fi

# update last updated date
echo "LAST_UPDATED_DATE=$curr_updated_date" > last-updated-date.txt

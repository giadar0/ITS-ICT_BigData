## Prerequistes (csvtool)
# To install it in Ubuntu:
# $ sudo apt-get install csvtool
# To install it in OS X:
# $ brew install opam
# $ opam init
# $ eval `opam config env`
# $ opam install csvtool
# $ csvtool --help

csvtool -t ',' -u ';' cat ../strava_activities.csv | jq -R -s -f csv2json.jq | sed -e 's/: \"\"/: null/g' > ../strava_activities.json


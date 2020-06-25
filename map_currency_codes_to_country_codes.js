const request = require('request')
const fs = require('fs')

async function run() {

  var result = '';
  let $codes = await downloadUrl('https://gist.githubusercontent.com/HarishChaudhari/4680482/raw/b61a5bdf5f3d5c69399f9d9e592c4896fd0dc53c/country-code-to-currency-code-mapping.csv');

  $codes.split('\n').slice(1).forEach(row => {
  	let splittedLine = row.split(',')
	result += '"' + splittedLine[3].toLowerCase() + '"' +
		" -> R.drawable." + 
		splittedLine[1].toLowerCase() + "\n"
	})

  fs.writeFile('output.txt', result, error => { console.log(error); });
}
run();

function downloadUrl(url) {
  console.log("Downloading: " + url);
  return new Promise((resolve, reject) => {
    request(url, (error, response, body) => {
      if (response === undefined) {
        downloadUrl(url);
      } else if (error) reject(error);
      else if (response.statusCode != 200) {
        reject('Invalid status code <' + response.statusCode + '>');
      } else {
        console.log('Downloaded: ' + url);
        resolve(body);
      }
    });
  });
}
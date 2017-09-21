
const fs = require('fs');
var request = require('request');
var path = require('path');
//var spawn = require('child_process').spawn;
var spawn = require('child_process').spawn;

const imgFilePath = 'images/';

//For reading filenames from stdin
const StringDecoder = require('string_decoder').StringDecoder;
const decoder = new StringDecoder('utf8');

const uploadUrl = 'http://tolkki.eu:3000/upload';

const camera = spawn('./camera/motion_detection', {
  detached: false
});

camera.stdout.on('data', data => {

    //get filename from stdio -stream
    let imgFileName = decoder.write(data);
    //Remove possible \n
    let tmpStrArr = imgFileName.split('\n');
    imgFileName = tmpStrArr[0];

    let filePath = path.join(__dirname, imgFilePath + imgFileName);

    var r = request.post(uploadUrl, (err, httpResponse, body) => {
        if (err) {
            return console.error('upload failed:', err);
        }
        else {
            //Remove a send photo
            fs.unlink(filePath, err => {
                if(err) {
                    console.log('Removing ' + imgFileName + ' failed.');
                    console.log(err);
                }
                else {
                    console.log(imgFileName + ' terminated.');
                }
            });
        }
    });
    let form = r.form();
    form.append('image', fs.createReadStream(filePath));
});

//Kill motion_detection process before closing nodejs
process.on('exit', (code) => {
    camera.kill('SIGINT');
});

process.on('SIGINT', (code) => {
    camera.kill('SIGINT');
});

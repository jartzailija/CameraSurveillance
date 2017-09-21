//TODO: sanitoi tietokantajutskat
class VideoController {

    /**
     * [constructor description]
     * @param {String} name
     * @param  {Array} objArr [description]
     *       @param {Object}
     *              @param {String} timestamp
     *              @param {String} name
     *
     */
    constructor(name, objArr) {
        this.fps = 25;
        this.tmpFolder = './tmp/'
        this.videoFolder = './videos/';
        this.name = name;
        this.fileExtension = '.mp4';
        this.dbController = require('./dbController.js');
        this.builder;
        this.configFileName = this.name + '.txt';
        this.configPath = this.tmpFolder + this.configFileName;

        this.fs = require('fs');
        
		
		this.duration = this.calculateWholeDuration(objArr);
		
		var thisObj = this;
		
        this.makeConfigFile(objArr).then(
            () => {
                thisObj.builder = require('fluent-ffmpeg');
                thisObj.generateVideo()
                    .then(
                        () => {
                            thisObj.removeConfigFile();
                            thisObj.dbController.addVideo(thisObj.name + thisObj.fileExtension, thisObj.name, thisObj.duration);
                            console.log('valmis!');
                            
                        },
                        (err) => {
                            console.log(err);
                        }
                    );
            },
            (err) => {
                console.log(err);
            }
        );



    }
	
	calculateWholeDuration(objArr) {
		console.log('lasketaan');
		var lastIndex = objArr.length - 1;
		var begins = objArr[0].timestamp;
		var ends = objArr[lastIndex].timestamp;
		
		console.log('length ' + (Number(ends) - Number(begins)));
		
		return Number(ends) - Number(begins);
	}

    //TODO: poista tämä ja viittaukset tähän
    calculateFrameCount(objArr) {
        var thisObj = this;
        return objArr.map( obj => {
            var frameCount = Math.round(Number(thisObj.fps) * Number(Math.abs(obj.duration)));
            obj.framecount = frameCount;
            return obj;
        });
    }

    makeConfigFile(objArr) {
        var thisObj = this;
        let configArr = this.calculateFrameCount(objArr);
        let concatStr = '';
        concatStr += configArr.map(obj => {
            //TODO: tsekkaa durationin toiminta
            return 'file ' + obj.name + '\n' + 'duration ' + obj.duration + '\n';
        });
        concatStr = concatStr.replace(/,/g, '');
        return new Promise((resolve, reject) => {
            thisObj.fs.writeFile(thisObj.configFileName, concatStr, (err) => {
                if(err) {
                    reject(err);
                }
                else {
                    console.log('generate file');
                    resolve();
                }
            });
        });
    }

    generateVideo() {
        console.log('generate video');
        var thisObj = this;
        return new Promise((resolve, reject) => {
            this.builder(this.configFileName)
                .inputFormat('concat')
                .on('end', () => {
                    resolve();
                })
                .on('error', (err) => {
                    reject(err);
                })
                .save(this.videoFolder + this.name + this.fileExtension);
        });

    }



    removeConfigFile() {
        this.fs.unlink(this.configFileName, (err) => {
            if(err) {
                console.log('Promblems while removing a tmpconfigfile ' + this.configFileName);
                console.log(err);
            }
        });
    }
}

module.exports = VideoController;

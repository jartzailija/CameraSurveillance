//TODO: tietokanta
class ImageGroup{
    constructor(name, dbController) {
        console.log('alustus');
        this.db = dbController;
        this.fs = require('fs');
        this.ffmpeg = require('fluent-ffmpeg');

        this.parentImgFolder = 'images/';
        this.imageNames = [];
        this.tmpFile = this.parentImgFolder + 'tmp/';
        this.videoController = require('./videoController.js');

        this.groupFileName = name + '/';
        this.name = name;
        this.timingTable = [];
    }

    addImage(imageName) {
        this.imageNames.push(imageName);
    }

    zeroPad(num) {
        var zero = 4 - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join("0") + num;
    }


    generateImageName(imageName, index) {
        let nameArr = imageName.split('.');

        let imageNum = nameArr[0];
        let extension = nameArr[1];
        index++;
        let name = this.zeroPad(index);
        return name + '.' + extension;
    }


    getTimeStamp(name) {
        let nameArr = name.split(".");
        return nameArr[0];
    }

    /*getImages() {
        return this.imageNames;
    }*/

    transferImagesToFinalDestination() {
        var newFolderName = this.parentImgFolder + this.groupFileName;
        var thisObj = this;
        var retPromise = new Promise((resolve, reject) => {
                thisObj.fs.mkdir(newFolderName,
                (err) => {

                    if(err) {
                        reject(err);
                    }

                    //save info when async funcs in loop are ready
                    let parallerMoves = [];
                    var i = 0;

                    thisObj.imageNames.map(oldName => {
                        console.log(oldName);
                        let newName = thisObj.generateImageName(oldName, i);
                        var promise = thisObj.moveImage(newFolderName, oldName, newName);
                        parallerMoves.push(promise);

                        let time = thisObj.getTimeStamp(oldName);
                        //let thisObj = this;
                        thisObj.db.addImage(newName, time, thisObj.name)
                            .then(
                                () => {},
                                (err) => {
                                    console.log("Error while adding image names to db.");
                                    console.log(err);
                                }
                            );

                        i++;
                    });

                    Promise.all(parallerMoves)
                        .then(
                            (objArr) => {
                                thisObj.generateTimingTable(objArr);
                            }
                        )
                        .then(
                            () => {
                                resolve();
                            }
                        )
                        .catch(e => {
                            console.log(e);
                        }
                    );
                }
            );
        });

        return retPromise;
    }


    /**
     * [moveImage description]
     * @param  {[type]} newFolderName [description]
     * @param  {[type]} name          [description]
     * @param  {[type]} newName       [description]
     * @return {Promise}               [description]
     */
    moveImage(newFolderName, oldName, newName) {
        var thisObj = this;
        var oldPath = thisObj.tmpFile + oldName;
        var newPath = newFolderName + newName;

        return new Promise((resolve, reject) => {
            var source = this.fs.createReadStream(oldPath);
            var desti = this.fs.createWriteStream(newPath);

            source.pipe(desti);
            source.on('end', () => {
                thisObj.fs.unlink(oldPath, function(err) {
                    if(err) {
                        reject(err);
                    }
                    else {
                        /**
                         * @param {Object}
                         *        {String} name - filename
                         *        {String} timestamp - when an image is shooted in ms
                         */
                        let timestamp = thisObj.getTimeStamp(oldName);
                        resolve({
                            name: newName,
                            timestamp: timestamp
                        });
                    }
                });
            });
        });

    }


    fixTimingTable() {
        if(this.timingTable.length > 0) {
            let times = [];

            for(var index in this.timingTable) {
                var timeObj = this.timingTable[index];
                let timestamp = timeObj.timestamp;

                if(Number(index) + 1 < this.timingTable.length) {
                    var nextTimeObj = this.timingTable[Number(index) + 1];
                    let duration = Number(nextTimeObj.timestamp) - Number(timestamp);
                    times.push(duration);
                    timeObj.duration = Number(duration) / 1000; //As seconds
                }

                //Last image, generate a mean time to duration
                else {

                    if(this.timingTable.length > 1) {
                        var sum = times.reduce((a, b) => { return a + b; });
                        timeObj.duration = Number(sum) / Number(times.length) / 1000; //As seconds;
                    }
                    else {
                        //If it has only one image, set default value
                        timeObj.duration = 0.16;
                    }
                }
            }
        }
    }

    generateTimingTable(objArr) {
        let thisObj = this;
        objArr.map(obj => {
            let timeObj = {
                name: thisObj.parentImgFolder + thisObj.groupFileName + obj.name,
                timestamp: obj.timestamp,
                duration: 0.16
            };
            this.timingTable.push(timeObj);
        });
    }


    createVideo() {
        var thisObj = this;
        var imagesTransferredPromise = this.transferImagesToFinalDestination();
        imagesTransferredPromise.then(() => {
            thisObj.fixTimingTable();

            let videoController = new thisObj.videoController(
                thisObj.name,
                thisObj.timingTable
            );

        },
        (err) => {
            console.log(err);
        });
    }
}

module.exports = ImageGroup;

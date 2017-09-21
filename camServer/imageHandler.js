//TODO: siivoa ja kommentoi
//TODO: imageGroup-siivoaja
class ImageHandler {
    constructor() {
        this.fs = require('fs');
        this.ImageGroup = require('./imageGroup.js');
        this.db = require('./dbController.js');

        this.imageFolder = './images/';
        this.waitingTime = 3000;
        this.videoGeneratingTimer;
        //this.backgroundImageGroups = [];
        this.activeImageGroup = false;
    }

    setNewGroupIfNeeded(fileName) {
        if(!this.activeImageGroup) {
            let time = this.getTimeFromName(fileName);
            this.activeImageGroup = new this.ImageGroup(time, this.db);
        }
    }

    setImage(imageName) {
        this.setNewGroupIfNeeded(imageName);
        this.activeImageGroup.addImage(imageName);

        clearTimeout(this.videoGeneratingTimer);
        this.startTimer();
    }

    getTimeFromName(name) {
        let nameArr = name.split(".");
        return nameArr[0];
    }

    /*editImageName(name) {
        return this.imageGroup.generateImageName(name);
    }*/

    /*getFolderName(imageName) {
        let time = this.getTimeFromName(imageName);
        if(!this.activeImageGroup) {
            this.startTimer();
            this.setNewGroupIfNeeded(imageName)

            return time + '/';
        }
        return '';
    }*/

    startTimer() {
        let thisObj = this;
        this.videoGeneratingTimer = setTimeout(
            () => {
                try {
                    clearTimeout(thisObj.videoGeneratingTimer);
                    thisObj.activeImageGroup.createVideo();
                    thisObj.activeImageGroup = false;
                }
                catch(e) {
                    console.log('Problem while killing a timer');
                    console.log(e);
                }
            },
            this.waitingTime
        );
    }

}


module.exports = new ImageHandler();

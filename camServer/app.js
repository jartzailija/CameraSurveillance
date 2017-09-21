const express = require('express');
const multer = require('multer');
const path = require('path');
var imageHandler = require('./imageHandler.js');
var mainController = require('./main.js');
var dbCtrl = require('./dbController.js');

var app = express();
app.use(express.static(path.join(__dirname, 'images')));
app.use(express.static(path.join(__dirname, 'videos')));

//Saves images with their original names (contains a name collision risk)
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, './images/tmp/');
    },
    filename: function (req, file, cb) {
        cb(null, file.originalname);
    }
});

var images = multer({
    storage: storage,
    fileFilter: (req, file, cb) => {
        // accept image only
        if (!file.originalname.match(/\.(jpg|jpeg|png|gif|webp)$/)) {
            return cb(new Error('Only image files are allowed!'), false);
        }
        //Accept a file, if this line is reached
        cb(null, file.originalname);
        imageHandler.setImage(file.originalname);
    }
});

app.post('/upload', images.single('image'), async (req, res) => {
    try {
        res.send({success: true});
    } catch (err) {
        res.sendStatus(400);
    }
});

/*app.get('/check', (req, res) => {
    let newVideos = true;
    let newVideo = __dirname + '/videos/testi.mp4';
    if(newVideos) {
        //res.setHeader('Content-Type', result.mimetype);
        res.sendFile(newVideo);
    }
});*/

/*app.get('/check', (req, res) => {
    //res.send('{"alert": "true", "videoUrl": "'  + req.protocol + '://' + req.get('host') + '/testi.mp4" }');
    var jsonPromise = mainController.getAlertJson(req.protocol + '://' + req.get('host') + '/');
    jsonPromise.then(
        json => {
            res.send(json);
        },
        err => {
            console.log(err);
        }
    );
});*/

app.get('/videos', (req, res) => {
    var searchPromise = dbCtrl.getAllVideos();
    var basicUrl = req.protocol + '://' + req.get('host') + '/videos/';
    mainController.dbVideoResultsToJson(searchPromise, true, basicUrl)
        .then(
            json => {
                res.send(json);
            },
            err => {
                console.log(err);
            }
        );
});

app.get('/videos/unseen', (req, res) => {
    var searchPromise = dbCtrl.getUnseenVideos();
    var basicUrl = req.protocol + '://' + req.get('host') + '/videos/';
    mainController.dbVideoResultsToJson(searchPromise, true, basicUrl)
        .then(
            json => {
                res.send(json);
            },
            err => {
                console.log(err);
            }
        );
});

app.get('/videos/:videoname', (req, res) => {
    //dbCtrl.markVideoAsSeen(req.params.videoname);
    res.sendFile(__dirname + '/videos/' + req.params.videoname);
});

app.get('/images/:videoId/:imagename', (req, res) => {
	var videoId = req.params.videoId;
	var imageName = req.params.imagename;
	res.sendFile(__dirname + '/images/' + videoId + '/' + imageName);
	
});

app.get('/videoid/:videoId', (req, res) => {
	var videoId = req.params.videoId;
	var searchPromise = dbCtrl.getImagesFromGroup(videoId);
	mainController.dbResultsToJson(searchPromise)
		.then(
			json => {
                res.send(json);
            },
            err => {
                console.log(err);
            }
		);
});



/*
app.get('/testikuva', (req, res) => {
    res.sendFile(__dirname + '/testikuva.jpg');
});
app.get('/testikuva2', (req, res) => {
    res.sendFile(__dirname + '/testikuva2.png');
});*/



app.listen(3000, function () {
    console.log('listening on port 3000!');
});

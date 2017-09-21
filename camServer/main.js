class Main {
    constructor() {
        this.dbCtrl = require('./dbController.js');
    }

    getAlertJson(path) {
        var videoPromise = this.dbCtrl.getUnseenVideos();
        return new Promise((resolve, reject) => {
            videoPromise.then(
                videos =>
                {
                    let alertJsonObj = {
                        alert: false,
                        videos: []
                    };
                    if(videos.length > 0) {
                        alertJsonObj.alert = true;

                        videos.map(video => {
                            var videoObj = {
                                url: path + video.name,
                                time: video.time
                            };
                            alertJsonObj.videos.push(videoObj);
                        });
                    }
                    var jsonString = JSON.stringify(alertJsonObj);
                        resolve(jsonString);
                },
                err => {
                    reject(err);
                }
            );
        });
    }
	
	dbResultsToJson(queryPromise) {
		return new Promise((resolve, reject) => {
			queryPromise.then(
				results => {
					let resultJsonObj = {
                        results: results
                    };
					
					var jsonString = JSON.stringify(resultJsonObj);
                    resolve(jsonString);
				},
				err => {
					reject(err);
				}
			);
		});
		
	}

    dbVideoResultsToJson(queryPromise, useAddUrls, basicUrl) {
        return new Promise((resolve, reject) => {
            queryPromise.then(
                results =>
                {
                    if(useAddUrls && basicUrl !== 'undefined') {
                        results = this.addUrls(results, basicUrl);
                    }
					
					results = this.addEpochTime(results);
					//results = this.addId(results);

                    let resultJsonObj = {
                        results: results
                    };

                    var jsonString = JSON.stringify(resultJsonObj);
                    resolve(jsonString);
                },
                err => {
                    reject(err);
                }
            );
        });
    }

    addUrls(rows, basicUrl) {
        var editedRows = [];

        rows.map(row => {
            if(row.hasOwnProperty('name')) {
                var url = basicUrl + row.name;
                row.url = url;
                editedRows.push(row);
            }
        });

        return editedRows;
    }
	
	addEpochTime(results) {
		var editedResults = [];
		
		results.map(result => {
			if(result.hasOwnProperty('time')) {
				var timeStr = result.time;
				var epoch = new Date(timeStr).getTime() + "";
				result.epochTime = epoch;
				editedResults.push(result);
			}
		});
		
		return editedResults;
	}
	
	/*addId(results) {
		var editedResults = [];
		
		results.map(result => {
			if(result.hasOwnProperty('name')) {
				var name = result.name;
				var nameArr = name.split(".");
				var id = nameArr[0];
				result.id = id;
				editedResults.push(result);
			}
		});
		
		return editedResults;
	}*/

}

module.exports = new Main();

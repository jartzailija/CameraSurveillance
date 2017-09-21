//TODO: sanitoi
class DbController {
    constructor() {
        let sqlite3 = require('sqlite3').verbose();
        this.fs = require('fs');
        this.dataBase = new sqlite3.Database('./databases/main.db');
        this.initDataBase();

    }

	//TODO: thumbnail
    //addImage(name, time, group, thumbNail) {
    addImage(name, time, group) {
        var thisObj = this;
        return new Promise(
            (resolve, reject) => {
                var stmt = this.db.prepare(
                    `
                        INSERT INTO images (name, time, groupname, thumbnail) VALUES (?, ?, ?, ?)
                    `
                );
                //stmt.run(name, time, group, thumbNail);
                stmt.run(name, time, group, name);
                stmt.finalize((err) => {
                    if(err) {
                        reject(err);
                    }
                    else {
                        resolve();
                    }
                });
            }
        );
    }
	
	
	markVideoAsSeen(videoName) {
		console.log('set a video as seen');
		var stmt = this.db.prepare(
            `
                UPDATE videos SET isSeen = 1 WHERE name = ?;
            `
        );
        stmt.run(videoName);
        stmt.finalize();
	}

    addVideo(name, id, duration) {
		console.log('add video');
        var stmt = this.db.prepare(
            `
                INSERT INTO videos (name, id, duration) VALUES (?, ?, ?)
            `
        );
        stmt.run(name, id, duration);
        stmt.finalize();
    }

    getUnseenVideos() {
        var query = 'SELECT name, time, id, duration FROM videos WHERE isSeen = 0;';
        return this.selectQuery(query);
    }

    getAllVideos() {
        var query = 'SELECT * FROM videos ORDER BY time DESC;';
        return this.selectQuery(query);
    }

    selectQuery(queryStr, params) {
        var videoGetterPromise = new Promise((resolve, reject) => {

			var items = [];
			if(params === undefined) {
				this.db.all(queryStr, (err, rows) => {
					rows.forEach(row => {
						items.push(row);
					});

					resolve(items);

					if(err) {
						reject(err);
					}
				});
			}
			else {
				this.db.all(queryStr, params, (err, rows) => {
					rows.forEach(row => {
						items.push(row);
					});

					resolve(items);

					if(err) {
						reject(err);
					}
				});
			}
            
        });

        return videoGetterPromise;
    }

    initDataBase() {
        //video is ID -integer, not a foreign key, due to simplicity and
        //makes conditionality possible
        //Timestamp is a string, which makes it's handling easier
        this.dataBase.run(`CREATE TABLE IF NOT EXISTS images (
            name TEXT,
			thumbnail TEXT,
            time TEXT,
            groupname TEXT
        )`);
        this.dataBase.run(`CREATE TABLE IF NOT EXISTS videos (
            name TEXT,
			id TEXT,
			duration TEXT,
            time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            isSeen INTEGER DEFAULT 0
        )`);
    }

    getImagesFromGroup(groupName) {
		var query = 'SELECT name, time, thumbnail FROM images WHERE groupname = ?';
		var paramArr = [groupName];
		
		return this.selectQuery(query, paramArr);
        /*this.dataBase.all(
            `SELECT name, time, thumbnail FROM images WHERE groupname = ?`,
            groupName,  //TODO: sanitointi
            (err, rows) => {
                if(err) {
                    console.log('Error getting unhandled images.');
                    console.log(err);
                }
                else if(rows){
                    console.log('rows');
                    console.log(rows);
                }
                else {
                    console.log('No unhandled images');
                }
            }
        );*/
    }

    setVideoAsSeen(videoName) {
        //this.db()
    }

    get db() {
        return this.dataBase;
    }

    set db(db) {
        this.dataBase = db;
    }

    close() {
        this.db.close();
    }

}

module.exports = new DbController();

# RpiMotionDetection

## What does this code do
This is a part of my soon ready project. Here camera shoots single 
images and compares them to running average. If it notices motion, it 
saves a photo.

## More info

This code runs with Raspberry Pi (tested with Rpi3) and uses it's camera 
module. Works when Debian is installed to Rpi. Needs to run 
[OpenCV](https://github.com/opencv/opencv.git) -computer vision library, 
and [Raspicam](https://github.com/cedricve/raspicam) -library. Algorithm 
is copied from 
[pyimagesearch.com](http://www.pyimagesearch.com/2015/06/01/home-surveillance-and-motion-detection-with-the-raspberry-pi-python-and-opencv/) 
and edited to work with C++.

## Installing
Please notice, that OpenCV compilation takes 1-2 h even with Rpi3. ``` 
mkdir motion_detection cd motion_detection
#install dependencies ..................................................
mkdir libs cd libs
#opencv installation ..................................................
git clone https://github.com/opencv/opencv.git cd opencv mkdir release 
cd release cmake -D CMAKE_BUILD_TYPE=RELEASE -D 
CMAKE_INSTALL_PREFIX=/usr/local ..
#this takes ~1-2h
make sudo make install cd ../..
#raspicam installation 
#..................................................
mkdir raspicam cd raspicam git clone 
https://github.com/cedricve/raspicam . mkdir build cd build cmake .. 
make sudo make install sudo ldconfig
#compile this code ....................................
cd ../.. git clone https://github.com/jartzailija/RpiMotionDetection.git 
mv -v ./RpiMotionDetection/* ./ rm -rf ./RpiMotionDetection cmake . make
#run ....................................
./motion_detection ```

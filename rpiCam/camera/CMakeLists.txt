cmake_minimum_required (VERSION 3.1) 
set (CMAKE_CXX_STANDARD 11)
project (motion_detection)
set(CMAKE_MODULE_PATH "/usr/local/lib/cmake/${CMAKE_MODULE_PATH}") 
find_package(raspicam REQUIRED)
find_package(OpenCV REQUIRED)
IF  ( OpenCV_FOUND AND raspicam_CV_FOUND)
MESSAGE(STATUS "COMPILING OPENCV TESTS")
add_executable (motion_detection camera.cpp)  
target_link_libraries (motion_detection ${raspicam_CV_LIBS})
ELSE()
MESSAGE(FATAL_ERROR "OPENCV NOT FOUND IN YOUR SYSTEM")
ENDIF()
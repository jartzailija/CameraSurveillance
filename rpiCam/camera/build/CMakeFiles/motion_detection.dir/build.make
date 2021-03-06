# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.6

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/pi/projects/camera_surveillance/camera

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/pi/projects/camera_surveillance/camera/build

# Include any dependencies generated for this target.
include CMakeFiles/motion_detection.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/motion_detection.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/motion_detection.dir/flags.make

CMakeFiles/motion_detection.dir/camera.cpp.o: CMakeFiles/motion_detection.dir/flags.make
CMakeFiles/motion_detection.dir/camera.cpp.o: ../camera.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pi/projects/camera_surveillance/camera/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/motion_detection.dir/camera.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/motion_detection.dir/camera.cpp.o -c /home/pi/projects/camera_surveillance/camera/camera.cpp

CMakeFiles/motion_detection.dir/camera.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/motion_detection.dir/camera.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/pi/projects/camera_surveillance/camera/camera.cpp > CMakeFiles/motion_detection.dir/camera.cpp.i

CMakeFiles/motion_detection.dir/camera.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/motion_detection.dir/camera.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/pi/projects/camera_surveillance/camera/camera.cpp -o CMakeFiles/motion_detection.dir/camera.cpp.s

CMakeFiles/motion_detection.dir/camera.cpp.o.requires:

.PHONY : CMakeFiles/motion_detection.dir/camera.cpp.o.requires

CMakeFiles/motion_detection.dir/camera.cpp.o.provides: CMakeFiles/motion_detection.dir/camera.cpp.o.requires
	$(MAKE) -f CMakeFiles/motion_detection.dir/build.make CMakeFiles/motion_detection.dir/camera.cpp.o.provides.build
.PHONY : CMakeFiles/motion_detection.dir/camera.cpp.o.provides

CMakeFiles/motion_detection.dir/camera.cpp.o.provides.build: CMakeFiles/motion_detection.dir/camera.cpp.o


# Object files for target motion_detection
motion_detection_OBJECTS = \
"CMakeFiles/motion_detection.dir/camera.cpp.o"

# External object files for target motion_detection
motion_detection_EXTERNAL_OBJECTS =

motion_detection: CMakeFiles/motion_detection.dir/camera.cpp.o
motion_detection: CMakeFiles/motion_detection.dir/build.make
motion_detection: /opt/vc/lib/libmmal_core.so
motion_detection: /opt/vc/lib/libmmal_util.so
motion_detection: /opt/vc/lib/libmmal.so
motion_detection: /usr/local/lib/libopencv_ml.so.3.2.0
motion_detection: /usr/local/lib/libopencv_objdetect.so.3.2.0
motion_detection: /usr/local/lib/libopencv_shape.so.3.2.0
motion_detection: /usr/local/lib/libopencv_stitching.so.3.2.0
motion_detection: /usr/local/lib/libopencv_superres.so.3.2.0
motion_detection: /usr/local/lib/libopencv_videostab.so.3.2.0
motion_detection: /usr/local/lib/libopencv_calib3d.so.3.2.0
motion_detection: /usr/local/lib/libopencv_features2d.so.3.2.0
motion_detection: /usr/local/lib/libopencv_flann.so.3.2.0
motion_detection: /usr/local/lib/libopencv_highgui.so.3.2.0
motion_detection: /usr/local/lib/libopencv_photo.so.3.2.0
motion_detection: /usr/local/lib/libopencv_video.so.3.2.0
motion_detection: /usr/local/lib/libopencv_videoio.so.3.2.0
motion_detection: /usr/local/lib/libopencv_imgcodecs.so.3.2.0
motion_detection: /usr/local/lib/libopencv_imgproc.so.3.2.0
motion_detection: /usr/local/lib/libopencv_core.so.3.2.0
motion_detection: CMakeFiles/motion_detection.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/pi/projects/camera_surveillance/camera/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable motion_detection"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/motion_detection.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/motion_detection.dir/build: motion_detection

.PHONY : CMakeFiles/motion_detection.dir/build

CMakeFiles/motion_detection.dir/requires: CMakeFiles/motion_detection.dir/camera.cpp.o.requires

.PHONY : CMakeFiles/motion_detection.dir/requires

CMakeFiles/motion_detection.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/motion_detection.dir/cmake_clean.cmake
.PHONY : CMakeFiles/motion_detection.dir/clean

CMakeFiles/motion_detection.dir/depend:
	cd /home/pi/projects/camera_surveillance/camera/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/pi/projects/camera_surveillance/camera /home/pi/projects/camera_surveillance/camera /home/pi/projects/camera_surveillance/camera/build /home/pi/projects/camera_surveillance/camera/build /home/pi/projects/camera_surveillance/camera/build/CMakeFiles/motion_detection.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/motion_detection.dir/depend


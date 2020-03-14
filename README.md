# FFMPEG-WITH-UI

**Simple tool that converts all files in the specified input directory to specified format using FFMPEG CLI. The converted files are of their orginal names (except the file extension), and are placed inside the specified output directory. This program comes with a UI, it's for my girlfriend :D.**

Essentially, it is doing following command for every file in the selected input directory:

    e.g.,

    ffmpeg -y -i fileToBeConverted.mov -c copy convertedFile.mp4

    or
    
    ffmpeg -y -i fileToBeConverted.mov convertedFile.mp4

**This tool works on Linux and Windows OS. Notice that this program has no control over FFMPEG, if you decide to kill this Java program before the FFMPEG stops, it will continue to run until it has done its job.**

### Prerequisite

- Java 11

## How to use it

- Install FFMPEG library
- Download the executable in RELEASE
- Run it as follows:

e.g.,
    
    java -jar ffmpeg-with-ui-1.0.1.jar

## Demo

<img src="https://user-images.githubusercontent.com/45169791/76688205-ae744c80-6622-11ea-9027-c30bab145b55.gif">

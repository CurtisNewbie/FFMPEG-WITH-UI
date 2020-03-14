# FFMPEG-WITH-UI

**Simple tool that converts all files in the specified input directory to specified format using FFMPEG CLI. The converted files are of their orginal names (except the file extension), and are placed inside the specified output directory. This program comes with a UI, it's for my girlfriend :D.**

Essentially, it is doing following command for every file in the selected input directory:

    ffmpeg -y -i fileToBeConverted.mov convertedFile.mp4

**This tool only works on Linux, as I am using BASH.**

## How to use it

- Install FFMPEG library
- Download the executable in RELEASE
- Run it as follows:

e.g.,
    
    java -jar ffmpeg-with-ui-1.0-SNAPSHOT.jar

## Demo

<img src="https://user-images.githubusercontent.com/45169791/76686947-83d0c680-6617-11ea-8106-560c615d9dcf.png">

# FFMPEG-WITH-UI

**Simple tool that converts all files in a directory to specified format using FFMPEG CLI. The converted files are of their orginal names. This program comes with a UI, it's for my girlfriend.**

Essentially, it is doing following command for every file in the directory:

    ffmpeg -i fileToBeConverted.mov -vcodec copy -acodec copy convertedFile.mp4

**This tool only works on Linux, as I am using BASH.**

## How to use it

- Install FFMPEG library
- Compile this tool using javac

e.g.,

    javac FfmpegConvert.java

- Run it, and provides three arguments:
  - [0] input directory, which contains all the files that you want to convert
  - [1] output directory, where the files are created
  - [2] format, e.g., "mp4"

e.g.,

    java FfmpegConvert /home/yongjie/in /home/yongjie/tempOut mp4

Then all the files in _/home/yongjie/in_ are converted to mp4 format and generated in _/home/yongjie/tempOut_.

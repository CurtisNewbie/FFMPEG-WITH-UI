package com.curtisnewbie.util;

import java.io.File;
import java.util.*;

import com.curtisnewbie.controller.Loggable;

/**
 * ------------------------------------
 * 
 * Author: Yongjie Zhuang
 * 
 * ------------------------------------
 * <p>
 * Class that contains static methods to convert media files using FFMPEG
 * </p>
 */
public class FfmpegConvert {

    /**
     * Convert all media files in {@code inDir} to a specific {@code format}, and
     * output them to {@code outDir}
     * 
     * @param inDir  input directory
     * @param outDir output directory
     * @param format media format, e.g., "mp4"
     * @param logger a Loggable that is used to log any successful or failure
     *               message
     */
    public static void convert(String inDir, String outDir, String format, Loggable logger) {
        var inDirFile = new File(inDir);
        if (!inDirFile.exists() && !inDirFile.isDirectory()) {
            logger.error("Input Directory does not exist or it is not a directory");
            return;
        }
        var outDirFile = new File(outDir);
        if (!outDirFile.exists() && !outDirFile.isDirectory()) {
            logger.error("Output Directory does not exists or it is not a directory");
            return;
        }

        List<String> inFiles = new ArrayList<>();
        for (File f : inDirFile.listFiles())
            inFiles.add(f.getAbsolutePath());
        try {
            convert(inFiles, outDir, format, logger);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    static void convert(List<String> inFiles, String outDir, String format, Loggable logger) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        logger.appendResult("Start Processing - " + new Date().toString());
        for (String f : inFiles) {
            String cmd = "ffmpeg -i " + f + " -vcodec copy -acodec copy " + outDir + "/" + fileName(f) + "." + format;
            Process p = runtime.exec(new String[] { "bash", "-c", cmd });
            if (p.waitFor() == 0) {
                logger.appendResult("[Success]: " + f);
            } else {
                logger.appendResult("[Failed]: " + f);
            }
        }
        logger.appendResult("Finish Processing - " + new Date().toString());
    }

    static String fileName(String path) {
        int start = -1;
        int end = -1;
        for (int i = path.length() - 1; i > 0; i--) {
            if (path.charAt(i) == '.' && end < 0) {
                end = i;
            } else if (path.charAt(i) == '/') {
                start = i + 1;
                break;
            }
        }
        if (start < 0 || end < 0)
            throw new IllegalArgumentException("File name cannot be parsed");

        return path.substring(start, end);
    }

    static void displayLog(List<String> log) {
        for (String s : log) {
            System.out.println(s);
        }
    }
}
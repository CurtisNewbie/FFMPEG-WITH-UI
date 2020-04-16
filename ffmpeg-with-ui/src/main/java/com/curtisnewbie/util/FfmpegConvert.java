package com.curtisnewbie.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    static char slash = isWinOS() ? '\\' : '/';
    static String cli_name = isWinOS() ? "cmd.exe" : "bash";
    static String parseAsString = isWinOS() ? "/c" : "-c";

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
            logger.error("Input directory does not exist or it is not a directory");
            return;
        }
        var outDirFile = new File(outDir);
        if (!outDirFile.exists() && !outDirFile.isDirectory()) {
            logger.error("Output directory does not exist or it is not a directory");
            return;
        }

        List<File> inFiles = new ArrayList<>();
        for (File f : inDirFile.listFiles())
            if (f.isFile())
                inFiles.add(f);
        try {
            logger.info("Processing started, this may take a while, please wait for notification.");
            convert(inFiles, outDir, format);
            logger.info("Done!");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    static void convert(List<File> files, String outDir, String format) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Start Processing - " + new Date().toString());
        for (File f : files) {
            String outputFilename = outDir + slash + f.getName() + "." + format;

            // codec copy doesn't work for all media files
            String cmd = String.format("ffmpeg -y -i '%s' -c copy '%s'", f.getAbsolutePath(), outputFilename);
            Process p = runtime.exec(new String[] { cli_name, parseAsString, cmd });
            displayProcessOutput(p);
            if (p.waitFor() == 0) {
                System.out.println("[Success]: " + outputFilename);
            } else {
                // try again without codec copy
                System.out.println("[Failed]: " + outputFilename);
                System.out.println("[Try again without codec copy]: " + outputFilename);
                cmd = "ffmpeg -y -i " + f + " " + outputFilename;
                if (runtime.exec(new String[] { cli_name, parseAsString, cmd }).waitFor() == 0)
                    System.out.println("[Success]: " + outputFilename);
                else
                    System.out.println("[Failed]: " + outputFilename);
            }
        }
        System.out.println("Finish Processing - " + new Date().toString());
    }

    static void displayLog(List<String> log) {
        for (String s : log) {
            System.out.println(s);
        }
    }

    private static boolean isWinOS() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    static void displayProcessOutput(Process process) {
        displayStream(process.getErrorStream());
        displayStream(process.getInputStream());
    }

    static void displayStream(InputStream in) {
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in));) {
                StringBuilder sb = new StringBuilder();
                int count = 6;
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                    if (--count <= 0) {
                        count = 6;
                        System.out.print(sb.toString());
                        sb.setLength(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
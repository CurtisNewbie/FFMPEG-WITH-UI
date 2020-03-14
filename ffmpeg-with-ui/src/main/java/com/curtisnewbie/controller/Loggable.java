package com.curtisnewbie.controller;

/**
 * ------------------------------------
 * 
 * Author: Yongjie Zhuang
 * 
 * ------------------------------------
 */
public interface Loggable {

    void appendResult(String msg);

    void error(String msg);

    void info(String msg);
}
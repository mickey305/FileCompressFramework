/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 K.Misaki
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mickey305.util;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
    protected static final String ANSI_RESET = "\u001b[0m";

    protected static final String ANSI_FONT_BLACK = "\u001b[30m";
    protected static final String ANSI_FONT_RED = "\u001b[31m";
    protected static final String ANSI_FONT_GREEN = "\u001b[32m";
    protected static final String ANSI_FONT_YELLOW = "\u001b[33m";
    protected static final String ANSI_FONT_BLUE = "\u001b[34m";
    protected static final String ANSI_FONT_PURPLE = "\u001b[35m";
    protected static final String ANSI_FONT_CYAN = "\u001b[36m";
    protected static final String ANSI_FONT_WHITE = "\u001b[37m";

    protected static final String ANSI_BKG_BLACK = "\u001b[40m";
    protected static final String ANSI_BKG_RED = "\u001b[41m";
    protected static final String ANSI_BKG_GREEN = "\u001b[42m";
    protected static final String ANSI_BKG_YELLOW = "\u001b[43m";
    protected static final String ANSI_BKG_BLUE = "\u001b[44m";
    protected static final String ANSI_BKG_PURPLE = "\u001b[45m";
    protected static final String ANSI_BKG_CYAN = "\u001b[46m";
    protected static final String ANSI_BKG_WHITE = "\u001b[47m";

    private Log() {}

    /**
     *
     * @return
     */
    @Nonnull
    private static String createHeader() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }

    /**
     *
     * @param clazz
     * @param methodName
     * @param msg
     */
    public synchronized static void d(Class clazz, String methodName, String msg) {
        if (Config.DEBUG_MODE)
            i(ANSI_BKG_YELLOW + ANSI_FONT_BLACK + "[" + clazz.getSimpleName() + "#" + methodName + "] " + ANSI_RESET + msg);
    }

    /**
     *
     * @param msg
     */
    public synchronized static void i(String msg) {
        System.out.println(ANSI_FONT_BLUE + createHeader() + " I/D " + ANSI_RESET + msg);
    }

    /**
     *
     * @param msg
     */
    public synchronized static void e(String msg) {
        System.err.println(createHeader() + "  E  " + msg);
    }
}

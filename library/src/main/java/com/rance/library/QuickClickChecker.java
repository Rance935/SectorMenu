package com.rance.library;

/**
 * 作者：Rance on 2016/11/10 16:41
 * 邮箱：rance935@163.com
 */
public class QuickClickChecker {
    private int threshold;
    private long lastClickTime = 0;

    public QuickClickChecker(int threshold) {
        this.threshold = threshold;
    }

    public boolean isQuick() {
        boolean isQuick = System.currentTimeMillis() - lastClickTime <= threshold;
        lastClickTime = System.currentTimeMillis();
        return isQuick;
    }
}

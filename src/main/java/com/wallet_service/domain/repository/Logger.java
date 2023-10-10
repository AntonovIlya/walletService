package com.wallet_service.domain.repository;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A class that implements logging.
 */
public class Logger implements Log {

    /**
     * Data structure for storing history.
     */
    private final Deque<String> history;

    /**
     * Time format for recording.
     */
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    /**
     * Constructs a queue implementation.
     */
    public Logger() {
        history = new ArrayDeque<>();
    }

    /**
     * Adds an entry to history with a timestamp.
     *
     * @param message Message text for recording
     */
    @Override
    public void log(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(simpleDateFormat.format(System.currentTimeMillis()));
        stringBuilder.append(" - ").append(message);
        history.addFirst(stringBuilder.toString());
    }

    /**
     * Returns the history in an easy-to-read format.
     */
    @Override
    public String getHistory() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s: history) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }
}

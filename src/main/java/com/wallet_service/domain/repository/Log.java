package com.wallet_service.domain.repository;

/**
 * An interface representing the logging logic.
 */
public interface Log {

    /**
     * Logging a message.
     *
     * @param message message
     */
    void log(String message);

    /**
     * Returns the logging history.
     */
    String getHistory();
}

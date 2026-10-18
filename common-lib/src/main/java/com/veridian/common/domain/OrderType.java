package com.veridian.common.domain;

/**
 * Specifies the execution instruction and price boundary constraints for an order.
 */
public enum OrderType {
    /**
     * A limit order executes at a specified price or better.
     * Rests in the order book if not immediately matched.
     */
    LIMIT,

    /**
     * A market order executes immediately against the best available resting orders in the book.
     * Takes liquidity without price protection unless slippage thresholds are configured.
     */
    MARKET,

    /**
     * A stop-loss order converts into an aggressive market order once a designated trigger price is breached.
     */
    STOP_LOSS,

    /**
     * A stop-limit order converts into a resting limit order once a designated trigger price is breached.
     */
    STOP_LIMIT
}

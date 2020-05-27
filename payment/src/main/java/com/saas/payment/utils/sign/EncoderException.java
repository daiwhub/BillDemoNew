package com.saas.payment.utils.sign;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-26  19:42
 *
 *         ***************************
 */
public class EncoderException extends Exception {
    private static final long serialVersionUID = 1L;

    public EncoderException() {
    }

    public EncoderException(String message) {
        super(message);
    }

    public EncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncoderException(Throwable cause) {
        super(cause);
    }
}


package com.saas.payment.utils.sign;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-26  19:41
 *
 *         ***************************
 */
public class DecoderException extends Exception {
    private static final long serialVersionUID = 1L;

    public DecoderException() {
    }

    public DecoderException(String message) {
        super(message);
    }

    public DecoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecoderException(Throwable cause) {
        super(cause);
    }
}

package com.saas.payment.utils.sign;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-26  19:40
 *
 *         ***************************
 */
public interface BinaryEncoder extends Encoder {
    byte[] encode(byte[] var1) throws EncoderException;
}
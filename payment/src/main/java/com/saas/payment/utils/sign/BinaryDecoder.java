package com.saas.payment.utils.sign;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-26  19:39
 *
 *         ***************************
 */
public interface BinaryDecoder extends Decoder {
    byte[] decode(byte[] var1) throws DecoderException;
}
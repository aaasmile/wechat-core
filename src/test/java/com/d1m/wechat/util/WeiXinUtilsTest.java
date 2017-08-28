package com.d1m.wechat.util;

import org.junit.Test;

import static com.d1m.wechat.util.WeiXinUtils.*;

/**
 * WeiXinUtilsTest
 *
 * @author f0rb on 2017-02-28.
 */
public class WeiXinUtilsTest {
    @Test
    public void encryptMsgTest() throws Exception {
        String plaintext = "<xml><ToUserName><![CDATA[gh_0759935ccdc9]]></ToUserName>\n" +
                "<FromUserName><![CDATA[owmquwiAz31Gk0RCqEkwvmM7QdAE]]></FromUserName>\n" +
                "<CreateTime>1486543327</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[白日依山尽黄河入海流]]></Content>\n" +
                "<MsgId>6384654973968016388</MsgId>\n" +
                "</xml>";

        String encodingAesKey = "LNAeUmUadfJiLQrHLvqI3APrtJcFDuQU9qpyr9gmkGL", token = "f0rb", appid = "wx66e2515c5b45df61";

        String encrypt = encryptMsg(encodingAesKey, plaintext, appid, token);

        //String encrypt = "G2suvwolBr980mWdJp7V4uywsXYWtc3AiehkT3VEDPBPgfAW/yVl+3EZbIa+1+HK4WEYn/YiLps2qKdi3/FQVo/TsmEx7Kl4VcuVVnKeTjOlkQjh1hLucIo+wZtS0/nKLNy8rhEFPZN0r1BQgkARJIPfLK3ptpNUTPcKqQ66Rc/0GntsTbAaK7o3PJnRgU+t1xSx0u+MoEY9FKKZn6MAc68arqHYQR0BvP13mtLd1Vy85hwb17KOTM12U4I7RS4XZc0FtWawsOlouA4tos8bsz5Q51GixVKHgw6PrclsNVBhl5fCDRWPCyW99ThBZy9R7ykGvQsrLuCbAjtYTsVsf0kTLvUYvAdrizDAH5sLrOLMou6YDiRiStdgE4zG9zdfUYNyjqoIsLAvbSUrB2QAc3fFwNSdPmSfpOCzBQ18EZc=";

        String decrypt = decryptMsg(encodingAesKey, encrypt, appid);
        System.out.println(decrypt);

        System.out.println(encrypt);

        System.out.println(encryptMsg(encodingAesKey, plaintext, appid, token));
        //System.out.println(Arrays.toString(intToBytes(23245)));
        //System.out.println(ByteBuffer.wrap(intToBytes(23245)).getInt());
        //System.out.println(bytesToInt(intToBytes(23245)));
    }


}
package com.d1m.wechat.common;

/**
 * Constant
 *
 * @author f0rb on 2017-02-15.
 */
public interface Constant {

    byte STATUS_INVALID = 0;//状态:无效

    byte STATUS_VALID = 1;//状态:有效

    String CUSTOMER_TOKEN = "customerToken";

    String AC_KEY = "ackey";

    interface Cache {
        String storeCache = "storeCache";
        String accessedMember = "accessedMember";
        String customerServiceConfig = "customerServiceConfig";

        String memberSession = "member-session";
        String tokenMember = "token-member";
        String memberToken = "member-token";
        /**
         * 第三方接口缓存名称
         */
        String THIRD_PARTY_INTERFACE = "third_party_interface";
    }
}

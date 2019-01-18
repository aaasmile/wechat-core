import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.domain.web.BaseResponse;

/**
 * @program: wechat-core
 * @Date: 2018/11/21 15:40
 * @Author: Liu weilin
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String[] split = ",,".split(",");

        System.out.println(split.length);
        final String success = JSONObject.toJSONString(BaseResponse.builder().msg("success").resultCode(1).build());
        System.out.println(success);
    }

}

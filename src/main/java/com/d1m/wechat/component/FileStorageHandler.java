package com.d1m.wechat.component;

/**
 * FileStorageHandler
 *
 * @author f0rb on 2017-04-13.
 */
public interface FileStorageHandler {

    /**
     * 从url下载文件并存储到FastDFS上, 并返回FastDFS的访问地址
     * 如果文件下载失败, 则直接返回传入的url
     *
     * @param url 文件链接
     * @return FastDFS的存储路径
     */
    String saveRemoteFile(Integer wechatId, String url);

}

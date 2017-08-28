package com.d1m.wechat.service;

import com.d1m.wechat.migrate.MigrateResult;
import com.d1m.wechat.model.User;

/**
 * 数据同步服务的接口
 *
 * @author f0rb on 2016-12-01.
 */
public interface MigrateService {

    boolean isKnownMaterialType(String materialType);

    /**
     * 拉取指定类型的素材, 可以选择是否更新
     *
     * @param materialType 素材类型
     * @param canUpdate    是否执行更新操作, 如果不允许更新，则只做新增操作
     * @param userId       操作用户id
     * @param wechatId     操作用户绑定的微信
     * @return 返回处理失败的WxMaterial
     */
    MigrateResult migrateMaterial(String materialType, Boolean canUpdate, Integer userId, Integer wechatId);

    /**
     * 初始化微信菜单
     * <p>
     * 提供是否更新参数，如果不允许更新，则系统内菜单为空的时候才抓取
     * 如果允许更新，则清空系统内菜单，用抓取的微信菜单代替
     *
     * @param canUpdate 是否执行更新操作, 如果不允许更新，则只做新增操作
     * @param user      操作用户
     * @param wechatId  操作用户绑定的微信
     * @return 返回处理失败的WxMaterial
     */
    MigrateResult migrateMenu(Boolean canUpdate, User user, Integer wechatId);

    MigrateResult migrateOpenId(Integer wechatId, String nextOpenId);

    MigrateResult migrateUser(Integer wechatId);

}

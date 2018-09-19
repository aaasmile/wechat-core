package com.d1m.wechat.dto;

import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.MemberTag;

import java.io.Serializable;
import java.util.List;

/**
 * @program: wechat-core
 * @Date: 2018/9/17 16:23
 * @Author: Liu weilin
 * @Description:
 */
public class MemberMemberTagDTO implements Serializable {
    private static final long serialVersionUID = 180230862957223442L;

    private List<MemberTag> memberTags;
    private List<MemberMemberTag> memberTagList;

    public List<MemberTag> getMemberTags() {
        return memberTags;
    }

    public void setMemberTags(List<MemberTag> memberTags) {
        this.memberTags = memberTags;
    }

    public List<MemberMemberTag> getMemberTagList() {
        return memberTagList;
    }

    public void setMemberTagList(List<MemberMemberTag> memberTagList) {
        this.memberTagList = memberTagList;
    }
}

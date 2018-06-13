package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.Member;
import com.bird.framework.xsy.mall.entity.PasswordRecord;
import com.bird.framework.xsy.mall.mapper.MemberMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    @Qualifier("mallSequenceService")
    private SequenceService sequenceService;

    @Autowired
    private PasswordRecordService passwordRecordService;

    public Member selectById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    public Member selectByUsername(String username) {
        return memberMapper.selectByUsername(username);
    }

    public Page<Member> page(String role, String gender, String mobile, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Member> page = (Page<Member>) memberMapper.findAll(role, gender, mobile);
        return page;
    }

    @Transactional
    public void save(Member member) {
        if (member.getId() != null) {
            // update
            memberMapper.updateByPrimaryKeySelective(member);
        } else {
            // insert
            String username = sequenceService.generate("mall-member", "%08d");
            member.setUsername(username);
            memberMapper.insert(member);
            PasswordRecord passwordRecord = new PasswordRecord();
            passwordRecord.setMemberId(member.getId());
            passwordRecord.setCreated(new java.util.Date());
            passwordRecordService.save(passwordRecord);
        }
    }

    public void delete(Long id) {
        memberMapper.deleteByPrimaryKey(id);
    }
}

package com.bird.framework.xsy.mall.jwt;

import com.bird.framework.xsy.mall.entity.Member;
import com.bird.framework.xsy.mall.enums.RoleEnum;
import com.bird.framework.xsy.mall.service.MemberService;
import com.bird.framework.xsy.mall.service.PasswordRecordService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordRecordService passwordRecordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.selectByUsername(username);

        if (member == null) {
            throw new UsernameNotFoundException(String.format("No member found with username '%s'.", username));
        } else {

            return JwtUserFactory.create(member, passwordRecordService.selectLastByUsername(username), Lists.newArrayList(RoleEnum.getName(member.getRole())));
        }
    }
}

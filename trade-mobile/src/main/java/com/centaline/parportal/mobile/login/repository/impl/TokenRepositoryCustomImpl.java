package com.centaline.parportal.mobile.login.repository.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.centaline.parportal.mobile.login.entity.TSmMobileToken;
import com.centaline.parportal.mobile.login.repository.TokenRepositoryCustom;
import com.centaline.trans.common.vo.TokenVo;

@Repository
public class TokenRepositoryCustomImpl implements TokenRepositoryCustom {
    final String               namespace = "com.centaline.parportal.mobile.login.repository.TSmMobileTokenMapper";
    @Autowired
    private SqlSessionTemplate session;

    @Override
    public void deleteToken(String token) {
        session.delete(namespace + ".delete", token);
    }

    @Override
    public int insterToken(TokenVo token) {
        return session.insert(namespace + ".insert", token);
    }

    @Override
    public int updateToken(TokenVo token) {
        return session.update(namespace + ".update", token);
    }

    @Override
    public TSmMobileToken selectToken(String token) {
        return (TSmMobileToken) session.selectOne(namespace + ".selectByPrimaryKey", token);
    }

    @Override
    public int updateOldToken(String token) {
        return session.update(namespace + ".updateOldToken", token);
    }

}

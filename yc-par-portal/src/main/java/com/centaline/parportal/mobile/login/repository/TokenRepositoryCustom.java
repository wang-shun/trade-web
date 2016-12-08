package com.centaline.parportal.mobile.login.repository;

import com.centaline.parportal.mobile.login.entity.TSmMobileToken;
import com.centaline.parportal.mobile.login.vo.TokenVo;

public interface TokenRepositoryCustom {
    void deleteToken(String token);

    int insterToken(TokenVo token);

    int updateToken(TokenVo token);

    TSmMobileToken selectToken(String token);

    public int updateOldToken(String token);
}

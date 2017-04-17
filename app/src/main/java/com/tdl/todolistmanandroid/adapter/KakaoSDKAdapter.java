package com.tdl.todolistmanandroid.adapter;

import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.tdl.todolistmanandroid.ToDoListManAndroid;

/**
 * Created by songm on 2017-04-17.
 */

public class KakaoSDKAdapter extends KakaoAdapter {
    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }
    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
        @Override
        public Context getApplicationContext() {
            return ToDoListManAndroid.getGlobalApplicationContext();
        }
    };
    }
}

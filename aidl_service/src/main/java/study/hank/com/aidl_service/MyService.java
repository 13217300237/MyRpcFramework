package study.hank.com.aidl_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import study.hank.com.aidl.IUserInfo;
import study.hank.com.aidl.bean.UserInfoBean;

public class MyService extends Service {

    ConcurrentMap<String, UserInfoBean> map;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put("name" + i, new UserInfoBean("name" + i, "accountNo" + i, i));
        }
        return new IUserInfo.Stub() {//数据接收器 Stub
            @Override
            public UserInfoBean getInfo(String name) {
                return map.get(name);
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyService", "onCreate: success");
    }
}

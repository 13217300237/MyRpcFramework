package study.hank.com.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import study.hank.com.aidl.IUserInfo;
import study.hank.com.aidl.bean.UserInfoBean;

public class ClientActivity extends AppCompatActivity {

    private TextView resultView;
    private String TAG = "clientLog";

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        resultView = findViewById(R.id.resultView);
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        findViewById(R.id.disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    unbindService(connection);
                    resultView.setText("尝试释放");
                } catch (IllegalArgumentException e) {
                    resultView.setText("已经释放了");
                }
            }
        });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iUserInfo != null) {
                    try {
                        ((Button) v).setText("查找name为:name" + ((i++) + 1) + "的UserInfoBean");
                        UserInfoBean bean = iUserInfo.getInfo("name" + i);
                        if (bean != null)
                            resultView.setText(bean.toString());
                        else
                            resultView.setText("没找到呀");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    resultView.setText("没有连接上service");
                }
            }
        });
    }

    //作为IPC的客户端，我们需要 建立起和Service的连接
    private IUserInfo iUserInfo;//操作句柄，可以通过它向service发送数据

    private void bindService() {
        if (iUserInfo == null) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "study.hank.com.aidl_service",
                    "study.hank.com.aidl_service.MyService"));
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            resultView.setText("尝试连接");
        } else {
            resultView.setText("已经连接上service" + iUserInfo);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iUserInfo = IUserInfo.Stub.asInterface(service);
            resultView.setText("连接成功");
            Log.d(TAG, "connection:" + "连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iUserInfo = null;
            resultView.setText("连接 已经断开");
            Log.d(TAG, "connection:" + "已经断开");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}

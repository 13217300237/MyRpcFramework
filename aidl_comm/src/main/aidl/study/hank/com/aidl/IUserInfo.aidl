// IUserInfo.aidl
package study.hank.com.aidl;

// Declare any non-default types here with import statements
import study.hank.com.aidl.bean.UserInfoBean;

interface IUserInfo {
    UserInfoBean getInfo(in String name);
}

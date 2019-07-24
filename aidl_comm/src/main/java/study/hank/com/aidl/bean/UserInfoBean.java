package study.hank.com.aidl.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBean implements Parcelable {
    @Override
    public String toString() {
        return "UserInfoBean{" +
                "name='" + name + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", age=" + age +
                '}';
    }

    //属性
    private String name;
    private String accountNo;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserInfoBean(String name, String accountNo, int age) {
        this.name = name;
        this.accountNo = accountNo;
        this.age = age;
    }

    /**
     * 序列化转bean时
     *
     * @param in
     */
    protected UserInfoBean(Parcel in) {
        name = in.readString();
        accountNo = in.readString();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(accountNo);
        dest.writeInt(age);
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}

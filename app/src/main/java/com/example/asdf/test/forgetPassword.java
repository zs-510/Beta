package com.example.asdf.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.AccessibleObject;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by Useradmin on 2016/11/5.
 */
public class forgetPassword extends Activity{
    private ImageView leftDrawer;//回退
    private Button getNumber;
    private EditText phoneNumber;
    private EditText checkNunber;
    private Button check;
    private String userPhone;
    private static final int CODE_ING = 1;                  //已发送，倒计时
    private static final int CODE_REPEAT = 2;               //重新发送
    private static final int SMSDDK_HANDLER = 3;            //短信回调
    private int TIME = 60;//倒计时60s
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        getNumber= (Button) findViewById(R.id.getnumber);
        phoneNumber= (EditText) findViewById(R.id.phoneNumber);
        checkNunber= (EditText) findViewById(R.id.checkNumber);
        leftDrawer= (ImageView) findViewById(R.id.leftdrawer);
        check= (Button) findViewById(R.id.check);
        initSDK();//短信初始化
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword.this.finish();
            }
        });
        getNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhone = phoneNumber.getText().toString();
                new AlertDialog.Builder(forgetPassword.this)
                        .setTitle("发送短信")
                        .setMessage("我们将把验证码发送到以下号码:\n" + "+86:" + userPhone)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SMSSDK.getVerificationCode("86", userPhone);
                                getNumber.setClickable(false);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i = 60; i > 0; i--) {
                                            handler.sendEmptyMessage(CODE_ING);
                                            if (i <= 0) {
                                                break;
                                            }
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        handler.sendEmptyMessage(CODE_REPEAT);
                                    }
                                }).start();
                            }
                        })
                        .create()
                        .show();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode("86", userPhone, checkNunber.getText().toString());//对验证码进行验证->回调函数
            }
        });
    }
    //初始化SMSSDK
    private void initSDK()
    {
        SMSSDK.initSDK(forgetPassword.this, "18e3768f516a3", "e05f202526cd41dcf17d2498d6a7aeeb");
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = SMSDDK_HANDLER;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CODE_ING://已发送,倒计时
                    getNumber.setText("重新发送(" + --TIME + "s)");
                    break;
                case CODE_REPEAT://重新发送
                    getNumber.setText("获取验证码");
                    getNumber.setClickable(true);
                    break;
                case SMSDDK_HANDLER:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    //回调完成
                    if (result == SMSSDK.RESULT_COMPLETE)
                    {
                        //验证码验证成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                        {
                            Toast.makeText(forgetPassword.this, "验证成功", Toast.LENGTH_LONG).show();
//                            if (check())//其他合法性的检测
//                            {
//                                UserModel user  = new UserModel();
//                                user.setUserId(MyUUID.getUUID());              //id
//                                user.setUserPhone(userPhone);
//                                user.setUserPassword(MD5.md5(userPassword));   //md5加密
//                                user.setUserGender(gender);                    //性别
//                                user.setUserName(userName);
//                                user.setUserBirthday("19920401");                      //暂时为空
//                                //user.setUserIdCard(userIdCard);
//                                //user.setUserImage("");                         //暂时为空
//                                //注册->服务器
//                                UserController.userRegister(user, handler);
//                            }

                        }
                        //已发送验证码
                        else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
                        {
                            Toast.makeText(getApplicationContext(), "验证码已经发送",
                                    Toast.LENGTH_SHORT).show();
                        } else
                        {
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    if(result==SMSSDK.RESULT_ERROR)
                    {
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            //do something
                        }
                    }
                    break;
            }
        }
    };
}

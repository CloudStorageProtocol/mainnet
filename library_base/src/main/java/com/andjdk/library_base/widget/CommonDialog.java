package com.andjdk.library_base.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andjdk.library_base.R;


public class CommonDialog extends Dialog {


    public static final int DIALOG_CANCEL = 0;
    public static final int DIALOG_OK = 1;

    /**
     * 确认(带标题)(当前默认弹框)
     */
    public CommonDialog(Context context, String title, String content) {
        super(context, R.style.common_dialog);
        View view = View.inflate(context, R.layout.dialog_middle, null);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(view);
        setCancelable(false);        //设置点击对话框以外的区域时，是否结束对话框
        ((TextView) view.findViewById(R.id.title)).setText(title);//设置对话框的标题内容隐藏
        TextView tvContent = ((TextView) view.findViewById(R.id.content));
        if(TextUtils.isEmpty(content)){
            tvContent.setVisibility(View.GONE);
        }else{
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }

        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 输入密码提示框
     */
    public CommonDialog(Context context, OnPasswordCLickListener onPasswordCLickListener) {
        super(context, R.style.common_dialog);
        View view = View.inflate(context, R.layout.dialog_password, null);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(view);
        setCancelable(false);        //设置点击对话框以外的区域时，是否结束对话框
        EditText editContent = ((EditText) view.findViewById(R.id.edit_content));

        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View v) {
                dismiss();
                if(onPasswordCLickListener !=null){
                    onPasswordCLickListener.onActionButtonClick(DIALOG_OK,editContent.getText().toString().trim());
                }
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onPasswordCLickListener !=null){
                    onPasswordCLickListener.onActionButtonClick(DIALOG_CANCEL,editContent.getText().toString().trim());
                }
            }
        });
    }


    /**
     * 支付成功提示框
     * @param context
     * @param onEnterCLickListener
     */
    public CommonDialog(Context context,OnEnterCLickListener onEnterCLickListener) {
        super(context, R.style.common_dialog);
        View view = View.inflate(context, R.layout.dialog_pay_success, null);
        Window window = getWindow();
        if(window !=null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(view);
            setCancelable(false);        //设置点击对话框以外的区域时，是否结束对话框

            view.findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(onEnterCLickListener !=null){
                        onEnterCLickListener.onEnterButtonClick();
                    }
                }
            });
        }
    }

    /**
     * 自定义内容
     * @param context
     * @param customView
     * @param onButtonCLickListener
     */
    public CommonDialog(Context context,View customView,OnButtonCLickListener onButtonCLickListener) {
        super(context, R.style.common_dialog);
        View view = View.inflate(context, R.layout.dialog_custom_middle, null);
        Window window = getWindow();
        if(window !=null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(view);
            setCancelable(false);        //设置点击对话框以外的区域时，是否结束对话框

            FrameLayout containerView = view.findViewById(R.id.lly_container);
            containerView.addView(customView);

            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {//确定
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(onButtonCLickListener !=null){
                        onButtonCLickListener.onActionButtonClick(DIALOG_CANCEL);
                    }
                }
            });

            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {//确定
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(onButtonCLickListener !=null){
                        onButtonCLickListener.onActionButtonClick(DIALOG_OK);
                    }
                }
            });

        }
    }

    public interface OnEnterCLickListener {
        void onEnterButtonClick();
    }

    public interface OnButtonCLickListener {
        void onActionButtonClick(int position);
    }

    public interface OnPasswordCLickListener {
        void onActionButtonClick(int position,String password);
    }
}

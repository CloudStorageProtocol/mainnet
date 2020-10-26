package com.uranus.library_user.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andjdk.library_base.utils.GlideUtils;
import com.andjdk.library_base.utils.Lmsg;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uranus.library_user.R;
import com.uranus.library_user.bean.InviteRecordInfo;

import java.util.List;

public class InviteRecordAdapter extends BaseQuickAdapter<InviteRecordInfo, BaseViewHolder> {

    public InviteRecordAdapter(int layoutResId, @Nullable List<InviteRecordInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InviteRecordInfo item) {
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_id,"ID:"+item.getUid());
        helper.setText(R.id.tv_container,String.format("小容器：%s个     大容器：%s个,",item.getSmall_product(),item.getBig_product()));
        helper.setText(R.id.tv_invite_num,String.format("共邀请%s人",item.getInvite_num()));
        helper.setText(R.id.tv_level,"U"+item.getLevel());

        if(!TextUtils.isEmpty(item.getAvatar())){
            GlideUtils.loadCircleImage(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
        }else {
            helper.setImageResource(R.id.iv_avatar,R.mipmap.avatar);
        }

    }
}

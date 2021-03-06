package com.oce.ocewallet.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.oce.ocewallet.R;
import com.oce.ocewallet.base.BaseActivity;
import com.oce.ocewallet.utils.AppManager;
import com.oce.ocewallet.utils.Md5Utils;
import com.oce.ocewallet.utils.ToastUtils;
import com.oce.ocewallet.view.InputPwdDialog;

import butterknife.BindView;
import butterknife.OnClick;


public class WalletBackupActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_backup)
    TextView btnBackup;
    @BindView(R.id.btn_backup_help)
    TextView btnBackupHelp;
    private InputPwdDialog inputPwdDialog;
    private String walletPwd;
    private String walletAddress;
    private String walletName;
    private String walletMnemonic;
    private long walletId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_backup;
    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.wallet_backup_backup_btn);
    }

    @Override
    public void initDatas() {
        AppManager.getAppManager().addActivity(this);
        Intent intent = getIntent();
        walletId = intent.getLongExtra("walletId", -1);
        walletPwd = intent.getStringExtra("walletPwd");
        walletAddress = intent.getStringExtra("walletAddress");
        walletName = intent.getStringExtra("walletName");
        walletMnemonic = intent.getStringExtra("walletMnemonic");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.btn_backup, R.id.btn_backup_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_backup:
                inputPwdDialog = new InputPwdDialog(this);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        if (TextUtils.isEmpty(pwd)) {
                            ToastUtils.showToast(R.string.input_pwd_dialog_tip);
                            return;
                        }
                        if (TextUtils.equals(Md5Utils.md5(pwd), walletPwd)) {
                            Intent intent = new Intent(WalletBackupActivity.this, MnemonicBackupActivity.class);
                            intent.putExtra("walletId", walletId);
                            intent.putExtra("walletMnemonic", walletMnemonic);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                inputPwdDialog.show();
                break;
            case R.id.btn_backup_help:
                break;
        }
    }

}

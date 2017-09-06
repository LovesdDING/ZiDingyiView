package patient.yilin.com.testzidingyiview.NiceDialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import patient.yilin.com.testzidingyiview.R;

public class TestNiceDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nice_dialog);


        findViewById(R.id.nice_dialog_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                      .setLayoutId(R.layout.share_layout)
                       .setConvertListener(new ViewConvertListener() {
                           @Override
                           public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                                holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(TestNiceDialogActivity.this, "分享成功", Toast.LENGTH_SHORT).show();

                                    }
                                });
                           }
                       })
                        .setDimAmout(0.3f)
                        .setShowBottom(true)
                        .show(getSupportFragmentManager()) ;

            }
        });

        findViewById(R.id.nice_dialog_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                            .setLayoutId(R.layout.friend_set_layout)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                public void convertView(ViewHolder holder, BaseNiceDialog dialog) {

                                }
                            })
                            .setShowBottom(true)
                            .setHeight(300)
                        .show(getSupportFragmentManager()) ;
            }
        });

        findViewById(R.id.nice_dialog_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                            .setLayoutId(R.layout.commit_layout)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                                    EditText editText = holder.getView(R.id.edit_input) ;
//                                    调用显示软键盘    点击某个控件的时候 显示软键盘
                                    InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(editText,0) ;
                                }
                            })
                            .setShowBottom(true)
                            .show(getSupportFragmentManager()) ;
            }
        });

        findViewById(R.id.nice_dialog_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                            .setLayoutId(R.layout.ad_layout)
                            .setConvertListener(new ViewConvertListener() {
                                @Override
                                public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                        holder.setOnClickListener(R.id.close, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                }
                            })
                            .setWidth(210)
                            .setOutCancel(false)
                            .setAnimStyle(R.style.EnterExitAnimation)
                         .show(getSupportFragmentManager()) ;

            }
        });

        findViewById(R.id.nice_dialog_btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceDialog.init()
                        .setLayoutId(R.layout.loading_layout)
                        .setWidth(100)
                        .setHeight(100)
                        .setDimAmout(0)
                        .show(getSupportFragmentManager()) ;
            }
        });


        findViewById(R.id.nice_dialog_btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.newInstance("1")
                        .setMargin(60)
                        .setOutCancel(false)
                        .show(getSupportFragmentManager()) ;
            }
        });


        findViewById(R.id.nice_dialog_btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.newInstance("2")
                        .setMargin(60)
                        .setOutCancel(false)
                        .show(getSupportFragmentManager()) ;
            }
        });
    }




    public static class ConfirmDialog extends BaseNiceDialog{
        private String type ;
        public static ConfirmDialog newInstance(String type){
             Bundle bundle = new Bundle() ;
             bundle.putString("type",type);
            ConfirmDialog dialog = new ConfirmDialog() ;
            dialog.setArguments(bundle);
            return dialog ;
        }

        @Override
        public int initLayoutId() {
            return R.layout.confirm_layout;
        }

        @Override
        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
            if ("1".equals(type)) {
                holder.setText(R.id.title,"提示");
                holder.setText(R.id.message,"您已支付成功!");
            }else if("2".equals(type)){
                holder.setText(R.id.title,"警告");
                holder.setText(R.id.message,"您的账号有盗号风险。");
            }

            holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments() ;
            if (bundle == null) {
                return ;
            }
            type =bundle.getString("type") ;
        }
    }
}

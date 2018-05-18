package com.example.aiyang.baidumapapp.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.example.aiyang.baidumapapp.R;

/**
 * Created by aiyang on 2018/5/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private String menuStr;
    private int menuResId;
    private String menuStr2;
    private int menuResId2;
    private TextView tvTitle;
    private FrameLayout viewContent;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());//百度地图
        setContentView(R.layout.activity_main);

        //1、设置支出，并不显示项目的title文字
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //2、将子类的布局解析到 FrameLayout 里面
        viewContent = (FrameLayout) findViewById(R.id.viewContent);
        LayoutInflater.from(this).inflate(getConentView(), viewContent);

        //3、初始化操作（此方法必须放在最后执行位置）
        init(savedInstanceState);
    }

    /**
     * 设置布局资源
     *
     * @return
     */
    protected abstract int getConentView();

    /**
     * 初始化操作
     *
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 设置页面标题
     *
     * @param title 标题文字
     */
    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle = (TextView) findViewById(R.id.tv_title);
            tvTitle.setText(title);
        }
    }
    /**
     * 设置显示返回按钮
     */
    protected void setTitleBack(boolean visible) {
        if (visible) {
            toolbar.setNavigationIcon(R.mipmap.back_whait);//设置返回按钮
        }
    }

    /**
     * 设置标题栏右键按钮事件
     *
     * @param menuStr 文字
     * @param menuResId 图片icon
     * @param onClickListener 事件响应
     */
    protected void setToolBarMenuOne(String menuStr, int menuResId, OnClickRightListener onClickListener) {
        this.onClickRightListener = onClickListener;
        this.menuStr = menuStr;
        this.menuResId = menuResId;
    }
    protected void setToolBarMenuTwo(String menuStr, int menuResId, OnClickRightListener onClickListener) {
        this.onClickRightListener2 = onClickListener;
        this.menuStr2 = menuStr;
        this.menuResId2 = menuResId;
    }
    /**
     * 设置拦截事件处理业务逻辑
     *
     * @param item 自定义菜单项
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_one:
                this.onClickRightListener.onClick();

                break;
            case R.id.menu_item_two:
                this.onClickRightListener2.onClick();

                break;
        }
        return true;//拦截系统处理事件
    }

    /**
     * 加载Toolbar标题右菜单图标和文字
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {//显示自定义右菜单
            getMenuInflater().inflate(R.menu.toobar, menu);
        }else if (menuResId2 != 0 || !TextUtils.isEmpty(menuStr2)){
            getMenuInflater().inflate(R.menu.toobar, menu);
        }else {
            //如果把下面这行代码表示右侧菜单显示默认值。
            //显示的默认Menu、Item里的值必须在menu文件中配置好文字和icon。
            getMenuInflater().inflate(R.menu.toobar, menu);
        }
        return true;
    }

    /**
     * 选择性显示图标或文字
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.menu_item_one).setIcon(menuResId);
        } else if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_item_one).setTitle(menuStr);
        }else{
            menu.findItem(R.id.menu_item_one).setVisible(false);
        }

        if (menuResId2 != 0){
            menu.findItem(R.id.menu_item_two).setIcon(menuResId2);
        } else if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_item_two).setTitle(menuStr2);
        }else {
            menu.findItem(R.id.menu_item_two).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private OnClickRightListener onClickRightListener;
    private OnClickRightListener onClickRightListener2;

    public interface OnClickRightListener {
        void onClick();
    }

}

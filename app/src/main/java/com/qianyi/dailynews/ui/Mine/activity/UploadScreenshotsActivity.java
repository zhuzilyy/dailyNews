package com.qianyi.dailynews.ui.Mine.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.BitmapToBase64;
import com.qianyi.dailynews.utils.PhotoUtils;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.dialog.PhotoChioceDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class UploadScreenshotsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_title)
    public TextView title;

    @BindView(R.id.upload_pic003)
    public ImageView upload_pic003;
    @BindView(R.id.upload_pic002)
    public ImageView upload_pic002;
    @BindView(R.id.upload_pic001)
    public ImageView upload_pic001;
    @BindView(R.id.big_shots) public ImageView big_shots;
    private PhotoChioceDialog photoChioceDialog;
    private String localImg,strBase64;

    @BindView(R.id.re_upload001) public RelativeLayout re_upload001;
    @BindView(R.id.re_upload002) public RelativeLayout re_upload002;
    @BindView(R.id.re_upload003) public RelativeLayout re_upload003;

    @BindView(R.id.del_001) public ImageView del_001;
    @BindView(R.id.del_002) public ImageView del_002;
    @BindView(R.id.del_003) public ImageView del_003;

    @BindView(R.id.submit_pic) public Button submit_pic;



    private ImageView currentShots;
    private ImageView currentDelPic;
    private int currentPosition = 1;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    private Bitmap showBitmap;

    private boolean pic001type=true;
    private boolean pic002type=true;
    private boolean pic003type=true;


    private Bitmap bitmap001;
    private Bitmap bitmap002;
    private Bitmap bitmap003;
    private List<String> imgPaths = new ArrayList<>();
    List<String> strings = new ArrayList<>();
    private int totalPicCount;
    private CustomLoadingDialog customLoadingDialog;
    private String id;

    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("上传截图");
        customLoadingDialog=new CustomLoadingDialog(this);
        id=getIntent().getStringExtra("id");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_upload_screenshts);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }

    @OnClick({R.id.re_upload001,R.id.re_upload002,R.id.re_upload003,R.id.del_001,R.id.del_002,
            R.id.del_003,R.id.submit_pic,R.id.tv_jitu})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jitu:
                Intent intent = new Intent(UploadScreenshotsActivity.this,ScreenshotExamplesActivity.class);
                startActivity(intent);
                break;
            case R.id.re_upload001:
                if(pic001type){
                    currentShots=upload_pic001;
                    currentDelPic=del_001;
                    currentPosition=1;
                    upLoadPic(upload_pic001);
                }else {
                    big_shots.setImageBitmap(bitmap001);
                }
                break;
            case R.id.re_upload002:
                if(pic002type){
                    currentShots=upload_pic002;
                    currentDelPic=del_002;
                    currentPosition=2;
                    upLoadPic(upload_pic002);
                }else {
                    big_shots.setImageBitmap(bitmap002);
                }
                break;
            case R.id.re_upload003:
                if(pic003type){
                    currentShots=upload_pic003;
                    currentDelPic=del_003;
                    currentPosition=3;
                    upLoadPic(upload_pic003);
                }else {
                    big_shots.setImageBitmap(bitmap003);
                }
                break;
            case R.id.del_001:
                currentPosition=1;
                deleteImg(del_001,upload_pic001);
                bitmap001=null;
                break;
            case R.id.del_002:
                currentPosition=2;
                deleteImg(del_002,upload_pic002);
                bitmap002=null;
                break;
            case R.id.del_003:
                currentPosition=3;
                deleteImg(del_003,upload_pic003);
                bitmap003=null;
                break;

            case R.id.submit_pic:

                if(bitmap001!=null&&bitmap002!=null&&bitmap003!=null){
                    String base64pic001=BitmapToBase64.Bitmap2StrByBase64(bitmap001);
                    String base64pic002=BitmapToBase64.Bitmap2StrByBase64(bitmap002);
                    String base64pic003=BitmapToBase64.Bitmap2StrByBase64(bitmap003);
                    strings.add(base64pic001);
                    strings.add(base64pic002);
                    strings.add(base64pic003);
                    if(strings.size()>0){
                       // for (int i = 0; i <strings.size() ; i++) {
                            submitPics(strings.get(0));
                      //  }
                    }
                }
                break;
        }
    }

    /****
     * 将图片转化成file
     */
    public File dowithPicToFile(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器
        String path = Environment.getExternalStorageDirectory() + "/Ask";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;

    }

    /***
     * 上传截图
     */
    private void submitPics(String filePic) {
        customLoadingDialog.show();
        RequestParams params = new RequestParams(ApiConstant.UPLOAD_PICS);
        params.addParameter("base64",filePic);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.i("uu",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String  picUrl = jsonObject.getString("data");
                    imgPaths.add(picUrl);
                    submitPicSecond();
                    /*if("0000".equals(code)){
                       String imgPath = jsonObject.getString("data");
                       if(TextUtils.isEmpty(imgPath)){
                           imgPaths.add(imgPath);

                       }
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("uu",ex.getMessage());
                customLoadingDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    private void submitPicSecond() {
        RequestParams params = new RequestParams(ApiConstant.UPLOAD_PICS);
        params.addParameter("base64",strings.get(1));
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(final String result) {

                Log.i("uu",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String  picUrl = jsonObject.getString("data");
                    imgPaths.add(picUrl);
                    submitPicThird();
                   /* if("0000".equals(code)){
                        String imgPath = jsonObject.getString("data");
                        if(TextUtils.isEmpty(imgPath)){
                            imgPaths.add(imgPath);
                            submitPicThird();
                        }
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("uu",ex.getMessage());
                customLoadingDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    private void submitPicThird() {
        RequestParams params = new RequestParams(ApiConstant.UPLOAD_PICS);
        params.addParameter("base64",strings.get(2));
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                customLoadingDialog.dismiss();
                Log.i("uu",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String  picUrl = jsonObject.getString("data");
                    imgPaths.add(picUrl);
                    if(imgPaths.size()>=3){
                        upScreenshot(imgPaths);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("uu",ex.getMessage());
                customLoadingDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    /***
     * 上传图片路劲
     * @param imgPaths
     */
    private void upScreenshot(List<String> imgPaths) {
        Log.i("ss",imgPaths+"");

        String imgStrs =  imgPaths.get(0)+"$lvmq$"+imgPaths.get(1)+"$lvmq$"+imgPaths.get(2);
        if(TextUtils.isEmpty(imgStrs)){
            return;
        }
        String userid = (String) SPUtils.get(UploadScreenshotsActivity.this, "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }


        ApiMine.saveImgs(ApiConstant.SAVE_PICS, userid, id, imgStrs, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("ss",s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if("0000".equals(code)){
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("ss",e.getMessage());
            }
        });


    }

    /**
     * 清空图片
     * @param del_001
     * @param upload_pic001
     */
    private void deleteImg(ImageView del_001, ImageView upload_pic001) {
        del_001.setVisibility(View.GONE);
        upload_pic001.setImageBitmap(null);
        if(1==currentPosition){
            pic001type = true;
        }
        if(2==currentPosition){
            pic002type = true;
        }
        if(3==currentPosition){
            pic003type = true;
        }

    }

    /***
     * 上传图片
     * @param iv
     */
    private void upLoadPic(final ImageView iv) {
        photoChioceDialog = new PhotoChioceDialog(this);
        photoChioceDialog.show();
        photoChioceDialog.setClickCallback(new PhotoChioceDialog.ClickCallback() {
            @Override
            public void doAlbum() {
                readPhotoAlbum(iv);
            }
            @Override
            public void doCancel() {
            }
            @Override
            public void doCamera() {
                takePhoto(iv);
            }
        });

    }

    //跳转到拍照
    private void takePhoto(ImageView iv) {
        requestPermissions(UploadScreenshotsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                if (hasSdcard()) {
                    imageUri = Uri.fromFile(fileUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        //通过FileProvider创建一个content类型的Uri
                        imageUri = FileProvider.getUriForFile(UploadScreenshotsActivity.this, "com.by.daily_news.fileprovider", fileUri);
                    PhotoUtils.takePicture(UploadScreenshotsActivity.this, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    Toast.makeText(UploadScreenshotsActivity.this, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
                    Log.e("asd", "设备没有SD卡");
                }
            }

            @Override
            public void denied() {
                Toast.makeText(UploadScreenshotsActivity.this, "部分权限获取失败，正常功能受到影响", Toast.LENGTH_LONG).show();
            }
        });


    }
    //读取相册
    private void readPhotoAlbum(ImageView iv) {
        requestPermissions(UploadScreenshotsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                PhotoUtils.openPic(UploadScreenshotsActivity.this, CODE_GALLERY_REQUEST);
            }

            @Override
            public void denied() {
                Toast.makeText(UploadScreenshotsActivity.this, "部分权限获取失败，正常功能受到影响", Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int output_X = 480, output_Y = 480;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 0, 0, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.by.daily_news.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 0, 0, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toast.makeText(UploadScreenshotsActivity.this, "设备没有SD卡!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap,currentShots);

                    }
                    break;
            }
        }
    }

    private void showImages(Bitmap bitmap,ImageView CurrentImage) {
        CurrentImage.setImageBitmap(bitmap);
        currentDelPic.setVisibility(View.VISIBLE);
        strBase64= BitmapToBase64.Bitmap2StrByBase64(bitmap);
        showBitmap=bitmap;
        if(1==currentPosition){
            pic001type = false;
            bitmap001=bitmap;
        }
        if(2==currentPosition){
            pic002type = false;
            bitmap002=bitmap;
        }
        if(3==currentPosition){
            pic003type = false;
            bitmap003=bitmap;
        }

    }


}

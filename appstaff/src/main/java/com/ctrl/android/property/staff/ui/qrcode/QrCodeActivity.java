package com.ctrl.android.property.staff.ui.qrcode;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.staff.R;
import com.ctrl.android.property.staff.base.AppToolBarActivity;
import com.ctrl.android.property.staff.dao.ExpressDao;
import com.ctrl.android.property.staff.dao.VisitDao;
import com.ctrl.android.property.staff.entity.Img;
import com.ctrl.android.property.staff.ui.express.ExpressDetailActivity;
import com.ctrl.android.property.staff.ui.visit.VisitOrderHandleDetailActivity;
import com.ctrl.android.property.staff.util.StrConstant;
import com.ctrl.android.property.staff.util.TimeUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class QrCodeActivity extends AppToolBarActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ExpressDao edao;
    private VisitDao vdao;
    private String id;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        edao=new ExpressDao(this);
        vdao=new VisitDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        showProgress(false);
        if (requestCode == 2) {
            Intent intent = new Intent(QrCodeActivity.this, ExpressDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", id);    //快递id
            bundle.putString("name", edao.getExpressRecive().getRecipientName());    //收件人
            bundle.putString("tel", edao.getExpressRecive().getMobile());  //收件电话
            bundle.putString("building", edao.getExpressRecive().getBuilding());//楼号
            bundle.putString("unit", edao.getExpressRecive().getUnit());//单元号
            bundle.putString("room", edao.getExpressRecive().getRoom());//房间号
            bundle.putString("company", edao.getExpressRecive().getKindName());//快递公司
            bundle.putString("number", edao.getExpressRecive().getLogisticsNum());//快递编号
            bundle.putInt("status", edao.getExpressRecive().getStatus());//快递状态
            ArrayList<Img>list=new ArrayList<>();
            list = (ArrayList) edao.getListImg();
           // Log.i("TAG","size"+list.size());
           // Log.i("TAG","url"+list.get(0).getZipImg());
            if(list !=null) {
                bundle.putSerializable("list", list);
            }
                intent.putExtra("expressBundle", bundle);
                intent.addFlags(1026);
                startActivity(intent);
                AnimUtil.intentSlidIn(QrCodeActivity.this);
                this.finish();
            }


        if (requestCode ==1) {
            Intent intent = new Intent(QrCodeActivity.this, VisitOrderHandleDetailActivity.class);
            Bundle bundle = new Bundle();
            Log.i("tag", "number===" + vdao.getVisitDetail().getVisitNum());
            bundle.putString("number",vdao.getVisitDetail().getVisitNum());//到访编号
            bundle.putString("name",vdao.getVisitDetail().getVisitorName());    //到访人
            bundle.putString("time", TimeUtil.date(Long.parseLong(vdao.getVisitDetail().getArriveTime())));//到访时间
            bundle.putString("count",vdao.getVisitDetail().getPeopleNum()+"");//到访人数
            bundle.putString("car", vdao.getVisitDetail().getNumberPlates());  //车牌号
            bundle.putString("stop", vdao.getVisitDetail().getResidenceTime());  //预计停留时间
            bundle.putInt("status", vdao.getVisitDetail().getHandleStatus());//到访状态
            bundle.putString("communityVisitId", vdao.getVisitDetail().getCommunityVisitId());//到访状态
            intent.putExtra("visitBundle",bundle);
            intent.addFlags(1126);
            startActivity(intent);
           // AnimUtil.intentSlidIn(QrCodeActivity.this);
            this.finish();
        }
        super.onRequestSuccess(requestCode);
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        AnimUtil.intentSlidIn(QrCodeActivity.this);
        this.finish();
    }

    /**
     * 处理扫描结果
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            MessageUtils.showLongToast(getApplicationContext(), "Scan failed!");
            finish();
        }else {
            if(getIntent().getFlags()==1054) {
                Intent intent = new Intent();
                intent.putExtra("expressNum", resultString);
                setResult(StrConstant.EXPRESS_NUM, intent);
            }
            if(getIntent().getFlags()==5001){
              edao.requestQrCodeExpressDetail(resultString);
                this.id=resultString;
            }
            if(getIntent().getFlags()==888){
                vdao.requestVisitDetail("0",resultString);
            }
            finish();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    @Override
    public String setupToolBarTitle() {
        return "二维码扫描";
    }

//    @Override
//    public boolean setupToolBarLeftButton(ImageView leftButton) {
//        leftButton.setImageResource(R.drawable.toolbar_back);
//        leftButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        return true;
//    }
    @Override
    public boolean setupToolBarRightButton(ImageView rightButton) {
        rightButton.setImageResource(R.drawable.toolbar_home);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomePage();
            }
        });
        return true;
    }
}
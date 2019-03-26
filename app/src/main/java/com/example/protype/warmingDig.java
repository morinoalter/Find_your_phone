package com.example.protype;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class warmingDig extends AppCompatActivity {
    private AudioManager am;
    private MediaPlayer mp;
    private Button closeDig;
    private ImageView imageview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warming);
        am=(AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        //
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,7,AudioManager.FLAG_PLAY_SOUND);
        mp=MediaPlayer.create(this,
                              R.raw.music );
        mp.start();

        closeDig=findViewById(R.id.button2);
        closeDig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                      mp.pause();
                      finish();
            }
        });

        imageview =findViewById(R.id.imageView);
        //0.5f 表示旋转中心的位置
        Animation rotateAnim = new RotateAnimation(-15.f,15.f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f) ;
        //持续时间，ms单位
        rotateAnim.setDuration(100);
        //重复模式
        rotateAnim.setRepeatMode(Animation.REVERSE);
        //重复次数
        rotateAnim.setRepeatCount(1000);

        AnimationSet as= new AnimationSet(false);
        as.addAnimation(rotateAnim);
        imageview.startAnimation(as);
    }


}

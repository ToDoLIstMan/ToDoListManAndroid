package com.tdl.todolistmanandroid.Receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdl.todolistmanandroid.R;
import com.tdl.todolistmanandroid.activity.MainActivity;
import com.tdl.todolistmanandroid.activity.PickGroupActivity;
import com.tdl.todolistmanandroid.database.user;
import com.tdl.todolistmanandroid.database.work;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by songm on 2017-04-10.
 */

public class AlarmReceiver extends BroadcastReceiver {
    List<Integer> groupId;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(final Context context, Intent intent) {

        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_stat_notification_icon_24).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("일정이 추가되었습니다").setContentText("추가된 일정을 확인하세요.")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());


//        groupId = new ArrayList<>();
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference[] myRef = {database.getReference().child("user").child(intent.getStringExtra("userUid"))};
//        myRef[0].addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.e("adf",dataSnapshot.toString());
//                user u = dataSnapshot.getValue(user.class);
//                groupId.addAll(u.getGroups());
//
//                myRef[0].removeEventListener(this);
//                for(final int i : groupId) {
//                     database.getReference().child("work").child("" + i).child("2017-05-30").addChildEventListener(new ChildEventListener() {
//                         @Override
//                         public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                             if(dataSnapshot.exists()) {
//                                 work work = dataSnapshot.getValue(work.class);
//                                 Log.e("dfdfd","adsfadsf");
//                                 if(work.getuId().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                                     AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                                     Intent intent = new Intent(context, WorkReceiver.class);
//                                     intent.putExtra("title", work.getTitle());
//                                     intent.putExtra("detail", work.getDetail());
//                                     String[] a = new String[2];
//                                     a = work.getStartTime().split(":");
//                                     Log.e("adsf", a[0] + "   " + a[1]);
//                                     PendingIntent sender = PendingIntent.getBroadcast(context, Integer.valueOf(i + "00" + work.getId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                                     Calendar calendar = Calendar.getInstance();
//                                     //알람시간 calendar에 set해주기
//                                     if (calendar.get(Calendar.HOUR) < 4)
//                                         calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DATE), Integer.valueOf(a[0]), Integer.valueOf(a[1]), 0);
//                                     else
//                                         calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)), calendar.get(Calendar.DATE), Integer.valueOf(a[0]), Integer.valueOf(a[1]), 0);
//                                     Log.e("month", "" + calendar.get(Calendar.MONTH));
//
//
//                                     calendar.add(Calendar.SECOND, 10);
//
//                                     if (Build.VERSION.SDK_INT >= 23) {
//                                         am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
//                                         Log.e("your sdk verson is", "up to 23");
//                                     } else {
//                                         if (Build.VERSION.SDK_INT >= 19) {
//                                             am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
//                                             Log.e("your sdk verson is", "up to 19");
//
//                                         } else {
//                                             am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
//                                             Log.e("your sdk verson is", "under the 19");
//
//                                         }
//                                     }
//
//
//                                     Log.e("dfd", "알람 생성");
//                                 }
//                                 else {
//                                     Log.e("get Alarm", "오늘 일 없음.");
//                                 }
//                             }else {
//                                 Log.e("get Alarm", "오늘 일 없음.");
//                             }
//                         }
//
//                         @Override
//                         public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                         }
//
//                         @Override
//                         public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                         }
//
//                         @Override
//                         public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                         }
//
//                         @Override
//                         public void onCancelled(DatabaseError databaseError) {
//
//                         }
//                     });
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }
}

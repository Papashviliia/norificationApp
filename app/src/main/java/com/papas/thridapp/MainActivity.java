package com.papas.thridapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView txtComment, txtCurrentTask;
    private static final int NOTIFY_ID = 101;

    EditText editInput;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private NotificationManager notificationManager;
    AlertDialog alert;
    private String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtComment = (TextView) findViewById(R.id.txtComment);
        txtCurrentTask = (TextView) findViewById(R.id.textView5);
        editInput = (EditText) findViewById(R.id.txtEdit);


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle ("Ошибка") // Заголовок окна
                .setMessage("Вы забыли ввести имя. \n\nПопробуйте еще раз")
                .setIcon(R.drawable.ic_alert) // иконка
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        alert = builder.create(); // Создание диалогового окна

        notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)
        ;

    }

    public void buttonClick(View view) {
        name = editInput.getText().toString();
        if(name.length()>0){
            showToast();
            txtCurrentTask.setText("Сходите в магазин, " + name );
        }
            else {
            alert.show();
        }
    }

    public void showToast(){
        Toast.makeText(getApplicationContext(), "Сходите в магазин, " +
                name, Toast.LENGTH_LONG).show();
    }

    public static void createNotificationChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                            NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }

    }

    public void showNotification(){
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Напоминание")
                .setContentText("Не забудьте выполнить задачу")
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        createNotificationChannel(notificationManager);
        notificationManager.notify(NOTIFY_ID, builder.build());



    }




}
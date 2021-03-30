package com.example.lab5;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.IBinder;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


public class MainActivity extends AppCompatActivity {

    private RecyclerView taskRView;
    private TaskAdapter taskAdapter;
    private TaskManager taskManager;
    private Toast toast;

    private Button mStartBtn;
    private Button mStopBtn;

    private FloatingActionButton addNewBtn = null;
    private Handler handler = new Handler();

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            GetTasksAsync task = new GetTasksAsync(MainActivity.this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // This registers messageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(messageReceiver, new IntentFilter("MyService"));
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onPause();
    };


    public class CustomTaskClicksHandler implements TaskHolder.OnTaskClickListeners {
        @Override
        public void onTaskClick(Task task) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(MainActivity.this, task.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public void onCheckBoxClick(Task task) {
            Task mTask = task;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DeleteTaskAsync task = new DeleteTaskAsync(mTask.getId());
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            },  100);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRView = findViewById(R.id.list);
        mStartBtn = findViewById(R.id.button2);
        mStopBtn = findViewById(R.id.button3);

        mStartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);
        });

        mStopBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            stopService(intent);
        });

        taskRView.setLayoutManager(new LinearLayoutManager(this));

        taskManager = new TaskManager(this);
        taskAdapter = new TaskAdapter(this, new ArrayList<>(),  new CustomTaskClicksHandler());

        taskRView.setAdapter(taskAdapter);

        addNewBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);


        addNewBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, 0);
        });

        GetTasksAsync task = new GetTasksAsync(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        Bundle bundle = data.getExtras();

        String title = bundle.getString(AddTaskActivity.TASK_TITLE);
        String strDate = bundle.getString(AddTaskActivity.TASK_DATE);

        try {
            Date taskDate = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
            Task task = new Task(title, taskDate, null);
            AddTaskAsync taskAsync = new AddTaskAsync(task);
            taskAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            return;
        }

    }

    protected class AddTaskAsync extends AsyncTask<Void, Void, Void> {

        private Task mTask;

        public AddTaskAsync(Task task) {
            mTask = task;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskManager.addTask(mTask);
            List<Task> tasks = taskManager.getTasks();
            runOnUiThread(() -> {
                taskAdapter.setTasks(tasks);
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    protected class DeleteTaskAsync extends AsyncTask<Void, Void, Void> {

        private String id;

        public DeleteTaskAsync(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskManager.removeTask(id);
            List<Task> tasks = taskManager.getTasks();
            runOnUiThread(() -> {
                taskAdapter.setTasks(tasks);
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    protected class GetTasksAsync extends AsyncTask<Void, Void, Void> {

        private Context mContext;

        public GetTasksAsync(Context ctx) {
            mContext = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(() -> {
                taskAdapter.setTasks(taskManager.getTasks());
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    static public class MyService extends Service {

        private TaskManager taskManager;

        private Handler mHandler =  new Handler();

        private long delay = 5000;

        private Runnable onTimer = new Runnable() {
            @Override
            public void run() {

                byte[] array = new byte[8];
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("UTF-8"));
                Task task = new Task(generatedString, new Date(), null);
                taskManager.addTask(task);

                Intent intent = new Intent("MyService");
                LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(intent);


                mHandler.postDelayed(this, delay);
            }
        };

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacksAndMessages(null);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            taskManager = new TaskManager(getApplicationContext());
        }

        public int onStartCommand(Intent intent, int flags, int startId) {
            System.out.println("onstart");
            mHandler.postDelayed(MyService.this.onTimer, MyService.this.delay);
            return START_NOT_STICKY;
        }
    }
}
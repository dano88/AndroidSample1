//====================================================================
//
// Application: Message Handlers
// Author:      Dan Ouellette
// Activity:    ActMain
// Description:
//   This Android application shows message handlers.  It starts two
// worker threads.  One worker thread communicates data to the main
// thread using a shared singleton.  The other worker thread
// communicates data to the main thread using a message object.
// Status messages are written to the Logcat.
//
//====================================================================
package biz.ssc.messagehandlers;

// Import packages
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

//--------------------------------------------------------------------
// class ActMain
//--------------------------------------------------------------------
public class ActMain extends AppCompatActivity
{

    //----------------------------------------------------------------
    // Constants and variables
    //----------------------------------------------------------------
    public static final String APP_NAME = "Message Handlers";
    public static final String APP_VERSION = "1.0";
    private Button btnStartThreads;
    private static EditText txtSingletonData;
    private static EditText txtMessageData;
    private Button btnStopThreads;
    private Thread thread1;
    private SingletonThread singletonThread;
    private Thread thread2;
    private MessageThread messageThread;

    //----------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laymain);

        // Connect to controls
        btnStartThreads = findViewById(R.id.btnStartThreads);
        txtSingletonData = findViewById(R.id.txtSingletonData);
        txtMessageData = findViewById(R.id.txtMessageData);
        btnStopThreads = findViewById(R.id.btnStopThreads);

        // Set buttons
        btnStartThreads.setEnabled(true);
        btnStopThreads.setEnabled(false);

    }

    //----------------------------------------------------------------
    // startThreads
    //----------------------------------------------------------------
    public void startThreads(View v)
    {

        // Set flag and buttons
        Shared.Data.workerThreadsRunning = true;
        btnStartThreads.setEnabled(false);
        btnStopThreads.setEnabled(true);

        // Start singleton thread
        singletonThread = new SingletonThread();
        thread1 = new Thread(singletonThread);
        thread1.start();

        // Start message thread
        messageThread = new MessageThread();
        thread2 = new Thread(messageThread);
        thread2.start();

    }

    //----------------------------------------------------------------
    // stopThreads
    //----------------------------------------------------------------
    public void stopThreads(View v)
    {

        // Set flag and buttons
        Shared.Data.workerThreadsRunning = false;
        btnStartThreads.setEnabled(true);
        btnStopThreads.setEnabled(false);

        // Loop while threads are closing
        System.out.println("[Main] Closing worker threads ...");
        while (Thread.currentThread().getThreadGroup().activeCount() > 1)
        {
            try
            {
                System.out.println(
                    "[Main] Waiting for worker threads to close ...");
                Thread.sleep(Shared.Data.STOP_THREADS_PAUSE);
            }
            catch (InterruptedException e)
            {
                System.out.println("Error: main thread " +
                    "interrupted.");
                System.out.println("Exception message:\n" +
                    e.getMessage());
            }
        }
        System.out.println("[Main] All worker threads closed.");

    }

    //----------------------------------------------------------------
    // singletonHandler
    //----------------------------------------------------------------
    public static Handler singletonHandler =
        new Handler(Looper.getMainLooper())
    {

        //------------------------------------------------------------
        // handleMessage
        //------------------------------------------------------------
        @Override
        public void handleMessage(Message msg)
        {
            txtSingletonData.setText(
                String.valueOf(Shared.Data.number));
        }

    };

    //----------------------------------------------------------------
    // messageHandler
    //----------------------------------------------------------------
    public static Handler messageHandler =
        new Handler(Looper.getMainLooper())
    {

        //------------------------------------------------------------
        // handleMessage
        //------------------------------------------------------------
        @Override
        public void handleMessage(Message msg)
        {
            txtMessageData.setText(String.valueOf(msg.arg1));
        }

    };

}

//====================================================================
//
// Application: Message Handlers
// Class:       SingletonThread
// Description:
//   This Android application class represents a worker thread that
// communicates data to the main thread with a singleton object.
//
//====================================================================
package biz.ssc.messagehandlers;

// Import packages
import java.util.Random;

//--------------------------------------------------------------------
// class SingletonThread
//--------------------------------------------------------------------
public class SingletonThread implements Runnable
{

    //----------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------
    public SingletonThread()
    {
        System.out.println("[Singleton] Singleton thread has " +
            "started ...");
    }

    //----------------------------------------------------------------
    // run
    //----------------------------------------------------------------
    @Override
    public void run()
    {

        // Loop to send messages to main thread
        while (Shared.Data.workerThreadsRunning)
        {

            // Create and store data
            Shared.Data.number =
                (new Random()).nextInt(899) + 100;

            // Test if message sent
            if (ActMain.singletonHandler.sendEmptyMessage(0))
                System.out.println("[Singleton] Singleton thread " +
                    "message sent to main thread.");
            else
                System.out.println("Error: singleton thread " +
                    "message NOT sent to main thread.");

            // Pause thread
            try
            {
                Thread.sleep(Shared.Data.SINGLETON_THREAD_PAUSE);
            }
            catch (InterruptedException e)
            {
                System.out.println("Error: singleton thread " +
                    "interrupted.");
                System.out.println("Exception message:\n" +
                    e.getMessage());
            }

        }
        System.out.println("[Singleton] Singleton thread has ended.");

    }

}

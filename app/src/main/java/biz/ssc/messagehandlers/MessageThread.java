//====================================================================
//
// Application: Message Handlers
// Class:       MessageThread
// Description:
//   This Android class represents a worker thread that communicates
// data to the main thread with a message object.
//
//====================================================================
package biz.ssc.messagehandlers;

// Import packages
import android.os.Message;
import java.util.Random;

//--------------------------------------------------------------------
// class MessageThread
//--------------------------------------------------------------------
public class MessageThread implements Runnable
{

    //----------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------
    public MessageThread()
    {
        System.out.println("[Message] Message thread has started ...");
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

            // Declare variables
            Message msg = new Message();

            // Create message
            msg.arg1 = (new Random()).nextInt(899) + 100;

            // Test if message sent
            if (ActMain.messageHandler.sendMessage(msg))
                System.out.println("[Message] Message thread " +
                    "message sent to main thread.");
            else
                System.out.println("Error: message thread " +
                    "message NOT sent to main thread.");

            // Pause thread
            try
            {
                Thread.sleep(Shared.Data.MESSAGE_THREAD_PAUSE);
            }
            catch (InterruptedException e)
            {
                System.out.println("Error: message thread " +
                    "interrupted.");
                System.out.println("Exception message:\n" +
                    e.getMessage());
            }
        }
        System.out.println("[Message] Message thread has ended.");

    }

}

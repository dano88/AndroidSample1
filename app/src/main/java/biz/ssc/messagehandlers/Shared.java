//====================================================================
//
// Application: Message Handlers
// Class:       Shared
// Description:
//   This Android class holds data shared among threads.
//
//====================================================================
package biz.ssc.messagehandlers;

//--------------------------------------------------------------------
// enum Shared
//--------------------------------------------------------------------
public enum Shared
{

    // Define enum value
    Data;

    // Declare enum fields
    public final int SINGLETON_THREAD_PAUSE = 1000;
    public final int MESSAGE_THREAD_PAUSE = 2000;
    public final int STOP_THREADS_PAUSE = 3000;
    public boolean workerThreadsRunning;
    public int number;
        // Value shared between singleton thread and main thread

}

package Controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kahlen on 06-12-2015.
 */
public class ThreadUtil {

    /**
     * ExecutorService is used for handling threads. Excecutors.newCachedThreadpool() automatically
     * spawns new threads when needed and caches them for reuse when processes are finished.
     */
    public static final ExecutorService executorService = Executors.newCachedThreadPool();
}

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class registerer {

    public static void main(String[] args) throws ParseException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");

        //date and time which you can register
        String dateString = "12-22-2021 06:00:05";
        Date date = sdf.parse(dateString);
        long millisDif = date.getTime()-System.currentTimeMillis();

        scheduler.schedule(new task(args[0], args[1]), millisDif, TimeUnit.MILLISECONDS);
        scheduler.shutdown();
    }

}

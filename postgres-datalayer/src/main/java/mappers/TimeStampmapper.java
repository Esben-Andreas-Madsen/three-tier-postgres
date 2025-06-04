package mappers;

import com.google.protobuf.Timestamp;
import java.util.Date;

public class TimeStampmapper {

    public Date map(Timestamp timestamp) {
        return timestamp == null ? null : new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1_000_000);
    }

    public Timestamp map(Date date) {
        if (date == null) return null;

        long millis = date.getTime();
        long seconds = millis / 1000;
        int nanos = (int) ((millis % 1000) * 1_000_000);

        return Timestamp.newBuilder().setSeconds(seconds).setNanos(nanos).build();
    }
}

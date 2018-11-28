package net.lordofthecraft.omniscience.api.entry;

import net.lordofthecraft.omniscience.OmniConfig;
import net.lordofthecraft.omniscience.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static net.lordofthecraft.omniscience.api.data.DataKeys.CREATED;

public class DataEntryComplete extends DataEntry {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(OmniConfig.INSTANCE.getDateFormat());

    public String getRelativeTime() {
        Optional<Object> date = data.get(CREATED);
        return date.map(time -> {
            Date created = null;
            if (time instanceof Date) {
                created = (Date) time;
            } else if (time instanceof Long) {
                created = new Date(((Long) time) * 1000);
            }

            if (created != null) {
                return DateUtil.getTimeSince(created);
            } else {
                return "";
            }
        }).orElse(null);
    }

    public String getTime() {
        Optional<Object> date = data.get(CREATED);
        return date.map(time -> {
            if (time instanceof Date) {
                return simpleDateFormat.format((Date) time);
            } else if (time instanceof Long) {
                return simpleDateFormat.format(new Date(((Long) time) * 1000));
            }
            return null;
        }).orElse(null);
    }
}

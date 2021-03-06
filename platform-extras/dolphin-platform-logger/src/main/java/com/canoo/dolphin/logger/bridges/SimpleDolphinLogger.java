package com.canoo.dolphin.logger.bridges;

import com.canoo.dolphin.logger.DolphinLoggerConfiguration;
import com.canoo.dolphin.logger.DolphinLoggerUtils;
import com.canoo.dolphin.logger.impl.LogMessage;
import com.canoo.dolphin.logger.spi.DolphinLoggerBridge;
import com.canoo.dp.impl.platform.core.ansi.AnsiOut;
import org.slf4j.event.Level;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class SimpleDolphinLogger implements DolphinLoggerBridge {

    private final DateFormat dateFormat;

    private final Level level;

    public SimpleDolphinLogger(final DolphinLoggerConfiguration configuration) {
        this.level = Objects.requireNonNull(configuration.getGlobalLevel());
        this.dateFormat = Objects.requireNonNull(configuration.getDateFormat());
    }

    @Override
    public void log(final LogMessage logMessage) {
        if (DolphinLoggerUtils.isLevelEnabled(this.level, logMessage.getLevel())) {

            final String textColor = Optional.ofNullable(logMessage.getLevel()).
                    map(l -> Level.valueOf(l)).
                    map(l -> {
                        if (l.equals(Level.ERROR)) {
                            return AnsiOut.ANSI_RED;
                        }
                        if (l.equals(Level.WARN)) {
                            return AnsiOut.ANSI_YELLOW;
                        }
                        if (l.equals(Level.INFO)) {
                            return AnsiOut.ANSI_BLUE;
                        }
                        return AnsiOut.ANSI_CYAN;
                    }).orElse(AnsiOut.ANSI_CYAN);

            final StringBuilder buf = new StringBuilder();
            buf.append(AnsiOut.ANSI_WHITE);
            buf.append(dateFormat.format(new Date(logMessage.getTimestamp())));
            buf.append(AnsiOut.ANSI_RESET);

            buf.append(" ");

            buf.append(AnsiOut.ANSI_BOLD);
            buf.append(textColor);
            buf.append(logMessage.getLevel());

            buf.append(" - ");

            buf.append(logMessage.getMessage());
            buf.append(AnsiOut.ANSI_RESET);

            buf.append(AnsiOut.ANSI_WHITE);
            buf.append(" - ");

            buf.append(logMessage.getLoggerName());


            if (!logMessage.getMarker().isEmpty()) {
                buf.append(" - [");
                for (String marker : logMessage.getMarker()) {
                    buf.append(marker);
                    if (logMessage.getMarker().indexOf(marker) < logMessage.getMarker().size() - 1) {
                        buf.append(", ");
                    }
                }
                buf.append("]");
            }
            buf.append(" - ");
            buf.append(logMessage.getThreadName());
            buf.append(AnsiOut.ANSI_RESET);

            if(logMessage.getThrowable() != null) {
                buf.append(AnsiOut.ANSI_RED);
                buf.append(System.lineSeparator());
                buf.append(toStackTrace(logMessage.getThrowable()));
                buf.append(AnsiOut.ANSI_RESET);
            }
            print(buf.toString());
        }
    }


    private String toStackTrace(final Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter stream = new PrintWriter(stringWriter);
        if (throwable != null) {
            throwable.printStackTrace(stream);
        }
        return stringWriter.toString().substring(0, stringWriter.toString().length() - 1);
    }

    private synchronized void print(final String message) {
        System.out.println(message);
    }
}

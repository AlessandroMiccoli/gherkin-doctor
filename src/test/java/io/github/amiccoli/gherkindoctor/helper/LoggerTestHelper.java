package io.github.amiccoli.gherkindoctor.helper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerTestHelper {

    @Getter
    @AllArgsConstructor
    public static class LogCombo {
        public Level level;
        public String message;
    }

    public static ListAppender<ILoggingEvent> startLogger(Class<?> clazz) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        return listAppender;
    }

    public static void stopLogger(ListAppender<ILoggingEvent> listAppender) {
        listAppender.stop();
    }

    public static void verifyLog(ListAppender<ILoggingEvent> listAppender, Level level, String message) {
        var logsList = listAppender.list;
        assertThat(logsList).hasSize(1);

        ILoggingEvent log = logsList.get(0);
        assertThat(log.getLevel()).isEqualTo(level);
        assertThat(log.getFormattedMessage()).contains(message);
    }

    public static void verifyContainLog(ListAppender<ILoggingEvent> listAppender, Level level, String message) {
        var logsList = listAppender.list;

        var maybeLog = logsList.stream()
                .filter(log  -> log.getLevel().equals(level) && log.getFormattedMessage().contains(message))
                .findFirst();

        assertThat(maybeLog)
                .as("Validate there is the expected log.")
                .isPresent();
    }

    public static void verifyLog(ListAppender<ILoggingEvent> listAppender, int logCount, List<LogCombo> logsCombo) {
        var logsList = listAppender.list;
        assertThat(logsList).hasSize(logCount);

        for (int i = 0; i < logCount; i++) {
            var log = logsList.get(i);
            var logCombo = logsCombo.get(i);
            assertThat(log.getLevel()).isEqualTo(logCombo.getLevel());
            assertThat(log.getFormattedMessage()).contains(logCombo.getMessage());
        }
    }
}

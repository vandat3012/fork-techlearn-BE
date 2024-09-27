package com.techzen.techlearn.service.impl;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VAlarm;
import biweekly.component.VEvent;
import biweekly.property.Method;
import biweekly.property.Trigger;
import biweekly.util.Duration;
import com.techzen.techlearn.dto.CalendarDTO;
import com.techzen.techlearn.service.MailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GmailServiceImpl implements MailService {

    JavaMailSender javaMailSender;
    @Override
    public void sendScheduleSuccessEmail(CalendarDTO calenderDto) throws MessagingException, IOException {
        // Send email
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setRecipients(Message.RecipientType.TO, getToAddress(calenderDto.getAttendees()));
        mimeMessage.setSubject(calenderDto.getSubject());

        MimeMultipart mimeMultipart = new MimeMultipart("mixed");

        mimeMultipart.addBodyPart(createCalenderMimeBody(calenderDto));

        mimeMessage.setContent(mimeMultipart);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendEmails(List<String> recipientEmails, String subject, String title, String description,
                           LocalDateTime startTime, LocalDateTime endTime, String actionUrl, String actionText, String primaryColor) throws MessagingException {
        String htmlTemplate = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Calendar Event Notification</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #fff; max-width: 600px; margin: 0 auto; padding: 20px; }
                    h1 { color: %1$s; }
                    .event-details { background-color: #f9f9f9; border-left: 4px solid %1$s; padding: 15px; margin-bottom: 20px; }
                    .event-time { font-weight: bold; color: %1$s; }
                    .btn { display: inline-block; padding: 10px 20px; background-color: %1$s; color: #ffffff; text-decoration: none; border-radius: 5px; }
                </style>
            </head>
            <body>
                <h1>%2$s</h1>
                <div class="event-details">
                    <h2>%3$s</h2>
                    <p>%4$s</p>
                    <p class="event-time">Bắt đầu: %5$s</p>
                    <p class="event-time">Kết thúc: %6$s</p>
                </div>
                <a href="%7$s" class="btn">%8$s</a>
            </body>
            </html>
        """;

        String formattedHtml = String.format(htmlTemplate,
                primaryColor,
                subject,
                title,
                description,
                startTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                endTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                actionUrl,
                actionText);

        for (String recipientEmail : recipientEmails) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("thanhtuanle939@gmail.com");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(formattedHtml, true);

            javaMailSender.send(message);
        }
    }

    private Address[] getToAddress(List<String> attendees) {
        return attendees.stream().map(email -> {
            Address address = null;
            try {
                address = new InternetAddress(email);
            } catch (AddressException e) {
                e.printStackTrace();
            }
            return address;
        }).toArray(Address[]::new);
    }

    private Date getStartDate(LocalDateTime eventDateTime) {
        Instant instant = eventDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    private String createCalendar(CalendarDTO calendarDto) {
        ICalendar icalendar = new ICalendar();
        icalendar.addProperty(new Method(Method.REQUEST));
        icalendar.setUrl(calendarDto.getMeetingLink());

        VEvent event = new VEvent();
        event.setUrl(calendarDto.getMeetingLink());
        event.setSummary(calendarDto.getSummary());
        event.setDescription(calendarDto.getMeetingLink());
        event.setDateStart(getStartDate(calendarDto.getEventDateTime()));
        event.setDuration(new Duration.Builder()
                .minutes(10)
                .build());
        event.setOrganizer(calendarDto.getOrganizer());

        Trigger trigger = new Trigger(getStartDate(calendarDto.getEventDateTime().minusMinutes(5)));
        VAlarm alarm = VAlarm.email(trigger, "Reminder for your meeting", "", calendarDto.getAttendees());
        alarm.setDescription("This is reminder for your meeting: " + calendarDto.getSummary());
        event.addAlarm(alarm);

//        VAlarm alarm = new VAlarm(new Duration.Builder().minutes(-5).build());
//        alarm.setDescription("This is a reminder that the event is starting soon.");
//        event.addAlarm(alarm);
//
//        for (String email : calendarDto.getAttendees()) {
//            Attendee attendee = new Attendee(email);
//            event.addAttendee(attendee);
//        }

        icalendar.addEvent(event);
        return Biweekly.write(icalendar).go();
    }

    private BodyPart createCalenderMimeBody(CalendarDTO calenderDto) throws IOException, MessagingException {
        MimeBodyPart calenderBody = new MimeBodyPart();

        final DataSource source = new ByteArrayDataSource(createCalendar(calenderDto), "text/calender; charset=UTF-8");
        calenderBody.setDataHandler(new DataHandler(source));
        calenderBody.setHeader("Content-Type", "text/calendar; charset=UTF-8; method=REQUEST");

        return calenderBody;
    }
}

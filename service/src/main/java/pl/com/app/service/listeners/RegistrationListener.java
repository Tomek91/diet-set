package pl.com.app.service.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.com.app.dto.MailSenderDTO;
import pl.com.app.model.User;
import pl.com.app.service.MailService;
import pl.com.app.service.RegistrationService;

import java.util.UUID;

import static j2html.TagCreator.body;
import static j2html.TagCreator.*;


@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationEvenData> {

    private final RegistrationService registrationService;
    private final MailService mailService;

    @Override
    public void onApplicationEvent(OnRegistrationEvenData data) {
        sendRegistrationConfirmationEmail(data);
    }

    private void sendRegistrationConfirmationEmail(OnRegistrationEvenData data) {

        User user = data.getUser();
        String token = UUID.randomUUID().toString();
        registrationService.createVerificationToken(user, token);

       // String recipientAddress = user.getEmail();
        String recipientAddress = "tomek.r9@wp.pl";
        String subject = "PHONES-APP: registration confirmation";
        String url = data.getUrl() + "registration/registerConfirmation?token=" + token;


        String message =
                body(
                        h2("Hello!"),
                        h3("This is Diet Set -App."),
                        h3(
                                text("Click "),
                                a("here").withHref(url),
                                text(" to activate account.")
                        )
                ).render();


        mailService.sendMail(
                MailSenderDTO.builder()
                        .recipientAddress(recipientAddress)
                        .subject(subject)
                        .message(message)
                        .build()
        );
    }
}

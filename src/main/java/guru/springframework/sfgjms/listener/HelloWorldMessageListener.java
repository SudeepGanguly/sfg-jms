package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloWorldMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage ,
                       @Headers MessageHeaders headers , Message message){

        System.out.println("I've got a Message");
        System.out.println(helloWorldMessage);

//        throw new RuntimeException();
    }


    @JmsListener(destination = JmsConfig.MY_REPLY_BACK_QUEUE)
    public void listenAndReply(@Payload HelloWorldMessage helloWorldMessage ,
                       @Headers MessageHeaders headers , Message message) throws JMSException {

        HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Reply to Ay Caramba")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(),payloadMsg);

    }
}

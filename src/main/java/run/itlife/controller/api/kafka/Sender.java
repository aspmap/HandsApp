package run.itlife.controller.api.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

  private final KafkaTemplate<Integer, String> template;

  public Sender(KafkaTemplate<Integer, String> template) {
    this.template = template;
  }

  public void sendMsg(String toSend, int key) {
    this.template.send("feedback", key, toSend);
  }

}

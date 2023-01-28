package run.itlife.service;

import java.util.List;

public interface HandshakeService {

    List<Long> selectUsersId();
    List<Long> selectUsersSubId();

}

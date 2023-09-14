package run.itlife.service;

import java.util.List;
import java.util.Map;

public interface HandshakeService {

    List<Long> selectUsersId();
    List<Long> selectUsersSubId();
    List<String> findUsersById(Long userId);


}

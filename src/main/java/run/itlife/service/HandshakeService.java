package run.itlife.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HandshakeService {

    List<Long> selectUsersId();
    ArrayList<Integer> selectUsersId(Integer userId);
    List<Long> selectUsersSubId();
    List<String> findUsersById(Integer userId);


}

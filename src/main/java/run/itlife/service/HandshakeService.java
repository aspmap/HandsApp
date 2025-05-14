package run.itlife.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HandshakeService {
    ArrayList<Integer> selectUsersId(Integer userId);
    List<String> findUsersById(Integer userId);


}

package run.itlife.service;

import java.util.ArrayList;
import java.util.List;

public interface HandshakeService {
    ArrayList<Integer> findUsersId(Integer userId);
    List<String> findUsersById(Integer userId);


}

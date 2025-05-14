package run.itlife.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.repository.HandshakeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HandshakeServiceImpl implements HandshakeService {

    private final HandshakeRepository handshakeRepository;

    public HandshakeServiceImpl(HandshakeRepository handshakeRepository) {
        this.handshakeRepository = handshakeRepository;
    }

    @Override
    public ArrayList<Integer> selectUsersId(Integer userId) {
        ArrayList<Integer> userIdList = new ArrayList<>();
        userIdList = handshakeRepository.selectUsersId(userId);
        return userIdList;
    }

    @Override
    public List<String> findUsersById(Integer userId) {
        return handshakeRepository.findUsersById(userId);
    }

}

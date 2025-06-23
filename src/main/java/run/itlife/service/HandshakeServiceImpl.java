package run.itlife.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.repository.HandshakeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HandshakeServiceImpl implements HandshakeService {
    private final HandshakeRepository handshakeRepository;

    public HandshakeServiceImpl(HandshakeRepository handshakeRepository) {
        this.handshakeRepository = handshakeRepository;
    }

    @Override
    public ArrayList<Integer> findUsersId(Integer userId) {
        ArrayList<Integer> userIdList;
        userIdList = handshakeRepository.findUsersId(userId);
        return userIdList;
    }

    @Override
    public List<String> findUsersById(Integer userId) {
        return handshakeRepository.findUsersById(userId);
    }

}

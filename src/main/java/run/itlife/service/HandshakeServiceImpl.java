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
    public List<Long> selectUsersId() {
        List<Long> userIdList = new ArrayList<>();
        userIdList = handshakeRepository.selectUsersId();
        return userIdList;
    }

    @Override
    public List<Long> selectUsersSubId() {
        List<Long> userSubIdList = new ArrayList<>();
        userSubIdList = handshakeRepository.selectUsersSubId();
        return userSubIdList;
    }

    @Override
    public List<String> findUsersById(Long userId) {
        return handshakeRepository.findUsersById(userId);
    }

}

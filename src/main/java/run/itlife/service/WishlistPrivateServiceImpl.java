package run.itlife.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.repository.WishlistPrivateRepository;

@Service
@Transactional
public class WishlistPrivateServiceImpl implements WishlistPrivateService {
    private final WishlistPrivateRepository wishlistPrivateRepository;

    public WishlistPrivateServiceImpl(WishlistPrivateRepository wishlistPrivateRepository) {
        this.wishlistPrivateRepository = wishlistPrivateRepository;
    }

    @Override
    public void deletePermissions(Long id) {
        wishlistPrivateRepository.deleteById(id);
    }

    @Override
    public Integer searchAlreadyPermissions(Long id, Long userId) {
        return wishlistPrivateRepository.searchAlreadyPermissions(id, userId);
    }
}

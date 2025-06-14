package run.itlife.service;

public interface WishlistPrivateService {
    void deletePermissions(Long id);
    Integer searchAlreadyPermissions(Long id, Long userId);
}

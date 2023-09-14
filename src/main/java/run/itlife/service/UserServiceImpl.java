package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.repository.RoleRepository;
import run.itlife.repository.UserRepository;
import javax.persistence.EntityExistsException;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.utils.SecurityUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Уровень обслуживания
// Класс, реализующий интерфейс, который отвечает за логику создания пользователей, поиск пользователей
@Service
@Transactional
public class UserServiceImpl implements UserService {

    // сервисы в свою очередь включают репозиторий
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder cryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll(Sort.by("username"));
    }

    public void create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new EntityExistsException();
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("USER")));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setIsClosed(false);
        user.setIsHidden(false);
        user.setIsGoogle(false);
        user.setFirstname(user.getFirstname());
        user.setSurname(user.getSurname());
        userRepository.save(user);
    }

    public void createGoogleUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new EntityExistsException();
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("USER")));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setIsClosed(false);
        user.setIsHidden(false);
        user.setIsGoogle(true);
        user.setFirstname(user.getFirstname());
        user.setSurname(user.getSurname());
        userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto) {
        checkAuthority(userDto.getUserId());
        User user = userRepository.findById(userDto.getUserId()).orElseThrow();
        if (!StringUtils.isEmpty(userDto.getUsername()))
            user.setUsername(userDto.getUsername());
        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
        if (!StringUtils.isEmpty(userDto.getPhoto()))
            user.setPhoto(userDto.getPhoto());
        if (!StringUtils.isEmpty(userDto.getFirstname()))
            user.setFirstname(userDto.getFirstname());
        if (!StringUtils.isEmpty(userDto.getSurname()))
            user.setSurname(userDto.getSurname());
        if (!StringUtils.isEmpty(userDto.getEmail()))
            user.setEmail(userDto.getEmail());
        if (!StringUtils.isEmpty(userDto.getInfo()))
            user.setInfo(userDto.getInfo());
        if (!StringUtils.isEmpty(userDto.getPhone()))
            user.setPhone(userDto.getPhone());
        if (!StringUtils.isEmpty(userDto.getSex()))
            user.setSex(userDto.getSex());
        if (!StringUtils.isEmpty(userDto.getWww()))
            user.setWww(userDto.getWww());
        userRepository.save(user);
    }

    @Override
    public void delete_profile(String user) {
        Optional<User> username = userRepository.findByUsername(user);
        if(username.isPresent()) {
            userRepository.deleteById(username.get().getUserId());
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    @Override
    public void checkAuthority(long userId) {
        SecurityUtils.checkAuthority(userRepository.findById(userId)
                .orElseThrow()
                .getUsername());
    }

    @Override
    public List<User> getUsersOnly() {
         return userRepository.getUsersOnly();
    }

    @Override
    public ArrayList<UserDto> getUsersOnlyKey(String currentUsername) {
        ArrayList<UserDto> dto = new ArrayList<>();
        ArrayList<String> splitUsers = userRepository.getUsersOnlyKey(currentUsername);
        String[] usersString;
        for (int i = 0; i < splitUsers.size(); i++) {
            UserDto dtoOne = new UserDto();
            usersString = splitUsers.get(i).split(",");
            int usersStringLength = usersString.length;
            dtoOne.setIsSub(usersString[0]);
            dtoOne.setUsername(usersString[1].trim());
            dtoOne.setPhoto(usersString[2].trim());
            dtoOne.setFirstname(usersString[3].trim());
            dtoOne.setSurname(usersString[4].trim());
            dtoOne.setEmail(usersString[5].trim());
            dtoOne.setIsGoogle(usersString[6].trim());
            dto.add(dtoOne);
        }
        return dto;
    }

    @Override
    public List<User> searchUsers(String substring) {
        return userRepository.searchUsers("%" + substring +"%");
    }

    @Override
    public int countSearchUsers(String substring) {
        return userRepository.countSearchUsers("%" + substring +"%");
    }

}
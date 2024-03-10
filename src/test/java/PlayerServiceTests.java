import com.example.rockpaperscissorsultimate.domain.exceptions.player.FailedToCreatePlayerException;
import com.example.rockpaperscissorsultimate.domain.player.Player;
import com.example.rockpaperscissorsultimate.domain.player.PlayerStatus;
import com.example.rockpaperscissorsultimate.domain.player.Role;
import com.example.rockpaperscissorsultimate.repository.PlayerRepository;
import com.example.rockpaperscissorsultimate.service.PlayerService;
import com.example.rockpaperscissorsultimate.utils.PlayerUtils;
import com.example.rockpaperscissorsultimate.web.dto.player.SignUpPlayerRequest;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.ConstraintViolation;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTests {
    
    @Mock
    private PlayerRepository playerRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private Validator validator;
    
    @InjectMocks
    private PlayerService playerService;
    
    @Test
    public void PlayerService_GetAllPlayers_ReturnsPlayers(){
        Player player1 = Player.builder()
                .username("1")
                .email("aijsdhajkdsa@mail.ru")
                .passwordHash("asdyuasijodnPASIDUHASDNasdn123")
                .coins(PlayerUtils.BALANCE_START)
                .elo(PlayerUtils.ELO_MINIMUM)
                .loses(0)
                .wins(0)
                .draws(0)
                .playerStatus(PlayerStatus.READY)
                .gamesAmount(0)
                .roles(Set.of(Role.valueOf("USER")))
                .build();
        
        Player player2 = Player.builder()
                .username("2")
                .email("aijhajkdsa@mail.ru")
                .passwordHash("asdyuasijodnPASIDUHASDN14asdn123")
                .coins(PlayerUtils.BALANCE_START)
                .elo(PlayerUtils.ELO_MINIMUM)
                .loses(0)
                .wins(0)
                .draws(0)
                .playerStatus(PlayerStatus.READY)
                .gamesAmount(0)
                .roles(Set.of(Role.valueOf("USER")))
                .build();
        
        List<Player> dummyPlayers = Arrays.asList(player1, player2);
        
        // Set up the mock behavior
        when(playerRepository.findAll()).thenReturn(dummyPlayers);
        
        // Call the method under test
        List<Player> result = playerService.getAllPlayers();
        
        // Verify that the repository method was called
        verify(playerRepository, times(1)).findAll();
        
        // Perform assertions on the result
        assertNotNull(result);
        assertEquals(dummyPlayers.size(), result.size());
        assertEquals(dummyPlayers, result);
    }
    
    @Test
    public void PlayerService_GetAllPlayers_ReturnsEmptyList(){

        List<Player> dummyPlayersEmpty = new ArrayList<>();
        
        // Set up the mock behavior
        when(playerRepository.findAll()).thenReturn(dummyPlayersEmpty);
        
        // Call the method under test
        List<Player> result = playerService.getAllPlayers();
        
        // Verify that the repository method was called
        verify(playerRepository, times(1)).findAll();
        
        // Perform assertions on the result
        assertNotNull(result);
        assertEquals(dummyPlayersEmpty.size(), result.size());
        assertEquals(dummyPlayersEmpty, result);
    }
    
    @Test
    public void PlayerService_CreatePlayer_ReturnsCreatedPlayer(){
        SignUpPlayerRequest request = SignUpPlayerRequest.builder()
                .username("testing123")
                .email("testemail123@mail.ru")
                .passwordText("TestingTest123")
                .build();
        
        Player createdPlayer = Player.builder()
                .id("1")
                .username("testingUser123")
                .email("testemail123@mail.ru")
                .passwordHash(passwordEncoder.encode("TestingTest123"))
                .coins(PlayerUtils.BALANCE_START)
                .elo(PlayerUtils.ELO_MINIMUM)
                .loses(0)
                .wins(0)
                .draws(0)
                .playerStatus(PlayerStatus.READY)
                .gamesAmount(0)
                .roles(Set.of(Role.valueOf("USER")))
                .build();
        
        when(playerRepository.save(Mockito.any(Player.class))).thenReturn(createdPlayer);
        
        Player result = playerService.createPlayer(request);
        
        verify(playerRepository, times(1)).save(any(Player.class));
        
        assertNotNull("Object is null", result);
        assertEquals("The object differs from the expected one",result, createdPlayer);
    }
    
    //TODO: Не работает проверка валидации
    /*@Test
    public void PlayerService_CreatePlayerWithBadUsername_ThrowsFailedToCreatePlayerException(){
        SignUpPlayerRequest request = SignUpPlayerRequest.builder()
                .username("bad")
                .email("testemail123@mail.ru")
                .passwordText("TestingPassword123")
                .build();
        
        Set<ConstraintViolation<SignUpPlayerRequest>> violations = new HashSet<>();
        ConstraintViolation<SignUpPlayerRequest> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(Path.class));
        when(violation.getMessage()).thenReturn("Some validation error message");
        violations.add(violation);
        
        when(validator.validate(any(SignUpPlayerRequest.class))).thenReturn(violations);
        
        assertThrows(FailedToCreatePlayerException.class, () -> playerService.createPlayer(request));
    }
    
    @Test
    public void PlayerService_CreatePlayerWithBadEmail_ThrowsFailedToCreatePlayerException(){
        SignUpPlayerRequest request = SignUpPlayerRequest.builder()
                .username("bad")
                .email("testemail123@mail.ru")
                .passwordText("TestingPassword123")
                .build();
        
        assertThrows(FailedToCreatePlayerException.class, () -> playerService.createPlayer(request));
    }
    
    @Test
    public void PlayerService_CreatePlayerWithBadPassword_ThrowsFailedToCreatePlayerException(){
        SignUpPlayerRequest request = SignUpPlayerRequest.builder()
                .username("goodname123")
                .email("testemail123@mail.ru")
                .passwordText("badpassword")
                .build();
        
        assertThrows(FailedToCreatePlayerException.class, () -> playerService.createPlayer(request));
    }*/
    
}

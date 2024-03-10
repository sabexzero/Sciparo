import com.example.rockpaperscissorsultimate.domain.lobby.Lobby;
import com.example.rockpaperscissorsultimate.repository.LobbyRepository;
import com.example.rockpaperscissorsultimate.service.LobbyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LobbyServiceTests {
    
    @Mock
    private LobbyRepository lobbyRepository;
    
    @InjectMocks
    private LobbyService lobbyService;
    
    @Test
    public void LobbyService_GetAllLobbies_ReturnsPlayers(){
        Lobby lobby1 = new Lobby();
        lobby1.setId("1");
        lobby1.setTitle("Lobby 1");
        lobby1.setPlayersId(List.of("1"));
        lobby1.setCreatorUsername("1");
        lobby1.setCreatorElo(1);
        lobby1.setBet(1);
        lobby1.setReadyPlayersNumber(0);
        
        Lobby lobby2 = new Lobby();
        lobby1.setId("2");
        lobby1.setTitle("Lobby 2");
        lobby1.setPlayersId(List.of("2"));
        lobby1.setCreatorUsername("2");
        lobby1.setCreatorElo(1);
        lobby1.setBet(1);
        lobby1.setReadyPlayersNumber(0);
        
        List<Lobby> dummyLobbies = Arrays.asList(lobby1, lobby2);
        
        // Set up the mock behavior
        when(lobbyRepository.findAll()).thenReturn(dummyLobbies);
        
        // Call the method under test
        List<Lobby> result = lobbyService.getAllLobbies();
        
        // Verify that the repository method was called
        verify(lobbyRepository, times(1)).findAll();
        
        // Perform assertions on the result
        assertNotNull(result);
        assertEquals(dummyLobbies.size(), result.size());
        assertEquals(dummyLobbies, result);
    }
    
}

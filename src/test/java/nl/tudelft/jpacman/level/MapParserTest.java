package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MapParserTest {
    private MapParser mapParser;
    private final LevelFactory levelFactory = mock(LevelFactory.class);
    private final BoardFactory boardFactory = mock(BoardFactory.class);


    @Test
    @Order(1)
    @DisplayName("null文件名")
    public void nullFile(){
        assertThatThrownBy(()-> {
            mapParser.parseMap((String) null);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    @Order(2)
    @DisplayName("读取不存在文件")
    public void notExistFile(){
        mapParser = new MapParser(levelFactory,boardFactory);
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
        String file = "/notexistmap.txt";
        assertThatThrownBy(()-> {
            mapParser.parseMap(file);
        }).isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Could not get resource for: "+file);
    }

    @Test
    @Order(3)
    @DisplayName("存在的文件")
    public void existFile() throws IOException {
        mapParser = new MapParser(levelFactory,boardFactory);
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
        String file = "/simplemap.txt";
        mapParser.parseMap(file);
        verify(boardFactory,times(4)).createGround();
        verify(boardFactory,times(2)).createWall();
        verify(levelFactory,times(1)).createGhost();
    }

    @Test
    @Order(4)
    @DisplayName("不能识别地图文件")
    public void unrecongizedMap(){
        mapParser = new MapParser(levelFactory,boardFactory);
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
        String file = "/unrecongizedMap.txt";
        assertThatThrownBy(()-> {
            mapParser.parseMap(file);
        }).isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Could not get resource for: /unrecongizedMap.txt");
    }

}

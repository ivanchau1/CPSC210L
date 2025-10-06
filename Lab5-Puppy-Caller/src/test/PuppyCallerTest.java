package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class PuppyCallerTest {

    private PuppyCaller pc;
    private ArrayList<Puppy> puppies1;
    private ArrayList<Puppy> puppies2;
    private Puppy puppy1;
    private Puppy puppy2;
    private Puppy puppy3;
    
    @BeforeEach
    void setup() {
        pc = new PuppyCaller();
        puppies1 = new ArrayList<>();
        puppies2 = new ArrayList<>();
        puppy1 = new Puppy(0,0,1,"bob");
        puppy2 = new Puppy(1,1,2,"steve");
        puppy3 = new Puppy(2,2,3,"joe");
    }

    @Test
    void testConstructor() {
        assertNull(pc.getCurrentPuppy());
    }

    @Test
    void testSetCurrentPuppy() {
        assertFalse(puppy1.hasBeenCalled());
        pc.setCurrentPuppy(puppy1);
        assertEquals(puppy1, pc.getCurrentPuppy());
    }

    @Test
    void testTypeNameForCurrentPuppyValidChar() {
        pc.setCurrentPuppy(puppy2);
        assertNotNull(pc.getCurrentPuppy());
        assertEquals(puppy2, pc.getCurrentPuppy());
        assertTrue(!pc.getCurrentPuppy().hasBeenCalled());

        assertTrue(pc.typeNameForCurrentPuppy('s'));
        assertEquals('t', pc.getCurrentPuppy().getNextChar());
        assertTrue(pc.typeNameForCurrentPuppy('t'));
        assertFalse(pc.getCurrentPuppy().hasBeenCalled());
        assertTrue(pc.typeNameForCurrentPuppy('e'));
        assertTrue(pc.typeNameForCurrentPuppy('v'));
        assertTrue(pc.typeNameForCurrentPuppy('e'));
        
        assertNull(pc.getCurrentPuppy());
        
        
    }

    @Test
    void testTypeNameForCurrentPuppyInvalidChar() {
        pc.setCurrentPuppy(puppy2);
        assertNotNull(pc.getCurrentPuppy());
        assertTrue(!pc.getCurrentPuppy().hasBeenCalled());

        assertFalse(pc.typeNameForCurrentPuppy('d'));
        assertEquals(puppy2, pc.getCurrentPuppy());

    }

    @Test
    void testFindPuppyMultipleFail() {
        assertEquals(0, puppies1.size());

        puppies1.add(puppy1);
        puppies1.add(puppy2);
        assertEquals(2, puppies1.size());
        assertEquals(puppy1, puppies1.get(0));
        assertEquals(puppy2, puppies1.get(1));
        assertFalse(puppies1.get(0).hasBeenCalled());
        assertFalse(puppies1.get(1).hasBeenCalled());

        assertNull(pc.findPuppy(puppies1, 'c'));
        assertNull(pc.findPuppy(puppies1, 'a'));

    }

    @Test
    void testFindPuppyOnlyChecksFirst() {
        puppies1.add(puppy1);
        puppies1.add(puppy2);
        assertEquals(2, puppies1.size());
        assertEquals(puppy1, puppies1.get(0));
        assertEquals(puppy2, puppies1.get(1));
        assertFalse(puppies1.get(0).hasBeenCalled());
        assertFalse(puppies1.get(1).hasBeenCalled());

        assertEquals(puppy1, pc.findPuppy(puppies1, 'b'));
        assertEquals(puppy2, pc.findPuppy(puppies1, 's'));
    }

    @Test
    void testHandleInputCurrentPuppyNotUpdated() {
        assertNull(pc.getCurrentPuppy());
        
        puppies1.add(puppy1);
        puppies1.add(puppy2);
        assertEquals(2, puppies1.size());
        pc.setCurrentPuppy(puppy1);

        assertFalse(pc.handleInput(puppies1, 'd'));

    }

    @Test
    void testHandleInputNoCallOnFirstMatch() {
        assertNull(pc.getCurrentPuppy());
        
        puppies1.add(puppy1);
        puppies1.add(puppy2);
        assertEquals(2, puppies1.size());

        assertTrue(pc.handleInput(puppies1, 'b'));
        assertFalse(pc.handleInput(puppies1, 's'));
        assertTrue(pc.handleInput(puppies1, 'o'));
    }
}

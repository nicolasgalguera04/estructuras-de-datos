import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import lineal.MyArrayList;

class MyArrayListTest {

    MyArrayList<String> List1;

    @BeforeEach
    void setup() {
        List1 = new MyArrayList<>();
    }

    @Test
    void shouldAddElementAndIncreaseSize() {
        // Arrange + Act
        List1.add("A");

        // Assert
        assertEquals(1, List1.size());
        assertEquals("A", List1.get(0));
    }
}
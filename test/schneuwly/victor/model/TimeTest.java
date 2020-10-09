package schneuwly.victor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void creationTest() {
        Time t = new Time(0, 0);
        t = new Time(23, 59);
        t = new Time(23);

        assertThrows(IllegalArgumentException.class, () -> new Time(24));
        assertThrows(IllegalArgumentException.class, () -> new Time(52));
        assertThrows(IllegalArgumentException.class, () -> new Time(0, 60));
        assertThrows(IllegalArgumentException.class, () -> new Time(0, 120));
    }

    @Test
    void getHourTest() {
        assertEquals(0, new Time(0).getHours());
        assertEquals(12, new Time(12).getHours());
        assertEquals(23, new Time(23).getHours());
    }

    @Test
    void getMinutesTest() {
        assertEquals(0, new Time(0, 0).getMinutes());
        assertEquals(12, new Time(1, 12).getMinutes());
        assertEquals(23, new Time(2, 23).getMinutes());
    }

    @Test
    void plusHoursTest() {
        Time t = new Time(5);

        assertThrows(IllegalArgumentException.class, () -> t.plusHours(-5));

        assertEquals(5, t.plusHours(24).getHours());
        assertEquals(5, t.plusHours(0).getHours());
        assertEquals(15, t.plusHours(10).getHours());
        assertEquals(20, t.plusHours(15).getHours());
        assertEquals(0, t.plusHours(19).getHours());

        assertEquals(t.getMinutes(), t.plusHours(24).getMinutes());
        assertEquals(t.getMinutes(), t.plusHours(0).getMinutes());
        assertEquals(t.getMinutes(), t.plusHours(10).getMinutes());
        assertEquals(t.getMinutes(), t.plusHours(15).getMinutes());

    }

    @Test
    void minusHoursTest() {
        Time t = new Time(5);

        assertThrows(IllegalArgumentException.class, () -> t.minusHours(-5));

        assertEquals(5, t.minusHours(24).getHours());
        assertEquals(5, t.minusHours(0).getHours());
        assertEquals(0, t.minusHours(5).getHours());
        assertEquals(19, t.minusHours(10).getHours());
        assertEquals(3, t.minusHours(2).getHours());

        assertEquals(t.getMinutes(), t.minusHours(24).getMinutes());
        assertEquals(t.getMinutes(), t.minusHours(0).getMinutes());
        assertEquals(t.getMinutes(), t.minusHours(10).getMinutes());
        assertEquals(t.getMinutes(), t.minusHours(15).getMinutes());

    }

    @Test
    void plusMinutesTest() {
        Time t = new Time(5, 30);

        assertThrows(IllegalArgumentException.class, () -> t.plusMinutes(-5));

        assertEquals(30, t.plusMinutes(60).getMinutes());
        assertEquals(30, t.plusMinutes(0).getMinutes());
        assertEquals(40, t.plusMinutes(10).getMinutes());
        assertEquals(45, t.plusMinutes(15).getMinutes());

        assertEquals(6, t.plusMinutes(60).getHours());

        assertEquals(7, t.plusMinutes(115).getHours());
        assertEquals(25, t.plusMinutes(115).getMinutes());

    }

    @Test
    void minusMinutesTest() {
        Time t = new Time(5, 30);

        assertThrows(IllegalArgumentException.class, () -> t.minusMinutes(-5));

        assertEquals(30, t.minusMinutes(60).getMinutes());
        assertEquals(30, t.minusMinutes(0).getMinutes());
        assertEquals(0, t.minusMinutes(30).getMinutes());
        assertEquals(20, t.minusMinutes(10).getMinutes());
        assertEquals(15, t.minusMinutes(15).getMinutes());

        assertEquals(4, t.minusMinutes(60).getHours());
        assertEquals(0, t.minusMinutes(5 * 60).getHours());
        assertEquals(23, t.minusMinutes(6 * 60).getHours());

        assertEquals(3, t.minusMinutes(115).getHours());
        assertEquals(35, t.minusMinutes(115).getMinutes());

    }

    @Test
    void equalsAndHashCode() {
        Time t = new Time(5, 30);
        assertEquals(t, new Time(5, 30));
        assertEquals(t, t.plusMinutes(1100).minusMinutes(1100).plusHours(150).minusHours(150));

        assertEquals(t.hashCode(), new Time(5, 30).hashCode());
        assertEquals(t.hashCode(), t.plusMinutes(1100).minusMinutes(1100).plusHours(150).minusHours(150).hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("05:30", new Time(5, 30).toString());
        assertEquals("23:05", new Time(23, 5).toString());
        assertEquals("23:00", new Time(23).toString());
    }
}
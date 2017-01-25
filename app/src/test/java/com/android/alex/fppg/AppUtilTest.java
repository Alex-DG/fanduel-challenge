package com.android.alex.fppg;

import com.android.alex.fppg.model.Player;
import com.android.alex.fppg.util.AppUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Local unit test on the AppUtil class
 *
 * JUnitTests
 */
public class AppUtilTest {

    @Test
    public void getRandomList_not_null() throws Exception {
        assertNotNull(AppUtil.getRandomList(new ArrayList<Player>(), 2));
        assertNotNull(AppUtil.getRandomList(null, 200));
    }

    @Test
    public void getRandomList_subList() throws Exception {

        List<Player> players = new ArrayList<>();

        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        List<Player> subList0 = AppUtil.getRandomList(players, 0);
        assertEquals(0, subList0.size());

        List<Player> subList1 = AppUtil.getRandomList(players, 1);
        assertEquals(1, subList1.size());

        List<Player> subList2 = AppUtil.getRandomList(players, 2);
        assertEquals(2, subList2.size());

        List<Player> subList3 = AppUtil.getRandomList(players, 3);
        assertEquals(3, subList3.size());

        List<Player> subList4 = AppUtil.getRandomList(players, 4);
        assertEquals(0, subList4.size());
    }
}
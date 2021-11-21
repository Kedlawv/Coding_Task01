package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    @Test
    void getActivePlayersByScoreDescNoGroup() {
        List<Person> input = Arrays.asList(
                new Person("Bilbo", 10, Group.G3, true),
                new Person("Frodo", 5, Group.G1, false),
                new Person("Legolas", 15, Group.G2, true),
                new Person("Gandalf", 50, Group.G1, true)
        );

        List<Person> expected = Arrays.asList(
                new Person("Gandalf", 50, Group.G1, true),
                new Person("Legolas", 15, Group.G2, true),
                new Person("Bilbo", 10, Group.G3, true)
        );

        List<Person> actual = Task.getActivePlayersByScoreDesc(input);

        assertEquals(expected, actual);
    }

    @Test
    void getActivePlayersByScoreDescSameGroup() {
        List<Person> input = Arrays.asList(
                new Person("Bilbo", 10, Group.G3, false),
                new Person("Frodo", 5, Group.G1, true),
                new Person("Legolas", 15, Group.G2, true),
                new Person("Gandalf", 50, Group.G1, true)
        );

        List<Person> expected = Arrays.asList(
                new Person("Gandalf", 50, Group.G1, true),
                new Person("Frodo", 5, Group.G1, true)
        );

        List<Person> actual = Task.getActivePlayersByScoreDesc(input, Group.G1);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupWithHighestScore() {
        List<Person> input = Arrays.asList(
                new Person("Bilbo", 10, Group.G3, false),
                new Person("Frodo", 5, Group.G1, true),
                new Person("Legolas", 15, Group.G2, true),
                new Person("Gandalf", 50, Group.G2, true),
                new Person("Aragorn", 70, Group.G1, false),
                new Person("Gimli", 20, Group.G3, true)
        );

        Group expected = Group.G1;

        Group actual = Task.getGroupWithHighestScore(input);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupWithHighestScoreIfTwoEqualThanGroupWithLessActivePlayers() {
        List<Person> input = Arrays.asList(
                new Person("Bilbo", 10, Group.G3, false),
                new Person("Frodo", 5, Group.G1, true),
                new Person("Legolas", 25, Group.G2, true),
                new Person("Gandalf", 50, Group.G2, false),
                new Person("Aragorn", 70, Group.G1, true),
                new Person("Gimli", 20, Group.G3, true)
        );

        Group expected = Group.G2;

        Group actual = Task.getGroupWithHighestScore(input);

        assertEquals(expected, actual);
    }

    @Test
    void getGroupWithHighestScoreIfAllEqualThanReturnRandomGroup() {
        List<Person> input = Arrays.asList(
                new Person("Bilbo", 10, Group.G3, false),
                new Person("Frodo", 10, Group.G1, true),
                new Person("Legolas", 10, Group.G2, true),
                new Person("Gandalf", 10, Group.G2, false),
                new Person("Aragorn", 10, Group.G1, true),
                new Person("Gimli", 10, Group.G3, true)
        );

        List<Group> expected = Arrays.asList(Group.values());

        Group actual = Task.getGroupWithHighestScore(input);
        System.out.println(actual);

        assertTrue(expected.contains(actual));
    }

    @Test
    void printPoints() {
        List<Person> input = Arrays.asList(
                new Person("Bilbo", 10, Group.G3, false),
                new Person("Frodo", 20, Group.G1, true),
                new Person("Legolas", 5, Group.G2, true),
                new Person("Gandalf", 40, Group.G2, false),
                new Person("Aragorn", 30, Group.G1, true),
                new Person("Gimli", 15, Group.G3, true)
        );

        Task.printPoints(input);
    }
}
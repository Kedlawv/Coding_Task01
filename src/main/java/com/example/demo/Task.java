package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Task {

    /**
     * Testy na pusta liste i nulle pominolem
     */

  /*
  Zwróć listę aktywnych graczy posortowanych po ich wyniku malejąco
   */
    public static List<Person> getActivePlayersByScoreDesc(List<Person> people) {

        return people.stream()
                .filter(Person::isActive)
                .sorted(Comparator.comparing(Person::getScore).reversed())
                .collect(Collectors.toList());
    }

  /*
  Zwróć listę aktywnych graczy z danej grupy posortowanych po ich wyniku malejąco
   */

    public static List<Person> getActivePlayersByScoreDesc(List<Person> people, Group group) {
        return people.stream()
                .filter(person -> person.getTeam() == group && person.isActive())
                .sorted(Comparator.comparing(Person::getScore).reversed())
                .collect(Collectors.toList());
    }

    /*
    Zwróć grupę, która posiada najwyższy wynik. Jeżeli wynik wielu grup jest taki sam, zwróć tę, która ma mniejszą liczbę aktywnych członków.
    Jeżeli ta liczba jest również równa, zwróć którąkolwiek z nich.
     */
    public static Group getGroupWithHighestScore(List<Person> people) {

        List<Person> playersGroupOne = getTeam(people, Group.G1);
        List<Person> playersGroupTwo = getTeam(people, Group.G2);
        List<Person> playersGroupThree = getTeam(people, Group.G3);

        Integer groupOneScore = getTotalScore(playersGroupOne);
        Integer groupTwoScore = getTotalScore(playersGroupTwo);
        Integer groupThreeScore = getTotalScore(playersGroupThree);

        if (groupOneScore > groupTwoScore && groupOneScore > groupThreeScore) {
            return Group.G1;
        }
        if (groupTwoScore > groupOneScore && groupTwoScore > groupThreeScore) {
            return Group.G2;
        }
        if (groupThreeScore > groupOneScore && groupThreeScore > groupTwoScore) {
            return Group.G3;
        }

        if (groupOneScore.equals(groupTwoScore)) {
            if (groupTwoScore.equals(groupThreeScore)) {
                int pick = new Random().nextInt(Group.values().length);
                return Group.values()[pick];
            } else {
                return getActivePlayersCount(playersGroupOne)
                        > getActivePlayersCount(playersGroupTwo) ? Group.G2 : Group.G1;
            }
        } else {
            return getActivePlayersCount(playersGroupTwo)
                    > getActivePlayersCount(playersGroupThree) ? Group.G3 : Group.G2;
        }
    }

    private static List<Person> getTeam(List<Person> people, Group g1) {
        return people.stream().filter(p -> p.getTeam() == g1).collect(Collectors.toList());
    }

    private static long getActivePlayersCount(List<Person> playersGroupOne) {
        return playersGroupOne.stream().filter(Person::isActive).count();
    }

    private static long getInactivePlayersCount(List<Person> playersGroupOne) {
        return playersGroupOne.stream().filter(p -> !p.isActive()).count();
    }

    private static Integer getTotalScore(List<Person> people) {
        return people.stream()
                .reduce(0, (partialScore, person) -> partialScore + person.getScore(), Integer::sum);
    }

  /*
  Zwróć listę wyników posortowaną malejąco na podstawie ilości punktów per zespół.
  Pojedynczy String powinien mieć format: "NazwaGrupy CałkowityWynik (lista_aktywnych_członków) [ilość nieaktywnych członków]"
   */

    public static List<String> printPoints(List<Person> people) {
        StringBuilder sb = new StringBuilder();
        List<String> pointsList = new ArrayList<>();

        List<List<Person>> listOfGroups = new ArrayList<>();

        List<Person> playersGroupOne = getTeam(people, Group.G1);
        List<Person> playersGroupTwo = getTeam(people, Group.G2);
        List<Person> playersGroupThree = getTeam(people, Group.G3);

        listOfGroups.add(playersGroupOne);
        listOfGroups.add(playersGroupTwo);
        listOfGroups.add(playersGroupThree);

        for (List<Person> groupOfPeople : listOfGroups) {
            sb.append("Group: ");
            sb.append(groupOfPeople.get(0).getTeam()).append(" ");
            sb.append("Score: ");
            sb.append(getTotalScore(groupOfPeople)).append("\n\n");
            sb.append("Active players:\n");
            getActivePlayersByScoreDesc(groupOfPeople)
                    .stream().forEach(p -> sb.append(p.getName()).append("\n"));
            sb.append("\nInactive Players Count: ").append(getInactivePlayersCount(groupOfPeople)).append("\n");
            sb.append("___________________________________________________________________________\n");

            pointsList.add(sb.toString());
            sb.setLength(0);
        }

        return pointsList;
    }
}

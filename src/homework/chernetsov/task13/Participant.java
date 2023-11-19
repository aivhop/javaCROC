package homework.chernetsov.task13;

import java.util.Objects;

public record Participant(String firstname, String surname, int id) {
    private static int nextId = 0;
    public Participant(String firstname, String surname) {
        this(firstname,surname,nextId);
        nextId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ": " + firstname + " " + surname;
    }
}

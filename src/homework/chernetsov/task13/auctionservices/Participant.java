package homework.chernetsov.task13.auctionservices;

import java.util.Objects;

public record Participant(String name, int id) {
    private static int nextId = 0;

    public Participant(String name) {
        this(name, nextId);
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Sorry, the name can't be empty");
        }
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
        return id + ": " + name;
    }
}

package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.events.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A cell that has a number and a set of possibilities. Even cells can contain only even numbers, and odd cells can
 * contain only odd numbers. A cell may have a number of observers, and notifies events to the observers.
 */
public class Cell extends Subject
{
    enum Type {EVEN, ODD}

    // TODO: add private member variables for Board
    private Optional<Integer> num;
    private Set<Integer> posSet;
    private Type type;
    private Set<Group> groupSet;

    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     *
     * @param type EVEN or ODD
     */
    public Cell(@NotNull Type type)
    {
        // TODO: implement this done
        this.num = Optional.empty();
        this.posSet = new HashSet<Integer>();
        this.type = type;
        this.groupSet = new HashSet<Group>();

        if (this.type == Type.EVEN) // add the possibility for even type cell
        {
            for(int i = 2; i <= 8; i += 2)
            {
                posSet.add(i); // add 2, 4, 6, 8
            }
        }

        else if (this.type == Type.ODD) // add the possibility for odd type cell
        {
            for(int i = 1; i <= 9; i += 2)
            {
                posSet.add(i); // add 1, 3, 5, 7, 9
            }
        }
    }

    /**
     * Returns the type of this cell.
     *
     * @return the type
     */
    @NotNull
    public Type getType()
    {
        // TODO: implement this done
        return type;
    }

    /**
     * Returns the number of this cell.
     *
     * @return the number; Optional.empty() if no number assigned
     */
    @NotNull
    public Optional<Integer> getNumber()
    {
        // TODO: implement this done
        return num;
    }

    /**
     * Sets a number of this cell and notifies a SetNumberEvent, provided that the cell has previously no number
     * and the given number to be set is in the set of possibilities.
     *
     * @param number the number
     */
    public void setNumber(int number)
    {
        // TODO: implement this done
        if (this.num.isEmpty() && posSet.contains(number))
        {
            this.num = Optional.of(number);
            notifyObservers(new SetNumberEvent(this.num.get()));
        }
    }

    /**
     * Removes the number of this cell and notifies an UnsetNumberEvent, provided that the cell has a number.
     */
    public void unsetNumber()
    {
        // TODO: implement this done
        if (this.num.isPresent())
        {
            int temp = this.num.get();
            this.num = Optional.empty();
            notifyObservers(new UnsetNumberEvent(temp));
        }
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group
     */
    public void addGroup(@NotNull Group group)
    {
        // TODO: implement this done
        groupSet.add(group);
        addObserver(group);
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    @NotNull
    public Boolean containsPossibility(int n)
    {
        // TODO: implement this done
        return posSet.contains(n);
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    @NotNull
    public Boolean emptyPossibility()
    {
        // TODO: implement this done
        return posSet.isEmpty();
    }

    /**
     * Adds the possibility of a given number, if possible, and notifies an EnabledEvent if the set of possibilities,
     * previously empty, becomes non-empty. Even (resp., odd) cells have only even (resp., odd) possibilities. Also,
     * if this number is already used by another cell in the same group with this cell, the number cannot be added to
     * the set of possibilities.
     *
     * @param number the number
     */
    public void addPossibility(int number)
    {
        // TODO: implement this done
        if (this.type == Type.EVEN && (number % 2 == 0)) // when cell is even
        {
            for(Group group : groupSet) // check if the number is available in group
            {
                if (!group.isAvailable(number)) return;
            }

            posSet.add(number); // add the number

            if (posSet.size() == 1) // if posSet was previously empty, and became non-empty
            {
                notifyObservers(new EnabledEvent());
            }
        }

        else if (this.type == Type.ODD && (number % 2 == 1)) // when cell is odd
        {
            for(Group group : groupSet) // check if the number is available in group
            {
                if (!group.isAvailable(number)) return;
            }

            posSet.add(number); // add the number

            if (posSet.size() == 1) // if posSet was previously empty, and became non-empty
            {
                notifyObservers(new EnabledEvent());
            }
        }
    }

    /**
     * Removes the possibility of a given number. Notifies a DisabledEvent if the set of possibilities becomes empty.
     * Note that even (resp., odd) cells have only even (resp., odd) possibilities.
     *
     * @param number the number
     */
    public void removePossibility(int number)
    {
        // TODO: implement this done
        if (this.type == Type.EVEN && (number % 2 == 0)) // when cell is even
        {
            posSet.remove(number); // remove the number

            if (posSet.size() == 0) // if posSet became empty
            {
                notifyObservers(new DisabledEvent());
            }
        }

        else if (this.type == Type.ODD && (number % 2 == 1)) // when cell is odd
        {
            posSet.remove(number); // remove the number

            if (posSet.size() == 0) // if posSet became empty
            {
                notifyObservers(new DisabledEvent());
            }
        }
    }
}

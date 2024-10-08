package linkedLists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlattenNestedIterator implements Iterator<Integer> {


    List<Integer> flattenedList;
    int index = 0;

    public FlattenNestedIterator(List<NestedInteger> nestedList) {
        flattenedList = new ArrayList<>();
        populateList(nestedList);
    }

    @Override
    public Integer next() {
        Integer temp = flattenedList.get(index);
        index++;
        return temp;
    }

    @Override
    public boolean hasNext() {
        return flattenedList.size() > index;
    }

    private void populateList(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.isEmpty()) return;
        for (NestedInteger nestedInteger : nestedList) {
            populateList(nestedInteger.getList());
            if (nestedInteger.getInteger() != null) flattenedList.add(nestedInteger.getInteger());
        }
    }
}

interface NestedInteger {

    public boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}



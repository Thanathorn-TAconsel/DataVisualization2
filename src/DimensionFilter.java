import java.util.ArrayList;
import java.util.HashMap;

public class DimensionFilter {
    HashMap<Integer, ArrayList<String>> filter = new HashMap<>();
    DimensionFilter() {

    }
    public void clear() {
        filter.clear();
    }
    public void addException(int dimensionIndex,String value) {
        ArrayList<String> v;
        if (filter.containsKey(dimensionIndex)) {
            v = filter.get(dimensionIndex);
        } else {
            v = new ArrayList<>();
            filter.put(dimensionIndex,v);
        }
        v.add(value);
    }
    public boolean inExcetion(String[] data) {
        for (Integer key: filter.keySet()) {
            ArrayList<String> ava = filter.get(key);
            if (ava.contains(data[key])) {
                return true;
            }
        }
        return false;
    }
    public boolean inExcetion(int dimensionIndex,String value) {
        ArrayList<String> v;
        if (filter.containsKey(dimensionIndex)) {
            v = filter.get(dimensionIndex);
            if (v.contains(value)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

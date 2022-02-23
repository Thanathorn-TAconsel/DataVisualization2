import java.util.HashMap;

public class MeasureFilter {
    HashMap<Integer,HashMap<Integer,Range>> filter = new HashMap<>();
    MeasureFilter() {

    }
    public void clear() {
        filter.clear();
    }
    public void addException(int measureIndex,int operation,Range range) {
        HashMap<Integer,Range> sel;
        if (filter.containsKey(measureIndex)) {
            sel = filter.get(measureIndex);
        } else {
            sel = new HashMap<>();
            filter.put(measureIndex,sel);
        }
        sel.put(operation,range);
    }
    public boolean inExcetion(int measureIndex,int operation,double value) {
        HashMap<Integer,Range> sel;
        if (filter.containsKey(measureIndex)) {
            sel = filter.get(measureIndex);
            if (sel.containsKey(operation)) {
                Range selRange = sel.get(operation);
                if (selRange.inRange(value)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isValid(int measureIndex,int operation,double value) {
        return !inExcetion(measureIndex,operation,value);
    }
}
class Range {
    double min,max;
    Range(double min,double max) {
        this.min = min;
        this.max = max;
    }
    public boolean inRange(double value) {
        if (value >= min && value <= max) {
            return true;
        }
        return false;
    }
}
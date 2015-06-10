package andycheung.baseproject.pojo;

/**
 * Created by Andy Cheung on 4/5/15.
 */
public class ListData<T> {
    public int typeId;
    public T data;

    public ListData(int type, T data) {
        super();
        this.typeId = type;
        this.data = data;
    }

    public String toString() {
        return "ListData{typeId:" + typeId + ";data:" + data + "}";
    }
}

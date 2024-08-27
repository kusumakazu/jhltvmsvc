package id.co.kusumakazu.domain.response.speaker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StylesItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    @JsonProperty("type")
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "StylesItem{" + "name = '" + name + '\'' + ",id = '" + id + '\'' + ",type = '" + type + '\'' + "}";
    }
}

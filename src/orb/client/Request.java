package orb.client;

public class Request {

    private Integer codOp;
    private Integer objId;
    private String key;
    private Integer value;
    private Integer numParams = 0;

    public Request(Integer codOp) {
        this.codOp = codOp;
        this.numParams = 1;
    }

    public Request(Integer codOp, Integer objId, String key) {
        this.codOp = codOp;
        this.objId = objId;
        this.key = key;
        this.numParams = 3;
    }

    public Request(Integer codOp, Integer objId, String key, Integer value) {
        this.codOp = codOp;
        this.objId = objId;
        this.key = key;
        this.value = value;
        this.numParams = 4;
    }

    public Integer getCodOp() {
        return codOp;
    }

    public Integer getObjId() {
        return objId;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public String toRequestString() {
        switch (this.numParams) {
            case 1:
                return String.valueOf(this.codOp);
            case 3:
                return String.valueOf(this.codOp).concat("-")
                        .concat(String.valueOf(this.objId)).concat("-")
                        .concat(this.key).concat("-");
            case 4:
                return String.valueOf(this.codOp).concat("-")
                        .concat(String.valueOf(this.objId)).concat("-")
                        .concat(this.key).concat("-")
                        .concat(String.valueOf(this.value));
            default:
                return null;
        }
    }
}
